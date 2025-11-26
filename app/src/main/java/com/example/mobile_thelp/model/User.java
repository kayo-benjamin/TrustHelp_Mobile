package com.example.mobile_thelp.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("idUsuario")
    private Integer idUsuario;

    @SerializedName("usuNome")
    private String usuNome;

    @SerializedName("usuEmail")
    private String usuEmail;

    @SerializedName("usuSenha")
    private String usuSenha;
    
    @SerializedName("idPapel")
    private Integer idPapel;

    @SerializedName("idOrganizacao")
    private Integer idOrganizacao;

    // Construtores
    public User() {}

    public User(String usuNome, String usuEmail, String usuSenha, Integer idPapel, Integer idOrganizacao) {
        this.usuNome = usuNome;
        this.usuEmail = usuEmail;
        this.usuSenha = usuSenha;
        this.idPapel = idPapel;
        this.idOrganizacao = idOrganizacao;
    }

    // Getters e Setters
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
}
