package com.example.mobile_thelp;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {

    private EditText tbxNome, tbxEmail, tbxSenha;
    private Button btnCadastrar;
    private TextView tvVoltarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        // Inicializar componentes
        tbxNome = findViewById(R.id.tbx_nome);
        tbxEmail = findViewById(R.id.tbx_email_cadastro);
        tbxSenha = findViewById(R.id.tbx_senha_cadastro);
        btnCadastrar = findViewById(R.id.btn_cadastrar);
        tvVoltarLogin = findViewById(R.id.tv_voltar_login);

        // Sublinhar texto do link
        tvVoltarLogin.setPaintFlags(tvVoltarLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Ação do botão cadastrar
        btnCadastrar.setOnClickListener(v -> {
            String nome = tbxNome.getText().toString();
            String email = tbxEmail.getText().toString();
            String senha = tbxSenha.getText().toString();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {
                // Aqui você faria o cadastro (banco de dados, API, etc)
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                // Voltar para tela de login
                finish();
            }
        });

        // Voltar para login
        tvVoltarLogin.setOnClickListener(v -> {
            finish(); // Fecha a activity atual e volta para a anterior
        });
    }
}
