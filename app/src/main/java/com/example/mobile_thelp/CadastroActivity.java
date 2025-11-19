package com.example.mobile_thelp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_thelp.client.RetrofitClient;
import com.example.mobile_thelp.model.ApiResponse;
import com.example.mobile_thelp.model.User;
import com.example.mobile_thelp.services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome, etEmail, etSenha, idPapel, idOrganizacao;
    private Button btnCadastrar;
    private TextView tvVoltarLogin;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        apiService = RetrofitClient.getApiService();

        // VINCULANDO COM OS IDS CORRETOS DO XML
        etNome = findViewById(R.id.tbx_nome);
        etEmail = findViewById(R.id.tbx_email_cadastro);
        etSenha = findViewById(R.id.tbx_senha_cadastro);
        idPapel = findViewById(R.id.editIdPapel);
        idOrganizacao = findViewById(R.id.editIdOrganizacao);
        btnCadastrar = findViewById(R.id.btn_cadastrar);
        tvVoltarLogin = findViewById(R.id.tv_voltar_login);

        btnCadastrar.setOnClickListener(v -> fazerCadastro());

        tvVoltarLogin.setOnClickListener(v -> {
            // Voltar para a tela de login
            finish();
        });
    }

    private void fazerCadastro() {
        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();
        String papelStr = idPapel.getText().toString().trim();
        String organizacaoStr = idOrganizacao.getText().toString().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || papelStr.isEmpty() || organizacaoStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Integer idPapelInt = Integer.parseInt(papelStr);
            Integer idOrganizacaoInt = Integer.parseInt(organizacaoStr);


            User novoUsuario = new User(nome, email, senha, idPapelInt, idOrganizacaoInt);

            Call<ApiResponse> call = apiService.cadastro(novoUsuario);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse apiResponse = response.body();

                        if (apiResponse.isSuccess()) {
                            Toast.makeText(CadastroActivity.this,
                                    "Cadastro realizado com sucesso!",
                                    Toast.LENGTH_LONG).show();
                            finish(); // Volta para o login
                        } else {
                            Toast.makeText(CadastroActivity.this,
                                    apiResponse.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CadastroActivity.this,
                                "Erro: " + response.code(),
                                Toast.LENGTH_SHORT).show();
                        Log.e("CadastroActivity", "Erro HTTP: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(CadastroActivity.this,
                            "Erro de conexão: " + t.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Log.e("CadastroActivity", "Erro de conexão", t);
                }
            });

        } catch (NumberFormatException e) {
            Toast.makeText(this, "IDs devem ser números inteiros", Toast.LENGTH_SHORT).show();
        }
    }
}
