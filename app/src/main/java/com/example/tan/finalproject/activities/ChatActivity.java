package com.example.tan.finalproject.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.tan.finalproject.adapters.ChatMessageAdapter;
import com.example.tan.finalproject.database.PreferenceConnection;
import com.example.tan.finalproject.models.Message;
import com.example.tan.finalproject.models.RecentChat;
import com.example.tan.finalproject.models.User;
import com.example.tan.finalproject.services.InstantMessageService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView mRecyclerView;
    ChatMessageAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    List<Message> mList = new ArrayList<>();
    ImageButton btnSend;
    TextInputEditText edMessage;
    TextView tvFriendName;
    Toolbar mToolbar;
    DatabaseReference mDatabaseReference;
    String userID,userName;
    User friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //create references
        references();

        //get friend chat information
        friend = (User) getIntent().getExtras().getSerializable("chat");

        //get userid information
        userID = new PreferenceConnection(this).getUserID();
        userName = new PreferenceConnection(this).getUserName();
        Log.i("tag1",userID+userName);

        //toolbar config
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvFriendName = (TextView) findViewById(R.id.tvFriendName);
        tvFriendName.setText(friend.getName());

        //recyclerview config
        mRecyclerView = (RecyclerView) findViewById(R.id.ChatList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ChatMessageAdapter(mList, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //recyclerview's data update
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child("messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message mess = dataSnapshot.getValue(Message.class);
                if (mess.getSender().equals(userID)&&mess.getReceiver().equals(friend.getUserId())) {
                    mAdapter.update(new Message(mess.getSender(),mess.getReceiver(),mess.getMsg(),mess.getTime(),false));
                    mRecyclerView.scrollToPosition(mList.size()-1);
                }
                else if (mess.getSender().equals(friend.getUserId())&&mess.getReceiver().equals(userID)) {
                    mAdapter.update(new Message(mess.getSender(),mess.getReceiver(),mess.getMsg(),mess.getTime(),true));
                    mRecyclerView.scrollToPosition(mList.size()-1);
                }
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



        //button send message
        btnSend.setOnClickListener(this);

        //set keyboard auto-adjust when chatting
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void references() {
        btnSend = (ImageButton) findViewById(R.id.btnSend);
        edMessage = (TextInputEditText) findViewById(R.id.edMsg);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }


    //stop service which listen new message when onResume()
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(ChatActivity.this, InstantMessageService.class);
        stopService(intent);
    }

    //start service which listen new message when onPause()
    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(ChatActivity.this, InstantMessageService.class);
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnSend)) {
            if (!edMessage.getText().toString().equals("")) {
                Message mess = new Message(userID,friend.getUserId(),edMessage.getText().toString().trim(),new Date().getTime());
                mDatabaseReference.child("messages").push().setValue(mess);
                mDatabaseReference.child("recents").child(userID).child(friend.getUserId()).setValue(new RecentChat(friend.getUserId(),friend.getName(),edMessage.getText().toString(),mess.getTime(),true));
                mDatabaseReference.child("recents").child(friend.getUserId()).child(userID).setValue(new RecentChat(userID,userName,edMessage.getText().toString(),mess.getTime(),false));
                edMessage.setText("");
            }
        }
    }
}
