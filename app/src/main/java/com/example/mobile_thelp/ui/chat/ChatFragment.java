package com.example.mobile_thelp.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile_thelp.R;
import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = view.findViewById(R.id.rvMensagens);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new ChatMessagesAdapter(mockMsgs()));

        view.findViewById(R.id.btnEnviar).setOnClickListener(v -> {
            // Somente UI – sem backend
        });
    }

    private List<ChatMessage> mockMsgs(){
        List<ChatMessage> list = new ArrayList<>();
        list.add(new ChatMessage("Analista", "Olá! Como posso ajudar?", "10:30"));
        list.add(new ChatMessage("Você", "Sistema não abre.", "10:31"));
        return list;
    }
}