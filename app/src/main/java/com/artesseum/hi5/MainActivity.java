package com.artesseum.hi5;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private DatabaseReference storeUserDataReference;
    private Button buttonRegister, buttonLogin;
    private EditText editTextEmail, editTextPassword, editUserName;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){

            finish();
            startActivity(new Intent(getApplicationContext(),AppActivity.class));


        }else




        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        buttonLogin = (Button)findViewById(R.id.buttonSignin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editUserName = (EditText) findViewById(R.id.editUseName);

        buttonRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);



    }


    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String username = editUserName.getText().toString().toLowerCase().trim();

        if (TextUtils.isEmpty(username)){
            Toast.makeText(this, "Username is Blank",Toast.LENGTH_SHORT).show();
            return;

        }


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
                            String current_User_Id = firebaseAuth.getCurrentUser().getUid();
                            storeUserDataReference = FirebaseDatabase.getInstance().getReference().child("users").child(current_User_Id);

                            storeUserDataReference.child("email").setValue(email);
                            storeUserDataReference.child("username").setValue(username)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(MainActivity.this, "Registered",Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                                finish();
                                                startActivity(new Intent(getApplicationContext(),AppActivity.class));


                                            }else{
                                                progressDialog.dismiss();
                                                Toast.makeText(MainActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                            }



                                        }
                                    });


                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }


    private void userLogin(){
        String username = editUserName.getText().toString().toLowerCase().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)){
            Toast.makeText(this, "Username is Blank",Toast.LENGTH_SHORT).show();
            return;

        }


        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "No Email Detected",Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"No Password Detected",Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Initialising User Account");


        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            finish();
                           startActivity(new Intent(getApplicationContext(),AppActivity.class));


                        }else{
                            Toast.makeText(MainActivity.this,"Unable to login",Toast.LENGTH_SHORT).show();
                            return;


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
            userLogin();

        }

    }


}
