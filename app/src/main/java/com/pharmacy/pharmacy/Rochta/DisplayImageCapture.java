package com.pharmacy.pharmacy.Rochta;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacy.pharmacy.Customs.ImageResizer;
import com.pharmacy.pharmacy.Model.MyImage;
import com.pharmacy.pharmacy.R;

import org.json.JSONException;
import org.json.JSONObject;


public class DisplayImageCapture extends FragmentActivity {
    private MyImage image;
    private ImageView imageView;
    private TextView description;
    private String jstring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        imageView = (ImageView) findViewById(R.id.display_image_view);
      //  description = (TextView) findViewById(R.id.text_view_description);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            jstring = extras.getString("IMAGE");
        }
        image = getMyImage(jstring);
        //description.setText(image.toString());
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        imageView.setImageBitmap(ImageResizer
                .decodeSampledBitmapFromFile(image.getPath(), width, height));
    }

    private MyImage getMyImage(String image) {
        try {
            JSONObject job = new JSONObject(image);
            return (new MyImage(job.getString("title"),
                    job.getString("description"), job.getString("path"),
                    job.getLong("datetimeLong")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * go back to main activity
     *
     * /*
     * @param
     */
  /*  public void btnBackOnClick(View v) {
       Fragment fragment = new CaptureRochtaScreen2();
        FragmentManager fragmentManager2 = this.getSupportFragmentManager();
        fragmentManager2.beginTransaction()
                .replace(R.id.content_frame, fragment, "camera").addToBackStack("camera").commit();
        finish();
    }

    /**
     * delete the current item;
     *
     * @param v
     */
 /*   public void btnDeleteOnClick(View v) {
        DAOdb db = new DAOdb(this);
        db.deleteImage(image);
        db.close();
      Intent intent=new Intent(DisplayImage.this,CaptureRochtaScreen2.class);
        startActivity(intent);
        finish();
    }
*/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the user's current game state
        if (jstring != null) {
            outState.putString("jstring", jstring);
        }
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        if (savedInstanceState.containsKey("jstring")) {
            jstring = savedInstanceState.getString("jstring");
        }
    }


}
