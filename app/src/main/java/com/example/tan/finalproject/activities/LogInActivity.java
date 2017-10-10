package com.example.tan.finalproject.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tan.finalproject.R;
import com.example.tan.finalproject.models.User;
import com.example.tan.finalproject.services.InstantMessageService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LogInActivity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mPreferenceEditor;
    EditText edEmail;
    EditText edPassword;
    Button btnLogIn;
    TextView tvSignUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //create editor for SharedPreferences
        mPreferences = getSharedPreferences("my_data",MODE_PRIVATE);
        mPreferenceEditor = mPreferences.edit();

        //create references for ui components
        createReferences();
        
        //button event listener and handler
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void createReferences() {
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        btnLogIn = (Button) findViewById(R.id.btnLogin);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
    }


    //authentication implementation method
    public void login() {
        if (!validate()) {
            return;
        }
        btnLogIn.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LogInActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang xác thực...");
        progressDialog.show();
        final String email = edEmail.getText().toString();
        final String password = edPassword.getText().toString();
        //log in with firebase authentication
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            progressDialog.dismiss();
                            onLoginFailed();
                        }
                        else {

                            DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
                            dr.child("users").child(task.getResult().getUser().getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    mPreferenceEditor.putBoolean("logged",true);
                                    mPreferenceEditor.putString("avatar",user.getAvatar());
                                    mPreferenceEditor.putString("email",email);
                                    mPreferenceEditor.putString("name",user.getName());
                                    mPreferenceEditor.putString("pass",password);
                                    mPreferenceEditor.putString("userid",user.getUserId());
                                    mPreferenceEditor.commit();
                                    progressDialog.dismiss();
                                    onLoginSuccess();
                                    Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }
                    }
                });

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        btnLogIn.setEnabled(true);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Đăng nhập thất bại", Toast.LENGTH_LONG).show();

        btnLogIn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("Hãy nhập địa chỉ email hợp lệ");
            valid = false;
        } else {
            edEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edPassword.setError("Mật khẩu tối thiểu 4 ký tự, tối đa 10 ký tự");
            valid = false;
        } else {
            edPassword.setError(null);
        }

        return valid;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(LogInActivity.this, InstantMessageService.class);
        stopService(intent);
    }

}