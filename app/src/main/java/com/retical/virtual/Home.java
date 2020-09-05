package com.retical.virtual;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class Home extends AppCompatActivity  {
   private TextView mRoom,mJoin,mCreate,mName;
   private Button  mprofile;
   FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
mAuth=FirebaseAuth.getInstance();
        //Initialization
        mRoom=findViewById(R.id.Room);
        mJoin=findViewById(R.id.JoinRoom);
        mCreate=findViewById(R.id.CreateRoom);
        mprofile=findViewById(R.id.profile);
        mName=findViewById(R.id.UserName);
        //Initialization ends


mName.setText(mAuth.getCurrentUser().getDisplayName());

        //listner code starts
        mRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,Room.class);
                startActivity(intent);


            }
        });
        mJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,JoinRoom.class);
                startActivity(intent);
            }
        });
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,CreateRoom.class);
                startActivity(intent);
            }
        });
        mprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Profile.class);
                startActivity(intent);
            }
        });

//listener code neds



    }

}
