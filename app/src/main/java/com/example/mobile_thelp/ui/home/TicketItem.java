package com.example.mobile_thelp.ui.home;

public class TicketItem {
    public String titulo;
    public String descricao;
    public String status;
    public String prioridade;

    public TicketItem(String t, String d, String s, String p) {
        titulo = t; descricao = d; status = s; prioridade = p;
    }
}