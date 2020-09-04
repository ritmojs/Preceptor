package com.retical.virtual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.MoreObjects;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StudentLogin extends AppCompatActivity implements View.OnClickListener {

    TextView mbtn;
    private Button mbtn1;
    private EditText user;
    private EditText pass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_login);
        mbtn=findViewById(R.id.signUP);

        mbtn1 = findViewById(R.id.signin_btn);
        user = findViewById(R.id.signin_email);
        pass = findViewById(R.id.signin_pass);
        mAuth = FirebaseAuth.getInstance();


        mbtn.setOnClickListener(this);

        mbtn1.setOnClickListener(this);

    }
    private void userLogin()
    {
        String email =user.getText().toString().trim();
        String password=pass.getText().toString().trim();
        if(email.isEmpty()){
            user.setError("Email is required");
            user.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            user.setError("Please enter a valid email");
            user.requestFocus();
            return;
        }
        if(password.isEmpty()){
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            pass.setError("Minimum length of password is 6");
            pass.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    boolean emailVerified = mAuth.getCurrentUser().isEmailVerified();
                    if(emailVerified==true) {


                        finish();
                        Intent intent=new Intent(StudentLogin.this,Home.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(StudentLogin.this, "Email Not Verified. Verification link Sent..", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            boolean emailVerified = mAuth.getCurrentUser().isEmailVerified();
            if(emailVerified==true) {

                finish();
                Intent intent = new Intent(StudentLogin.this,Home.class);
                String Email = mAuth.getCurrentUser().getEmail();
                intent.putExtra("Email", Email);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "Verify Your Email First", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        { case R.id.signin_btn:
            userLogin();

            break;
            case R.id.signUP:
                finish();
                Intent intent=new Intent (StudentLogin.this,RegisterStudent.class);
                startActivity(intent);
                break;
        }
    }
}
