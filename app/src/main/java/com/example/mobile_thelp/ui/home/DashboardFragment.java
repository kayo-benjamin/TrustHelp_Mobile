package com.example.mobile_thelp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_thelp.R;
import com.example.mobile_thelp.client.RetrofitClient;
import com.example.mobile_thelp.model.Chamado;
import com.example.mobile_thelp.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private RecyclerView rvChamados;
    private ProgressBar progressBar;
    private TextView tvWelcomeTitle;
    private ApiService apiService;
    
    // ✅ MODO OFFLINE ATIVADO PARA TESTES SEM API
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
        progressBar = view.findViewById(R.id.progressBar);
        tvWelcomeTitle = view.findViewById(R.id.tv_welcome_title);
        
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userName = prefs.getString("user_name", "Visitante");
        tvWelcomeTitle.setText("Olá, " + userName + "!");

        apiService = RetrofitClient.getApiService();

        setupRecyclerView();
        
        // Carrega mock ou API dependendo do modo
        if (isOfflineMode) {
            loadMockChamados();
        } else {
            // loadUserChamados();
        }

        view.findViewById(R.id.btn_create_ticket).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_home_to_create)
        );
        
        view.findViewById(R.id.btn_profile).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.navigation_profile)
        );
    }

    private void setupRecyclerView() {
        rvChamados.setLayoutManager(new LinearLayoutManager(getContext()));
        rvChamados.setAdapter(new TicketsAdapter(new ArrayList<>(), c -> {}));
    }
    
    private void loadMockChamados() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        List<Chamado> mockList = new ArrayList<>();
        mockList.add(new Chamado("Erro de Acesso", "Não consigo acessar o ERP.", 1, 1, "alta"));
        mockList.add(new Chamado("Instalação de Software", "Solicito instalação do Office.", 1, 1, "media"));
        mockList.add(new Chamado("Monitor Queimado", "O monitor não liga mais.", 1, 1, "alta"));
        
        updateList(mockList);
    }
    
    private void updateList(List<Chamado> chamados) {
        if (getContext() == null) return;
        TicketsAdapter adapter = new TicketsAdapter(chamados, item -> 
            Toast.makeText(getContext(), "Visualizando: " + item.getTitulo(), Toast.LENGTH_SHORT).show()
        );
        rvChamados.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (isOfflineMode) loadMockChamados();
    }
}
