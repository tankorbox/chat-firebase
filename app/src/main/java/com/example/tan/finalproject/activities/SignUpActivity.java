package com.example.tan.finalproject.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    EditText edName, edEmail, edPassword, edPasswordReEnter;
    Button btnSignUp;
    TextView tvLogIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //create references for ui components
        createReferences();

        //click event listener and handler
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void createReferences() {
        edName = (EditText) findViewById(R.id.edName);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        edPasswordReEnter = (EditText) findViewById(R.id.edPasswordReEnter);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        tvLogIn = (TextView) findViewById(R.id.tvLogIn);
    }

    public void signUp() {
        Log.d(TAG, "Signup");
        if (!validate()) {
            onSignupFailed();
            return;
        }
        btnSignUp.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang tạo tài khoản...");
        progressDialog.show();

        final String name = edName.getText().toString();
        final String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();
        //register through firebase
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    onSignupFailed();
                    progressDialog.dismiss();
                    resetInput();
                } else {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    User newUser = new User(name, firebaseUser.getUid(), "", firebaseUser.getEmail(), false);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                    databaseReference.child(firebaseUser.getUid()).setValue(newUser);
                    onSignupSuccess();
                    progressDialog.dismiss();
                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                }
            }
        });

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 8000);
    }

    private void resetInput() {
        edEmail.setText("");
        edName.setText("");
        edPassword.setText("");
        edPasswordReEnter.setText("");
    }

    //handle on registration success
    public void onSignupSuccess() {
        Toast.makeText(getBaseContext(), "Đăng ký thành công!", Toast.LENGTH_LONG).show();
        btnSignUp.setEnabled(true);
        setResult(RESULT_OK, null);
    }

    //handle on registration failure
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Đăng ký thất bại!", Toast.LENGTH_LONG).show();
        btnSignUp.setEnabled(true);
    }


    //validate input data for registration
    public boolean validate() {
        boolean valid = true;

        String name = edName.getText().toString();
        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();
        String reEnterPassword = edPasswordReEnter.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            edName.setError("Tối thiểu 3 ký tự");
            valid = false;
        } else {
            edName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("Hãy điền Email hợp lệ");
            valid = false;
        } else {
            edEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edPassword.setError("Mật khẩu tối thiểu 4 ký tự và tối đa 10 ký tự");
            valid = false;
        } else {
            edPassword.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            edPasswordReEnter.setError("Mật khẩu không khớp !");
            valid = false;
        } else {
            edPasswordReEnter.setError(null);
        }

        return valid;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(SignUpActivity.this, InstantMessageService.class);
        stopService(intent);
    }
}