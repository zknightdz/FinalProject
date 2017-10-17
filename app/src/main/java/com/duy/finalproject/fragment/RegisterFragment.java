package com.duy.finalproject.fragment;

/**
 * Created by DUY on 7/24/2017.
 */

import android.app.ProgressDialog;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtEmail;
    private Button btnSignup;
    private TextView tvLogin;
    private LoginActivity  loginActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_signup, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initViews();
    }

    private void initViews() {
        loginActivity = (LoginActivity) getActivity();
        edtUsername = (EditText) getActivity().findViewById(R.id.input_username);
        edtPassword = (EditText) getActivity().findViewById(R.id.input_password);
        edtEmail = (EditText) getActivity().findViewById(R.id.input_email);
        btnSignup = (Button) getActivity().findViewById(R.id.btnSignUp);
        tvLogin = (TextView) getActivity().findViewById(R.id.tvLogin);

        btnSignup.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                register();
                break;
            case R.id.tvLogin:
                loginActivity.showFragment(loginActivity.getFragmentLogin());
                break;
        }
    }

    private void register() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setMessage("Creating Account...");
        dialog.show();

        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String email = edtEmail.getText().toString();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(getActivity(), "Invalid", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }

        User user = new User(username, password, email);

        InterfaceAPI interfaceAPI = ApiClient.getClient().create(InterfaceAPI.class);

        Call<APIRespond<User>> call = interfaceAPI.createUser(user);
        //Call<APIRespond<User>> call = interfaceAPI.listUser();

        call.enqueue(new Callback<APIRespond<User>>() {
            @Override
            public void onResponse(Call<APIRespond<User>> call, Response<APIRespond<User>> response) {
                dialog.dismiss();
                APIRespond<User> respond = response.body();
                switch (respond.getCode()){
                    case 0:
                        Toast.makeText(getActivity(), "Register success!", Toast.LENGTH_SHORT).show();
                        User u = respond.getArr().get(0);
                        loginActivity.showFragment(loginActivity.getFragmentLogin());
                        loginActivity.getFragmentLogin().getEdtUsername().setText(u.getUsername());
                        loginActivity.getFragmentLogin().getEdtPassword().setText(u.getPassword());
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "Username exist!", Toast.LENGTH_SHORT).show();
                        break;
                    case 500:
                        Toast.makeText(getActivity(), "Server error!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<APIRespond<User>> call, Throwable t) {
                Toast.makeText(getActivity(), "Register fail!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

}
