package com.mr.rohmani.moviezamannow;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_popular:
                    fragment = new Popular();
                    callFragment(fragment);
                    setTitle(R.string.title_popular);
                    return true;
                case R.id.nav_top:
                    fragment = new Top();
                    callFragment(fragment);
                    setTitle(R.string.title_top);
                    return true;
                case R.id.nav_now:
                    fragment = new Now();
                    callFragment(fragment);
                    setTitle(R.string.title_now);
                    return true;
                case R.id.nav_upcoming:
                    fragment = new Upcoming();
                    callFragment(fragment);
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

        manager = getFragmentManager();

        if (savedInstanceState == null) {
            fragment = new Popular();
            callFragment(fragment);
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void callFragment(Fragment fragment) {
        transaction = manager.beginTransaction();

        transaction.remove(fragment);
        transaction.replace(R.id.mylayout, fragment);
        transaction.commit();
    }
}
