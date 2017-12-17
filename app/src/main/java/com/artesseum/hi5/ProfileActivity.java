package com.artesseum.hi5;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class ProfileActivity extends Activity {

    TextView mEmail, mName;
    ImageView profilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_profile);

        }catch (Exception e){
            Toast.makeText(ProfileActivity.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }

        mEmail = findViewById(R.id.userEmailDisplay);
        mName = findViewById(R.id.displaynameProfileView);
        profilePic = findViewById(R.id.userProfilePic);

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String displayname = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String profileImage = FirebaseDatabase.getInstance().getReference().child("photoURL").toString();


        mEmail.setText(email);
        mName.setText(displayname);

        Glide.with(this).load(profileImage).asBitmap().into(profilePic);



    }
}
