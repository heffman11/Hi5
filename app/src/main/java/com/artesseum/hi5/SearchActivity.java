package com.artesseum.hi5;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artesseum.hi5.models.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class SearchActivity extends AppCompatActivity {

    public static String finalSearchText="";
    public static String senderUSerID="";
    public static String receiverUserId="";
    private String CurrentState;
    private RecyclerView usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        CurrentState="not_friends";
        String senderID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        senderUSerID = senderID;

        Button searchButton = (Button)findViewById(R.id.searchButton);

       // startQuery();

            searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuery();

            }
        });




    }

    private void startQuery() {
        getSearchText();

        Query query = FirebaseDatabase.getInstance().getReference().child("users").child("displayname");


        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addChildEventListener(childEventListener);

        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>().setQuery(query,Users.class)
                        .build();


        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Users, usersHolder>(options) {
            @Override
            public usersHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_profile,parent,false);
                return new usersHolder(view);

            }
            @Override
            protected void onBindViewHolder(usersHolder holder, int position, Users model) {

                holder.setDisplayname(model.getDisplayname());
                holder.setDisplayname(model.getEmail());
                holder.setDisplayname(model.getPhotoURL());


            }


        };

      usersList = (RecyclerView) findViewById(R.id.useresRecycleView);
      usersList.setHasFixedSize(true);
      usersList.setLayoutManager(new LinearLayoutManager(this));
      usersList.setAdapter(adapter);







    }


    private void getSearchText() {

        EditText searchText = (EditText)findViewById(R.id.searchText);
        String searchTextFinal = searchText.getText().toString();
        finalSearchText=searchTextFinal;


    }

    public static class usersHolder extends RecyclerView.ViewHolder{
        TextView username, email;
        ImageView profile;

        public usersHolder(View itemView){
            super(itemView);
        }



        public void setDisplayname(String displayname){
            TextView username = (TextView) itemView.findViewById(R.id.displaynameProfileView);
            username.setText(displayname);
        }



    }





}
