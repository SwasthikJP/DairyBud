package com.example.dairymanagement;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static  int SPLASH_SCREEN = 1000;
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView logo, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


//        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
//        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image = findViewById(R.id.imageView2);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                 FirebaseAuth mAuth=FirebaseAuth.getInstance();

                FirebaseUser currentUser = mAuth.getCurrentUser();

                if(currentUser != null){

                    Intent intent = new Intent(MainActivity.this,buy.class);
                    startActivity(intent);
                    finish();

                }else {
                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_SCREEN);



    }
}