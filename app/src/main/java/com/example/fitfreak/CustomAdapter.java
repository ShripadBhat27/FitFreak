package com.example.fitfreak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    ArrayList<Integer> data;
    Context context;

    public CustomAdapter(@NonNull Context context,ArrayList<Integer>data) {
        super(context, R.layout.row,data);
        this.data=data;
        this.context=context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        TextView Rdays = view.findViewById(R.id.Rdays);
        TextView Rcalories = view.findViewById(R.id.Rcalories);
        TextView Rdistance = view.findViewById(R.id.Rdistance);
        TextView Rsteps=view.findViewById(R.id.Rsteps);

        int numsteps=data.get(position);

        Rdays.setText("Day # "+(position+1));
        Rsteps.setText("No of Steps :"+numsteps);
        double meter=(1000*numsteps)/1350;
        Rdistance.setText("Distance Covered :" + String.format("%.3f",meter/1000));
        double caloriesburned=meter*76/1000;

        Rcalories.setText("Calories Burned :"+String.format("%.2f",caloriesburned));


        return view;
    }
}
