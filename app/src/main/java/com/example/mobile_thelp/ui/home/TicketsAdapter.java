package com.example.mobile_thelp.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile_thelp.R;
import com.example.mobile_thelp.model.Chamado;
import com.google.android.material.chip.Chip;
import java.util.List;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.VH> {

    public interface OnTicketClick {
        void onClick(Chamado chamado); // Alterado para passar o objeto Chamado
    }

    private final List<Chamado> data;
    private final OnTicketClick onClick;

    public TicketsAdapter(List<Chamado> data, OnTicketClick onClick) {
        this.data = data;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chamado, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Chamado item = data.get(pos);
        h.titulo.setText(item.getTitulo());
        h.descricao.setText(item.getDescricao());
        
        // Configurar Status
        h.chipStatus.setText(mapStatus(item.getStatus()));
        
        // Configurar Prioridade
        String prio = item.getPrioridade();
        h.chipPrioridade.setText(mapPrioridade(prio));
        
        // Cores para prioridade (opcional, para melhor visualização)
        if ("alta".equalsIgnoreCase(prio)) {
            h.chipPrioridade.setChipBackgroundColorResource(android.R.color.holo_red_light);
        } else if ("media".equalsIgnoreCase(prio)) {
            h.chipPrioridade.setChipBackgroundColorResource(android.R.color.holo_orange_light);
        } else {
            h.chipPrioridade.setChipBackgroundColorResource(android.R.color.holo_green_light);
        }

        h.itemView.setOnClickListener(v -> onClick.onClick(item));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView titulo, descricao;
        Chip chipStatus, chipPrioridade;

        VH(View v) {
            super(v);
            titulo = v.findViewById(R.id.tvTitulo);
            descricao = v.findViewById(R.id.tvDescricao);
            chipStatus = v.findViewById(R.id.chipStatus);
            chipPrioridade = v.findViewById(R.id.chipPrioridade);
        }
    }

    private String mapStatus(String s) {
        if (s == null) return "Desconhecido";
        switch (s.toLowerCase()) {
            case "aberto": return "Aberto";
            case "em_andamento": return "Em Andamento";
            case "fechado": return "Fechado";
            default: return s;
        }
    }

    private String mapPrioridade(String p) {
        if (p == null) return "Média";
        switch (p.toLowerCase()) {
            case "baixa": return "Baixa";
            case "media": return "Média";
            case "alta": return "Alta";
            default: return p;
        }
    }
}
