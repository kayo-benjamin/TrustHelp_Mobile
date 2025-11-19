package com.example.mobile_thelp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private TextView tvCadastro; // Declaração do TextView de cadastro
    private ProgressBar progressBar;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar API Service
        apiService = RetrofitClient.getApiService();

        // Inicializar views
        etEmail = findViewById(R.id.tbx_user_login);
        etSenha = findViewById(R.id.tbx_senha_login);
        btnLogin = findViewById(R.id.btn_entrar_login);
        tvCadastro = findViewById(R.id.tv_cadastro); // Inicialização do TextView
        progressBar = findViewById(R.id.progress_bar);

        btnLogin.setOnClickListener(v -> fazerLogin());

        // Configurar o clique para ir para a tela de cadastro
        tvCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
            startActivity(intent);
        });
    }

    private void fazerLogin() {
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mostrar progress bar e desabilitar botão
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);

        LoginRequest loginRequest = new LoginRequest(email, senha);

        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                // Esconder progress bar e reabilitar botão
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    if (loginResponse.isSuccess()) {
                        Toast.makeText(MainActivity.this,
                                "Login bem-sucedido!",
                                Toast.LENGTH_LONG).show();

                        String token = loginResponse.getToken();
                        Log.d("MainActivity", "Token: " + token);

                        // Navegar para HostActivity (que contém a navegação principal)
                        Intent intent = new Intent(MainActivity.this, HostActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this,
                                loginResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,
                            "Falha no login. Verifique suas credenciais.",
                            Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", "Erro: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Esconder progress bar e reabilitar botão
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);

                Toast.makeText(MainActivity.this,
                        "Erro de conexão. Verifique se o servidor está rodando.",
                        Toast.LENGTH_LONG).show();
                Log.e("MainActivity", "Erro: " + t.getMessage(), t);
            }
        });
    }
}
