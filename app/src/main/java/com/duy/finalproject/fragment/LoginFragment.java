package com.duy.finalproject.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.duy.finalproject.activity.LoginActivity;
import com.duy.finalproject.R;
import com.duy.finalproject.activity.MainActivity;
import com.duy.finalproject.api.InterfaceAPI;
import com.duy.finalproject.config.ApiClient;
import com.duy.finalproject.models.APIRespond;
import com.duy.finalproject.models.User;
import com.duy.finalproject.share.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DUY on 7/18/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView tvSignUp;

    public EditText getEdtUsername() {
        return edtUsername;
    }

    public EditText getEdtPassword() {
        return edtPassword;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_login,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initViews();
    }

    private void initViews() {
        edtUsername = (EditText) getActivity().findViewById(R.id.edtUsername);
        edtPassword = (EditText) getActivity().findViewById(R.id.edtPassword);
        btnLogin = (Button) getActivity().findViewById(R.id.btnLogin);
        tvSignUp = (TextView) getActivity().findViewById(R.id.tvSignUp);

        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                //login();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.tvSignUp:
                LoginActivity loginActivity = (LoginActivity) getActivity();
                loginActivity.showFragment(loginActivity.getFragmentRegister());
                break;
        }
    }

    private void login() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage("Waiting...");
        dialog.show();

        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        
        if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(getActivity(), "abcd", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }

        User user = new User(username, password);

        InterfaceAPI interfaceAPI = ApiClient.getClient().create(InterfaceAPI.class);

        Call<APIRespond<User>> call = interfaceAPI.login(user);
        //Call<APIRespond<User>> call = interfaceAPI.delete("5981f7a20912ad1c7cc6e51a");

        call.enqueue(new Callback<APIRespond<User>>() {
            @Override
            public void onResponse(Call<APIRespond<User>> call, Response<APIRespond<User>> response) {
                APIRespond<User> respond = response.body();
                switch (respond.getCode()){
                    case 0:
                        Toast.makeText(getActivity(), "Login success!", Toast.LENGTH_SHORT).show();
                        Utils.user = respond.getArr().get(0);

                        LoginActivity loginActivity = (LoginActivity) getActivity();
                        loginActivity.getEditor().putString("User",respond.getArr().get(0).getUsername());
                        loginActivity.getEditor().putString("Password",respond.getArr().get(0).getPassword());
                        loginActivity.getEditor().commit();

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        break;
                    case -4:
                        Toast.makeText(getActivity(), "Username or password incorrect", Toast.LENGTH_SHORT).show();
                        break;
                    case 500:
                        Toast.makeText(getActivity(), "Server error!", Toast.LENGTH_SHORT).show();
                        break;
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<APIRespond<User>> call, Throwable t) {
                Toast.makeText(getActivity(), "Login fail!", Toast.LENGTH_SHORT).show();
                //Log.e(TAG, t.getMessage());
                dialog.dismiss();
            }
        });



    }
}
