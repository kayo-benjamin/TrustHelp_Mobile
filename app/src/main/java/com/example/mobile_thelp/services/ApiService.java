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
import retrofit2.http.Path;

public interface ApiService {

    // Endpoint de login
    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    // Endpoint de cadastro
    @POST("/auth/cadastro")
    Call<ApiResponse> cadastro(@Body User usuario);

    // Listar todos os usuários
    @GET("/api")
    Call<List<User>> getUsers();

    // Buscar usuário por ID
    @GET("/api/{id}")
    Call<User> getUserById(@Path("id") Integer id);

    // Buscar usuário por email
    @GET("/api/email/{email}")
    Call<User> getUserByEmail(@Path("email") String email);

    // Criar novo usuário
    @POST("/api")
    Call<ApiResponse> createUser(@Body User usuario);
}
