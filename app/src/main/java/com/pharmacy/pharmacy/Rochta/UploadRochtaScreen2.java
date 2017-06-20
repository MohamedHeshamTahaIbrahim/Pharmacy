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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.DeviceRegistration;
import com.backendless.Messaging;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.backendless.messaging.DeliveryOptions;
import com.backendless.messaging.PublishOptions;
import com.backendless.messaging.PublishPolicyEnum;
import com.google.gson.Gson;
import com.pharmacy.pharmacy.Adapter.ImageAdapter;
import com.pharmacy.pharmacy.AppController;
import com.pharmacy.pharmacy.DAOdbCapture;
import com.pharmacy.pharmacy.DAOdbUpload;
import com.pharmacy.pharmacy.MainActivity;
import com.pharmacy.pharmacy.Model.MyImage;
import com.pharmacy.pharmacy.Model.Rochta;
import com.pharmacy.pharmacy.MyPushService;
import com.pharmacy.pharmacy.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    Uri selectedImage;
    View view;
    ImageButton capture_camera;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_upload_rochta2, container, false);
//        DeviceRegistration devReg = Backendless.Messaging.getDeviceRegistration();
        Backendless.Messaging.registerDevice("386360262521", new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void response) {
                Log.i("MyApp","Device has registered");




            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("MyApp","Server reported an error -"+fault.getMessage());
            }
        });
     /*   final Rochta rochta=new Rochta();
        PublishOptions publishOptions=new PublishOptions();
        HashMap<String,String>headers=new HashMap<String,String>();
        headers.put("alert-text","You have Received Rochta from Customer");
        headers.put("android-ticker-text","You have Received Rochta from Customer");
        headers.put("android-content-title","You have Received Rochta from Customer");
        headers.put("android-content-text",rochta.getImage_location());
        publishOptions.setHeaders(headers);
        DeliveryOptions deliveryOptions=new DeliveryOptions();
        deliveryOptions.setPublishPolicy(PublishPolicyEnum.BOTH);
        Backendless.Messaging.publish("You have Received Rochta from Customer",publishOptions,deliveryOptions);*/
        cameraimage=(ImageView)view.findViewById(R.id.cameraimage);
        Backendless.setUrl( AppController.getInstance().SERVER_URL );
        Backendless.initApp(getActivity(), AppController.getInstance().APPLICATION_ID, AppController.getInstance().API_KEY );
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
            selectedImage = data.getData();
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
                sendImageToUser(selectedImage);
                Toast.makeText(getActivity(), "جاري توصيل الروشتة للصيديليات",
                        Toast.LENGTH_LONG).show();



              /*  Intent intent =new Intent(getActivity(), MyPushService.class);
                getActivity().startService(intent);*/
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
    private void sendImageToUser(Uri imageUri){
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
            String timestamp=new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
            String imageFileName="JPEG_"+timestamp+"_.jpg";
            String imageDirectory="h";
            final Rochta rochta=new Rochta();
            rochta.setRochta_type("image");
            rochta.setImage_location(imageDirectory+"/"+imageFileName);

            Backendless.Files.Android.upload(bitmap, Bitmap.CompressFormat.JPEG, 100, imageFileName, imageDirectory, new AsyncCallback<BackendlessFile>() {
                @Override
                public void handleResponse(BackendlessFile response) {
                    Log.i("sendPhoto","Photo saved to Backendless!");
                    Backendless.Persistence.save(rochta, new AsyncCallback<Rochta>() {
                        @Override
                        public void handleResponse(Rochta response) {

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.i("sendPhoto","failed image");

                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
