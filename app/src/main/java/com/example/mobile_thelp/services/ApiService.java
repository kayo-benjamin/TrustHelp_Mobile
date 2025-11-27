package com.example.mobile_thelp.services;

import com.example.mobile_thelp.model.ApiResponse;
import com.example.mobile_thelp.model.Chamado;
import com.example.mobile_thelp.model.LoginRequest;
import com.example.mobile_thelp.model.LoginResponse;
import com.example.mobile_thelp.model.Organizacao;
import com.example.mobile_thelp.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    // --- AUTENTICAÇÃO ---
    // Endpoint: /auth/cadastro
    @POST("auth/cadastro")
    Call<ApiResponse> cadastro(@Body User usuario);

    // Endpoint: /auth/login
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    // --- ORGANIZAÇÃO ---
    // Endpoint: /organizacao/save
    @POST("organizacao/save")
    Call<Organizacao> criarOrganizacao(@Body Organizacao organizacao);

    // Endpoint: /organizacao/{id}
    @GET("organizacao/{id}")
    Call<Organizacao> getOrganizacaoById(@Path("id") Long id);

    // --- USUÁRIOS ---
    // Endpoint: /usuarios/{id}
    @GET("usuarios/{id}")
    Call<User> getUsuarioById(@Path("id") Long id);

    // --- CHAMADOS ---
    // Endpoint: /chamados/save
    @POST("chamados/save")
    Call<Chamado> criarChamado(@Body Chamado chamado);

    // Endpoint: /chamados/usuario/{idUsuario}
    @GET("chamados/usuario/{idUsuario}")
    Call<List<Chamado>> getChamadosPorUsuario(@Path("idUsuario") Long idUsuario);
}
