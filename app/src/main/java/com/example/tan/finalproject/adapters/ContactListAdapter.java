package com.example.tan.finalproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tan.finalproject.R;
import com.example.tan.finalproject.activities.ChatActivity;
import com.example.tan.finalproject.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tan on 2/28/2017.
 */

public class ContactListAdapter  extends RecyclerView.Adapter<ContactListAdapter.MyViewHolder>{
    List<User> listData = new ArrayList<>();
    Context context;

    //contructor
    public ContactListAdapter(List<User> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    //create adapter view
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_contact_list,parent,false);
        return new MyViewHolder(view);
    }

    //bind data onto recyclerview item
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final User user = listData.get(position);
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("chat",user);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    //remove an item method
    public void removeItem(int position) {
        listData.remove(position);
        this.notifyItemRemoved(position);
    }

    //return number of items
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void update(User user) {
        listData.add(user);
        this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView name;
        TextView email;
        //event lister registration
        public MyViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvName);
            ivAvatar =(ImageView) itemView.findViewById(R.id.ivAvatar);
            email = (TextView) itemView.findViewById(R.id.tvEmail);
        }
    }
}
