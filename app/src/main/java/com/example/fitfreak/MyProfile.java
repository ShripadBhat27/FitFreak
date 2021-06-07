package com.example.fitfreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyProfile extends AppCompatActivity {
    DatabaseReference databaseReference;
    String userId;

    TextView textView1,textView2,textView3,textView4,textView7;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(userId);

        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
        textView7=findViewById(R.id.textView7);
        button=findViewById(R.id.plogout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);

                textView1.setText("Name : "+ user.getName());
                textView2.setText("Email : "+ user.getEmail());
                textView3.setText("Age : "+ user.getAge());
                textView4.setText("Days So far : "+ user.getDays());
                textView7.setText("CALORIE INTAKE :"+user.getCalorieIntake()+" kCal");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}