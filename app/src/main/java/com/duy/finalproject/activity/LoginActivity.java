package com.duy.finalproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.duy.finalproject.R;
import com.duy.finalproject.fragment.LoginFragment;
import com.duy.finalproject.fragment.RegisterFragment;
import com.duy.finalproject.models.User;
import com.duy.finalproject.share.Utils;

import butterknife.BindView;
import jp.wasabeef.blurry.Blurry;

/**
 * Created by DUY on 7/24/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private LoginFragment fragmentLogin = new LoginFragment();
    private RegisterFragment fragmentRegister = new RegisterFragment();
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
//    @BindView(R.id.imBackground)
//    ImageView imBackground;

    public LoginFragment getFragmentLogin() {
        return fragmentLogin;
    }

    public RegisterFragment getFragmentRegister() {
        return fragmentRegister;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkLogin();
        initFragments();
        ImageView imageView = (ImageView) findViewById(R.id.imBackground);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        Blurry.with(getApplicationContext()).from(bitmap).into(imageView);
    }

    private void initFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.panel,fragmentLogin);
        transaction.add(R.id.panel,fragmentRegister);
        transaction.commit();
        showFragment(fragmentLogin);
    }

    public void showFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment instanceof RegisterFragment){
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        } else {
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        }

        transaction.hide(fragmentLogin);
        transaction.hide(fragmentRegister);
        transaction.show(fragment);
        transaction.commit();
    }

    public void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.panel,fragment);
        transaction.commit();
    }

    private void checkLogin(){
        prefs = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = prefs.edit();

        String user = prefs.getString("User", null);
        String password = prefs.getString("Password", null);

        if (user != null && password != null){
            Utils.user = new User(user,password);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
