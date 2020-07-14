package cn.xzbenben.kotlintest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.annotation.Nullable;

public class AnimActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ht);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim0(findViewById(R.id.iv_ht));
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim(findViewById(R.id.iv_ht));
            }
        });

    }

    public void anim0(View v) {
        float px = v.getWidth() * 0.265f;
        float py = v.getWidth() * 0.72f;
        RotateAnimation animation = new RotateAnimation(0f, 20f, px, py);
        animation.setDuration(150);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(5);
        v.startAnimation(animation);
    }

    public void anim(View v) {
        float px = v.getWidth() / 2;
        float py = v.getWidth() / 2;
        RotateAnimation animation = new RotateAnimation(0f, 20f, px, py);
        animation.setDuration(150);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(5);
        v.startAnimation(animation);
    }

}
