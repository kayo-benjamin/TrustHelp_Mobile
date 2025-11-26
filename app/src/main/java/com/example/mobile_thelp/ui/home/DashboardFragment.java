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
    
    // ✅ MODO ONLINE ATIVADO (Integração API)
    private boolean isOfflineMode = false; 

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
        String userName = prefs.getString("user_name", "Usuário");
        tvWelcomeTitle.setText("Olá, " + userName + "!");

        apiService = RetrofitClient.getApiService();

        setupRecyclerView();
        
        // Carrega da API
        loadUserChamados();

        view.findViewById(R.id.btn_create_ticket).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_home_to_create)
        );
        
        view.findViewById(R.id.btn_profile).setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.navigation_profile)
        );
    }

    private void setupRecyclerView() {
        rvChamados.setLayoutManager(new LinearLayoutManager(getContext()));
        TicketsAdapter adapter = new TicketsAdapter(new ArrayList<>(), chamado -> {
            Toast.makeText(getContext(), "Detalhes: " + chamado.getTitulo(), Toast.LENGTH_SHORT).show();
        });
        rvChamados.setAdapter(adapter);
    }

    private void loadUserChamados() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        long userId = prefs.getLong("user_id", -1);

        if (userId == -1) {
            Toast.makeText(getContext(), "Usuário não identificado", Toast.LENGTH_SHORT).show();
            return;
        }

        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        Call<List<Chamado>> call = apiService.getChamadosPorUsuario(userId);
        call.enqueue(new Callback<List<Chamado>>() {
            @Override
            public void onResponse(Call<List<Chamado>> call, Response<List<Chamado>> response) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    updateList(response.body());
                } else {
                    Toast.makeText(getContext(), "Erro ao carregar: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Chamado>> call, Throwable t) {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Falha na conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void updateList(List<Chamado> chamados) {
        if (getContext() == null) return;
        if (chamados.isEmpty()) {
            Toast.makeText(getContext(), "Nenhum chamado encontrado", Toast.LENGTH_SHORT).show();
        }
        TicketsAdapter adapter = new TicketsAdapter(chamados, item -> 
            Toast.makeText(getContext(), "Abrindo: " + item.getTitulo(), Toast.LENGTH_SHORT).show()
        );
        rvChamados.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        loadUserChamados(); 
    }
}
