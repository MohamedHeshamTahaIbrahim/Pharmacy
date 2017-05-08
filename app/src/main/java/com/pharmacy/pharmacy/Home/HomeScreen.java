package com.pharmacy.pharmacy.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.pharmacy.pharmacy.R;
import com.pharmacy.pharmacy.Rochta.CaptureRochtaScreen;
import com.pharmacy.pharmacy.Rochta.UploadRochtaScreen;
import com.pharmacy.pharmacy.Rochta.WriteRochtaScreen;
import com.pharmacy.pharmacy.SquareImageButton;

/**
 * Created by Mohamed Hesham on 2017-05-06.
 */

public class HomeScreen extends Activity  implements View.OnClickListener{
    SquareImageButton cameraBtn,gallerybtn,writeBtn;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cameraBtn=(SquareImageButton) findViewById(R.id.cameraBtn);
        gallerybtn=(SquareImageButton)findViewById(R.id.gallerybtn);
        writeBtn=(SquareImageButton)findViewById(R.id.writeBtn);
        cameraBtn.setOnClickListener(this);
        gallerybtn.setOnClickListener(this);
        writeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cameraBtn:
                Intent intent=new Intent(HomeScreen.this, CaptureRochtaScreen.class);
                startActivity(intent);
                break;
            case R.id.gallerybtn:
                Intent intent1=new Intent(HomeScreen.this, UploadRochtaScreen.class);
                startActivity(intent1);
                break;
            case R.id.writeBtn:
                Intent intent2=new Intent(HomeScreen.this, WriteRochtaScreen.class);
                startActivity(intent2);
                break;
        }
    }
}
