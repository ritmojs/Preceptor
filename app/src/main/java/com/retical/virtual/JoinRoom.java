package com.retical.virtual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinRoom extends AppCompatActivity {
    private EditText RoomID;
    private Button mbtn;
    private DatabaseReference RootRef;
    String text;
    private FirebaseAuth mAuth;
    private  String RoomName,EduStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_room);
        RoomID = findViewById(R.id.enter_peuID);
        RootRef = FirebaseDatabase.getInstance().getReference();
        mbtn = findViewById(R.id.search);
        mAuth=FirebaseAuth.getInstance();
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = RoomID.getText().toString();

                verifyRoomExistance();


            }
        });


    }

    private void verifyRoomExistance() {


        RootRef.child("Room").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(text).exists()) {
                    RoomName=dataSnapshot.child(text).child("RoomName").getValue().toString();
                    RootRef.child("User").child(mAuth.getCurrentUser().getUid()).child("Room").child("Joined").child(text).setValue(RoomName);
                    RootRef.child("Room").child(text).child("Members").child(mAuth.getCurrentUser().getUid()).setValue(0);

                    finish();

                }
                else
                {
                    RoomID.setError("Room Not Exist");

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

