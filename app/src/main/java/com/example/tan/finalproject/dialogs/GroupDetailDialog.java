package com.example.tan.finalproject.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * Created by Tan on 5/10/2017.
 */

public class GroupDetailDialog extends AlertDialog.Builder {
    String members;
    public GroupDetailDialog(@NonNull Context context, String members) {
        super(context);
        this.members = members;
    }

    public void popup() {
        this.setTitle("Member List:");
        final String[] actions = members.split(",");
        this.setItems(actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        this.show();
    }
}