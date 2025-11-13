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
    private EditText editNome, editEmail, editSenha, editTelefone, editCpf, editDataNascimento;
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
        editTelefone = findViewById(R.id.tbx_telefone_cadastro);
        editCpf = findViewById(R.id.editCpf); // ADD
        editDataNascimento = findViewById(R.id.editDataNascimento); // ADD
        btnCadastrar = findViewById(R.id.btn_cadastrar);

        apiService = RetrofitClient.getApiService();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();
        String telefone = editTelefone.getText().toString().trim();
        String cpf = editCpf.getText().toString().trim(); // ADD
        String dataNascimento = editDataNascimento.getText().toString().trim(); // ADD

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(nome, email, senha, telefone, cpf, dataNascimento);

        Call<User> call = apiService.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(CadastroActivity.this, "Erro: " + errorBody, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(CadastroActivity.this, "Erro no cadastro", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(CadastroActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("CADASTRO", "Erro: " + t.getMessage());
            }
        });
    }
}