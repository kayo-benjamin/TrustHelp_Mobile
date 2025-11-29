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
    // AUTENTICAÇÃO
    // ============================================
    @POST("auth/cadastro")
    Call<ApiResponse> cadastro(@Body User usuario);


    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    // ============================================
    // ORGANIZAÇÃO
    // ============================================

    @POST("organizacao")
    Call<Organizacao> criarOrganizacao(@Body Organizacao organizacao);


    @GET("organizacao/{id}")
    Call<Organizacao> getOrganizacaoById(@Path("id") Long id);


    @GET("organizacao")
    Call<List<Organizacao>> getAllOrganizacoes();

    @GET("organizacao/ativas")
    Call<List<Organizacao>> getOrganizacoesAtivas();


    @GET("organizacao/cnpj/{cnpj}")
    Call<Organizacao> getOrganizacaoByCnpj(@Path("cnpj") String cnpj);

    @PUT("organizacao/{id}")
    Call<Organizacao> atualizarOrganizacao(@Path("id") Long id, @Body Organizacao organizacao);

    // ✅ NOVO: PUT /organizacao/{id}/desativar
    @PUT("organizacao/{id}/desativar")
    Call<Organizacao> desativarOrganizacao(@Path("id") Long id);
    @POST("organizacao/cadastrar")
    Call<ApiResponse> cadastrarOrganizacao(@Body Organizacao organizacao);

    @GET("organizacao/verificar/{id}")
    Call<ApiResponse> verificarOrganizacao(@Path("id") Integer id);

    // ============================================
    // USUÁRIOS
    // ============================================

    // ✅ CORRIGIDO: GET /api/{id} (era /usuarios/{id})
    @GET("api/{id}")
    Call<User> getUsuarioById(@Path("id") Long id);

    // ✅ NOVO: GET /api - listar todos usuários
    @GET("api")
    Call<List<User>> getAllUsuarios();

    // ✅ NOVO: GET /api/email/{email}
    @GET("api/email/{email}")
    Call<User> getUsuarioByEmail(@Path("email") String email);

    // ✅ NOVO: POST /api - criar usuário
    @POST("api")
    Call<User> criarUsuario(@Body User usuario);

    // ============================================
    // CHAMADOS
    // ============================================

    // ✅ Endpoint: POST /chamados/save
    // ============================================
// CHAMADOS - CORRIGIDO
// ============================================

// ❌ REMOVER ISSO:
// @POST("chamados/save")
// @GET("chamados/usuario/{idUsuario}")

// ✅ USAR ISSO:

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

    // Atribuir usuário
    @PATCH("api/chamados/{id}/atribuir")
    Call<Chamado> atribuirUsuario(@Path("id") Integer id, @Query("idUsuario") Integer idUsuario);

    // Buscar por status
    @GET("api/chamados/status/{status}")
    Call<List<Chamado>> getChamadosPorStatus(@Path("status") String status);

    // Deletar chamado
    @DELETE("api/chamados/{id}")
    Call<ApiResponse> deleteChamado(@Path("id") Integer id);


    // ============================================
    // HEALTH CHECK
    // ============================================

    // ✅ NOVO: GET /health - verificar saúde do backend
    @GET("health")
    Call<HealthCheckResponse> getBackendHealth();

    // ✅ NOVO: GET /health/routes - listar rotas registradas
    @GET("health/routes")
    Call<ApiResponse> getRegisteredRoutes();
}
