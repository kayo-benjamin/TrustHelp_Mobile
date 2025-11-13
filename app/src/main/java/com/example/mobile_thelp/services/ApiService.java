package com.example.mobile_thelp.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import java.util.List;
import com.example.mobile_thelp.model.User;

public interface ApiService {

    // Cadastrar usuário
    @POST("/api/users")
    Call<User> createUser(@Body User user);

    // Buscar usuário por ID
    @GET("/api/users/{id}")
    Call<User> getUserById(@Path("id") Long id);

    // Listar todos os usuários
    @GET("/api/users")
    Call<List<User>> getAllUsers();

    // Login (baseado na sua API)
    @POST("/api/users/login")
    Call<User> login(@Body User user);
}
