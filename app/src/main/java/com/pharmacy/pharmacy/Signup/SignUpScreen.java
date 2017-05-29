package com.pharmacy.pharmacy.Signup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pharmacy.pharmacy.AppController;
import com.pharmacy.pharmacy.Login.LoginScreen;
import com.pharmacy.pharmacy.Model.User;
import com.pharmacy.pharmacy.R;
import com.pharmacy.pharmacy.Validation.FormValidation;

/**
 * Created by Mohamed Hesham on 2017-05-06.
 */

public class SignUpScreen extends Activity  {




 EditText userName,email,password,confirmpassword,address,mobile;


   Button signUp;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
       userName=(EditText)findViewById(R.id.userName);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        confirmpassword=(EditText)findViewById(R.id.confirmPassword);
        address=(EditText)findViewById(R.id.address);
        mobile=(EditText)findViewById(R.id.mobile);
        signUp=(Button)findViewById(R.id.signUp);
        userName.setTypeface(AppController.getInstance().regular);
        email.setTypeface(AppController.getInstance().regular);
        password.setTypeface(AppController.getInstance().regular);
        confirmpassword.setTypeface(AppController.getInstance().regular);
        address.setTypeface(AppController.getInstance().regular);
        mobile.setTypeface(AppController.getInstance().regular);
        signUp.setTypeface(AppController.getInstance().bold);


    }


}
