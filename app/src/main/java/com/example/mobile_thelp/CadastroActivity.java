package com.example.mobile_thelp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_thelp.client.RetrofitClient;
import com.example.mobile_thelp.model.ApiResponse;
import com.example.mobile_thelp.model.Organizacao;
import com.example.mobile_thelp.model.User;
import com.example.mobile_thelp.services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {

    // Campos Usuário
    private EditText etNome, etEmail, etSenha, idPapel, idOrganizacao;
    
    // Campos Organização
    private EditText etOrgNome, etOrgCnpj, etOrgEmail, etOrgTelefone;
    
    // Containers
    private LinearLayout layoutUsuario, layoutOrganizacao;
    private RadioGroup rgTipoCadastro;
    
    private Button btnCadastrar;
    private TextView tvVoltarLogin;
    private ApiService apiService;

    private boolean isOfflineMode = false; // Alterar para false para usar a API real

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        apiService = RetrofitClient.getApiService();

        initViews();
        setupListeners();
        setupCnpjMask();
        setupTelefoneMask();
    }

    private void initViews() {
        // Usuário
        etNome = findViewById(R.id.tbx_nome);
        etEmail = findViewById(R.id.tbx_email_cadastro);
        etSenha = findViewById(R.id.tbx_senha_cadastro);
        idPapel = findViewById(R.id.editIdPapel);
        idOrganizacao = findViewById(R.id.editIdOrganizacao);

        // Organização
        etOrgNome = findViewById(R.id.et_org_nome);
        etOrgCnpj = findViewById(R.id.et_org_cnpj);
        etOrgEmail = findViewById(R.id.et_org_email);
        etOrgTelefone = findViewById(R.id.et_org_telefone);

        // Controles
        layoutUsuario = findViewById(R.id.layout_usuario);
        layoutOrganizacao = findViewById(R.id.layout_organizacao);
        rgTipoCadastro = findViewById(R.id.rg_tipo_cadastro);
        btnCadastrar = findViewById(R.id.btn_cadastrar);
        tvVoltarLogin = findViewById(R.id.tv_voltar_login);
    }

    private void setupListeners() {
        rgTipoCadastro.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_usuario) {
                layoutUsuario.setVisibility(View.VISIBLE);
                layoutOrganizacao.setVisibility(View.GONE);
                // Garante que os campos e botão estejam habilitados ao trocar de aba
                idOrganizacao.setEnabled(true);
                btnCadastrar.setEnabled(true);
            } else {
                layoutUsuario.setVisibility(View.GONE);
                layoutOrganizacao.setVisibility(View.VISIBLE);
                btnCadastrar.setEnabled(true);
            }
        });

        btnCadastrar.setOnClickListener(v -> {
            if (rgTipoCadastro.getCheckedRadioButtonId() == R.id.rb_usuario) {
                verificarEcadastrarUsuario();
            } else {
                cadastrarOrganizacao();
            }
        });

        tvVoltarLogin.setOnClickListener(v -> finish());
    }

    private void setupCnpjMask() {
        etOrgCnpj.addTextChangedListener(new TextWatcher() {
            boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString().replaceAll("[^\\d]", "");
                if (str.length() > 14) str = str.substring(0, 14);
                
                StringBuilder masked = new StringBuilder();
                int len = str.length();

                for (int i = 0; i < len; i++) {
                    if (i == 2 || i == 5) masked.append(".");
                    else if (i == 8) masked.append("/");
                    else if (i == 12) masked.append("-");
                    masked.append(str.charAt(i));
                }

                isUpdating = true;
                etOrgCnpj.setText(masked.toString());
                etOrgCnpj.setSelection(masked.length());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupTelefoneMask() {
        etOrgTelefone.addTextChangedListener(new TextWatcher() {
            boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString().replaceAll("[^\\d]", "");
                if (str.length() > 11) str = str.substring(0, 11);
                
                StringBuilder masked = new StringBuilder();
                int len = str.length();

                if (len > 0) {
                    masked.append("(");
                    for (int i = 0; i < len; i++) {
                        if (i == 2) {
                            masked.append(") ");
                        } else if (i == 7) {
                            masked.append("-");
                        }
                        masked.append(str.charAt(i));
                    }
                }

                isUpdating = true;
                etOrgTelefone.setText(masked.toString());
                etOrgTelefone.setSelection(masked.length());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void verificarEcadastrarUsuario() {
        String papelStr = idPapel.getText().toString().trim();
        String organizacaoStr = idOrganizacao.getText().toString().trim();

        if (etNome.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty() || 
            etSenha.getText().toString().isEmpty() || papelStr.isEmpty() || organizacaoStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Long orgId = Long.parseLong(organizacaoStr);
            
            // Verificar se a organização existe antes de cadastrar
            btnCadastrar.setEnabled(false);
            
            if (isOfflineMode) {
                // Simulação: Org ID 1 existe, outros não
                if (orgId == 1) cadastrarUsuarioFinal();
                else showOrganizacaoNaoEncontradaDialog();
            } else {
                Call<Organizacao> call = apiService.getOrganizacaoById(orgId);
                call.enqueue(new Callback<Organizacao>() {
                    @Override
                    public void onResponse(Call<Organizacao> call, Response<Organizacao> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Organização existe, prosseguir
                            cadastrarUsuarioFinal();
                        } else {
                            // Organização não encontrada (404 ou outro erro)
                            btnCadastrar.setEnabled(true);
                            showOrganizacaoNaoEncontradaDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<Organizacao> call, Throwable t) {
                        btnCadastrar.setEnabled(true);
                        Toast.makeText(CadastroActivity.this, "Erro ao verificar organização: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID da Organização inválido", Toast.LENGTH_SHORT).show();
        }
    }

    private void showOrganizacaoNaoEncontradaDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Organização não encontrada")
                .setMessage("A organização com este ID não existe.\n\nDeseja cadastrar uma nova organização?")
                .setPositiveButton("SIM, CADASTRAR", (dialog, which) -> {
                    rgTipoCadastro.check(R.id.rb_organizacao); // Vai para a aba de organização
                })
                .setNegativeButton("NÃO", (dialog, which) -> {
                    showCredencialIncorretaDialog();
                })
                .setCancelable(false)
                .show();
    }

    private void showCredencialIncorretaDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Para se cadastrar como usuário, é obrigatório fornecer um ID de organização válido.")
                .setPositiveButton("ENTENDIDO", (dialog, which) -> {
                    dialog.dismiss();
                    idOrganizacao.requestFocus(); // Foca no campo para corrigir
                })
                .setCancelable(false)
                .show();
    }

    private void cadastrarUsuarioFinal() {
        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();
        Integer papel = Integer.parseInt(idPapel.getText().toString().trim());
        Integer org = Integer.parseInt(idOrganizacao.getText().toString().trim());

        User novoUsuario = new User(nome, email, senha, papel, org);
        
        if (isOfflineMode) {
            Toast.makeText(this, "Usuário (Simulado) cadastrado!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Call<ApiResponse> call = apiService.cadastro(novoUsuario);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    btnCadastrar.setEnabled(true);
                    if (response.isSuccessful()) {
                        Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(CadastroActivity.this, "Erro: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    btnCadastrar.setEnabled(true);
                    Toast.makeText(CadastroActivity.this, "Erro conexão", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void cadastrarOrganizacao() {
        String nome = etOrgNome.getText().toString().trim();
        String cnpj = etOrgCnpj.getText().toString().trim();
        String email = etOrgEmail.getText().toString().trim();
        String telefone = etOrgTelefone.getText().toString().trim();

        if (nome.isEmpty() || cnpj.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (cnpj.length() < 18) {
             Toast.makeText(this, "CNPJ incompleto", Toast.LENGTH_SHORT).show();
             return;
        }

        btnCadastrar.setEnabled(false);
        
        Organizacao novaOrg = new Organizacao(nome, cnpj, email, telefone);

        if (isOfflineMode) {
            showSuccessOrgDialog(123L);
        } else {
            Call<Organizacao> call = apiService.criarOrganizacao(novaOrg);
            call.enqueue(new Callback<Organizacao>() {
                @Override
                public void onResponse(Call<Organizacao> call, Response<Organizacao> response) {
                    btnCadastrar.setEnabled(true);
                    if (response.isSuccessful() && response.body() != null) {
                        showSuccessOrgDialog(response.body().getId());
                    } else {
                        Toast.makeText(CadastroActivity.this, "Erro ao criar: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Organizacao> call, Throwable t) {
                    btnCadastrar.setEnabled(true);
                    Toast.makeText(CadastroActivity.this, "Erro conexão", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showSuccessOrgDialog(Long orgId) {
        new AlertDialog.Builder(this)
                .setTitle("Cadastro Realizado!")
                .setMessage("Organização cadastrada com sucesso.\nID da Organização: " + orgId + "\n\nDeseja cadastrar um usuário agora?")
                .setPositiveButton("QUERO", (dialog, which) -> {
                    rgTipoCadastro.check(R.id.rb_usuario);
                    idOrganizacao.setText(String.valueOf(orgId));
                    // idOrganizacao.setEnabled(false); // REMOVIDO BLOQUEIO para permitir correção se necessário
                    Toast.makeText(this, "Preencha os dados do usuário", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("NÃO QUERO", (dialog, which) -> {
                    finish(); 
                })
                .setCancelable(false)
                .show();
    }
}
