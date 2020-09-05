package info.androidhive.materialdesign.activity;

/**
 * Created by HP on 2/16/2018.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Objects;

import info.androidhive.materialdesign.R;


//import pk.evsoft.delivery_monster_cus.animation.GifView;

public class SplashScreen extends Activity {
    private ImageView imageView;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.gifImageView);




        //gifImageView = (GifImageView)findViewById(R.id.gifImageView);
        //gifImageView.setGifImageResource(R.drawable.splash_screen_animation_transparent);
        int SPLASH_TIME_OUT = 6000;
        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                //Intent i = new Intent(SplashScreen.this, MainActivity.class);
                //startActivity(i);
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if(sharedPreferences.contains("tutorial_display")){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if(sharedPreferences.contains("isLogin")){
                            if (Objects.equals(sharedPreferences.getString("isLogin", ""), "yes")) {
                                Intent intent = new Intent(SplashScreen.this, MainActivity_bottom_view.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }else{
                                //Intent intent = new Intent(SplashScreen.this, NonLogin_Activity.class);
                                Intent intent = new Intent(SplashScreen.this, MainActivity_bottom_view.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }else{
                            //Intent intent = new Intent(SplashScreen.this, NonLogin_Activity.class);
                            Intent intent = new Intent(SplashScreen.this, MainActivity_bottom_view.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                    }
                }else{

                    Intent intent = new Intent(SplashScreen.this, TutorialDotViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
        //gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        //gifImageView.setGifImageResource(R.raw.splash_screen_animation_transparent);
    }

}