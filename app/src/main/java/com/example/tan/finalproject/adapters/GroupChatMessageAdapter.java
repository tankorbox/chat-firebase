package com.example.tan.finalproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tan.finalproject.R;
import com.example.tan.finalproject.models.Group;
import com.example.tan.finalproject.models.GroupMessage;
import com.example.tan.finalproject.models.Message;
import com.example.tan.finalproject.utilities.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tan on 2/28/2017.
 */

public class GroupChatMessageAdapter  extends RecyclerView.Adapter<GroupChatMessageAdapter.MyViewHolder>{


    public static final int SENDER = 0;
    public static final int RECEIVER = 1;
    List<GroupMessage> listData = new ArrayList<>();
    Context context;

    //contructor
    public GroupChatMessageAdapter(List<GroupMessage> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    //create adapter view
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.groupchat_item_purple, parent, false);
            MyViewHolder vh = new MyViewHolder((RelativeLayout) v);
            return vh;
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.groupchat_item_green, parent, false);
            MyViewHolder vh = new MyViewHolder((RelativeLayout) v);
            return vh;
        }
    }

    //bind data onto recyclerview item
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        GroupMessage groupMessage = listData.get(position);
        holder.tvMsg.setText(groupMessage.getMsg());
        if (position==0) holder.tvTimeAndSender.setText(TimeUtils.longDateToString_TypeA(groupMessage.getTime()));
        else if (groupMessage.getTime()-listData.get(position-1).getTime()>60000){
            holder.tvTimeAndSender.setText(TimeUtils.longDateToString_TypeA(groupMessage.getTime()));
        }
        if (groupMessage.isLeft()) {
            holder.tvSender.setVisibility(View.VISIBLE);
            holder.tvSender.setText(groupMessage.getSenderName());
        }
        else {
            holder.tvSender.setVisibility(View.GONE);
        }
    }

    //remove an item method
    public void removeItem(int position) {
        listData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listData.size());
    }

    //return number of items
    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMsg;
        public TextView tvTimeAndSender;
        public TextView tvSender;
        public MyViewHolder(RelativeLayout v) {
            super(v);
            tvMsg = (TextView) v.findViewById(R.id.tvMsg);
            tvTimeAndSender = (TextView) v.findViewById(R.id.tvTimeAndSender);
            tvSender = (TextView) v.findViewById(R.id.tvSender);
        }
    }

    @Override
    public int getItemViewType(int position) {
        GroupMessage groupMessage = listData.get(position);
        if (groupMessage.isLeft()) {
            return RECEIVER;
        } else {
            return SENDER;
        }
    }

    public void update(GroupMessage msg) {
        listData.add(msg);
        this.notifyDataSetChanged();
    }

}
