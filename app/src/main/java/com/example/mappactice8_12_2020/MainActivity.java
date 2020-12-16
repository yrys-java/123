package com.example.mappactice8_12_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private final int TIME = 3200;
    private ImageView logoView;
    private TextView logo, slogan;
    private Animation top, bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeAcitivity.class);
                startActivity(intent);
                finish();
            }
        }, TIME);

        top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        logoView = findViewById(R.id.imageView);
        logo = findViewById(R.id.logo);
        slogan = findViewById(R.id.slogan);

        logoView.setAnimation(top);
        logo.setAnimation(bottom);
        slogan.setAnimation(bottom);

    }


}