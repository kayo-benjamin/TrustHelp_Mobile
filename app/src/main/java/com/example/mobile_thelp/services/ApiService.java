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
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // ============================================
    // AUTENTICAÇÃO
    // ============================================

    // ✅ Endpoint: POST /auth/cadastro
    @POST("auth/cadastro")
    Call<ApiResponse> cadastro(@Body User usuario);

    // ✅ Endpoint: POST /auth/login
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    // ============================================
    // ORGANIZAÇÃO
    // ============================================

    // ✅ CORRIGIDO: POST /organizacao (sem /save)
    @POST("organizacao")
    Call<Organizacao> criarOrganizacao(@Body Organizacao organizacao);

    // ✅ Endpoint: GET /organizacao/{id}
    @GET("organizacao/{id}")
    Call<Organizacao> getOrganizacaoById(@Path("id") Long id);

    // ✅ NOVO: GET /organizacao - listar todas
    @GET("organizacao")
    Call<List<Organizacao>> getAllOrganizacoes();

    // ✅ NOVO: GET /organizacao/ativas - listar ativas
    @GET("organizacao/ativas")
    Call<List<Organizacao>> getOrganizacoesAtivas();

    // ✅ NOVO: GET /organizacao/cnpj/{cnpj}
    @GET("organizacao/cnpj/{cnpj}")
    Call<Organizacao> getOrganizacaoByCnpj(@Path("cnpj") String cnpj);

    // ✅ NOVO: PUT /organizacao/{id} - atualizar
    @PUT("organizacao/{id}")
    Call<Organizacao> atualizarOrganizacao(@Path("id") Long id, @Body Organizacao organizacao);

    // ✅ NOVO: PUT /organizacao/{id}/desativar
    @PUT("organizacao/{id}/desativar")
    Call<Organizacao> desativarOrganizacao(@Path("id") Long id);

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
    @POST("chamados/save")
    Call<Chamado> criarChamado(@Body Chamado chamado);

    // ✅ Endpoint: GET /chamados/usuario/{idUsuario}
    @GET("chamados/usuario/{idUsuario}")
    Call<List<Chamado>> getChamadosPorUsuario(@Path("idUsuario") Long idUsuario);

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
