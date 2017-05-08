package com.pharmacy.pharmacy.Rochta;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.pharmacy.pharmacy.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Mohamed Hesham on 2017-05-08.
 */

public class CaptureRochtaScreen extends Activity implements View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 1;

    String base64 = "";
    Bitmap photo,useBitmap;
    ImageView cameraimage,addPhotoIcon;
    Button confirm;
    private File dir, destImage,f;
    private String cameraFile = null;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_rochta);

        cameraimage=(ImageView)findViewById(R.id.cameraimage);
        addPhotoIcon=(ImageView)findViewById(R.id.addPhotoIcon);
        confirm=(Button)findViewById(R.id.confirm);
        addPhotoIcon.setOnClickListener(this);
        confirm.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

         try {


                Bundle extras = data.getExtras();
                if (extras != null) {
                    photo = extras.getParcelable("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 80, stream);

                    byte[] byteArray = stream.toByteArray();
                    base64 = Base64.encodeToString(byteArray,
                            Base64.DEFAULT);



                    cameraimage.setVisibility(View.VISIBLE);

                    cameraimage.setImageBitmap(photo);
//                    Uri tempUri = Uri.parse(MediaStore.Images.Media.insertImage(Personal_Information.this.getContentResolver(), photo, "Title", null));
//                    RotateImage(getRealPathFromURI(tempUri),photo);

                }
//                else if(CmsInter.CAMERA_REQUEST){
//
//                }
            } catch (Exception e) {
                // TODO: handle exception
            }

        /*super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_FROM_CAMERA:
                if (resultCode==RESULT_OK) {
                    if(f==null){
                        if(cameraFile!=null)
                            f = new File(cameraFile);
                        else
                            Log.e("check", "camera file object null line no 279");
                    }else
                        Log.e("check", f.getAbsolutePath());
                     useBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                    // now use this bitmap wherever you want
                    cameraimage.setVisibility(View.VISIBLE);
                    cameraimage.setImageBitmap(useBitmap);

                }
                break;
        }*/
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addPhotoIcon:
               Intent cameraIntent = new Intent(
                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
                /*dir = new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath(), "MyApp");
                if (!dir.isDirectory())
                    dir.mkdir();

                destImage = new File(dir, new Date().getTime() + ".png");
                cameraFile = destImage.getAbsolutePath();
                try{
                    if(!destImage.createNewFile())
                        Log.e("check", "unable to create empty file");

                }catch(IOException ex){
                    ex.printStackTrace();
                }

                f = new File(destImage.getAbsolutePath());
                Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destImage));
                startActivityForResult(i,PICK_FROM_CAMERA);*/
                break;
            case  R.id.confirm:
                Intent intent=new Intent(CaptureRochtaScreen.this,CaptureRochtaConfirmScreen.class);
                //  intent.putExtra("BitmapImage",useBitmap);
                intent.putExtra("kk",photo);
                startActivity(intent);

                break;

        }
    }
}
