package com.pharmacy.pharmacy.ContactUs;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacy.pharmacy.R;

/**
 * Created by Mohamed Hesham on 2017-05-15.
 */

public class ContactUSScreen extends Fragment {
   View view;
    TextView contactusTitle,contactusDesc,phone,mail,title,follow;
    ImageButton settings;
    ImageView facebook,instagram,youtube;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_contactus, container, false);
        contactusTitle = (TextView)view.findViewById(R.id.contactusTitle);
        contactusDesc = (TextView)view.findViewById(R.id.contactusDesc);
        phone = (TextView)view.findViewById(R.id.phone);
        mail = (TextView)view.findViewById(R.id.mail);

        follow = (TextView) view.findViewById(R.id.follow);
        facebook = (ImageView)view. findViewById(R.id.facebook);
        instagram = (ImageView)view.findViewById(R.id.instagram);
        youtube = (ImageView) view.findViewById(R.id.youtube);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:"
                        + "care@liberty.ae"
                        + "?subject=" + "Feedback" + "&body=" + "");
                intent.setData(data);
                startActivity(intent);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "tel:"+ "800 5423789";
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
                startActivity(callIntent);

            }
        });
        return view;
    }
}
