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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Register extends AppCompatActivity {
    TextInputEditText name,email,password,age;
    Button register;
    TextView login;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    DatabaseReference databaseUsers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.Name);
        email=findViewById(R.id.email2);
        password=findViewById(R.id.Password2);
        age=findViewById(R.id.age);
        register=findViewById(R.id.Register);
        login=findViewById(R.id.login);

        fAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        databaseUsers= FirebaseDatabase.getInstance().getReference("Users");
        //check if user is already login
        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=email.getText().toString();
                String mPassword=password.getText().toString();
                String Name=name.getText().toString();
                String Age=age.getText().toString();
                if(TextUtils.isEmpty(Email)){
                    email.setError("EMAIL IS EMPTY!!");
                    return;
                }

                if(TextUtils.isEmpty(mPassword)){
                    password.setError("Password IS EMPTY!!");
                    return;
                }

                if(mPassword.length()<6){
                    password.setError("ENTER 6 or more characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register on firebaSE
                fAuth.createUserWithEmailAndPassword(Email,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "USER CREATED", Toast.LENGTH_SHORT).show();
                            String userID=fAuth.getCurrentUser().getUid();
                            ArrayList<Integer> array=new ArrayList<>();
                            User u1=new User(Name,Email,Age,userID,0,array);
                            databaseUsers.child(userID).setValue(u1);


                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(Register.this, "ERROR ->"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}