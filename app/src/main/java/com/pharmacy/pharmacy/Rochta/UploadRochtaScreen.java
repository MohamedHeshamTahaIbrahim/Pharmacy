package com.pharmacy.pharmacy.Rochta;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.pharmacy.pharmacy.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Mohamed Hesham on 2017-05-09.
 */

public class UploadRochtaScreen extends Activity implements View.OnClickListener {
 ImageView galleryimage/*,addPhotoIcon*/;
    Button confirm;
    String base64 = "";
    Bitmap photo,useBitmap;
    private static final int PICK_FROM_GALLERY = 2;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_rochta);
       galleryimage=(ImageView)findViewById(R.id.galleryimage);
       // addPhotoIcon=(ImageView)findViewById(R.id.addPhotoIcon);
        confirm=(Button)findViewById(R.id.confirm);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 150);
        try {
            intent.putExtra("return-data", true);
            startActivityForResult(Intent.createChooser(intent,
                    "Complete action using"), PICK_FROM_GALLERY);
        } catch (ActivityNotFoundException e) {
            // Do nothing for now
        }
       // addPhotoIcon.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           /* case R.id.addPhotoIcon:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 150);
                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_GALLERY);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
                break;*/
            case R.id.confirm:
                Toast.makeText(UploadRochtaScreen.this, "جاري توصيل الروشتة للصيديليات",
                        Toast.LENGTH_LONG).show();
                /* Intent intent1=new Intent(UploadRochtaScreen.this,UploadRochtaConfirmScreen.class);
                //  intent.putExtra("BitmapImage",useBitmap);
                intent1.putExtra("tt",photo);
                startActivity(intent1);*/
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_GALLERY) {
            try {
                Bundle extras2 = data.getExtras();
                if (extras2 != null) {
                    photo = extras2.getParcelable("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 80, stream);
                    byte[] byteArray = stream.toByteArray();
                    base64 = Base64.encodeToString(byteArray,
                            Base64.NO_WRAP);
                    galleryimage.setVisibility(View.VISIBLE);

//                    userPhoto.setMinimumWidth(personalImage.getWidth());
//                    userPhoto.setMinimumHeight(personalImage.getHeight());
//                    userPhoto.setMaxWidth(personalImage.getWidth());
//                    userPhoto.setMaxHeight(personalImage.getHeight());
                    galleryimage.setImageBitmap(photo);
                }
                else{
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = UploadRochtaScreen.this.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    photo = BitmapFactory.decodeFile(picturePath);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 80, stream);
                    byte[] byteArray = stream.toByteArray();
                    base64 = Base64.encodeToString(byteArray,
                            Base64.NO_WRAP);

                    galleryimage.setVisibility(View.VISIBLE);

//                    userPhoto.setMinimumWidth(personalImage.getWidth());
//                    userPhoto.setMinimumHeight(personalImage.getHeight());
//                    userPhoto.setMaxWidth(personalImage.getWidth());
//                    userPhoto.setMaxHeight(personalImage.getHeight());
                    galleryimage.setImageBitmap(photo);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
    }
}
