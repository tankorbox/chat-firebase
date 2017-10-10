package com.example.tan.finalproject.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tan.finalproject.R;
import com.example.tan.finalproject.fragments.ContactListFragment;
import com.example.tan.finalproject.fragments.GroupFragment;
import com.example.tan.finalproject.fragments.PersonalInformationFragment;
import com.example.tan.finalproject.fragments.RecentFragment;
import com.example.tan.finalproject.services.InstantMessageService;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView mBottomNavigationView;
    Toolbar mToolbar;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get share preference to read
        mPreferences = getSharedPreferences("my_data",MODE_PRIVATE);

        //toolbar's configuration
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //set RecentFragment is the first Fragment when Activity activated
        switchFragments(new RecentFragment());

        //Bottom Navigation's configuration and event click handler
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationBottom);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemChat: {
                        switchFragments(new RecentFragment());
                        break;
                    }
                    case R.id.itemList: {
                        switchFragments(new ContactListFragment());
                        break;
                    }
                    case R.id.itemGroup: {
                        switchFragments(new GroupFragment());
                        break;
                    }
                    case R.id.itemPersonal: {
                        switchFragments(new PersonalInformationFragment());
                        break;
                    }
                }
                return true;
            }
        });
    }

    //switch fragment when click item on BottomNavigation
    public void switchFragments(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frContainer,fragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(MainActivity.this, InstantMessageService.class);
        stopService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(MainActivity.this, InstantMessageService.class);
        startService(intent);
    }
}
