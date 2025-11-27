package com.example.mobile_thelp.model;

import com.google.gson.annotations.SerializedName;

public class Organizacao {

    // Reforçando o mapeamento com todas as variações prováveis
    @SerializedName(value = "idOrganizacao", alternate = {"id", "id_organizacao", "pk_organizacao", "id_org"})
    private Long idOrganizacao;

    @SerializedName("id") // Campo redundante apenas para garantir
    private Long idGenerico;

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

    // Getter inteligente
    public Long getId() { 
        if (idOrganizacao != null) return idOrganizacao;
        return idGenerico;
    }

    public void setId(Long id) { this.idOrganizacao = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
