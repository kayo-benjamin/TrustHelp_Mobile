package com.example.mobile_thelp.services;

import com.example.mobile_thelp.model.LoginRequest;
import com.example.mobile_thelp.model.LoginResponse;
import com.example.mobile_thelp.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    // Cadastrar usuário
    @POST("/user")
    Call<User> createUser(@Body User user);

    // Buscar usuário por ID
    @GET("/api/users/{id}")
    Call<User> getUserById(@Path("id") Long id);

    // Listar todos os usuários
    @GET("/api/users")
    Call<List<User>> getAllUsers();

    // Login
    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
