package com.example.fitfreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class caloriecal extends AppCompatActivity {
    DatabaseReference databaseReference;
    String userId;
    TextView caloriesintake,monthlyintake;
    EditText getfood,getquantity;
    Button calculate;
    private String link="https://api.nal.usda.gov/fdc/v1/foods/search?api_key=YffhvBYCUEp44VByOPC0AxKUq2LQS30ozvUunFVR&query=";
    double cal;
    double quantity=0;
    double ans;
    double overall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caloriecal);
        getquantity=findViewById(R.id.get_quantity);
        getfood=findViewById(R.id.enter_food_content);
        calculate=findViewById(R.id.calculate);
        caloriesintake=findViewById(R.id.calorie_intake_content);
        monthlyintake=findViewById(R.id.monthly_intake_content);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                overall=user.calorieIntake;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(caloriecal.this, "PLEASE WAIT!!", Toast.LENGTH_SHORT).show();
                fetchData();
                quantity=Double.parseDouble(getquantity.getText().toString());
                ans=cal*quantity;

                caloriesintake.setText(""+ans+" KCAL");

                overall+=ans;
                databaseReference.child("calorieIntake").setValue(overall);
                monthlyintake.setText(""+overall +" KCAL");

            }
        });
    }
    private void fetchData()
    {
        StringRequest request = new StringRequest(Request.Method.GET, link+getfood.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("foods");
                            JSONObject jsonObject1=jsonArray.getJSONObject(0);
                            JSONArray jsonArray1=jsonObject1.getJSONArray("foodNutrients");
                            for(int i=0;i<jsonArray1.length();i++)
                            {
                                JSONObject jsonObject2=jsonArray1.getJSONObject(i);
                                String nutrient=jsonObject2.getString("nutrientName");
                                if(nutrient.equals("Energy"))
                                {
                                    cal=Double.parseDouble(jsonObject2.getString("value"));

                                    break;
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}