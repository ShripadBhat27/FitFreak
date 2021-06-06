package com.example.fitfreak;

import java.util.ArrayList;

public class User {
    String name,email,age,id;
    public int days;
    public int calorieIntake;
    ArrayList<Integer> steps;

    public ArrayList<Integer> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Integer> steps) {
        this.steps = steps;
    }

    public User(){
    }

    public User(String name, String email, String age, String id, int days,int calorieIntake ,ArrayList<Integer> steps) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.id = id;
        this.days = days;
        this.steps = steps;
        this.calorieIntake=calorieIntake;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getCalorieIntake() {
        return calorieIntake;
    }

    public void setCalorieIntake(int calorieIntake) {
        this.calorieIntake = calorieIntake;
    }
}
