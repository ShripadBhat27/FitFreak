package com.example.fitfreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mSingin;
    ProgressBar progressBar2;
    FirebaseAuth fAuth;
    TextView mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail=findViewById(R.id.email2);
        mPassword=findViewById(R.id.Password2);
        mSingin=findViewById(R.id.SignIN);
        progressBar2=findViewById(R.id.progressBar2);
        mRegister=findViewById(R.id.alreadyRegister);
        fAuth=FirebaseAuth.getInstance();


        mSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=mEmail.getText().toString();
                String Password=mPassword.getText().toString();
             
                if(TextUtils.isEmpty(Email)){
                    mEmail.setError("EMAIL IS EMPTY!!");
                    return;
                }

                if(TextUtils.isEmpty(Password)){
                    mPassword.setError("Password IS EMPTY!!");
                    return;
                }

                if(Password.length()<6){
                    mPassword.setError("ENTER 6 or more characters");
                    return;
                }

                progressBar2.setVisibility(View.VISIBLE);
                //Authenticate
                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "LOGIN In Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(Login.this, "Error ->"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

    }
}