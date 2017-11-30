package com.artesseum.hi5;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppActivity extends AppCompatActivity {

  //  Toolbar toolbar;

    private DatabaseReference databaseReference;
    private Button btnTest;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();*/


    databaseReference = FirebaseDatabase.getInstance().getReference().child("Message");
    btnTest = (Button) findViewById(R.id.btntest);

    btnTest.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final DatabaseReference newPost = databaseReference.push();
            newPost.child("content").setValue("Hi Five")
                    .addOnCompleteListener(AppActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AppActivity.this, "Hi 5 Sent",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AppActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




    }





}
