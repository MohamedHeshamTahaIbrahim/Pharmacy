package com.pharmacy.pharmacy.Splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.pharmacy.pharmacy.Login.LoginScreen;
import com.pharmacy.pharmacy.R;

/**
 * Created by Mohamed Hesham on 2017-05-05.
 */

public class SplashScreen extends Activity {
    int SPLASH_DISPLAY_LENGHT= 1000;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
    intent=new Intent(SplashScreen.this, LoginScreen.class);
        startActivity(intent);
       SplashScreen.this.finish();
    }
},SPLASH_DISPLAY_LENGHT);
    }
}
