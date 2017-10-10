package com.example.tan.finalproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tan.finalproject.R;
import com.example.tan.finalproject.activities.ChatActivity;
import com.example.tan.finalproject.models.Message;
import com.example.tan.finalproject.models.RecentChat;
import com.example.tan.finalproject.models.User;
import com.example.tan.finalproject.utilities.TimeUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

/**
 * Created by Tan on 3/23/2017.
 */

public class RecentChatAdapter extends RecyclerView.Adapter<RecentChatAdapter.MyViewHolder> {
    List<RecentChat> listData;
    Context context;
    SharedPreferences mPreferences;
    String userid;

    public RecentChatAdapter(List<RecentChat> listData, Context context) {
        this.listData = listData;
        this.context = context;
        mPreferences = context.getSharedPreferences("my_data",Context.MODE_PRIVATE);
        userid = mPreferences.getString("userid","");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_recent_chat, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final RecentChat recentChat = listData.get(position);
        holder.tvName.setText(recentChat.getFriendName());
        holder.tvLastedMsg.setText(recentChat.getLastedMsg());
        if (!recentChat.isRead()) {
            holder.tvName.setTypeface(null,Typeface.BOLD);
            holder.tvLastedMsg.setTypeface(null, Typeface.BOLD_ITALIC);
            holder.tvLastedMsg.setAlpha((float) 0.87);
        }
        else {
            holder.tvName.setTypeface(null,Typeface.NORMAL);
            holder.tvLastedMsg.setTypeface(null,Typeface.ITALIC);
            holder.tvLastedMsg.setAlpha((float) 0.5);
        }
        if (TimeUtils.getCurrentTimeInLong() - recentChat.getTime() < 8640000) {
            holder.tvTime.setText(TimeUtils.longDateToString_TypeC(recentChat.getTime()));
        } else {
            holder.tvTime.setText(TimeUtils.longDateToString_TypeD(recentChat.getTime()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("chat", new User(recentChat.getFriendName(), recentChat.getFriendID(), "", "", false));
                intent.putExtras(bundle);
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
                dr.child("recents").child(recentChat.getFriendID()).child(userid).child("read").setValue(true);
                dr.child("recents").child(userid).child(recentChat.getFriendID()).child("read").setValue(true);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void add(RecentChat recentChat) {
        listData.add(recentChat);
        sortData();
        this.notifyDataSetChanged();
    }

    public void update(RecentChat recentChat) {
        for (int i = 0; i < listData.size(); i++) {
            RecentChat rc = listData.get(i);
            if (rc.getFriendID().equals(recentChat.getFriendID()) && rc.getFriendName().equals(recentChat.getFriendName())) {
                listData.remove(i);
                listData.add(i, recentChat);
                break;
            }
        }
        Log.i("tag1","kiki");
        sortData();
        this.notifyDataSetChanged();
    }

    private void sortData() {
        for (int i = 0; i <= listData.size() - 2; i++) {
            for (int j = i + 1; j <= listData.size() - 1; j++) {
                if (listData.get(j).getTime() > listData.get(i).getTime()) {
                    RecentChat rc = listData.get(j);
                    listData.set(j, listData.get(i));
                    listData.set(i, rc);
                }
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvLastedMsg;
        TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvLastedMsg = (TextView) itemView.findViewById(R.id.tvLastedMsg);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }
}