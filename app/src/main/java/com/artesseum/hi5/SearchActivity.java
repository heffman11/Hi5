package com.artesseum.hi5;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class SearchActivity extends AppCompatActivity {

    public static String finalSearchText="";
    public static String senderUSerID="";
    private RecyclerView usersList;
    FirebaseRecyclerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        startQuery();
        usersList = (RecyclerView) findViewById(R.id.useresRecycleView);
        usersList.setHasFixedSize(true);
        usersList.setLayoutManager(new LinearLayoutManager(this));





        String senderID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        senderUSerID = senderID;
        final EditText searchText = (EditText)findViewById(R.id.searchText);







        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    String searchTextFinal = searchText.getText().toString();
                    finalSearchText=searchTextFinal;
                    startQuery();

                }catch (Error e){

                }            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    String searchTextFinal = searchText.getText().toString();
                    finalSearchText=searchTextFinal;
                    startQuery();

                }catch (Error e){

                }


            }
        });

    }


    private void startQuery() {


        Query query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("displayname").equalTo(finalSearchText);
        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>().setQuery(query,Users.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<Users, usersHolder>(options) {
            @Override
            public usersHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_profile,parent,false);

                return new usersHolder(view);

            }
            @Override
            protected void onBindViewHolder(usersHolder holder, int position, Users model) {

                holder.setDisplayname(model.getDisplayname());
                holder.setEmail(model.getEmail());
                holder.setPhotoURL(model.getPhotoURL());


            }


        };

      usersList = (RecyclerView) findViewById(R.id.useresRecycleView);
      usersList.setHasFixedSize(true);
      usersList.setLayoutManager(new LinearLayoutManager(this));
      usersList.setAdapter(adapter);
      adapter.startListening();

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

        public void setEmail(String email){
            TextView emailview = (TextView) itemView.findViewById(R.id.userEmailDisplay);
            emailview.setText(email);
        }

        public void setPhotoURL(String photoURL){
            ImageView profilepicview = (ImageView) itemView.findViewById(R.id.userProfilePic);
            String umageURI = photoURL;
            Picasso.with(itemView.getContext()).load(photoURL).into(profilepicview);
        }



    }





}
