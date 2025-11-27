package com.example.mobile_thelp.ui.ticket;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mobile_thelp.R;
import com.example.mobile_thelp.client.RetrofitClient;
import com.example.mobile_thelp.model.Chamado;
import com.example.mobile_thelp.services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTicketFragment extends Fragment {

    private EditText etTitle, etDescription;
    private RadioGroup rgPriority;
    private Button btnSubmit;
    private ApiService apiService;

    // ✅ MODO ONLINE ATIVADO (Integração API)
    private boolean isOfflineMode = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ticket, container, false);

        etTitle = view.findViewById(R.id.et_ticket_title);
        etDescription = view.findViewById(R.id.et_ticket_description);
        rgPriority = view.findViewById(R.id.rg_priority);
        btnSubmit = view.findViewById(R.id.btn_submit_ticket);

        apiService = RetrofitClient.getApiService();

        btnSubmit.setOnClickListener(v -> submitTicket());

        return view;
    }

    private void submitTicket() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        btnSubmit.setEnabled(false);
        btnSubmit.setText("Enviando...");

        String priority = "media";
        int selectedId = rgPriority.getCheckedRadioButtonId();
        if (selectedId == R.id.rb_low) priority = "baixa";
        else if (selectedId == R.id.rb_high) priority = "alta";

        // Recuperar IDs salvos
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        long userIdLong = prefs.getLong("user_id", -1);
        
        // Por enquanto, usamos ID de organização fixo ou recuperado se disponível
        // Idealmente, salvaria 'org_id' no login também
        int orgId = 1;

        if (userIdLong == -1) {
            Toast.makeText(getContext(), "Erro: ID do usuário não encontrado. Faça login novamente.", Toast.LENGTH_LONG).show();
            btnSubmit.setEnabled(true);
            return;
        }

        // CÓDIGO REAL DA API
        Chamado novoChamado = new Chamado(title, description, orgId, (int) userIdLong, priority);

        Call<Chamado> call = apiService.criarChamado(novoChamado);
        call.enqueue(new Callback<Chamado>() {
            @Override
            public void onResponse(Call<Chamado> call, Response<Chamado> response) {
                btnSubmit.setEnabled(true);
                btnSubmit.setText("Abrir Chamado");

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Chamado criado com sucesso! ID: " + response.body().getId(), Toast.LENGTH_LONG).show();
                    // Voltar para o Dashboard
                    Navigation.findNavController(getView()).popBackStack();
                } else {
                    Toast.makeText(getContext(), "Erro ao criar chamado: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Chamado> call, Throwable t) {
                btnSubmit.setEnabled(true);
                btnSubmit.setText("Abrir Chamado");
                Toast.makeText(getContext(), "Falha na conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
