package com.pharmacy.pharmacy.Login;

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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.pharmacy.pharmacy.AppController;
import com.pharmacy.pharmacy.MainActivity;
import com.pharmacy.pharmacy.R;
import com.pharmacy.pharmacy.Signup.SignUpScreen;
import com.pharmacy.pharmacy.Validation.FormValidation;

/**
 * Created by Mohamed Hesham on 2017-05-06.
 */

public class LoginScreen extends Activity  {






    EditText userName,password;

     Button login,createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
      login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().equals("m.hesham@rytalo.com")&&password.getText().toString().equals("123456")){
             Intent intent=new Intent(LoginScreen.this,SignUpScreen.class);
                startActivity(intent);}
            }
        });


    }



}