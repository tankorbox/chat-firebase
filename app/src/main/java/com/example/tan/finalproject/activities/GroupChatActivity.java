package com.example.tan.finalproject.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tan.finalproject.R;
import com.example.tan.finalproject.adapters.GroupChatMessageAdapter;
import com.example.tan.finalproject.dialogs.GroupDetailDialog;
import com.example.tan.finalproject.models.GroupMessage;
import com.example.tan.finalproject.services.InstantMessageService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tan on 5/9/2017.
 */

public class GroupChatActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    GroupChatMessageAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    List<GroupMessage> messageList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    ImageButton btnSend;
    TextInputEditText edMessage;
    TextView tvFriendName;
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);


        //get group infor
        final String groupID = getIntent().getStringExtra("group_id");
        final String groupName = getIntent().getStringExtra("group_name");

        //get user's info
        sharedPreferences = getSharedPreferences("my_data",MODE_PRIVATE);
        Log.i("tag1",sharedPreferences.getString("userid","")+"preferen");
        final String userID = sharedPreferences.getString("userid","");
        final String userName = sharedPreferences.getString("name","");

        //set toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvFriendName = (TextView) findViewById(R.id.tvFriendName);
        tvFriendName.setText(groupName);
        tvFriendName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GroupDetailDialog(GroupChatActivity.this,groupName).popup();
            }
        });

        //config recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.rvGroupChatList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GroupChatMessageAdapter(messageList, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //update recyclerview's data
        final DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
        dr.child("group_message").child(groupID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GroupMessage message = dataSnapshot.getValue(GroupMessage.class);
                if (message.getSenderID().equals(userID)) {
                    message.setLeft(false);
                }
                else {
                    message.setLeft(true);
                }
                mAdapter.update(message);
                mRecyclerView.scrollToPosition(messageList.size()-1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //button send message event listener-handler
        btnSend = (ImageButton) findViewById(R.id.btnSend);
        edMessage = (TextInputEditText) findViewById(R.id.edMsg);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edMessage.getText().toString().equals("")) {
                    GroupMessage message = new GroupMessage(userID,userName,edMessage.getText().toString().trim(),new Date().getTime(),false);
                    dr.child("group_message").child(groupID).push().setValue(message);
                    edMessage.setText("");
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount());
                }
            }
        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(GroupChatActivity.this, InstantMessageService.class);
        stopService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(GroupChatActivity.this, InstantMessageService.class);
        startService(intent);
    }
}

