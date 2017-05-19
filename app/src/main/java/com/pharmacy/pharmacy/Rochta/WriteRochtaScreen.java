package com.pharmacy.pharmacy.Rochta;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pharmacy.pharmacy.AppController;
import com.pharmacy.pharmacy.MainActivity;
import com.pharmacy.pharmacy.R;

/**
 * Created by Mohamed Hesham on 2017-05-09.
 */

public class WriteRochtaScreen extends Fragment  implements View.OnClickListener {
    View view;
    EditText notes;
    Button confirm;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_write_rochta, container, false);
        notes=(EditText)view.findViewById(R.id.notes);
        confirm=(Button)view.findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        return view;
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
        }
    }
}
