package com.example.tan.finalproject.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tan.finalproject.R;
import com.example.tan.finalproject.activities.ChatActivity;
import com.example.tan.finalproject.activities.GroupChatActivity;
import com.example.tan.finalproject.dialogs.GroupDetailDialog;
import com.example.tan.finalproject.models.Group;
import com.example.tan.finalproject.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tan on 2/28/2017.
 */

public class GroupListAdapter  extends RecyclerView.Adapter<GroupListAdapter.MyViewHolder>{
    List<Group> listData = new ArrayList<>();
    Context context;

    //contructor
    public GroupListAdapter(List<Group> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    //create adapter view
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_group_list,parent,false);
        return new MyViewHolder(view);
    }

    //bind data onto recyclerview item
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Group group = listData.get(position);
        holder.tvGroupName.setText(group.getGroupName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start group chat
                Intent intent = new Intent(context,GroupChatActivity.class);
                intent.putExtra("group_id",group.getGroupID());
                intent.putExtra("group_name",group.getGroupName());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new GroupDetailDialog(context,group.getGroupName()).popup();
                return false;
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

    public void update(Group group) {
        listData.add(group);
        this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvGroupName;
        //event lister registration
        public MyViewHolder(final View itemView) {
            super(itemView);
            tvGroupName = (TextView) itemView.findViewById(R.id.tvGroupName);
        }
    }
}
