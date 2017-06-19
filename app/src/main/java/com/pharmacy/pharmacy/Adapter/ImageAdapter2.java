package com.pharmacy.pharmacy.Adapter;

/**
 * Created by Mohamed Hesham on 2017-06-01.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacy.pharmacy.DAOdbCapture;
import com.pharmacy.pharmacy.Model.MyImage;
import com.pharmacy.pharmacy.R;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.media.ThumbnailUtils;
import android.util.FloatMath;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pharmacy.pharmacy.DAOdbCapture;
import com.pharmacy.pharmacy.Model.MyImage;
import com.pharmacy.pharmacy.R;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Mohamed Hesham on 2017-05-27.
 */
public class ImageAdapter2 extends ArrayAdapter<MyImage> {
    private final int THUMBSIZE = 96;
    MyImage image;
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    PointF startPoint = new PointF();
    PointF midPoint = new PointF();
    float oldDist = 1f;
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    PhotoViewAttacher photoViewAttacher ;
    boolean pressed=true;
    Activity context;
    ArrayList<MyImage> images=new ArrayList<MyImage>();
    /**
     * applying ViewHolder pattern to speed up ListView, smoother and faster
     * item loading by caching view in A ViewHolder object
     */
    private static class ViewHolder {
        ImageView imgIcon;
        TextView description;
        ImageButton zoom,rotate;

    }

