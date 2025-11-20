package com.example.mobile_thelp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_thelp.R;
import com.example.mobile_thelp.model.Chamado;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private RecyclerView rvChamados;
    private ProgressBar progressBar;

    // MODO DE TESTE SEM API
    private boolean isOfflineMode = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvChamados = view.findViewById(R.id.rvChamados);
        progressBar = view.findViewById(R.id.progressBar); // Certifique-se que existe no layout

        setupRecyclerView();

        if (isOfflineMode) {
            loadMockChamados();
        } else {
            // loadUserChamados(); // Chamada para carregar dados da API (desativado)
        }

        view.findViewById(R.id.fabCreate).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_home_to_create)
        );
    }

    private void setupRecyclerView() {
        rvChamados.setLayoutManager(new LinearLayoutManager(getContext()));
        // Inicializa com lista vazia
        TicketsAdapter adapter = new TicketsAdapter(new ArrayList<>(), chamado -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_home_to_detail);
        });
        rvChamados.setAdapter(adapter);
    }

    private void loadMockChamados() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        
        List<Chamado> mockList = new ArrayList<>();
        mockList.add(new Chamado("Impressora não funciona (MOCK)", "A impressora do 3º andar não liga.", 1, 1, "alta"));
        mockList.add(new Chamado("Mouse quebrado (MOCK)", "O mouse do meu computador parou de funcionar.", 1, 1, "media"));
        mockList.add(new Chamado("Sistema lento (MOCK)", "O sistema de vendas está muito lento hoje.", 1, 1, "baixa"));
        
        TicketsAdapter adapter = new TicketsAdapter(mockList, chamado -> {
            // Simulação de clique
        });
        rvChamados.setAdapter(adapter);
    }
}
