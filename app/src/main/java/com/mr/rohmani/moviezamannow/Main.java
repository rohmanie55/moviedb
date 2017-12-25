package com.mr.rohmani.moviezamannow;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.mr.rohmani.moviezamannow.fragment.Now;
import com.mr.rohmani.moviezamannow.fragment.Popular;
import com.mr.rohmani.moviezamannow.fragment.Top;
import com.mr.rohmani.moviezamannow.fragment.Upcoming;

public class Main extends AppCompatActivity {

    private TextView mTextMessage;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment fragment = null;
    private Class fragmentClass;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_popular:
                    fragmentClass = Popular.class;
                    callFragment();
                    setTitle(R.string.title_popular);
                    return true;
                case R.id.nav_top:
                    fragmentClass = Top.class;
                    callFragment();
                    setTitle(R.string.title_top);
                    return true;
                case R.id.nav_now:
                    fragmentClass = Now.class;
                    callFragment();
                    setTitle(R.string.title_now);
                    return true;
                case R.id.nav_upcoming:
                    fragmentClass = Upcoming.class;
                    callFragment();
                    setTitle(R.string.title_upcoming);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if (savedInstanceState == null) {
            fragmentClass = Popular.class;
            callFragment();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void callFragment() {
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mylayout, fragment).commit();
    }
}
