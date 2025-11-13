package com.example.mobile_thelp.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.List;
import com.example.mobile_thelp.model.User;

public interface ApiService {

    @POST("/users")
    Call<User> createUser(@Body User user);

    @POST("/users/login")
    Call<User> login(@Body User user);


    @GET("/users")
    Call<List<User>> getUsers();
}
