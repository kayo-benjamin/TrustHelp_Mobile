package com.example.mobile_thelp;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_thelp.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText tbxUser, tbxSenha;
    private Button btnEntrar;
    private TextView tvCadastro;
    private ProgressBar progressBar;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ✅ Inicializar SessionManager
        sessionManager = new SessionManager(this);

        // ✅ Verificar se já está logado
        if (sessionManager.isLoggedIn()) {
            irParaHome();
            return;
        }

        // ✅ Inicializar componentes
        tbxUser = findViewById(R.id.tbx_user);
        tbxSenha = findViewById(R.id.tbx_senha);
        btnEntrar = findViewById(R.id.btn_entrar);
        tvCadastro = findViewById(R.id.tv_cadastro);
        progressBar = findViewById(R.id.progress_bar);

        // ✅ Sublinhar texto de cadastro
        tvCadastro.setPaintFlags(tvCadastro.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // ✅ Botão Entrar
        btnEntrar.setOnClickListener(v -> {
            String email = tbxUser.getText().toString().trim();
            String senha = tbxSenha.getText().toString().trim();

            if (validarCampos(email, senha)) {
                fazerLogin(email, senha);
            }
        });

        // ✅ Link para Cadastro
        tvCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
            startActivity(intent);
        });
    }

    // ✅ VALIDAR CAMPOS
    private boolean validarCampos(String email, String senha) {
        if (email.isEmpty()) {
            tbxUser.setError("Digite seu email");
            tbxUser.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tbxUser.setError("Email inválido");
            tbxUser.requestFocus();
            return false;
        }

        if (senha.isEmpty()) {
            tbxSenha.setError("Digite sua senha");
            tbxSenha.requestFocus();
            return false;
        }

        if (senha.length() < 6) {
            tbxSenha.setError("Senha deve ter no mínimo 6 caracteres");
            tbxSenha.requestFocus();
            return false;
        }

        return true;
    }


    // ✅ FAZER LOGIN
    private void fazerLogin(String email, String senha) {
        progressBar.setVisibility(View.VISIBLE);
        btnEntrar.setEnabled(false);

        // ✅ Simular autenticação
        if (email.equals("admin@admin.com") && senha.equals("admin123")){
            irParaHome();
        }else {
            Toast.makeText(this, "Email ou senha inválidos", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btnEntrar.setEnabled(true);
        }
    }

    // ✅ IR PARA TELA HOME
    private void irParaHome() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}