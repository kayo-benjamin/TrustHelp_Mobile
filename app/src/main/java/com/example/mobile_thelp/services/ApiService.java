package com.example.mobile_thelp.services;

import com.example.mobile_thelp.model.ApiResponse;
import com.example.mobile_thelp.model.LoginRequest;
import com.example.mobile_thelp.model.LoginResponse;
import com.example.mobile_thelp.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("/users")
    Call<List<User>> getUsers();

    @POST("/cadastro")
    Call<ApiResponse> cadastro(@Body User usuario);

    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}
