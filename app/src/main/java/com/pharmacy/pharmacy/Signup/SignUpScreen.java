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
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.pharmacy.pharmacy.AppController;
import com.pharmacy.pharmacy.Login.LoginScreen;
import com.pharmacy.pharmacy.Model.User;
import com.pharmacy.pharmacy.R;
import com.pharmacy.pharmacy.Validation.FormValidation;
import com.pharmacy.pharmacy.utility.LoadingCallback;

/**
 * Created by Mohamed Hesham on 2017-05-06.
 */

public class SignUpScreen extends Activity  {




 EditText userName,email,password,confirmpassword,address,mobile;
FormValidation formValidation=new FormValidation();


   Button signUp;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     Backendless.setUrl( AppController.getInstance().SERVER_URL );
     Backendless.initApp( getApplicationContext(), AppController.getInstance().APPLICATION_ID, AppController.getInstance().API_KEY );
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

     signUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
       if(registervaluesvalid()){
        LoadingCallback<BackendlessUser> registrationCallback = createRegistrationCallback();

        registrationCallback.showLoading();
        registerUser(userName.getText().toString(),email.getText().toString(),password.getText().toString()
        ,address.getText().toString(),mobile.getText().toString(),registrationCallback);
       }
      }
     });
    }

public boolean registervaluesvalid(){
 return formValidation.Is_Valid_UserName(userName)&&formValidation.Is_Valid_Email(email)
         &&formValidation.Is_Valid_Password(password)
         &&formValidation.Is_Password_equal_Confirm_password(password,confirmpassword)
         &&formValidation.Is_Valid_address(address)&&formValidation.Is_Valid_mobile(mobile);
}
 public void registerUser( String name, String email, String password,String address,String mobile,
                           AsyncCallback<BackendlessUser> registrationCallback )
 {
  BackendlessUser user = new BackendlessUser();
  user.setEmail( email );
  user.setPassword( password );
  user.setProperty( "name", name );
  user.setProperty("address",address);
  user.setProperty("mobile",mobile);


  //Backendless handles password hashing by itself, so we don't need to send hash instead of plain text
  Backendless.UserService.register( user, registrationCallback );
 }
 /**
  * Creates a callback, containing actions to be executed on registration request result.
  * Shows a Toast with BackendlessUser's objectId on success,
  * show a dialog with an error message on failure.
  *
  * @return a callback, containing actions to be executed on registration request result
  */
 public LoadingCallback<BackendlessUser> createRegistrationCallback()
 {
  return new LoadingCallback<BackendlessUser>( this, getString( R.string.loading_register ) )
  {
   @Override
   public void handleResponse( BackendlessUser registeredUser )
   {
    super.handleResponse( registeredUser );
       Intent registrationResult = new Intent();
       registrationResult.putExtra( BackendlessUser.EMAIL_KEY, registeredUser.getEmail() );
       setResult( RESULT_OK, registrationResult );
       SignUpScreen.this.finish();
    //Toast.makeText( SignUpScreen.this, String.format( getString( R.string.info_registered ), registeredUser.getObjectId() ), Toast.LENGTH_LONG ).show();
   }
  };
 }
}
