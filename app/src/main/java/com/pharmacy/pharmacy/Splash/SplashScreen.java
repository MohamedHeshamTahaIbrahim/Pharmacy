package com.pharmacy.pharmacy.Splash;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.pharmacy.pharmacy.AppController;
import com.pharmacy.pharmacy.Login.LoginScreen;
import com.pharmacy.pharmacy.R;

/**
 * Created by Mohamed Hesham on 2017-05-05.
 */

public class SplashScreen extends Activity {
    int SPLASH_DISPLAY_LENGHT= 1000;
    Intent intent;
    Typeface faceBold, faceRegular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        faceBold = Typeface.createFromAsset(getAssets(), "fonts/Big-Vesta-Arabic-Bold.ttf");
        faceRegular = Typeface.createFromAsset(getAssets(), "fonts/Big-Vesta-Arabic-Regular.ttf");
        AppController.getInstance().bold=faceBold;
        AppController.getInstance().regular=faceRegular;
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
