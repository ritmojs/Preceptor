package com.retical.virtual;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;




public class Home extends AppCompatActivity  {
   private TextView mRoom,mJoin,mCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialization
        mRoom=findViewById(R.id.Room);
        mJoin=findViewById(R.id.JoinRoom);
        mCreate=findViewById(R.id.CreateRoom);
        //Initialization ends




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

//listener code neds



    }

}
