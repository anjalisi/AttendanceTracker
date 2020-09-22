package com.example.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Signup extends AppCompatActivity {
    Spinner dept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Adding drop down
        dept= (Spinner)findViewById(R.id.department);
        ArrayAdapter<String> deptList= new ArrayAdapter<String>(Signup.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.departments));
        deptList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(deptList);
    }
}