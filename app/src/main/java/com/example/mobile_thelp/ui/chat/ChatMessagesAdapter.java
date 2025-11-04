package com.example.mobile_thelp.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile_thelp.R;
import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.VH> {

    private final List<ChatMessage> data;

    public ChatMessagesAdapter(List<ChatMessage> d){ data=d; }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos){
        ChatMessage m = data.get(pos);
        h.usuario.setText(m.usuario);
        h.mensagem.setText(m.mensagem);
        h.data.setText(m.data);
    }

    @Override public int getItemCount(){ return data.size(); }

    static class VH extends RecyclerView.ViewHolder{
        TextView usuario, mensagem, data;
        VH(View v){ super(v);
            usuario=v.findViewById(R.id.tvUsuario);
            mensagem=v.findViewById(R.id.tvMensagem);
            data=v.findViewById(R.id.tvData);
        }
    }
}