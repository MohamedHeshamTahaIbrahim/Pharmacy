package com.pharmacy.pharmacy.Rochta;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ImageView;

import com.pharmacy.pharmacy.R;

/**
 * Created by Mohamed Hesham on 2017-05-09.
 */

public class UploadRochtaConfirmScreen extends Activity {
    ImageView galleryimage;
    EditText notes;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_rochta_confirm);
        galleryimage=(ImageView)findViewById(R.id.galleryimage);
        Bitmap bitmap = getIntent().getParcelableExtra("tt");
        galleryimage.setImageBitmap(bitmap);
        notes=(EditText)findViewById(R.id.notes);
    }


}
