package com.example.mobile_thelp.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName(value = "idUsuario", alternate = {"id", "id_usuario"})
    private Integer idUsuario;

    @SerializedName(value = "usuNome", alternate = {"nome", "usu_nome"})
    private String usuNome;

    @SerializedName(value = "usuEmail", alternate = {"email", "usu_email"})
    private String usuEmail;

    @SerializedName(value = "usuSenha", alternate = {"password", "usu_senha", "senha"})
    private String usuSenha;

    @SerializedName(value = "idPapel", alternate = {"id_papel", "papelId"})
    private Integer idPapel;

    @SerializedName(value = "idOrganizacao", alternate = {"id_organizacao", "organizacaoId"})
    private Integer idOrganizacao;

    @SerializedName("ativo")
    private Boolean ativo;

    // Construtores
    public User() {}

    public User(String usuNome, String usuEmail, String usuSenha, Integer idPapel, Integer idOrganizacao) {
        this.usuNome = usuNome;
        this.usuEmail = usuEmail;
        this.usuSenha = usuSenha;
        this.idPapel = idPapel;
        this.idOrganizacao = idOrganizacao;
    }

    // Getters Principais
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getUsuNome() { return usuNome; }
    public void setUsuNome(String usuNome) { this.usuNome = usuNome; }

    public String getUsuEmail() { return usuEmail; }
    public void setUsuEmail(String usuEmail) { this.usuEmail = usuEmail; }

    public String getUsuSenha() { return usuSenha; }
    public void setUsuSenha(String usuSenha) { this.usuSenha = usuSenha; }

    public Integer getIdPapel() { return idPapel; }
    public void setIdPapel(Integer idPapel) { this.idPapel = idPapel; }

    public Integer getIdOrganizacao() { return idOrganizacao; }
    public void setIdOrganizacao(Integer idOrganizacao) { this.idOrganizacao = idOrganizacao; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    // Métodos de compatibilidade (Aliases) para evitar erros se o código chamar o nome antigo
    public Integer getId() { return idUsuario; }
    public void setId(Integer id) { this.idUsuario = id; }

    public String getNome() { return usuNome; }
    public void setNome(String nome) { this.usuNome = nome; }

    public String getEmail() { return usuEmail; }
    public void setEmail(String email) { this.usuEmail = email; }

    public String getPassword() { return usuSenha; }
    public void setPassword(String password) { this.usuSenha = password; }
}
