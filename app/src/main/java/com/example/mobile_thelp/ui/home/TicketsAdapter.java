package com.example.mobile_thelp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile_thelp.R;
import com.google.android.material.chip.Chip;
import java.util.List;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.VH> {

    public interface OnTicketClick { void onClick(int position); }

    private final List<com.example.mobile_thelp.ui.home.TicketItem> data;
    private final OnTicketClick onClick;

    public TicketsAdapter(List<com.example.mobile_thelp.ui.home.TicketItem> data, OnTicketClick onClick) {
        this.data = data; this.onClick = onClick;
    }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chamado, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        com.example.mobile_thelp.ui.home.TicketItem it = data.get(pos);
        h.titulo.setText(it.titulo);
        h.descricao.setText(it.descricao);
        h.chipStatus.setText(mapStatus(it.status));
        h.chipPrioridade.setText(mapPrioridade(it.prioridade));
        h.itemView.setOnClickListener(v -> onClick.onClick(pos));
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView titulo, descricao; Chip chipStatus, chipPrioridade;
        VH(View v){ super(v); titulo=v.findViewById(R.id.tvTitulo); descricao=v.findViewById(R.id.tvDescricao);
            chipStatus=v.findViewById(R.id.chipStatus); chipPrioridade=v.findViewById(R.id.chipPrioridade); }
    }

    private String mapStatus(String s){
        switch (s){
            case "aberto": return "Aberto";
            case "em_andamento": return "Em andamento";
            case "fechado": return "Fechado";
            default: return s;
        }
    }
    private String mapPrioridade(String p){
        switch (p){
            case "baixa": return "Baixa";
            case "media": return "Média";
            case "alta": return "Alta";
            default: return p;
        }
    }
}