package com.artesseum.hi5;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;
    private DatabaseReference userData;
    String usernameCheck = "";


    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                // user logged in
                Log.d("AUTH", auth.getCurrentUser().getEmail());
                String email = auth.getCurrentUser().getEmail();
                String uid = auth.getCurrentUser().getUid();
                // store user in database

                userData = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                userData.child("email").setValue(email);
                Intent loggedIn = new Intent(MainActivity.this, AppActivity.class);
                startActivity(loggedIn);


                Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();

            }
            else{
                //// user auth
                Log.d("Auth", "Not Authenticated");
            }
        }
    }
    // check if UID has username, if not then show Username Select clas



    // on app start check log in and


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        auth = FirebaseAuth.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        ///// if current user is logged in, then log in
        if (auth.getCurrentUser() != null) {

            Intent loggedIn = new Intent(MainActivity.this, AppActivity.class);
            startActivity(loggedIn);
        ////////// here direct too on activity result. same process...

        } else {


            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build());

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setTheme(R.style.AppTheme_NoActionBar)
                            .setAvailableProviders(providers)
                            .build(), RC_SIGN_IN);
        }


  }


}

