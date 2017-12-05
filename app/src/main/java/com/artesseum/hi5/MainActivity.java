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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;
    private DatabaseReference userData;
    TextView displaynameView;

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                // user logged in
                Log.d("AUTH", auth.getCurrentUser().getEmail());


                Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                String displayName = auth.getCurrentUser().getDisplayName();
                String email = auth.getCurrentUser().getEmail();
                String uid = auth.getCurrentUser().getUid();



                // users store by key
                userData = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                userData.child("email").setValue(email);
                userData.child("displayname").setValue(displayName);
                displaynameView = findViewById(R.id.displayNameTextView);
                displaynameView.setText(displayName);




                // activate friends list method here
            }
            else{
                //// user auth
                Log.d("Auth", "Not Authenticated");
            }
        }
    }


    // on app start check log in and


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // search button
        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Add Users", Toast.LENGTH_SHORT).show();

                searchProcess();

            }
        });



        auth = FirebaseAuth.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
        ////////// here direct too on activiry result. same process...

        } else {


            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build());

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setTheme(R.style.AppTheme)
                            .setAvailableProviders(providers)
                            .build(), RC_SIGN_IN);
        }


        findViewById(R.id.logout).setOnClickListener(this);


    }

    //// Log Out
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.logout){
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                Intent restart = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(MainActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();

                startActivity(restart);
                finish();

                Log.d("Auth","User Logged Out");
                }
            });
        }

    }




    // search dialog
    public void searchProcess(){

       final ArrayList UserList = new ArrayList<String>();

        /// get data snapshot before  ------------------------THIS NEEDS LOOKING INTO __ ADD LITENERS FOR DEBUGGIN

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref2 = ref.child("users");
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    UserList.add(String.valueOf(dsp.getValue()));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        //////// Search

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_search, null);
        final EditText mUsername = mView.findViewById(R.id.etUsername);
        Button mAdd = mView.findViewById(R.id.addUserButton);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add && username not in database
                String usernametext = mUsername.getText().toString().trim();
                if(UserList.contains(usernametext)){
                    Toast.makeText(MainActivity.this,"UserAdded",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this,"Username Invalid",Toast.LENGTH_SHORT).show();
                }
            }

        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }
}

