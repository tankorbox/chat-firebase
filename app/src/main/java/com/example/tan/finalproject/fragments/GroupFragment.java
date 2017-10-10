package com.example.tan.finalproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.tan.finalproject.R;
import com.example.tan.finalproject.activities.UserPickerActivity;
import com.example.tan.finalproject.adapters.GroupListAdapter;
import com.example.tan.finalproject.models.Group;
import com.example.tan.finalproject.models.User;
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
 * Created by Tan on 3/23/2017.
 */

public class GroupFragment extends Fragment {
    List<Group> mGroupList;
    SharedPreferences mPreferences;
    RecyclerView mRecyclerView;
    GroupListAdapter mAdapter;
    FloatingActionButton fabGroupAdd;
    ProgressBar mProgressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group,null);

        //init list user
        mGroupList = new ArrayList<>();

        //create references for ui components
        createReferences(view);

        //get user info
        mPreferences = getActivity().getSharedPreferences("my_data", Context.MODE_PRIVATE);
        final String userName = mPreferences.getString("name","");

        //config recyclerview
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvGroupList);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new GroupListAdapter(mGroupList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        //load data from firebase into recyclerview
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
        dr.child("groups").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Group tempGroup = dataSnapshot.getValue(Group.class);
                if (tempGroup.getGroupName().contains(userName)) {
                    Group group = new Group(dataSnapshot.getKey(),tempGroup.getGroupName(),tempGroup.getUserList());
                    mAdapter.update(group);
                    mProgressBar.setVisibility(View.GONE);
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

        //floating action button click listener and handler
        fabGroupAdd = (FloatingActionButton) view.findViewById(R.id.fabGroupAdd);
        fabGroupAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserPickerActivity.class);
                startActivityForResult(intent,USER_PICKER_REQUEST_CODE);
            }
        });


        return view;
    }

    private void createReferences(View view) {
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    //get picked contacts from UserPickerActivity to create a new group
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==USER_PICKER_REQUEST_CODE&&resultCode==USER_PICKER_RESULT_CODE&&data!=null) {
            ArrayList<User> pickedUserList = new ArrayList<>();
            pickedUserList = (ArrayList<User>) data.getExtras().getSerializable("picked_list");
            if (pickedUserList==null||pickedUserList.size()==1) return;
            String groupName="";
            ArrayList userList= new ArrayList<>();
            for (User user : pickedUserList) {
                Log.i("tag3",user.getName());
                groupName += user.getName();
                userList.add(user);
                if (!user.equals(pickedUserList.get(pickedUserList.size()-1))) {
                    groupName +=",";
                }
            }
            DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
            dr.child("groups").push().setValue(new Group(groupName,userList));
        }
    }
}
