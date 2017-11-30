package com.artesseum.hi5;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private DatabaseReference mDatabase;
    private Button buttonRegister, buttonLogin;
    private EditText editTextEmail, editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        buttonLogin = (Button)findViewById(R.id.buttonSignin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

       buttonRegister.setOnClickListener(this);
       buttonLogin.setOnClickListener(this);



    }


    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "No Email Detected",Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"No Password Detected",Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Initialising User");
        progressDialog.show();
                               //     Create User
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            ///// new activity here
                            Toast.makeText(MainActivity.this, "Registered",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(MainActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }


    @Override
    public void onClick (View view){
        if (view == buttonRegister){
            registerUser();
        }

        if (view == buttonLogin){

        }

    }


   /* public void sendButtonClick(View view){
        final DatabaseReference newPost = mDatabase.push();
        newPost.child("content").setValue("Hi Five!");



    }*/

}
