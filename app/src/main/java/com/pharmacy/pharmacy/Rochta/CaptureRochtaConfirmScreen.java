package com.pharmacy.pharmacy.Rochta;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ImageView;

import com.pharmacy.pharmacy.R;

/**
 * Created by Mohamed Hesham on 2017-05-08.
 */

public class CaptureRochtaConfirmScreen extends Activity {
    ImageView cameraimage;
    EditText notes;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_rochta_confirm);
       cameraimage=(ImageView)findViewById(R.id.cameraimage);
        Bitmap bitmap = getIntent().getParcelableExtra("kk");
        cameraimage.setImageBitmap(bitmap);
        notes=(EditText)findViewById(R.id.notes);
    }
}
