package com.artesseum.hi5;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SearchActivity extends Activity {


    Button searchButton;
    DatabaseReference FriendRequetReference;
    EditText searchText;
    public static String finalSearchText="";
    public static String senderUSerID="";
    public static String receiverUserId="";
    private String CurrentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        CurrentState="not_friends";
        String senderID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        senderUSerID = senderID;

        Button searchButton = (Button)findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuery();

            }
        });




    }

    private void startQuery() {
        getSearchText();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        FriendRequetReference = FirebaseDatabase.getInstance().getReference().child("friend_request");
        Query query = reference.child("users").orderByChild("displayname").equalTo(finalSearchText);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    Toast.makeText(SearchActivity.this,"User not found",Toast.LENGTH_SHORT).show();

                }
                for(DataSnapshot singlesnapshot: dataSnapshot.getChildren()){
                    if(singlesnapshot.exists()){

                        final String receiverId = singlesnapshot.getKey();
                        receiverUserId=receiverId;

                        FriendRequetReference.child(senderUSerID).child(receiverId)
                                .child("request_type").setValue("sent")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    FriendRequetReference.child(receiverId).child(senderUSerID)
                                            .child("request_type").setValue("receiver")
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){

                                                        Toast.makeText(SearchActivity.this,"Request Sent",Toast.LENGTH_SHORT).show();


                                                    }
                                                }
                                            });
                                }
                            }
                        });



                        System.out.println(singlesnapshot.getKey());
                        System.out.println(singlesnapshot.child("displayname").getValue());

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }

    private void getSearchText() {

        EditText searchText = (EditText)findViewById(R.id.searchText);
        String searchTextFinal = searchText.getText().toString();
        finalSearchText=searchTextFinal;


    }
}
