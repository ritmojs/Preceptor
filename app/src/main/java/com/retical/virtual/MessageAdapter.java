package com.retical.virtual;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>
{
    private List<Messages> userMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef,EduRef;
    public String EduStarValue;
    private Context context;
    public String messageRoomId;
    ItemClickListener itemClickListener;
    public MessageAdapter (List<Messages> userMessagesList,String messageRoomID)
    {this.messageRoomId=messageRoomID;
        this.userMessagesList = userMessagesList;
        this.itemClickListener = itemClickListener;
    }




    public class MessageViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        public TextView senderMessageText, receiverMessageText,receiverUserName,EduStar;

        public CircleImageView receiverProfileImage;
        public ImageView messageSenderPicture, messageReceiverPicture;


        public MessageViewHolder(@NonNull View itemView)
        {
            super(itemView);
            receiverUserName=itemView.findViewById(R.id.UserName);
            EduStar=itemView.findViewById(R.id.EduStar);
            senderMessageText = (TextView) itemView.findViewById(R.id.sender_messsage_text);
            receiverMessageText = (TextView) itemView.findViewById(R.id.receiver_message_text);
            receiverProfileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_image);
            messageReceiverPicture = itemView.findViewById(R.id.message_receiver_image_view);
            messageSenderPicture = itemView.findViewById(R.id.message_sender_image_view);
        }

        @Override
        public void onClick(View view) {

        }
    }




    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_message_layout, viewGroup, false);

        mAuth = FirebaseAuth.getInstance();

        return new MessageViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder messageViewHolder, int i)
    {
        String messageSenderId = mAuth.getCurrentUser().getUid();
        final Messages messages = userMessagesList.get(i);

        final String fromUserID = messages.getFrom();

        String fromMessageType = messages.getType();

        usersRef = FirebaseDatabase.getInstance().getReference().child("User").child(fromUserID);


        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.hasChild("EduStar"))
                {
                     EduStarValue = dataSnapshot.child("EduStar").getValue().toString();

                    //Picasso.get().load(receiverImage).placeholder(R.drawable.profile_image).into(messageViewHolder.receiverProfileImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        messageViewHolder.receiverUserName.setVisibility(View.GONE);
        messageViewHolder.EduStar.setVisibility(View.GONE);
        messageViewHolder.receiverMessageText.setVisibility(View.GONE);
        messageViewHolder.receiverProfileImage.setVisibility(View.GONE);
        messageViewHolder.senderMessageText.setVisibility(View.GONE);
        messageViewHolder.messageSenderPicture.setVisibility(View.GONE);
        messageViewHolder.messageReceiverPicture.setVisibility(View.GONE);


        if (fromMessageType.equals("text"))
        {
            if (fromUserID.equals(messageSenderId))
            {
                messageViewHolder.senderMessageText.setVisibility(View.VISIBLE);

                messageViewHolder.senderMessageText.setBackgroundResource(R.drawable.sender_messages_layout);
                messageViewHolder.senderMessageText.setTextColor(Color.WHITE);
                messageViewHolder.senderMessageText.setText(messages.getMessage() + "\n \n" + messages.getTime() + " - " + messages.getDate());
            }
            else
            {
                messageViewHolder.receiverProfileImage.setVisibility(View.VISIBLE);
                messageViewHolder.receiverMessageText.setVisibility(View.VISIBLE);
                messageViewHolder.receiverUserName.setVisibility(View.VISIBLE);
                messageViewHolder.receiverUserName.setTextColor(Color.WHITE);
                messageViewHolder.EduStar.setVisibility(View.VISIBLE);
                EduRef=FirebaseDatabase.getInstance().getReference().child("Room").child(messageRoomId).child("Members").child(fromUserID);
                EduRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        messageViewHolder.EduStar.setText("⭐"+dataSnapshot.getValue().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

          usersRef.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  messageViewHolder.receiverUserName.setText(dataSnapshot.child("Name").getValue().toString());
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });





                messageViewHolder.receiverMessageText.setBackgroundResource(R.drawable.receiver_messages_layout);
                messageViewHolder.receiverMessageText.setTextColor(Color.BLACK);
                messageViewHolder.receiverMessageText.setText(messages.getMessage() + "\n \n" + messages.getTime() + " - " + messages.getDate());
            }
        }
    }




    @Override
    public int getItemCount()
    {
        return userMessagesList.size();
    }






}

