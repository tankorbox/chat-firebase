package com.example.tan.finalproject.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tan on 5/10/2017.
 */

public abstract class FirebaseConnection {

    public DatabaseReference getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference makeChild(DatabaseReference dr,String str) {
        return dr.child(str);
    }


}
