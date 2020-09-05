package com.retical.virtual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterStudent extends AppCompatActivity implements View.OnClickListener{


    EditText user,pass,name;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);
        mAuth = FirebaseAuth.getInstance();
        mRef= FirebaseDatabase.getInstance().getReference();
        user=(EditText)findViewById(R.id.memail);
        pass=(EditText)findViewById(R.id.pass);
        name=(EditText)findViewById(R.id.name) ;
        findViewById(R.id.SignIn).setOnClickListener(this);
        findViewById(R.id.signin).setOnClickListener(this);
    }
    private void registerUser()
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
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name.getText().toString())
                            .build();
                    mAuth.getCurrentUser().updateProfile(profileUpdates);

                    //Updation In database
                    mRef.child("User").child(mAuth.getCurrentUser().getUid()).child("Name").setValue(name.getText().toString());
                    mRef.child("User").child(mAuth.getCurrentUser().getUid()).child("Type").setValue("Student");
                    mRef.child("User").child(mAuth.getCurrentUser().getUid()).child("Password").setValue(pass.getText().toString());
                    mRef.child("User").child(mAuth.getCurrentUser().getUid()).child("Email").setValue(user.getText().toString());
                    mRef.child("User").child(mAuth.getCurrentUser().getUid()).child("EduStar").setValue(0.0);
                //    mRef.child("User").child(mAuth.getCurrentUser().getUid()).child("Room").child("Joined").child("").setValue(user.getText().toString());


                    Toast.makeText(RegisterStudent.this, "Email Register Successful", Toast.LENGTH_SHORT).show();
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                finish();

                                Toast.makeText(RegisterStudent.this, "Check Your Email for Verification Link", Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(RegisterStudent.this,StudentLogin.class);
                                startActivity(intent);

                            }

                        }
                    });
                      /*  finish();
                        Intent intent=new Intent(SignUp.this,Main2Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        String Email=user.getText().toString().trim();
                        intent.putExtra("Email",Email);
                        startActivity(intent);*/

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        { case R.id.signin:
            registerUser();
            break;
            case R.id.SignIn:
                finish();
                Intent intent=new Intent(RegisterStudent.this,StudentLogin.class);
                startActivity(intent);
                break;
        }
    }
}

