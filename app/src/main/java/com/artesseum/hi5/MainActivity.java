package com.artesseum.hi5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    LoginButton loginButton;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);



        //// firbase auth
        mAuth = FirebaseAuth.getInstance();

        ///////check if user already logged in
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken !=null){
            Intent appMainIntent = new Intent(MainActivity.this, AppActivity.class);
            startActivity(appMainIntent);
            finish();
        }else





        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this,"Successfully Logged In", Toast.LENGTH_LONG).show();
                Intent appMainIntent = new Intent(MainActivity.this, AppActivity.class);
                startActivity(appMainIntent);

            }

            @Override
            public void onCancel() {
                    Toast.makeText(MainActivity.this,"Cancelled", Toast.LENGTH_LONG).show();


                                }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(MainActivity.this,"error", Toast.LENGTH_LONG).show();


            }
        });




    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }


    ///////////// firebase auth

    private void handleFacebookAccessToken(AccessToken token){

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            ////////////update ui here
                        } else {
                            Toast.makeText(MainActivity.this, "Auth Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }






}
