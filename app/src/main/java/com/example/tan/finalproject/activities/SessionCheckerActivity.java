package com.example.tan.finalproject.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.tan.finalproject.R;

public class SessionCheckerActivity extends AppCompatActivity {
    SharedPreferences mPreferences;
    ProgressBar mProgressBar;
    int progressBarStatus = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_checker);

        //set up progress bar
        mProgressBar = (ProgressBar) findViewById(R.id.pbStart);
        mProgressBar.setMax(100);

        //User interface update through separated thread.
        Thread timer = new Thread(){
            public void run(){
                try{
                    while(progressBarStatus < 100){
                        Thread.sleep(10);
                        SessionCheckerActivity.this.runOnUiThread(new Runnable(){
                            public void run()
                            {
                                mProgressBar.setProgress(progressBarStatus);
                                progressBarStatus += 1;
                            }
                        });
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    next();
                }
            }
        };
        timer.start();

    }

    public void next() {
        mPreferences = getSharedPreferences("my_data",MODE_PRIVATE);
        Log.i("tag1",mPreferences.getBoolean("logged",false)+"");
        if (!mPreferences.getBoolean("logged",false)) {
            Intent mIntent = new Intent(SessionCheckerActivity.this,LogInActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mIntent);
        }
        else {
            Intent mIntent = new Intent(SessionCheckerActivity.this,MainActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mIntent);
        }
    }
}
