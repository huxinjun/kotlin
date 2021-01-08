package cn.xzbenben.ring;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 圆环进度
 * Created by xinjun on 1/7/21 4:32 PM
 */
public class ProgressRing extends View {
    int ringWidth = 60;
    Paint paint;
    Shader sweepGradient;
    private static int[] colorsForSingle = new int[]{
            Color.parseColor("#14bcbd"),
            Color.parseColor("#00cca1"),
            Color.parseColor("#00de9f"),
            Color.parseColor("#00f08d"),
            Color.parseColor("#00ff84"),
    };
    private static int[] colorsForDouble = new int[]{
            Color.parseColor("#00ff84"),
            Color.parseColor("#7bf868"),
            Color.parseColor("#ffee5d"),
            Color.parseColor("#84d58a"),
            Color.parseColor("#14bcbd"),
    };

    public ProgressRing(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    public void init() {
        paint = new Paint();
        paint.setAntiAlias(true); //消除锯齿
        paint.setStrokeWidth(ringWidth);
        paint.setStyle(Paint.Style.STROKE);  //绘制空心圆或 空心矩形,只显示边缘的线，不显示内部
    }


    private int progress = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        long s1 = System.currentTimeMillis();
        int loop = progress / 360;
        int remain = progress % 360;

        for (int i = 1; i <= loop; i++) {
            Log.e("onDraw", "onDraw.draw ring:" + i);
            drawProgressRing(canvas, i % 2 != 0 ? colorsForSingle : colorsForDouble, 359, i == 1, false);
        }
        long s2 = System.currentTimeMillis();
        if (remain > 0) {
            Log.e("onDraw", "onDraw.draw remain");
            drawProgressRing(canvas, (loop) % 2 == 0 ? colorsForSingle : colorsForDouble, remain, loop == 0, false);
        }
        long s3 = System.currentTimeMillis();
        Log.e("time", "draw loop=:" + (s2 - s1));
        Log.e("time", "draw remain=" + (s3 - s2));
        Log.e("time", "------------------------------");
    }


    public void drawFullRing(){

    }

    public void drawProgressRing(final Canvas canvas, int[] colors, final int angle, boolean first, boolean shadow) {

        long s1 = System.currentTimeMillis();


        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas bitmapCanvas = new Canvas(bitmap);

        long s2 = System.currentTimeMillis();

        final RectF rect = new RectF(ringWidth / 2f, ringWidth / 2f, getWidth() - ringWidth / 2f, getHeight() - ringWidth / 2f);
        //设置渐变
        sweepGradient = new SweepGradient(getWidth() / 2f, getHeight() / 2f, colors, null);
        //按照圆心旋转
        Matrix matrix = new Matrix();
        matrix.setRotate(-90, getWidth() / 2f, getHeight() / 2f);
        sweepGradient.setLocalMatrix(matrix);
        paint.setShader(sweepGradient);

        if (shadow) {
            RadialGradient mRadialGradient = new RadialGradient(getWidth() / 2f,
                    getHeight() / 2f,
                    getWidth() / 2f + 50,
                    new int[]{Color.BLACK, Color.WHITE},
                    null,
                    Shader.TileMode.MIRROR);
            Paint paintShadow = new Paint();
            paintShadow.setShader(mRadialGradient);
            RectF rectShadow = new RectF(-25, -25, getWidth() + 25, getHeight() + 25);
            canvas.drawArc(rectShadow, -90, angle, true, paintShadow);
//            canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, getWidth() / 2f + 20, paintShadow);
        }


        long s2_2 = System.currentTimeMillis();

//        canvas.drawArc(rect, -90, angle, false, paint);

        long s2_3 = System.currentTimeMillis();
        bitmapCanvas.drawArc(rect, -90, angle, false, paint);

        long s3 = System.currentTimeMillis();

        PointF pointHead = calcEdgePoint(-90);
        PointF pointTail = calcEdgePoint(-90 + angle - 1);
        int headColor = bitmap.getPixel((int) pointHead.x, (int) pointHead.y);
        int tailColor = bitmap.getPixel((int) pointTail.x, (int) pointTail.y);


        if (first)
            drawBall(bitmapCanvas, -90, headColor);
        drawBall(bitmapCanvas, -90 + angle, tailColor);

        long s4 = System.currentTimeMillis();

        canvas.drawBitmap(bitmap, 0, 0, new Paint());
        long s5 = System.currentTimeMillis();

        Log.e("time", "create bitmap=" + (s2 - s1));
        Log.e("time", "draw path,shader=" + (s2_2 - s2));
        Log.e("time", "draw path,on canvas=" + (s2_3 - s2_2));
        Log.e("time", "draw path,on bitmap canvas=" + (s3 - s2_3));
        Log.e("time", "draw ball=" + (s4 - s3));
        Log.e("time", "draw bitmap=" + (s5 - s4));


//        Paint painttest1 = new Paint();
//        painttest1.setColor(headColor);
//        canvas.drawCircle(20, 20, 20, painttest1);
//        painttest1.setColor(tailColor);
//        canvas.drawCircle(20, 60, 20, painttest1);
//
//        painttest1.setColor(Color.BLACK);
//        painttest1.setStrokeWidth(10);
//        painttest1.setStyle(Paint.Style.STROKE);
//        canvas.drawPoint((int) pointHead.x, (int) pointHead.y, painttest1);
//        canvas.drawPoint((int) pointTail.x, (int) pointTail.y, painttest1);
    }

    public void drawBall(Canvas canvas, int angle, int color) {
        /**
         * 圆点坐标：(x0,y0)
         * 半径：r
         * 角度：a0
         *
         * 则圆上任一点为：（x1,y1）
         * x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
         * y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )
         */

        PointF pointF = calcEdgePoint(angle);
        Log.e("drawBall", "drawBall.x=" + pointF.x + ",y=" + pointF.y + ",angle=" + angle);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true); //消除锯齿
        canvas.drawCircle((int) pointF.x, (int) pointF.y, ringWidth / 2f, paint);
    }


    private PointF calcEdgePoint(int angle) {
        float x0 = getWidth() / 2f;
        float y0 = getHeight() / 2f;
        float r = getWidth() / 2f - ringWidth / 2f;
        double x1 = x0 + r * Math.cos(Math.toRadians(angle));
        double y1 = y0 + r * Math.sin(Math.toRadians(angle));
        return new PointF((float) x1, (float) y1);
    }


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    /**
     * Canvas.drawArc太耗时,不建议使用动画
     */
    ValueAnimator valueAnimator;

    @Deprecated
    public void setProgressWithAnim(int progress) {

        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }

        valueAnimator = ValueAnimator.ofInt(this.progress, progress);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ProgressRing.this.progress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }
}