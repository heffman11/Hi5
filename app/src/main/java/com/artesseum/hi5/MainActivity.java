package com.artesseum.hi5;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;
    private DatabaseReference userData;

    //register
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            if (resultCode == RESULT_OK){

                /// facebook stuff

                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                Profile profile = Profile.getCurrentProfile();
                String profileURL = profile.getProfilePictureUri(200,200).toString();
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                String profileURLGoogle = acct.getPhotoUrl().toString();


                // user logged in
                Log.d("AUTH", auth.getCurrentUser().getEmail());
                String email = auth.getCurrentUser().getEmail();
                String uid = auth.getCurrentUser().getUid();
                Uri photoUrl = auth.getCurrentUser().getPhotoUrl();


                // store user in database

                userData = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                userData.child("email").setValue(email);
                if (profileURLGoogle!=null){
                    userData.child("photoUrl").setValue(profileURLGoogle);
                }else{
                    userData.child("photoUrl").setValue(profileURL);

                }
                Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();

                mainActivity();


            }
            else{
                //// user auth
                Log.d("Auth", "Not Authenticated");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();





        //---------------------------------------------------------------------------------------//
        // share button
        FloatingActionButton share = (FloatingActionButton) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Toast.makeText(MainActivity.this,"Share",Toast.LENGTH_SHORT).show();

                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT,"Sharing Text");
                startActivity(Intent.createChooser(sharingIntent,"Share via"));
            }
        });
        //---------------------------------------------------------------------------------------//






        ///// if current user is logged in, then log in
        if (auth.getCurrentUser() != null) {


            mainActivity();





        //--------------------------create log in screen--------------------------------------//

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
        //--------------------------create log in screen--------------------------------------//


  }

    private void mainActivity()  {


        TextView displayNameView = (TextView) findViewById(R.id.DisplayNameHere);
        ImageButton logoutButton = (ImageButton)findViewById(R.id.logoutButton);
        ImageButton searchButton = (ImageButton)findViewById(R.id.searchButton);



        String DisplayName = auth.getCurrentUser().getDisplayName();
        displayNameView.setText(DisplayName);


    //---------------------log out button---------------------------------------------------------//

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent restart = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(MainActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();

                startActivity(restart);
                finish();

                Log.d("Auth","User Logged Out");
            }
        });

        //---------------------Search button---------------------------------------------------------//


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(searchIntent);
            }
        });

        //---------------------Displayname button---------------------------------------------------------//

        displayNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UserProfile = new Intent(MainActivity.this, UserProfile.class);
                startActivity(UserProfile);
            }
        });






    }


}

