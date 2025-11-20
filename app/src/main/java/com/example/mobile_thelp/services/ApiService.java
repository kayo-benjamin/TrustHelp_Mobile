package com.example.mobile_thelp.services;

import com.example.mobile_thelp.model.ApiResponse;
import com.example.mobile_thelp.model.Chamado;
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

    // --- AUTENTICAÇÃO ---
    @POST("auth/cadastro")
    Call<ApiResponse> cadastro(@Body User usuario);

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    // --- USUÁRIOS ---
    // Buscar dados do usuário pelo ID (para o Dashboard)
    @GET("usuarios/{id}")
    Call<User> getUsuarioById(@Path("id") Long id);

    // --- CHAMADOS ---
    // Criar novo chamado
    @POST("chamados/save")
    Call<Chamado> criarChamado(@Body Chamado chamado);

    // Listar chamados de um usuário
    @GET("chamados/usuario/{idUsuario}")
    Call<List<Chamado>> getChamadosPorUsuario(@Path("idUsuario") Long idUsuario);
}
