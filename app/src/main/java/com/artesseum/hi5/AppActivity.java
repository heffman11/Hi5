package com.artesseum.hi5;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppActivity extends AppCompatActivity {

  //  Toolbar toolbar;

    private DatabaseReference databaseReference,usersReference;
    private Button btnTest;
    private RecyclerView friendsList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();*/


    databaseReference = FirebaseDatabase.getInstance().getReference().child("Message");
    usersReference = FirebaseDatabase.getInstance().getReference().child("users");
    friendsList = (RecyclerView) findViewById(R.id.friends);
    friendsList.setHasFixedSize(true);
    friendsList.setLayoutManager(new LinearLayoutManager(this));




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerAdapter<AllUsers, AllUsersViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<AllUsers, AllUsersViewHolder>
                (
                        AllUsers.class,
                        R.layout.all_users_display_layour,
                        AllUsersViewHolder.class,
                        usersReference

                ) {
            @Override
            protected void populateViewHolder(AllUsersViewHolder viewHolder, AllUsers model, int position) {

            viewHolder.setEmail(model.getEmail());


            }
        };

        friendsList.setAdapter(firebaseRecyclerAdapter);

    }




    public static class AllUsersViewHolder extends RecyclerView.ViewHolder{
        View mViews;

        public AllUsersViewHolder(View itemView) {
            super(itemView);

            mViews = itemView;


        }


        public void setEmail(String email){
            TextView userEmail = (TextView) mViews.findViewById(R.id.user_name);
            userEmail.setText(email);

        }

    }





}
