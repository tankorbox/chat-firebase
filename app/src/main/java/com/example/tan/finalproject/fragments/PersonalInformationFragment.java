package com.example.tan.finalproject.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tan.finalproject.R;
import com.example.tan.finalproject.activities.LogInActivity;


/**
 * Created by Tan on 3/23/2017.
 */

public class PersonalInformationFragment extends Fragment {
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    String hostName, hostID, hostEmail;
    TextView tvName, tvEmail;
    ImageView ivAvatar, ivCover;
    Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, null);

        //get logged user data
        mPreferences = getActivity().getSharedPreferences("my_data", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        hostID = mPreferences.getString("userid", "");
        hostName = mPreferences.getString("name", "");
        hostEmail = mPreferences.getString("email", "");

        //create references for ui components
        createReferences(view);

        //set textview
        tvName.setText(hostName);
        tvEmail.setText(hostEmail);

        //button log out click listener and handler
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.remove("name");
                mEditor.remove("email");
                mEditor.remove("pass");
                mEditor.remove("userid");
                mEditor.remove("avatar");
                mEditor.putBoolean("logged", false);
                mEditor.commit();
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);
            }
        });

        //imageview avatar click handler: chooser option
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AvatarOptionDialog(getContext()).popup();
            }
        });

        return view;
    }

    private void createReferences(View view) {
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        btnLogout = (Button) view.findViewById(R.id.btnLogout);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
        ivCover = (ImageView) view.findViewById(R.id.ivCover);
    }

    public class AvatarOptionDialog extends AlertDialog.Builder {
        public AvatarOptionDialog(@NonNull Context context) {
            super(context);
        }

        public void popup() {
            this.setTitle("Choose Action:");
            final String[] actions = {"See Avatar", "Capture New", "Pick From Gallery", "Cancel"};
            this.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 3) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                    if (which == 2) {
//                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        intent.setType("image/*");
//                        startActivityForResult(intent,GALLERY_PICKER_CODE);
                        dialog.dismiss();
                    }
                    if (which == 1) {
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(intent,IMAGE_CAPTURE_CODE);
                        dialog.dismiss();
                    }

                    if (which == 0) {
                        dialog.dismiss();
                    }
                }
            });
            this.show();
        }
    }
}