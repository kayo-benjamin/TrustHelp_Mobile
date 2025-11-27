package com.example.mobile_thelp.model;

import com.google.gson.annotations.SerializedName;

public class Organizacao {

    @SerializedName("idOrganizacao")
    private Long id;

    @SerializedName("orgNome")
    private String nome;

    @SerializedName("orgCnpj")
    private String cnpj;

    @SerializedName("orgEmail")
    private String email;

    @SerializedName("orgTelefone")
    private String telefone;

    @SerializedName("orgAtivo")
    private Boolean ativo;

    public Organizacao() {}

    public Organizacao(String nome, String cnpj, String email, String telefone) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.ativo = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
