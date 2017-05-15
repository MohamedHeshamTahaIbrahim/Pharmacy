package com.pharmacy.pharmacy.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.ui.NetworkImageViewPlus;
import com.pharmacy.pharmacy.R;

import java.util.ArrayList;

public class HomeGallary_Adapter extends BaseAdapter implements Callback {
    public Integer[] imageId = {
            R.drawable.image1,
            R.drawable.pharmacy_launch

    };
    int GalItemBg;
    private Context cont;

    // Adding images.


    public HomeGallary_Adapter(Context c) {
        cont = c;

    }


    @Override
    public int getCount() {
        return imageId.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // ImageView imgView = newtag ImageView(cont);

        LayoutInflater inflater = (LayoutInflater) cont
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.gallary_item, parent, false);

  ImageView imgView =(ImageView) convertView
                .findViewById(R.id.imageView1);

         imgView.setImageResource(imageId[position]);




        convertView.findViewById(R.id.relativeImage).setBackgroundColor(0);

        return convertView;
    }



    @Override
    public void invalidateDrawable(Drawable who) {
        // TODO Auto-generated method stub
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        // TODO Auto-generated method stub

    }

}
