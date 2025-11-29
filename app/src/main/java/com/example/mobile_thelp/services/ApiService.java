package com.example.mobile_thelp.services;

import com.example.mobile_thelp.model.ApiResponse;
import com.example.mobile_thelp.model.Chamado;
import com.example.mobile_thelp.model.LoginRequest;
import com.example.mobile_thelp.model.LoginResponse;
import com.example.mobile_thelp.model.Organizacao;
import com.example.mobile_thelp.model.User;
import com.example.mobile_thelp.model.HealthCheckResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // ============================================
    // AUTENTICAÇÃO (Prefixo api/ adicionado pois base é a raiz)
    // ============================================
    @POST("api/auth/cadastro")
    Call<ApiResponse> cadastro(@Body User usuario);

    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    // ============================================
    // ORGANIZAÇÃO
    // ============================================

    @POST("api/organizacao")
    Call<Organizacao> criarOrganizacao(@Body Organizacao organizacao);

    @GET("api/organizacao/{id}")
    Call<Organizacao> getOrganizacaoById(@Path("id") Long id);

    @GET("api/organizacao")
    Call<List<Organizacao>> getAllOrganizacoes();

    @GET("api/organizacao/ativas")
    Call<List<Organizacao>> getOrganizacoesAtivas();

    @GET("api/organizacao/cnpj/{cnpj}")
    Call<Organizacao> getOrganizacaoByCnpj(@Path("cnpj") String cnpj);

    @PUT("api/organizacao/{id}")
    Call<Organizacao> atualizarOrganizacao(@Path("id") Long id, @Body Organizacao organizacao);

    @PUT("api/organizacao/{id}/desativar")
    Call<Organizacao> desativarOrganizacao(@Path("id") Long id);

    @POST("api/organizacao/cadastrar")
    Call<ApiResponse> cadastrarOrganizacao(@Body Organizacao organizacao);

    @GET("api/organizacao/verificar/{id}")
    Call<ApiResponse> verificarOrganizacao(@Path("id") Integer id);

    // ============================================
    // USUÁRIOS
    // ============================================

    @GET("api/usuarios/{id}")
    Call<User> getUsuarioById(@Path("id") Long id);

    @GET("api/usuarios")
    Call<List<User>> getAllUsuarios();

    @GET("api/usuarios/email/{email}")
    Call<User> getUsuarioByEmail(@Path("email") String email);

    @POST("api/usuarios")
    Call<User> criarUsuario(@Body User usuario);

    // ============================================
    // CHAMADOS
    // ============================================

    // Criar chamado
    @POST("api/chamados")
    Call<Chamado> criarChamado(@Body Chamado chamado);

    // Listar todos chamados
    @GET("api/chamados")
    Call<List<Chamado>> getAllChamados();

    // Buscar por ID
    @GET("api/chamados/{id}")
    Call<Chamado> getChamadoById(@Path("id") Integer id);

    // Buscar por organização
    @GET("api/chamados/organizacao/{idOrganizacao}")
    Call<List<Chamado>> getChamadosPorOrganizacao(@Path("idOrganizacao") Integer idOrganizacao);

    // Atualizar chamado
    @PUT("api/chamados/{id}")
    Call<Chamado> atualizarChamado(@Path("id") Integer id, @Body Chamado chamado);

    // Atualizar status
    @PATCH("api/chamados/{id}/status")
    Call<Chamado> atualizarStatusChamado(@Path("id") Integer id, @Query("status") String status);

    // Atribuir usuário (Erro de digitação corrigido aqui)
    @PATCH("api/chamados/{id}/atribuir")
    Call<Chamado> atribuirUsuario(@Path("id") Integer id, @Query("idUsuario") Integer idUsuario);

    // Buscar por status
    @GET("api/chamados/status/{status}")
    Call<List<Chamado>> getChamadosPorStatus(@Path("status") String status);

    // Deletar chamado
    @DELETE("api/chamados/{id}")
    Call<ApiResponse> deleteChamado(@Path("id") Integer id);

    // Listar chamados por usuário (usado no Dashboard)
    @GET("api/chamados/usuario/{idUsuario}")
    Call<List<Chamado>> getChamadosPorUsuario(@Path("idUsuario") Long idUsuario);


    // ============================================
    // HEALTH CHECK
    // ============================================

    @GET("api/health")
    Call<HealthCheckResponse> getBackendHealth();

    @GET("api/health/routes")
    Call<ApiResponse> getRegisteredRoutes();
}
