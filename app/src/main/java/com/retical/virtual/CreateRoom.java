package com.retical.virtual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class CreateRoom extends AppCompatActivity {
    private TextView RoomID;
    private EditText RoomName;
    private Button mbtn;
    private DatabaseReference RootRef;
    String text;
String mRoomID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        RoomID = findViewById(R.id.CreateRoomID);
        RoomName=findViewById(R.id.RoomName);
        RootRef = FirebaseDatabase.getInstance().getReference();
        mbtn = findViewById(R.id.Create);

        Random r = new Random();
        int n = 100000 + r.nextInt(900000);
        mRoomID = String.valueOf(n);

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = RoomName.getText().toString();
                RootRef.child("Room").child(mRoomID).setValue(text);
                RoomID.setVisibility(View.VISIBLE);
                RoomID.setText(mRoomID);
                Intent intent=new Intent(CreateRoom.this,Room.class);
                startActivity(intent);

            }
        });


    }




}

