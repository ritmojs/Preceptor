package com.retical.virtual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountActivity extends AppCompatActivity {

    private Button StudentBtn,TeacherBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        //Initialization of Components
     StudentBtn=findViewById(R.id.StudentBtn);
     TeacherBtn=findViewById(R.id.TeacherBtn);






      StudentBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent=new Intent(AccountActivity.this,StudentLogin.class);
              startActivity(intent);
          }
      });

        TeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AccountActivity.this,TeacherLogin.class);
                startActivity(intent);
            }
        });











    }
}