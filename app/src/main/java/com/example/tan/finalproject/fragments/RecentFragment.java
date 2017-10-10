package com.example.tan.finalproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.tan.finalproject.R;
import com.example.tan.finalproject.adapters.RecentChatAdapter;
import com.example.tan.finalproject.models.RecentChat;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tan on 3/23/2017.
 */

public class RecentFragment extends Fragment {
    SharedPreferences mPreferences;
    RecentChatAdapter mAdapter;
    RecyclerView mRecyclerView;
    List<RecentChat> listData;
    String userID;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_recent,null);

        //get logged user info
        mPreferences = getActivity().getSharedPreferences("my_data",Context.MODE_PRIVATE);
        userID = mPreferences.getString("userid","");

        //create references for ui components
        createReferences(view);

        //init recent list
        listData = new ArrayList<>();

        //config recyclerview
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvRecentList);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecentChatAdapter(listData,getActivity());
        mRecyclerView.setAdapter(mAdapter);

        //load data from firebase
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
        dr.child("recents").child(userID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RecentChat recentChat = dataSnapshot.getValue(RecentChat.class);
                mAdapter.add(recentChat);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                RecentChat recentChat = dataSnapshot.getValue(RecentChat.class);
                mAdapter.update(recentChat);
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
        return view;
    }

    private void createReferences(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }
}
