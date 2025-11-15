package com.example.mobile_thelp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_thelp.client.RetrofitClient;
import com.example.mobile_thelp.model.User;
import com.example.mobile_thelp.services.ApiService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {
    private EditText editNome, editEmail, editSenha, editIdPapel, editIdOrganizacao;
    private TextView txtLogin;
    private Button btnCadastrar;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicializar views
        editNome = findViewById(R.id.tbx_nome);
        editEmail = findViewById(R.id.tbx_email_cadastro);
        editSenha = findViewById(R.id.tbx_senha_cadastro);
        editIdPapel = findViewById(R.id.editIdPapel);
        editIdOrganizacao = findViewById(R.id.editIdOrganizacao);
        btnCadastrar = findViewById(R.id.btn_cadastrar);
        txtLogin = findViewById(R.id.tv_voltar_login);

        apiService = RetrofitClient.getApiService();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volta para a tela de login (MainActivity)
                startActivity(new Intent(CadastroActivity.this, MainActivity.class));
                finish(); // Finaliza a CadastroActivity para não ficar na pilha
            }
        });
    }

    private void cadastrarUsuario() {
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();
        String idPapelStr = editIdPapel.getText().toString().trim();
        String idOrganizacaoStr = editIdOrganizacao.getText().toString().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || idPapelStr.isEmpty() || idOrganizacaoStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        Integer idPapel = Integer.parseInt(idPapelStr);
        Integer idOrganizacao = Integer.parseInt(idOrganizacaoStr);

        User user = new User(nome, email, senha, idPapel, idOrganizacao);

        Call<User> call = apiService.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish(); // Volta para tela anterior
                } else {
                    String errorMessage = "Erro no cadastro";
                    if (response.errorBody() != null) {
                        try {
                            // Loga o erro exato vindo da API
                            String errorBody = response.errorBody().string();
                            Log.e("CadastroError", "Corpo do erro: " + errorBody);
                            errorMessage += ": Verifique os dados e tente novamente."; // Mensagem mais amigável
                        } catch (IOException e) {
                            Log.e("CadastroError", "Erro ao ler o corpo do erro", e);
                        }
                    } else {
                        errorMessage += ": " + response.message();
                    }
                    Toast.makeText(CadastroActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("CadastroError", "Falha na comunicação com a API", t);
                Toast.makeText(CadastroActivity.this, "Falha na comunicação: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
