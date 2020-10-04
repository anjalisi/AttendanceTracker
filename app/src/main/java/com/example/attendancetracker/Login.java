package com.example.attendancetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    TextView goToSignUp;
    Button loginBtn;
    EditText email, pass;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    private void loginUser(){
        final String emailID= email.getText().toString().trim();
        String password= pass.getText().toString().trim();

        if(password.isEmpty())
        {
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        //Adding password check
        if(password.length()<6){
            pass.setError("Minimum length of password should be 6");
            pass.requestFocus();
            return;
        }
        if(emailID.isEmpty())
        {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        //Adding email checks
        if(!Patterns.EMAIL_ADDRESS.matcher(emailID).matches()){
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(emailID, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent dashboard= new Intent(getApplicationContext(), MainDashboard.class);
                    dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(dashboard);
                }
                else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        progressBar=(ProgressBar)findViewById(R.id.progBar);
        loginBtn=(Button)findViewById(R.id.loginButton);
        email=(EditText)findViewById(R.id.emailID);
        pass=(EditText)findViewById(R.id.password);
        //Redirect to signup
        goToSignUp=(TextView) findViewById(R.id.signupButton);
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }
    public void openSignUp(){
        Intent signupp= new Intent(this, Signup.class);
        startActivity(signupp);
    }
}