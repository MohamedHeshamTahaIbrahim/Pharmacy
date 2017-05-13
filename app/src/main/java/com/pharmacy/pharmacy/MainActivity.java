package com.pharmacy.pharmacy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pharmacy.pharmacy.Home.HomeScreen;
import com.pharmacy.pharmacy.Splash.SplashScreen;

public class MainActivity  extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment;
    DrawerLayout drawer;
    private SharedPreferences languagPreferences;
    private SharedPreferences.Editor languagePrefsEditor;
    TextView nameText,emailText;
    ImageView closeMenu;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    public static NavigationView navigationView;
    String name="";
    SpannableString mNewTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //AppController.getInstance().changedLanguage=false;
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // ((TextView)((MenuItem)navigationView.getMenu().getItem(1)).getActionView().findViewById(R.id.countView)).setText("567");
        navigationView.setItemIconTintList(null);
        View header=navigationView.getHeaderView(0);

        nameText = (TextView) header.findViewById(R.id.nameText);
        emailText = (TextView) header.findViewById(R.id.emailText);
        closeMenu = (ImageView) header.findViewById(R.id.closeMenu);
        closeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawers();
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        Intent intent=new Intent(MainActivity.this, HomeScreen.class);
        startActivity(intent);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // AppController.getInstance().changedLanguage=false;
        if (id == R.id.nav_profile) {
            Intent profile=new Intent(MainActivity.this,HomeScreen.class);
            startActivity(profile);
            // ((TextView)item.getActionView().findViewById(R.id.countView)).setText("567");
        } else if (id == R.id.nav_request) {
            // ((TextView)item.getActionView().findViewById(R.id.countView)).setText("567");


        }
        else if (id == R.id.nav_contactus) {

        }
        else if (id == R.id.nav_language) {


        }else if (id == R.id.nav_logout) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
    public void m(View view)
    {
        drawer.openDrawer(Gravity.RIGHT);
    }

}
