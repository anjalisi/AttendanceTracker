package com.example.attendancetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    Spinner dept;
    TextView loginSend;
    EditText emailId;
    EditText teacherName;
    EditText password;
    Button signupBtn;
    private FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    //Registration method
    private void registerUser(){
        //Get the data
        final String teacher= teacherName.getText().toString().trim();
        final String email= emailId.getText().toString().trim();
        String pass= password.getText().toString().trim();
        final String depart = dept.getSelectedItem().toString().trim();

        if(teacher.isEmpty())
        {
            teacherName.setError("Name is required");
            teacherName.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        //Adding password check
        if(pass.length()<6){
            password.setError("Minimum length of password should be 6");
            password.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            emailId.setError("Email is required");
            emailId.requestFocus();
            return;
        }
        //Adding email checks
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailId.setError("Enter a valid email");
            emailId.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    //we can now store additional fields
                    TeacherUser user= new TeacherUser(teacher, email, depart);
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent dashboard= new Intent(getApplicationContext(), MainDashboard.class);
                                dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(dashboard);
                                finish();
                            }
                            else{
                                Toast.makeText(Signup.this, "Registration Unsuccessful! Try Again"+depart, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else
                    Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!=null){
            //Means the user has already signed in

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //connecting
        progressBar=(ProgressBar)findViewById(R.id.progBar);
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
                registerUser();
            }
        });
        //Redirect to login
        loginSend=(TextView) findViewById(R.id.loginBtn);
        loginSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
    }
    public void openLogin() {
        Intent login = new Intent(this, Login.class);
        startActivity(login);

    }
}