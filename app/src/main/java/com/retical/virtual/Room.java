package com.retical.virtual;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Room extends AppCompatActivity {
private RecyclerView recyclerView;
private FirebaseAuth mAuth;
    String RoomID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        mAuth=FirebaseAuth.getInstance();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("User").child(mAuth.getCurrentUser().getUid()).child("Room").child("Joined");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String  RoomID=dataSnapshot.getKey();
                String question=dataSnapshot.getValue(String.class);


                ((RoomAdapter)recyclerView.getAdapter()).update(question,RoomID);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(Room.this));
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position ) {
                Intent intent = new Intent(Room.this,Chat.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        };
        RoomAdapter myAdapter=new RoomAdapter(recyclerView,Room.this,new ArrayList<String>(),new ArrayList<String>(),itemClickListener);
        recyclerView.setAdapter(myAdapter);





    }
}