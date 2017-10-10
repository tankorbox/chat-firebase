package com.example.tan.finalproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class UserPickerAdapter  extends RecyclerView.Adapter<UserPickerAdapter.MyViewHolder>{
    List<User> listData = new ArrayList<>();
    Context context;

    //contructor
    public UserPickerAdapter(List<User> listData, Context context) {
        this.listData = listData;
        for (User user: listData) {
            user.setPick(false);
        }
        this.context = context;
    }

    //create adapter view
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_user_picker_list,parent,false);
        return new MyViewHolder(view);
    }

    //bind data onto recyclerview item
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final User user = listData.get(position);
        holder.tvName.setText(user.getName());
        holder.cbPick.setOnCheckedChangeListener(null);
        if (user.isPick()) holder.cbPick.setChecked(true);
        else holder.cbPick.setChecked(false);
        holder.cbPick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    user.setPick(true);
                }
                else {
                    user.setPick(false);
                }
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
        user.setPick(false);
        listData.add(user);
        this.notifyDataSetChanged();
    }

    public ArrayList<User> getPickedUsers() {
        ArrayList<User> returnList = new ArrayList<>();
        for (User user: listData) {
            if (user.isPick()) returnList.add(user);
        }
        return returnList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        CheckBox cbPick;
        //event lister registration
        public MyViewHolder(final View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            cbPick = (CheckBox) itemView.findViewById(R.id.cbPick);
        }
    }
}
