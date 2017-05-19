package com.pharmacy.pharmacy.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.pharmacy.pharmacy.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by anupamchugh on 24/10/15.
 */
public class GalleryImageAdapter extends BaseAdapter
{
    private Context mContext;


    Timer timer;
    TimerTask task;
    public GalleryImageAdapter(Context context)
    {
        mContext = context;
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(final int index, View view, ViewGroup viewGroup)
    {
        final ImageView i = new ImageView(mContext);

    /*   final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
        // TODO Auto-generated method stub
        public void run() {


        }
        };
        handler.postDelayed(runnable, 2000);
*/ final Handler mHandler = new Handler();

        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {
            public void run() {

                i.setImageResource(mImageIds[index]);
                i.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                i.setScaleType(ImageView.ScaleType.FIT_XY);
              //  handler.postDelayed(this, 2000);

            }
        };

        int delay = 1000; // delay for 1 sec.

        int period = 1000; // repeat every 4 sec.

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {

                mHandler.post(mUpdateResults);

            }

        }, delay, period);


        return i;
    }

   public Integer[] mImageIds = {
           R.drawable.image1,
            R.drawable.image2
    };

}

