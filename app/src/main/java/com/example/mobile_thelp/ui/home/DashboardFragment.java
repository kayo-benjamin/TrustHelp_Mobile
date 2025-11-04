package com.example.mobile_thelp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile_thelp.R;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = view.findViewById(R.id.rvChamados);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new com.example.mobile_thelp.ui.home.TicketsAdapter(mockTickets(), id ->
                Navigation.findNavController(view).navigate(R.id.action_dashboard_to_detail)
        ));

        view.findViewById(R.id.fabCreate).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_dashboard_to_create)
        );
    }

    private List<com.example.mobile_thelp.ui.home.TicketItem> mockTickets() {
        List<com.example.mobile_thelp.ui.home.TicketItem> list = new ArrayList<>();
        list.add(new com.example.mobile_thelp.ui.home.TicketItem("Erro ao abrir sistema", "Ao abrir, a tela fica branca", "aberto", "media"));
        list.add(new com.example.mobile_thelp.ui.home.TicketItem("Impressora não imprime", "Fila trava a cada 2 páginas", "em_andamento", "alta"));
        return list;
    }
}