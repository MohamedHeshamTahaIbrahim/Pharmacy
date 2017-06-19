package com.pharmacy.pharmacy.Rochta;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.DataQueryBuilder;
import com.pharmacy.pharmacy.Adapter.ImageAdapter;
import com.pharmacy.pharmacy.AppController;
import com.pharmacy.pharmacy.MainActivity;
import com.pharmacy.pharmacy.Model.Rochta;
import com.pharmacy.pharmacy.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Mohamed Hesham on 2017-05-09.
 */

public class UploadRochtaScreen3 extends Fragment implements View.OnClickListener {
 ImageView galleryimage/*,addPhotoIcon*/;
    Button confirm;
    String base64 = "";
    Bitmap photo,useBitmap;
    private static final int PICK_FROM_GALLERY = 2;
    View view;
    GridView main_list_view;
    private ImageAdapter imageAdapter;
    ImageButton capture_camera;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        Backendless.setUrl( AppController.getInstance().SERVER_URL );
        Backendless.initApp(getActivity(), AppController.getInstance().APPLICATION_ID, AppController.getInstance().API_KEY );
         view= inflater.inflate(R.layout.activity_upload_rochta2, container, false);
        main_list_view=(GridView)view.findViewById(R.id.main_list_view);
       // galleryimage=(ImageView)view.findViewById(R.id.galleryimage);
        // addPhotoIcon=(ImageView)findViewById(R.id.addPhotoIcon);
        capture_camera=(ImageButton)view.findViewById(R.id.capture_camera) ;
        confirm=(Button)view.findViewById(R.id.confirm);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), PICK_FROM_GALLERY);

        // addPhotoIcon.setOnClickListener(this);
        confirm.setOnClickListener(this);
        capture_camera.setOnClickListener(this);
        main_list_view.setAdapter(imageAdapter);
        return view;
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
                Toast.makeText(getActivity(), "جاري توصيل الروشتة للصيديليات",
                        Toast.LENGTH_LONG).show();
                /* Intent intent1=new Intent(UploadRochtaScreen.this,UploadRochtaConfirmScreen.class);
                //  intent.putExtra("BitmapImage",useBitmap);
                intent1.putExtra("tt",photo);
                startActivity(intent1);*/
                break;
            case R.id.capture_camera:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), PICK_FROM_GALLERY);
                 break;
        }
    }

    @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_GALLERY) {
            if(resultCode== Activity.RESULT_OK){
               //final Uri uri=data.getData();
                Uri selectedImage = data.getData();
                sendImageToUser(selectedImage);
                String currentUser= Backendless.UserService.loggedInUser();
                Backendless.Persistence.of(BackendlessUser.class).findById(currentUser, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser user) {
                        final String currentUserName=(String)user.getProperty("name");

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });
            }
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
    private void send(){
        try {
            String filename = "myhelloworld-async.txt";
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            fileOutputStream.write("Hello mbaas!\nUploading files is easy!".getBytes());
            fileOutputStream.close();
            final File file = new File(filename);
            Backendless.Files.upload( file, "/sentPics", new AsyncCallback<BackendlessFile>()
            {
                @Override
                public void handleResponse( BackendlessFile uploadedFile )
                {
                    System.out.println( "File has been uploaded. File URL is - " + uploadedFile.getFileURL() );
                    file.delete();
                }

                @Override
                public void handleFault( BackendlessFault backendlessFault )
                {
                    System.out.println( "Server reported an error - " + backendlessFault.getMessage() );
                }
            } );
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private void getPhotoSendTo(String rochta_type){
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(String.format("rochta_type = '%s'",rochta_type));
        Backendless.Persistence.of(Rochta.class).find(queryBuilder, new AsyncCallback<List<Rochta>>() {
            @Override
            public void handleResponse(List<Rochta> response) {
            // List<Rochta>photos=response.get()
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

    }
}
