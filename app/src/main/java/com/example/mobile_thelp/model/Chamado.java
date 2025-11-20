package com.example.mobile_thelp.model;

import com.google.gson.annotations.SerializedName;

public class Chamado {

    @SerializedName("idChamado")
    private Long id;

    @SerializedName("chaTitulo")
    private String titulo;

    @SerializedName("chaDescricao")
    private String descricao;

    @SerializedName("idOrganizacao")
    private Integer idOrganizacao;

    @SerializedName("idUsuarioAbertura")
    private Integer idUsuarioAbertura; // ID de quem está criando o chamado

    @SerializedName("idUsuarioAtribuido")
    private Integer idUsuarioAtribuido; // Pode ser nulo inicialmente

    @SerializedName("chaStatus")
    private String status; // 'aberto', 'em_andamento', 'fechado'

    @SerializedName("chaPrioridade")
    private String prioridade; // 'baixa', 'media', 'alta'

    // Construtor vazio
    public Chamado() {
    }

    // Construtor para criar um novo chamado
    public Chamado(String titulo, String descricao, Integer idOrganizacao, Integer idUsuarioAbertura, String prioridade) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.idOrganizacao = idOrganizacao;
        this.idUsuarioAbertura = idUsuarioAbertura;
        this.prioridade = prioridade;
        this.status = "aberto"; // Status padrão
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdOrganizacao() {
        return idOrganizacao;
    }

    public void setIdOrganizacao(Integer idOrganizacao) {
        this.idOrganizacao = idOrganizacao;
    }

    public Integer getIdUsuarioAbertura() {
        return idUsuarioAbertura;
    }

    public void setIdUsuarioAbertura(Integer idUsuarioAbertura) {
        this.idUsuarioAbertura = idUsuarioAbertura;
    }

    public Integer getIdUsuarioAtribuido() {
        return idUsuarioAtribuido;
    }

    public void setIdUsuarioAtribuido(Integer idUsuarioAtribuido) {
        this.idUsuarioAtribuido = idUsuarioAtribuido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }
}
