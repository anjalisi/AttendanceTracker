package com.example.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    Spinner dept;
    EditText emailId;
    EditText teacherName;
    EditText password;
    Button signupBtn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //connecting
        emailId= (EditText)findViewById(R.id.emailID);
        password=(EditText) findViewById(R.id.password);
        teacherName= (EditText) findViewById(R.id.teacherName);
        signupBtn=(Button)findViewById(R.id.signupButton);
        //Adding drop down
        dept= (Spinner)findViewById(R.id.department);
        ArrayAdapter<String> deptList= new ArrayAdapter<String>(Signup.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.departments));
        deptList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(deptList);

        //Setting up firebase
        firebaseAuth= FirebaseAuth.getInstance();
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=teacherName.getText().toString();
                String email=emailId.getText().toString();
            }
        });
    }
}