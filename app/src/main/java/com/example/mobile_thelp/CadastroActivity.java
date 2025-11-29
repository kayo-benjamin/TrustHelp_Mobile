package com.example.mobile_thelp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.gson.Gson;

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

    // ✅ MODO ONLINE ATIVADO
    private boolean isOfflineMode = false;

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
        etNome = findViewById(R.id.tbx_nome);
        etEmail = findViewById(R.id.tbx_email_cadastro);
        etSenha = findViewById(R.id.tbx_senha_cadastro);
        idPapel = findViewById(R.id.editIdPapel);
        idOrganizacao = findViewById(R.id.editIdOrganizacao);

        etOrgNome = findViewById(R.id.et_org_nome);
        etOrgCnpj = findViewById(R.id.et_org_cnpj);
        etOrgEmail = findViewById(R.id.et_org_email);
        etOrgTelefone = findViewById(R.id.et_org_telefone);

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
        if (etOrgCnpj == null) return;
        etOrgCnpj.addTextChangedListener(new TextWatcher() {
            boolean isUpdating;
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) { isUpdating = false; return; }
                String str = s.toString().replaceAll("[^\\d]", "");
                if (str.length() > 14) str = str.substring(0, 14);
                StringBuilder masked = new StringBuilder();
                for (int i = 0; i < str.length(); i++) {
                    if (i == 2 || i == 5) masked.append(".");
                    else if (i == 8) masked.append("/");
                    else if (i == 12) masked.append("-");
                    masked.append(str.charAt(i));
                }
                isUpdating = true;
                etOrgCnpj.setText(masked.toString());
                etOrgCnpj.setSelection(masked.length());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void setupTelefoneMask() {
        if (etOrgTelefone == null) return;
        etOrgTelefone.addTextChangedListener(new TextWatcher() {
            boolean isUpdating;
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) { isUpdating = false; return; }
                String str = s.toString().replaceAll("[^\\d]", "");
                if (str.length() > 11) str = str.substring(0, 11);
                StringBuilder masked = new StringBuilder();
                if (str.length() > 0) {
                    masked.append("(");
                    for (int i = 0; i < str.length(); i++) {
                        if (i == 2) masked.append(") ");
                        else if (i == 7) masked.append("-");
                        masked.append(str.charAt(i));
                    }
                }
                isUpdating = true;
                etOrgTelefone.setText(masked.toString());
                etOrgTelefone.setSelection(masked.length());
            }
            @Override public void afterTextChanged(Editable s) {}
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
            btnCadastrar.setEnabled(false);
            
            Call<Organizacao> call = apiService.getOrganizacaoById(orgId);
            call.enqueue(new Callback<Organizacao>() {
                @Override
                public void onResponse(Call<Organizacao> call, Response<Organizacao> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        cadastrarUsuarioFinal();
                    } else {
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
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
        }
    }

    private void showOrganizacaoNaoEncontradaDialog() {
        if (isFinishing()) return;
        new AlertDialog.Builder(CadastroActivity.this)
                .setTitle("Organização não encontrada")
                .setMessage("Deseja cadastrar uma nova organização?")
                .setPositiveButton("SIM", (dialog, which) -> rgTipoCadastro.check(R.id.rb_organizacao))
                .setNegativeButton("NÃO", null)
                .show();
    }

    private void cadastrarUsuarioFinal() {
        User novoUsuario = new User(etNome.getText().toString(), etEmail.getText().toString(), 
                                  etSenha.getText().toString(), 
                                  Integer.parseInt(idPapel.getText().toString()), 
                                  Integer.parseInt(idOrganizacao.getText().toString()));
        
        Call<ApiResponse> call = apiService.cadastro(novoUsuario);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                btnCadastrar.setEnabled(true);
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Sucesso!", Toast.LENGTH_LONG).show();
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

        Call<Organizacao> call = apiService.criarOrganizacao(novaOrg);
        call.enqueue(new Callback<Organizacao>() {
            @Override
            public void onResponse(Call<Organizacao> call, Response<Organizacao> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Long orgId = response.body().getId();
                    if (orgId != null) {
                        btnCadastrar.setEnabled(true);
                        showSuccessOrgDialog(orgId);
                    } else {
                        buscarOrganizacaoPorCnpj(novaOrg.getCnpj());
                    }
                } else {
                    btnCadastrar.setEnabled(true);
                    Toast.makeText(CadastroActivity.this, "Erro ao criar: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Organizacao> call, Throwable t) {
                btnCadastrar.setEnabled(true);
                Toast.makeText(CadastroActivity.this, "Erro conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscarOrganizacaoPorCnpj(String cnpj) {
        Call<Organizacao> call = apiService.getOrganizacaoByCnpj(cnpj);
        call.enqueue(new Callback<Organizacao>() {
            @Override
            public void onResponse(Call<Organizacao> call, Response<Organizacao> response) {
                btnCadastrar.setEnabled(true);
                if (response.isSuccessful() && response.body() != null && response.body().getId() != null) {
                    showSuccessOrgDialog(response.body().getId());
                } else {
                    Toast.makeText(CadastroActivity.this, "Criado, mas ID não recuperado.", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Organizacao> call, Throwable t) {
                btnCadastrar.setEnabled(true);
                Toast.makeText(CadastroActivity.this, "Erro recuperação ID", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showSuccessOrgDialog(Long orgId) {
        if (isFinishing()) return;

        String idMsg = (orgId != null) ? String.valueOf(orgId) : "Não identificado";

        new AlertDialog.Builder(CadastroActivity.this)
                .setTitle("Cadastro Realizado!")
                .setMessage("Organização cadastrada com sucesso.\nID da Organização: " + idMsg + "\n\nDeseja cadastrar um usuário agora?")
                .setPositiveButton("QUERO", (dialog, which) -> {
                    rgTipoCadastro.check(R.id.rb_usuario);
                    if (orgId != null) {
                        idOrganizacao.setText(String.valueOf(orgId));
                    }
                    Toast.makeText(CadastroActivity.this, "Preencha os dados do usuário", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("NÃO QUERO", (dialog, which) -> {
                    finish(); 
                })
                .setCancelable(false)
                .show();
    }
}
