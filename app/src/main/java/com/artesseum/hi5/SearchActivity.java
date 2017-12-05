package com.artesseum.hi5;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SearchActivity extends Activity {


    Button searchButton;
    EditText searchText;
    public static String finalSearchText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);




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
        Query query = reference.child("users").orderByChild("displayname").equalTo(finalSearchText);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    Toast.makeText(SearchActivity.this,"User not found",Toast.LENGTH_SHORT).show();

                }
                for(DataSnapshot singlesnapshot: dataSnapshot.getChildren()){
                    if(singlesnapshot.exists()){
                        Toast.makeText(SearchActivity.this,"added",Toast.LENGTH_SHORT).show();
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
