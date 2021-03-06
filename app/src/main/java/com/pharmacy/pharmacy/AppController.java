package com.pharmacy.pharmacy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.cache.plus.ImageLoader;
import com.android.volley.toolbox.Volley;

import com.pharmacy.pharmacy.Customs.LruBitmapCache;
import com.pharmacy.pharmacy.Model.User;

import java.util.ArrayList;

import me.leolin.shortcutbadger.ShortcutBadger;


/**
 * Created by dinamounib on 8/29/16.
 */
public class AppController extends Application {
//<module external.linked.project.id=":app" external.linked.project.path="$MODULE_DIR$" external.root.project.path="$MODULE_DIR$/.." external.system.id="GRADLE" type="JAVA_MODULE" version="4">
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    Typeface facesArabic;
    public static final String TAG = AppController.class.getSimpleName();
    public static ConnectivityManager cm;
    public static Context context;
    public static String CurrentTag="home";
    String token;
    public static final String APPLICATION_ID = "612EB06E-EA93-58E9-FFC8-4EAFC87DB500";
    public static final String API_KEY = "5F3B4BF4-A2BB-7319-FFE6-BBE70AB0E200";
    public static final String SERVER_URL = "https://api.backendless.com";



    String language = "en";
    Dialog dialogMsg;

    // public ArrayList<Appointment> appointments = new ArrayList<>();
    public User user = null;
    private static AppController mInstance = new AppController();
    private static boolean activityVisible;
    Handler handler;
    public Typeface bold,regular;
    public static synchronized AppController getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }
    public void setBadgeNumber(int count) {
        ShortcutBadger.applyCount(this, count);
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", getPackageName());
       // intent.putExtra("badge_count_class_name", getLauncherClassName(this));
        sendBroadcast(intent);
    }

    public void removeBadgeNumber(Context con)
    {
        ShortcutBadger.removeCount(con);
        //ShortcutBadger.with(getApplicationContext()).remove();
    }
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        if(isNetworkStatusAvialable(context))
        {
            req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
            getRequestQueue().getCache().clear();
            getRequestQueue().add(req);

        }
        else{

            if(AppController.getInstance().getLanguage().equalsIgnoreCase("ar")){
                ToastMsg("لا يوجد اتصال بشبكة الانترنت. يرجى المحاولة مرة اخرى");
            }
            else {
                ToastMsg("No Internet Connection. Please try again");
            }
        }
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static void ToastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public  void openDialog(String title, String msg, String ok, Context con) {
        // TODO Auto-generated method stub
        dialogMsg = new AlertDialog.Builder(con).setTitle(title).setMessage(msg)
                .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public int GetWidth(){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
    public  void closeDialog() {
        if(dialogMsg != null)
            if(dialogMsg.isShowing())
                dialogMsg.dismiss();
    }


    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
    }

}
