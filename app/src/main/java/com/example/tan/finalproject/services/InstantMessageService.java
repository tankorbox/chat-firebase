package com.example.tan.finalproject.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.tan.finalproject.models.RecentChat;
import com.example.tan.finalproject.notifications.NewMessageNotification;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Tan on 5/9/2017.
 */

public class InstantMessageService extends Service {
    SharedPreferences mPreferences;
    ArrayList<String> userIdList = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPreferences = getApplicationContext().getSharedPreferences("my_data", MODE_PRIVATE);
        final String userID = mPreferences.getString("userid", "");
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
        dr = dr.child("recents").child(userID);
        dr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                RecentChat recentChat = dataSnapshot.getValue(RecentChat.class);
                if (!recentChat.isRead()) {
                    if (!checkExist(recentChat.getFriendID())) {
                        userIdList.add(recentChat.getFriendID());
                        NewMessageNotification.showNotification(getApplicationContext(), recentChat.getFriendName());
                    }
                }
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

        return START_STICKY;
    }

    private boolean checkExist(String friendID) {
        for (String s : userIdList) {
            if (s.compareTo(friendID)==0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userIdList.clear();
    }
}
