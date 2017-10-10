package com.example.tan.finalproject.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tan.finalproject.R;
import com.example.tan.finalproject.adapters.UserPickerAdapter;
import com.example.tan.finalproject.models.User;
import com.example.tan.finalproject.services.InstantMessageService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.tan.finalproject.common.Constants.USER_PICKER_REQUEST_CODE;
import static com.example.tan.finalproject.common.Constants.USER_PICKER_RESULT_CODE;

/**
 * Created by Tan on 5/9/2017.
 */

public class UserPickerActivity extends AppCompatActivity {
    List<User> mUserList;
    RecyclerView mRecyclerView;
    UserPickerAdapter mAdapter;
    Button btnSubmit, btnCancel;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_picker);

        //open share preference to read logged in user info
        mPreferences = getSharedPreferences("my_data",MODE_PRIVATE);
        final String userID = mPreferences.getString("userid","");
        final String userName  = mPreferences.getString("name","");
        final String userEmail = mPreferences.getString("email","");

        //init list user
        mUserList = new ArrayList<>();

        //config recyclerview with adapter
        mRecyclerView = (RecyclerView) findViewById(R.id.rvUserPicker);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new UserPickerAdapter(mUserList, this);
        mRecyclerView.setAdapter(mAdapter);

        //load data from firebase into recyclerview
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
        dr.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                if (!user.getUserId().equals(userID)) mAdapter.update(user);
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

        //button click event listener and handler
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                ArrayList<User> pickedList = mAdapter.getPickedUsers();
                //add logged user into group
                pickedList.add(new User(userName,userID,"",userEmail,true));
                for (User user: pickedList) {
                    Log.i("tag2",user.getName());
                }
                bundle.putSerializable("picked_list",pickedList);
                intent.putExtras(bundle);
                setResult(USER_PICKER_RESULT_CODE, intent);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(UserPickerActivity.this, InstantMessageService.class);
        stopService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(UserPickerActivity.this, InstantMessageService.class);
        startService(intent);
    }
}
