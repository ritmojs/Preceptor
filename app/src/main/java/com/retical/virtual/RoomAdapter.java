package com.retical.virtual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder>  {
    RecyclerView recyclerView;
    Context context;
    int position;
    ItemClickListener itemClickListener;
    static ArrayList<String> items=new ArrayList<>();
    static ArrayList<String> urls=new ArrayList<>();


    public void update(String question, String RoomID) {

        items.add(question);
        urls.add(RoomID);
        notifyDataSetChanged();
    }



    public RoomAdapter(RecyclerView recyclerView, Context context, ArrayList<String> items, ArrayList<String> urls, ItemClickListener itemClickListener) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.items = items;
        this.urls = urls;
        this.itemClickListener = itemClickListener;
    }


    @Override
    public int getItemCount() {

        return items.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.items,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Question.setText(items.get(position));
        holder.roomid.setText(urls.get(position));
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        TextView Question,roomid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Question=itemView.findViewById(R.id.RoomName);
            roomid=itemView.findViewById(R.id.roomid);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition());
        }
    }





}