    public ImageAdapter2(Activity context, ArrayList<MyImage> images) {
        super(context, R.layout.item_image);
        this.context=context;
        this.images=images;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        // view lookup cache stored in tag
        final ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the
        // item view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_image, parent, false);
          /*  viewHolder.description =
                    (TextView) convertView.findViewById(R.id.item_img_infor);*/
            viewHolder.imgIcon =
                    (ImageView) convertView.findViewById(R.id.item_img_icon);
            viewHolder.zoom=(ImageButton) convertView.findViewById(R.id.zoom);
            viewHolder.rotate=(ImageButton)convertView.findViewById(R.id.rotate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        image = getItem(position);
        // set description text
//        viewHolder.description.setText(image.toString());
        // set image icon
        viewHolder.imgIcon.setImageBitmap(ThumbnailUtils
                .extractThumbnail(BitmapFactory.decodeFile(image.getPath()),
                        THUMBSIZE, THUMBSIZE));
        photoViewAttacher=new PhotoViewAttacher(viewHolder.imgIcon);
        photoViewAttacher.update();
        viewHolder.rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    /* int mCurrRotation = 0;
        mCurrRotation %= 360;
        float fromRotation = mCurrRotation;
        float toRotation = mCurrRotation += 90;

        final RotateAnimation rotateAnim = new RotateAnimation(
                fromRotation, toRotation, viewHolder.imgIcon.getWidth()/2, viewHolder.imgIcon.getHeight()/2);

        rotateAnim.setDuration(1000); // Use 0 ms to rotate instantly
        rotateAnim.setFillAfter(true); // Must be true or the animation will reset

        viewHolder.imgIcon.startAnimation(rotateAnim);*/
                DAOdbCapture db = new DAOdbCapture(context);
                db.deleteImage(image);
                db.close();
                notifyDataSetChanged();
            }
        });
        viewHolder.zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  DAOdbCapture db = new DAOdbCapture(getContext());
                db.deleteImage(image);
                db.close();*/
         /*   final Animation zoom= AnimationUtils.loadAnimation(getContext(),R.anim.zoom);
                viewHolder.imgIcon.startAnimation(zoom);*/
                openCategoryPopup(context);

            }
        });

       /*viewHolder.button.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               switch (event.getAction() & MotionEvent.ACTION_MASK) {
                   case MotionEvent.ACTION_DOWN:

                       savedMatrix.set(matrix);
                       startPoint.set(event.getX(), event.getY());
                       mode = DRAG;
                       break;

                   case MotionEvent.ACTION_POINTER_DOWN:

                       oldDist = spacing(event);

                       if (oldDist > 10f) {
                           savedMatrix.set(matrix);
                           midPoint(midPoint, event);
                           mode = ZOOM;
                       }
                       break;

                   case MotionEvent.ACTION_UP:

                   case MotionEvent.ACTION_POINTER_UP:
                       mode = NONE;

                       break;

                   case MotionEvent.ACTION_MOVE:
                       if (mode == DRAG) {
                           matrix.set(savedMatrix);
                           matrix.postTranslate(event.getX() - startPoint.x,
                                   event.getY() - startPoint.y);
                       } else if (mode == ZOOM) {
                           float newDist = spacing(event);
                           if (newDist > 10f) {
                               matrix.set(savedMatrix);
                               float scale = newDist / oldDist;
                               matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                           }
                       }
                       break;

               }
               viewHolder.imgIcon.setImageMatrix(matrix);

               return true;
           }

           @SuppressLint("FloatMath")
           private float spacing(MotionEvent event) {
               float x = event.getX(0) - event.getX(1);
               float y = event.getY(0) - event.getY(1);
               return (float)Math.sqrt(x * x + y * y);
           }

           private void midPoint(PointF point, MotionEvent event) {
               float x = event.getX(0) + event.getX(1);
               float y = event.getY(0) + event.getY(1);
               point.set(x / 2, y / 2);
           }




       });*/
        // Return the completed view to render on screen
        return convertView;
    }
    public void openCategoryPopup(Activity activity) {
        int displayHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        int displayWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        int alertwidth = (int) (0.9 * (displayWidth));
        int alertHeight = (int) (0.61 * (displayHeight));

        int[] location = new int[2];
//        categoryLayout.getLocationOnScreen(location);


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView;

        dialogView = inflater.inflate(R.layout.zoom_dialog, null);


        dialogBuilder.setView(dialogView);
        final AlertDialog alert = dialogBuilder.create();
        final ImageView zoomImage = (ImageView) dialogView.findViewById(R.id.zoomImage);
        final ImageButton zoom=(ImageButton)dialogView.findViewById(R.id.zoom);
        final  ImageButton rotate=(ImageButton)dialogView.findViewById(R.id.rotate);
        zoomImage.setImageBitmap(ThumbnailUtils
                .extractThumbnail(BitmapFactory.decodeFile(image.getPath()),
                        THUMBSIZE, THUMBSIZE));

        final Animation zoomin = AnimationUtils.loadAnimation(getContext(), R.anim.zoomin);
        final Animation zoomout = AnimationUtils.loadAnimation(getContext(), R.anim.zoomout);
        //  TextView closeFilter = (TextView) dialogView.findViewById(R.id.closeFilter);
        //Set font for text in categoryfilter
       /* zoomImage.setAnimation(zoomin);
        zoomImage.setAnimation(zoomout);*/
        int mCurrRotation = 0;
        mCurrRotation %= 360;
        float fromRotation = mCurrRotation;
        float toRotation = mCurrRotation += 90;

        final RotateAnimation rotateAnim = new RotateAnimation(
                fromRotation, toRotation, zoomImage.getWidth()/2, zoomImage.getHeight()/2);

        rotateAnim.setDuration(1000); // Use 0 ms to rotate instantly
        rotateAnim.setFillAfter(true); // Must be true or the animation will reset

      /*  zoomImage.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(!pressed) {
                  v.startAnimation(zoomin);

                  pressed = !pressed;
              } else {
                  v.startAnimation(zoomout);

                  pressed = !pressed;

              }


          }
      });*/
        zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pressed) {
                    zoomImage.startAnimation(zoomin);

                    pressed = !pressed;
                } else {
                    zoomImage.startAnimation(zoomout);

                    pressed = !pressed;

                }
            }
        });
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mCurrRotation = 0;
                mCurrRotation %= 360;
                float fromRotation = mCurrRotation;
                float toRotation = mCurrRotation += 90;

                final RotateAnimation rotateAnim = new RotateAnimation(
                        fromRotation, toRotation, zoomImage.getWidth()/2, zoomImage.getHeight()/2);

                rotateAnim.setDuration(1000); // Use 0 ms to rotate instantly
                rotateAnim.setFillAfter(true); // Must be true or the animation will reset

                zoomImage.startAnimation(rotateAnim);
            }
        });




        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alert.getWindow().getAttributes());
        alert.getWindow().setAttributes(lp);
        alert.show();
//        alert.getWindow().setLayout(alertwidth, alertHeight);


    }
}
