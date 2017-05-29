package com.pharmacy.pharmacy.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacy.pharmacy.Adapter.GalleryImageAdapter;
import com.pharmacy.pharmacy.Adapter.HomeGallary_Adapter;
import com.pharmacy.pharmacy.AppController;
import com.pharmacy.pharmacy.MainActivity;
import com.pharmacy.pharmacy.R;
import com.pharmacy.pharmacy.Rochta.CaptureRochtaScreen;
import com.pharmacy.pharmacy.Rochta.CaptureRochtaScreen2;
import com.pharmacy.pharmacy.Rochta.Record;
import com.pharmacy.pharmacy.Rochta.RecordFragment;
import com.pharmacy.pharmacy.Rochta.RecordRochtaScreen;
import com.pharmacy.pharmacy.Rochta.UploadRochtaScreen;
import com.pharmacy.pharmacy.Rochta.UploadRochtaScreen2;
import com.pharmacy.pharmacy.Rochta.WriteRochtaScreen;
import com.pharmacy.pharmacy.SquareImageButton;

/**
 * Created by Mohamed Hesham on 2017-05-06.
 */

public class HomeScreen extends Fragment implements View.OnClickListener{
   ImageView cameraBtn,gallerybtn,writeBtn,recordbtn;
    private View view;
    private Gallery gallery;
    private HomeGallary_Adapter adp;
    Fragment fragment;
    TextView writerochtaTV,capturerochtaTV,uploadrochtaTv,recordrochtaTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
        cameraBtn=(ImageView) view.findViewById(R.id.capturerochta);
        gallerybtn=(ImageView) view.findViewById(R.id.uploadrochta);
        writeBtn=(ImageView) view.findViewById(R.id.writerochta);
        recordbtn=(ImageView) view.findViewById(R.id.recordrochta);
        writerochtaTV=(TextView)view.findViewById(R.id.writerochtaTV);
        capturerochtaTV=(TextView)view.findViewById(R.id.capturerochtaTV);
        uploadrochtaTv=(TextView)view.findViewById(R.id.uploadrochtaTv);
        recordrochtaTV=(TextView)view.findViewById(R.id.recordrochtaTV);
        writerochtaTV.setTypeface(AppController.getInstance().bold);
        capturerochtaTV.setTypeface(AppController.getInstance().bold);
        uploadrochtaTv.setTypeface(AppController.getInstance().bold);
        recordrochtaTV.setTypeface(AppController.getInstance().bold);
        cameraBtn.setOnClickListener(this);
        gallerybtn.setOnClickListener(this);
        writeBtn.setOnClickListener(this);
        recordbtn.setOnClickListener(this);
      /*  Gallery gallery = (Gallery) view.findViewById(R.id.gallery);
        // selectedImage=(ImageView)findViewById(R.id.imageView);

        gallery.setClipToPadding(true);
        final GalleryImageAdapter galleryImageAdapter= new GalleryImageAdapter(getActivity());
        gallery.setAdapter(galleryImageAdapter);*/
        return view;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.capturerochta:
                fragment = new CaptureRochtaScreen2();
                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                fragmentManager2.beginTransaction()
                        .replace(R.id.content_frame, fragment, "camera").addToBackStack("camera").commit();
                break;
            case R.id.uploadrochta:
                fragment = new UploadRochtaScreen2();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment, "upload").addToBackStack("upload").commit();
                break;
            case R.id.writerochta:
                fragment = new WriteRochtaScreen();
                FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                fragmentManager3.beginTransaction()
                        .replace(R.id.content_frame, fragment, "write").addToBackStack("write").commit();
                break;
            case R.id.recordrochta: {
                fragment = new RecordFragment();
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                fragmentManager1.beginTransaction()
                        .replace(R.id.content_frame, fragment, "record").addToBackStack("record").commit();
            break;
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
            MainActivity.navigationView.getMenu().getItem(3).setChecked(false);
            MainActivity.navigationView.getMenu().getItem(4).setChecked(false);
            MainActivity.navigationView.getMenu().getItem(5).setChecked(false);
        }
        AppController.activityResumed();
    }
    @Override
    public void onPause() {
        super.onPause();
        AppController.activityPaused();
    }
}
