package com.artesseum.hi5;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AppActivity extends Activity implements View.OnClickListener {

    //// Log Out
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            Intent restart = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
            restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(AppActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();

            startActivity(restart);
            finish();

            Log.d("Auth","User Logged Out");


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_app);



        findViewById(R.id.logout).setOnClickListener(this);



        // share button
        FloatingActionButton share = (FloatingActionButton) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Toast.makeText(AppActivity.this,"Share",Toast.LENGTH_SHORT).show();

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
                Intent searchIntent = new Intent(AppActivity.this,SearchActivity.class);
                startActivity(searchIntent);

            }
        });



















    }

}
