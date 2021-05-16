package com.example.fitfreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    public ArrayList<String> valuesArray;
    public ArrayList<String> keyArray;

    TextView textView1,textView2,textView3,textView4;
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

        valuesArray=new ArrayList<>(6);
        keyArray=new ArrayList<>(6);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot it:snapshot.getChildren()){
                    valuesArray.add(it.getValue().toString());
                    keyArray.add(it.getKey().toString());
                }


                textView1.setText(keyArray.get(4) + " :"+ valuesArray.get(4));
                textView2.setText(keyArray.get(2) + " :"+ valuesArray.get(2));
                textView3.setText(keyArray.get(0) + " :"+ valuesArray.get(0));
                textView4.setText(keyArray.get(1) + " :"+ valuesArray.get(1));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}