package com.example.mobile_thelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_thelp.client.RetrofitClient;
import com.example.mobile_thelp.model.LoginRequest;
import com.example.mobile_thelp.model.LoginResponse;
import com.example.mobile_thelp.services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private Button btnLogin;
    private TextView tvCadastro;
    private ProgressBar progressBar;
    private ApiService apiService;

    // ✅ MODO OFFLINE ATIVADO PARA TESTES SEM API
    private boolean isOfflineMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = RetrofitClient.getApiService();

        etEmail = findViewById(R.id.tbx_user_login);
        etSenha = findViewById(R.id.tbx_senha_login);
        btnLogin = findViewById(R.id.btn_entrar_login);
        tvCadastro = findViewById(R.id.tv_cadastro);
        progressBar = findViewById(R.id.progress_bar);

        btnLogin.setOnClickListener(v -> fazerLogin());

        tvCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
            startActivity(intent);
        });

        Button btnHealthCheck = findViewById(R.id.btn_health_check);
        if (btnHealthCheck != null) {
            btnHealthCheck.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, HealthCheckActivity.class);
                startActivity(intent);
            });
        }
    }

    private void fazerLogin() {
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);

        if (isOfflineMode) {
            // SIMULAÇÃO DE LOGIN
            new Handler().postDelayed(() -> {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);

                // Aceita qualquer login para testes ou o admin específico
                if ((email.equals("admin@thelp.com.br") && senha.equals("123456")) || true) {
                    Toast.makeText(MainActivity.this, "Login Simulado: Sucesso!", Toast.LENGTH_SHORT).show();

                    SharedPreferences prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putLong("user_id", 1L);
                    editor.putString("user_name", "Admin Teste");
                    editor.putString("user_email", email);
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, HostActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                }
            }, 1000);

        } else {
            // CÓDIGO REAL DA API (DESATIVADO)
            LoginRequest loginRequest = new LoginRequest(email, senha);
            Call<LoginResponse> call = apiService.login(loginRequest);
            
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);
                    // Lógica de sucesso aqui...
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
