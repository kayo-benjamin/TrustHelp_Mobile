package com.example.mobile_thelp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat; // Import adicionado

import com.example.mobile_thelp.health.HealthCheckManager;
import com.example.mobile_thelp.health.HealthCheckReport;
import com.example.mobile_thelp.health.HealthCheckResult;
import com.example.mobile_thelp.health.HealthStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HealthCheckActivity extends AppCompatActivity {

    private TextView tvOverallStatus;
    private TextView tvResults;
    private TextView tvTimestamp;
    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_check);

        // Vincular elementos do layout
        tvOverallStatus = findViewById(R.id.tv_overall_status);
        tvResults = findViewById(R.id.tv_results);
        tvTimestamp = findViewById(R.id.tv_timestamp);
        btnRefresh = findViewById(R.id.btn_refresh);

        // Configurar listener do botão
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runHealthCheck();
            }
        });

        // Executar health check ao abrir a tela
        runHealthCheck();
    }

    private void runHealthCheck() {
        // Mostrar feedback ao usuário
        tvResults.setText("Executando verificações...\n\nAguarde...");
        tvOverallStatus.setText("Verificando...");
        btnRefresh.setEnabled(false);

        // Executar health check em background (simulação)
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Simular delay para verificações
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Executar o health check
                final HealthCheckManager manager = new HealthCheckManager(
                        HealthCheckActivity.this
                );
                final HealthCheckReport report = manager.runAllChecks();

                // Atualizar UI na thread principal
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(report);
                        btnRefresh.setEnabled(true);
                    }
                });
            }
        }).start();
    }

    private void updateUI(HealthCheckReport report) {
        // Atualizar status geral com cores
        HealthStatus overallStatus = report.getOverallStatus();
        tvOverallStatus.setText("Status: " + overallStatus.getDescription());

        // Definir cor baseada no status usando ContextCompat (Forma Moderna)
        switch (overallStatus) {
            case UP:
                tvOverallStatus.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
                tvOverallStatus.setText("✓ Sistema Saudável");
                break;
            case WARNING:
                tvOverallStatus.setTextColor(ContextCompat.getColor(this, android.R.color.holo_orange_dark));
                tvOverallStatus.setText("⚠ Atenção Necessária");
                break;
            case DOWN:
                tvOverallStatus.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
                tvOverallStatus.setText("✗ Sistema com Problemas");
                break;
            default:
                tvOverallStatus.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                tvOverallStatus.setText("? Status Desconhecido");
                break;
        }

        // Atualizar timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
                new Locale("pt", "BR"));
        String timestamp = sdf.format(new Date(report.getGeneratedAt()));
        tvTimestamp.setText("Última atualização: " + timestamp);

        // Mostrar resultados detalhados
        StringBuilder sb = new StringBuilder();
        for (HealthCheckResult result : report.getResults()) {
            String icon = getStatusIcon(result.getStatus());
            String statusText = String.format("%s %s\n   Status: %s\n   Detalhes: %s\n\n",
                    icon,
                    result.getComponent(),
                    result.getStatus().getDescription(),
                    result.getMessage());
            sb.append(statusText);
        }

        tvResults.setText(sb.toString());
    }

    private String getStatusIcon(HealthStatus status) {
        switch (status) {
            case UP:
                return "✓";
            case WARNING:
                return "⚠";
            case DOWN:
                return "✗";
            default:
                return "?";
        }
    }
}
