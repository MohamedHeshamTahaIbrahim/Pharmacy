package com.pharmacy.pharmacy.Adapter;

import android.annotation.SuppressLint;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacy.pharmacy.DAOdbCapture;
import com.pharmacy.pharmacy.Model.MyImage;
import com.pharmacy.pharmacy.R;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Mohamed Hesham on 2017-05-27.
 */
public class ImageAdapter extends ArrayAdapter<MyImage> {
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


    /**
     * applying ViewHolder pattern to speed up ListView, smoother and faster
     * item loading by caching view in A ViewHolder object
     */
    private static class ViewHolder {
        ImageView imgIcon;
        TextView description;
        ImageButton button,rotate;

    }

    public ImageAdapter(Context context, ArrayList<MyImage> images) {
        super(context, 0, images);
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
            viewHolder.button=(ImageButton) convertView.findViewById(R.id.button);
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
     int mCurrRotation = 0;
        mCurrRotation %= 360;
        float fromRotation = mCurrRotation;
        float toRotation = mCurrRotation += 90;

        final RotateAnimation rotateAnim = new RotateAnimation(
                fromRotation, toRotation, viewHolder.imgIcon.getWidth()/2, viewHolder.imgIcon.getHeight()/2);

        rotateAnim.setDuration(1000); // Use 0 ms to rotate instantly
        rotateAnim.setFillAfter(true); // Must be true or the animation will reset

        viewHolder.imgIcon.startAnimation(rotateAnim);
    }
});
     viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  DAOdbCapture db = new DAOdbCapture(getContext());
                db.deleteImage(image);
                db.close();*/
            final Animation zoom= AnimationUtils.loadAnimation(getContext(),R.anim.zoom);
                viewHolder.imgIcon.startAnimation(zoom);
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
}
