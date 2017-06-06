package com.pharmacy.pharmacy.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.pharmacy.pharmacy.AppController;
import com.pharmacy.pharmacy.MainActivity;
import com.pharmacy.pharmacy.R;
import com.pharmacy.pharmacy.Signup.SignUpScreen;
import com.pharmacy.pharmacy.Validation.FormValidation;
import com.pharmacy.pharmacy.utility.LoadingCallback;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohamed Hesham on 2017-05-06.
 */

public class LoginScreen extends Activity  {
    EditText userName,password;
     Button login;
    TextView createAccount;
    Fragment fragment;
    ImageButton facebookLogin;
    private static final int REGISTER_REQUEST_CODE = 1;
    FormValidation formValidation=new FormValidation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Backendless.setUrl( AppController.getInstance().SERVER_URL );
        Backendless.initApp( getApplicationContext(), AppController.getInstance().APPLICATION_ID, AppController.getInstance().API_KEY );
        setContentView(R.layout.activity_login);
        userName=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
      login=(Button)findViewById(R.id.login);
        facebookLogin=(ImageButton)findViewById(R.id.facebookLogin);
      createAccount=(TextView)findViewById(R.id.createAccount);
        userName.setTypeface(AppController.getInstance().regular);
        password.setTypeface(AppController.getInstance().regular);
        login.setTypeface(AppController.getInstance().bold);
        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingCallback<BackendlessUser> loginCallback = createLoginCallback();

                loginCallback.showLoading();
                loginFacebookUser( loginCallback );
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( isLoginValuesValid(  ) )
                {
                    LoadingCallback<BackendlessUser> loginCallback = createLoginCallback();

                    loginCallback.showLoading();
                    loginUser( userName.getText().toString(), password.getText().toString(), loginCallback );
                }
              /*  if(userName.getText().toString().equals("m.hesham@rytalo.com")&&password.getText().toString().equals("123456")){*/
           /*  Intent intent=new Intent(LoginScreen.this,MainActivity.class);
                startActivity(intent);*//*}*/
            }
        });

       createAccount.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent registrationIntent = new Intent( LoginScreen.this, SignUpScreen.class );
               startActivityForResult( registrationIntent, REGISTER_REQUEST_CODE );
           }
       });
    }
    public void loginUser( String email, String password, AsyncCallback<BackendlessUser> loginCallback )
    {
        Backendless.UserService.login( email, password, loginCallback );
    }
    public boolean isLoginValuesValid(  )
    {
        return formValidation.Is_Valid_Email(userName)&&formValidation.Is_Valid_Password(password) ;
    }

    /**
     * Creates a callback, containing actions to be executed on login request result.
     * Shows a Toast with BackendlessUser's objectId on success,
     * show a dialog with an error message on failure.
     *
     * @return a callback, containing actions to be executed on login request result
     */
    public LoadingCallback<BackendlessUser> createLoginCallback()
    {
        return new LoadingCallback<BackendlessUser>( this, getString( R.string.loading_login ) )
        {
            @Override
            public void handleResponse( BackendlessUser loggedInUser )
            {
                super.handleResponse( loggedInUser );
                Intent intent=new Intent(LoginScreen.this,MainActivity.class);
                startActivity(intent);
               // Toast.makeText( LoginScreen.this, String.format( getString( R.string.info_logged_in ), loggedInUser.getObjectId() ), Toast.LENGTH_LONG ).show();
            }
        };
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        if( resultCode == RESULT_OK )
        {
            switch( requestCode )
            {
                case REGISTER_REQUEST_CODE:
                    String email = data.getStringExtra( BackendlessUser.EMAIL_KEY );
                    userName.setText( email );
                    password.requestFocus();

                    Toast.makeText( this, getString( R.string.info_registered_success ), Toast.LENGTH_SHORT ).show();
            }
        }
    }
    public void loginFacebookUser( AsyncCallback<BackendlessUser> loginCallback )
    {
        Map<String, String> fieldsMappings = new HashMap<>();
        fieldsMappings.put( "name", "name" );
        Backendless.UserService.loginWithFacebook( this, null, fieldsMappings, Collections.<String>emptyList(), loginCallback );
    }
    /**
     * Sends a request to Backendless to log in user with Twitter account.
     * Fetches Twitter user's name and saves it on Backendless.
     *
     * @param loginCallback a callback, containing actions to be executed on request result
     */
    public void loginTwitterUser( AsyncCallback<BackendlessUser> loginCallback )
    {
        Map<String, String> fieldsMappings = new HashMap<>();
        fieldsMappings.put( "name", "name" );
        Backendless.UserService.loginWithTwitter( this, null, fieldsMappings, loginCallback );
    }

}
