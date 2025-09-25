package com.example.hasmilktea;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000; // 5 giây
    private ProgressBar logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logoImage = findViewById(R.id.progressBar);

        // Hiệu ứng xoay vòng
        RotateAnimation rotate = new RotateAnimation(
                0, 360, // từ 0 đến 360 độ
                Animation.RELATIVE_TO_SELF, 0.5f, // xoay quanh tâm X
                Animation.RELATIVE_TO_SELF, 0.5f  // xoay quanh tâm Y
        );
        rotate.setDuration(3000); // mỗi vòng xoay 2 giây
        rotate.setRepeatCount(Animation.INFINITE); // xoay liên tục
        logoImage.startAnimation(rotate);

        // Sau 5 giây thì mờ dần rồi chuyển sang LoginActivity
        new Handler().postDelayed(() -> {
            // Hiệu ứng mờ dần
            AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
            fadeOut.setDuration(2000); // 1 giây
            logoImage.startAnimation(fadeOut);

            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Chuyển sang LoginActivity
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // đóng SplashActivity
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });

        }, SPLASH_DURATION);
    }
}
