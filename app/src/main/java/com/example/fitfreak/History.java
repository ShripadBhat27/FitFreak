package com.example.fitfreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class History extends AppCompatActivity {

    DatabaseReference databaseReference2;
    String userId;
    public ArrayList<Integer> valuesArray;


    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference2= FirebaseDatabase.getInstance().getReference("Calorie").child(userId);
        valuesArray=new ArrayList<>();

        listView=findViewById(R.id.listView);

            databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange (@NonNull DataSnapshot snapshot){

                         for (DataSnapshot it : snapshot.getChildren()) {

                             String value = it.getValue().toString();

                             valuesArray.add(parseInt(value));
                         }

                    ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_list_item_1,valuesArray);
                         CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),valuesArray);
                         listView.setAdapter(customAdapter);



                }

                    @Override
                    public void onCancelled (@NonNull DatabaseError error){

                }

            });


    }
}