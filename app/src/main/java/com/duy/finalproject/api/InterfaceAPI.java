package com.duy.finalproject.api;

import com.duy.finalproject.models.APIRespond;
import com.duy.finalproject.models.Post;
import com.duy.finalproject.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by DUY on 7/25/2017.
 */

public interface InterfaceAPI {

    @POST("user/register")
    Call<APIRespond<User>> createUser(@Body User user);

    @POST("user/login")
    Call<APIRespond<User>> login(@Body User user);

    @POST("user/deleteUser")
    Call<APIRespond<User>> deleteUser(@Body String user_id);

    @GET("user/listUser")
    Call<APIRespond<User>> listUser();

    @POST("post/add")
    Call<APIRespond<Post>> addNewPost(@Body Post Post);

}
