package com.example.mobile_thelp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_thelp.utils.SessionManager;

public class HomeActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private TextView tvBemVindo, tvEmailUsuario, tvIdUsuario;
    private Button btnMeuPerfil, btnConfiguracoes, btnAjuda, btnSobre, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializar SessionManager
        sessionManager = new SessionManager(this);

        // Inicializar componentes
        tvBemVindo = findViewById(R.id.tv_bem_vindo);
        tvEmailUsuario = findViewById(R.id.tv_email_usuario);
        tvIdUsuario = findViewById(R.id.tv_id_usuario);
        btnMeuPerfil = findViewById(R.id.btn_meu_perfil);
        btnConfiguracoes = findViewById(R.id.btn_configuracoes);
        btnAjuda = findViewById(R.id.btn_ajuda);
        btnSobre = findViewById(R.id.btn_sobre);
        btnLogout = findViewById(R.id.btn_logout);

        // Carregar dados do usuário
        carregarDadosUsuario();

        // Configurar botões
        configurarBotoes();
    }

    private void carregarDadosUsuario() {
        String nomeUsuario = sessionManager.getUserName();
        String emailUsuario = sessionManager.getUserEmail();
        Integer idUsuario = sessionManager.getUserId();

        // Atualizar TextViews
        tvBemVindo.setText("Bem-vindo, " + nomeUsuario + "!");
        tvEmailUsuario.setText("Email: " + emailUsuario);
        tvIdUsuario.setText("ID: " + idUsuario);
    }

    private void configurarBotoes() {

        // Botão Meu Perfil
        btnMeuPerfil.setOnClickListener(v -> {
            Toast.makeText(this, "Abrir Meu Perfil", Toast.LENGTH_SHORT).show();
            // TODO: Ir para tela de perfil
            // Intent intent = new Intent(HomeActivity.this, PerfilActivity.class);
            // startActivity(intent);
        });

        // Botão Configurações
        btnConfiguracoes.setOnClickListener(v -> {
            Toast.makeText(this, "Abrir Configurações", Toast.LENGTH_SHORT).show();
            // TODO: Ir para tela de configurações
            // Intent intent = new Intent(HomeActivity.this, ConfiguracoesActivity.class);
            // startActivity(intent);
        });

        // Botão Ajuda
        btnAjuda.setOnClickListener(v -> {
            Toast.makeText(this, "Abrir Ajuda", Toast.LENGTH_SHORT).show();
            // TODO: Ir para tela de ajuda
            // Intent intent = new Intent(HomeActivity.this, AjudaActivity.class);
            // startActivity(intent);
        });

        // Botão Sobre
        btnSobre.setOnClickListener(v -> {
            Toast.makeText(this, "TrustHelp v1.0", Toast.LENGTH_SHORT).show();
            // TODO: Ir para tela sobre
            // Intent intent = new Intent(HomeActivity.this, SobreActivity.class);
            // startActivity(intent);
        });

        // Botão Logout
        btnLogout.setOnClickListener(v -> {
            // Limpar sessão
            sessionManager.logout();

            Toast.makeText(this, "Logout realizado", Toast.LENGTH_SHORT).show();

            // Voltar para tela de login
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
