package cn.xzbenben.ring;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import cn.xzbenben.kotlintest.R;

/**
 * 圆环进度
 * Created by xinjun on 1/7/21 4:32 PM
 */
public class ProgressRing extends View {

    private float progress;
    private int radius;
    private RectF rect;
    private PointF center;

    private Paint emptyPaint;
    private Paint ringPaint;
    private Paint maskPaint;

    private ValueAnimator progressAnimator;

    private static int[] colors = new int[]{
            Color.parseColor("#4eb2b2"),
            Color.parseColor("#54bcad"),
            Color.parseColor("#62d3a6"),
            Color.parseColor("#6be0a4"),

            Color.parseColor("#72ee9e"),
            Color.parseColor("#69dea2"),
            Color.parseColor("#5dcaa8"),
            Color.parseColor("#4eb2b2"),
    };

    public ProgressRing(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressRing);
        progress = typedArray.getFloat(R.styleable.ProgressRing_progress, 0);
        radius = typedArray.getDimensionPixelSize(R.styleable.ProgressRing_radius, 20);
        typedArray.recycle();
        init();
    }


    public void init() {
        ringPaint = new Paint();
        ringPaint.setAntiAlias(true); //消除锯齿
        ringPaint.setStrokeWidth(radius);
        ringPaint.setStyle(Paint.Style.STROKE);  //绘制空心圆或 空心矩形,只显示边缘的线，不显示内部

        maskPaint = new Paint();
        maskPaint.setAntiAlias(true); //消除锯齿
        maskPaint.setStrokeWidth(radius);
        maskPaint.setStyle(Paint.Style.STROKE);  //绘制空心圆或 空心矩形,只显示边缘的线，不显示内部
        maskPaint.setColor(Color.parseColor("#332a2a2a"));

        emptyPaint = new Paint();
        emptyPaint.setAntiAlias(true); //消除锯齿
        emptyPaint.setStrokeWidth(radius);
        emptyPaint.setStyle(Paint.Style.STROKE);  //绘制空心圆或 空心矩形,只显示边缘的线，不显示内部
        emptyPaint.setColor(Color.parseColor("#F0F1F6"));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rect == null)
            initShader();
        int angle = (int) (progress * 360);

        drawBackground(canvas);

        if (progress == 0) {
            drawEmtpyRing(canvas);
            return;
        }

        long s1 = System.currentTimeMillis();
        int loop = angle / 360;
        int remain = angle % 360;

        if (loop > 0)
            drawFullRing(canvas, remain > 0);

        long s2 = System.currentTimeMillis();

        if (remain > 0) {
            drawProgressRing(canvas, remain);
        }
        long s3 = System.currentTimeMillis();
        Log.e("time", "draw full ring=:" + (s2 - s1));
        Log.e("time", "draw progress ring=" + (s3 - s2));
        Log.e("time", "------------------------------");
    }

    private void initShader() {
        center = new PointF(getWidth() / 2f, getHeight() / 2f);
        rect = new RectF(radius / 2f, radius / 2f, getWidth() - radius / 2f, getHeight() - radius / 2f);
        //设置渐变
        Shader sweepGradient = new SweepGradient(center.x, center.y, colors, null);
        //按照圆心旋转
        Matrix matrix = new Matrix();
        matrix.setRotate(-90, center.x, center.y);
        sweepGradient.setLocalMatrix(matrix);
        ringPaint.setShader(sweepGradient);
    }


    public void drawBackground(Canvas canvas) {
        canvas.drawCircle(center.x, center.y, center.x - radius / 2f, emptyPaint);
    }

    public void drawEmtpyRing(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(colors[0]);
        canvas.drawCircle(center.x, radius / 2f, radius / 2f, paint);
    }

    public void drawFullRing(Canvas canvas, boolean needGrayMask) {
        canvas.drawCircle(center.x, center.y, center.x - radius / 2f, ringPaint);
        if (needGrayMask)
            canvas.drawCircle(center.x, center.y, center.x - radius / 2f, maskPaint);
    }

    public void drawProgressRing(final Canvas canvas, final int angle) {

        long s1 = System.currentTimeMillis();

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas bitmapCanvas = new Canvas(bitmap);

        long s2 = System.currentTimeMillis();
        bitmapCanvas.drawArc(rect, -90, angle, false, ringPaint);

        long s3 = System.currentTimeMillis();

        PointF pointHead = calcEdgePoint(-90);
        PointF pointTail = calcEdgePoint(-90 + angle - 1);
        int headColor = bitmap.getPixel((int) pointHead.x, (int) pointHead.y);
        int tailColor = bitmap.getPixel((int) pointTail.x, (int) pointTail.y);


        drawBall(bitmapCanvas, -90, headColor);
        drawBall(bitmapCanvas, -90 + angle, tailColor);

        long s4 = System.currentTimeMillis();

        canvas.drawBitmap(bitmap, 0, 0, new Paint());
        long s5 = System.currentTimeMillis();

        Log.e("time", "create bitmap=" + (s2 - s1));
        Log.e("time", "draw arc=" + (s3 - s2));
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
        canvas.drawCircle((int) pointF.x, (int) pointF.y, radius / 2f, paint);
    }


    private PointF calcEdgePoint(int angle) {
        float x0 = center.x;
        float y0 = getHeight() / 2f;
        float r = center.y - radius / 2f;
        double x1 = x0 + r * Math.cos(Math.toRadians(angle));
        double y1 = y0 + r * Math.sin(Math.toRadians(angle));
        return new PointF((float) x1, (float) y1);
    }


    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }


    public void setProgress(float progress, int animDuration) {

        if (progressAnimator != null && progressAnimator.isRunning()) {
            progressAnimator.cancel();
        }

        progressAnimator = ValueAnimator.ofFloat(this.progress, progress);
        progressAnimator.setDuration(animDuration);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ProgressRing.this.progress = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        progressAnimator.start();
    }
}