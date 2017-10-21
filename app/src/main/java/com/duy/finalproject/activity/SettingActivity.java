package com.duy.finalproject.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.duy.finalproject.R;
import com.duy.finalproject.fragment.ProfileFragment;

public class SettingActivity extends AppCompatActivity {
    private ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initFragments();
    }

    private void initFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.contentContainer, profileFragment);
        transaction.commit();
        showFragment(profileFragment);
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(profileFragment);
        transaction.show(fragment);
        transaction.commit();
    }

}
