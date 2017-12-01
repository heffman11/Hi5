package com.artesseum.hi5;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class AppActivity extends AppCompatActivity {

  //  Toolbar toolbar;

    private DatabaseReference databaseReference,usersReference;
    private Button btnTest;
    private RecyclerView friendsList;
    ImageButton addButton;
    LinearLayout searchLayout;
    Dialog myDialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);



    databaseReference = FirebaseDatabase.getInstance().getReference().child("Message");
    usersReference = FirebaseDatabase.getInstance().getReference().child("users");
    friendsList = (RecyclerView) findViewById(R.id.friends);
    friendsList.setHasFixedSize(true);
    friendsList.setLayoutManager(new LinearLayoutManager(this));
    myDialog = new Dialog(this);






        FloatingActionButton share = (FloatingActionButton) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "make this open sharing", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


       /* FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
            add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });*/




    }


    public void showPopUp(View v){
        TextView textClose;
        ImageButton addButton;
        myDialog.setContentView(R.layout.search_popup);
        textClose = (TextView)myDialog.findViewById(R.id.closeearch);
        addButton = (ImageButton) myDialog.findViewById(R.id.addButtton);

        textClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

            myDialog.show();



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
            viewHolder.setUsername(model.getUsername());


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

        public void setUsername(String username){
            TextView Username = (TextView) mViews.findViewById(R.id.username);
            Username.setText(username);

        }



    }





}
