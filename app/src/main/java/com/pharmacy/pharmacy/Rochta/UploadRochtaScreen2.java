package com.pharmacy.pharmacy.Rochta;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pharmacy.pharmacy.Adapter.ImageAdapter;
import com.pharmacy.pharmacy.AppController;
import com.pharmacy.pharmacy.DAOdbCapture;
import com.pharmacy.pharmacy.DAOdbUpload;
import com.pharmacy.pharmacy.MainActivity;
import com.pharmacy.pharmacy.Model.MyImage;
import com.pharmacy.pharmacy.R;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Mohamed Hesham on 2017-05-08.
 */

public class UploadRochtaScreen2 extends Fragment implements View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 1;

    String base64 = "";
    Bitmap photo,useBitmap;
    ImageView cameraimage/*,addPhotoIcon*/;
    Button confirm;
    private File dir, destImage,f;
    private String cameraFile = null;
    private ArrayList<MyImage> images;
    private ImageAdapter imageAdapter;
    private GridView listView;
    private Uri mCapturedImageURI;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private DAOdbUpload daOdb;
    View view;
    ImageButton capture_camera;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_upload_rochta2, container, false);
        cameraimage=(ImageView)view.findViewById(R.id.cameraimage);
        //addPhotoIcon=(ImageView)findViewById(R.id.addPhotoIcon);
        confirm=(Button)view.findViewById(R.id.confirm);
        activeGallery();
        // Construct the data source
        images = new ArrayList();

        // Create the adapter to convert the array to views
        imageAdapter = new ImageAdapter(getActivity(), images);
        // Attach the adapter to a ListView
        listView = (GridView) view.findViewById(R.id.main_list_view);
        imageAdapter.notifyDataSetChanged();
        listView.setAdapter(imageAdapter);
        capture_camera=(ImageButton)view.findViewById(R.id.capture_camera) ;

        capture_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeGallery();
            }
        });
     addItemClickListener(listView);
        initDB();
        //addPhotoIcon.setOnClickListener(this);
        confirm.setOnClickListener(this);
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_LOAD_IMAGE &&
                resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            MyImage image = new MyImage();
            image.setTitle("Test");
            image.setDescription("test choose a photo from gallery and add it to " + "list view");
            image.setDatetime(System.currentTimeMillis());
            image.setPath(picturePath);
            //                    images.add(image);//notifyDataSetChanged does not work well sometimes
            imageAdapter.add(image);
            daOdb.addImage(image);
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
           /* case R.id.addPhotoIcon:

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
            /*    break;
            */
            case  R.id.confirm:
                /*Intent intent=new Intent(CaptureRochtaScreen.this,CaptureRochtaConfirmScreen.class);
                //  intent.putExtra("BitmapImage",useBitmap);
                intent.putExtra("kk",photo);
                startActivity(intent);*/
                Toast.makeText(getActivity(), "جاري توصيل الروشتة للصيديليات",
                        Toast.LENGTH_LONG).show();
                break;

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(AppController.getInstance().CurrentTag.equalsIgnoreCase("home")) {
            MainActivity.navigationView.getMenu().getItem(0).setChecked(true);
            MainActivity.navigationView.getMenu().getItem(1).setChecked(false);
            MainActivity.navigationView.getMenu().getItem(2).setChecked(false);

        }
        AppController.activityResumed();
    }
    @Override
    public void onPause() {
        super.onPause();
        AppController.activityPaused();
    }
    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }
    private void initDB() {
        daOdb = new DAOdbUpload(getActivity());
        //        add images from database to images ArrayList
        for (MyImage mi : daOdb.getImages()) {
            images.add(mi);
        }
    }
    private void addItemClickListener(final GridView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MyImage image = (MyImage) listView.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(),DisplayImageUpload.class);
                intent.putExtra("IMAGE", (new Gson()).toJson(image));
                startActivity(intent);
            }
        });
    }

}
