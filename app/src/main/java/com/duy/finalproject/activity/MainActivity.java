package com.duy.finalproject.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;


import com.duy.finalproject.R;
import com.duy.finalproject.fragment.HomeFragment;
import com.duy.finalproject.fragment.NotificationFragment;
import com.duy.finalproject.fragment.ProfileFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * Created by DUY on 8/3/2017.
 */

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment = new HomeFragment();
    private NotificationFragment notificationFragment = new NotificationFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private BottomBar bottomBar;

    public HomeFragment getHomeFragment() {
        return homeFragment;
    }

    public NotificationFragment getNotificationFragment() {
        return notificationFragment;
    }

    public ProfileFragment getProfileFragment() {
        return profileFragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomBar();
        initFragments();
    }

    private void initFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.contentContainer, homeFragment);
        transaction.add(R.id.contentContainer, notificationFragment);
        transaction.add(R.id.contentContainer, profileFragment);
        transaction.commit();
        showFragment(homeFragment);
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(homeFragment);
        transaction.hide(notificationFragment);
        transaction.hide(profileFragment);
        transaction.show(fragment);
        transaction.commit();
    }


    private void setupBottomBar() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_home:
                        showFragment(homeFragment);
                        break;
                    case R.id.tab_notif:
                        showFragment(notificationFragment);
                        break;
                    case R.id.tab_profile:
                        showFragment(profileFragment);
                        break;
                }
            }
        });
    }


}
