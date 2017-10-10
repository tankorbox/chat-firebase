package com.example.tan.finalproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tan.finalproject.R;
import com.example.tan.finalproject.models.Message;
import com.example.tan.finalproject.utilities.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tan on 2/28/2017.
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MyViewHolder> {
    public static final int SENDER = 0;
    public static final int RECEIVER = 1;
    List<Message> listData = new ArrayList<>();
    Context context;

    //contructor
    public ChatMessageAdapter(List<Message> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    //create adapter view
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_purple, parent, false);
            MyViewHolder vh = new MyViewHolder((RelativeLayout) v);
            return vh;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_green, parent, false);
            MyViewHolder vh = new MyViewHolder((RelativeLayout) v);
            return vh;
        }
    }

    //bind data onto recyclerview item
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Message mess = listData.get(position);
        holder.tvMsg.setText(mess.getMsg());
        if (position == 0) holder.tvTime.setText(TimeUtils.longDateToString_TypeA(mess.getTime()));
        else if (mess.getTime() - listData.get(position - 1).getTime() > 60000) {
            holder.tvTime.setText(TimeUtils.longDateToString_TypeA(mess.getTime()));
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
        public TextView tvTime;

        public MyViewHolder(RelativeLayout v) {
            super(v);
            tvMsg = (TextView) v.findViewById(R.id.tvMsg);
            tvTime = (TextView) v.findViewById(R.id.tvTime);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = listData.get(position);
        if (message.isLeft()) {
            return RECEIVER;
        } else {
            return SENDER;
        }
    }

    public void update(Message msg) {
        listData.add(msg);
        this.notifyDataSetChanged();
    }

}
