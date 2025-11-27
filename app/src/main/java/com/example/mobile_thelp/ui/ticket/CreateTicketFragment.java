package com.example.mobile_thelp.ui.ticket;

import android.os.Bundle;
import android.os.Handler;
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

public class CreateTicketFragment extends Fragment {

    private EditText etTitle, etDescription;
    private RadioGroup rgPriority;
    private Button btnSubmit;

    // ✅ MODO OFFLINE ATIVADO PARA TESTES SEM API
    private boolean isOfflineMode = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ticket, container, false);

        etTitle = view.findViewById(R.id.et_ticket_title);
        etDescription = view.findViewById(R.id.et_ticket_description);
        rgPriority = view.findViewById(R.id.rg_priority);
        btnSubmit = view.findViewById(R.id.btn_submit_ticket);

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

        if (isOfflineMode) {
            // SIMULAÇÃO DE ENVIO
            new Handler().postDelayed(() -> {
                if (getContext() == null) return;
                btnSubmit.setEnabled(true);
                btnSubmit.setText("Abrir Chamado");

                Toast.makeText(getContext(), "Chamado (Simulado) criado com sucesso!", Toast.LENGTH_LONG).show();
                Navigation.findNavController(getView()).popBackStack();
            }, 1500);

        } else {
            // CÓDIGO REAL (DESATIVADO)
        }
    }
}
