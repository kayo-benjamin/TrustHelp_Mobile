package com.example.mobile_thelp.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("idUsuario")
    private Long id;

    @SerializedName("usuNome")
    private String nome;

    @SerializedName("usuEmail")
    private String email;

    @SerializedName("usuSenha")
    private String senha;

    // Estes campos são necessários para o cadastro
    @SerializedName("idPapel")
    private Integer idPapel;

    @SerializedName("idOrganizacao")
    private Integer idOrganizacao;

    // Construtores
    public User() {}

    public User(String nome, String email, String senha, Integer idPapel, Integer idOrganizacao) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idPapel = idPapel;
        this.idOrganizacao = idOrganizacao;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public void setIdPapel(Integer idPapel) { this.idPapel = idPapel; }
    public void setIdOrganizacao(Integer idOrganizacao) { this.idOrganizacao = idOrganizacao; }
}
