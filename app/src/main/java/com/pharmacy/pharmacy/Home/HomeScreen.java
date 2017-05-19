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

import com.pharmacy.pharmacy.Adapter.GalleryImageAdapter;
import com.pharmacy.pharmacy.Adapter.HomeGallary_Adapter;
import com.pharmacy.pharmacy.AppController;
import com.pharmacy.pharmacy.MainActivity;
import com.pharmacy.pharmacy.R;
import com.pharmacy.pharmacy.Rochta.CaptureRochtaScreen;
import com.pharmacy.pharmacy.Rochta.Record;
import com.pharmacy.pharmacy.Rochta.RecordFragment;
import com.pharmacy.pharmacy.Rochta.RecordRochtaScreen;
import com.pharmacy.pharmacy.Rochta.UploadRochtaScreen;
import com.pharmacy.pharmacy.Rochta.WriteRochtaScreen;
import com.pharmacy.pharmacy.SquareImageButton;

/**
 * Created by Mohamed Hesham on 2017-05-06.
 */

public class HomeScreen extends Fragment implements View.OnClickListener{
    SquareImageButton cameraBtn,gallerybtn,writeBtn,recordbtn;
    private View view;
    private Gallery gallery;
    private HomeGallary_Adapter adp;
    Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
        cameraBtn=(SquareImageButton) view.findViewById(R.id.cameraBtn);
        gallerybtn=(SquareImageButton)view.findViewById(R.id.gallerybtn);
        writeBtn=(SquareImageButton)view.findViewById(R.id.writeBtn);
        recordbtn=(SquareImageButton)view.findViewById(R.id.recordbtn);
        cameraBtn.setOnClickListener(this);
        gallerybtn.setOnClickListener(this);
        writeBtn.setOnClickListener(this);
        recordbtn.setOnClickListener(this);
        Gallery gallery = (Gallery) view.findViewById(R.id.gallery);
        // selectedImage=(ImageView)findViewById(R.id.imageView);

        gallery.setClipToPadding(true);
        final GalleryImageAdapter galleryImageAdapter= new GalleryImageAdapter(getActivity());
        gallery.setAdapter(galleryImageAdapter);
        return view;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cameraBtn:
                Intent intent=new Intent(getActivity(), CaptureRochtaScreen.class);
                startActivity(intent);
                break;
            case R.id.gallerybtn:
                Intent intent1=new Intent(getActivity(), UploadRochtaScreen.class);
                startActivity(intent1);
                break;
            case R.id.writeBtn:
                Intent intent2=new Intent(getActivity(), WriteRochtaScreen.class);
                startActivity(intent2);
                break;
            case R.id.recordbtn:
                fragment = new RecordFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment, "record").addToBackStack("record").commit();
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
