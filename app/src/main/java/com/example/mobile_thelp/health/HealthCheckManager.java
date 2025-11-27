package com.example.mobile_thelp.health;

import android.content.Context;
import com.example.mobile_thelp.client.RetrofitClient;
import com.example.mobile_thelp.model.HealthCheckResponse;
import com.example.mobile_thelp.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthCheckManager {
    private Context context;
    private List<HealthCheckResult> results;
    private ApiService apiService;

    public HealthCheckManager(Context context) {
        this.context = context;
        this.results = new ArrayList<>();
        this.apiService = RetrofitClient.getApiService();
    }

    public HealthCheckReport runAllChecks() {
        results.clear();

        // Executar todas as verificações locais
        checkDatabase();
        checkPermissions();
        checkStorage();
        checkMemory();

        // Verificação de conectividade - agora verifica o backend
        checkBackendConnectivity();

        return new HealthCheckReport(results);
    }

    private void checkDatabase() {
        try {
            results.add(new HealthCheckResult(
                    "Database",
                    HealthStatus.UP,
                    "Database is accessible",
                    System.currentTimeMillis()
            ));
        } catch (Exception e) {
            results.add(new HealthCheckResult(
                    "Database",
                    HealthStatus.DOWN,
                    "Error: " + e.getMessage(),
                    System.currentTimeMillis()
            ));
        }
    }

    private void checkPermissions() {
        results.add(new HealthCheckResult(
                "Permissions",
                HealthStatus.UP,
                "All permissions granted",
                System.currentTimeMillis()
        ));
    }

    private void checkStorage() {
        try {
            long freeSpace = context.getFilesDir().getFreeSpace();
            long totalSpace = context.getFilesDir().getTotalSpace();
            double percentFree = (freeSpace * 100.0) / totalSpace;

            HealthStatus status = percentFree > 10 ? HealthStatus.UP : HealthStatus.WARNING;

            results.add(new HealthCheckResult(
                    "Storage",
                    status,
                    String.format("Free: %.2f%%", percentFree),
                    System.currentTimeMillis()
            ));
        } catch (Exception e) {
            results.add(new HealthCheckResult(
                    "Storage",
                    HealthStatus.DOWN,
                    "Error checking storage",
                    System.currentTimeMillis()
            ));
        }
    }

    private void checkMemory() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        double percentUsed = (usedMemory * 100.0) / maxMemory;

        HealthStatus status = percentUsed < 80 ? HealthStatus.UP : HealthStatus.WARNING;

        results.add(new HealthCheckResult(
                "Memory",
                status,
                String.format("Used: %.2f%%", percentUsed),
                System.currentTimeMillis()
        ));
    }

    // ✅ NOVO: Verifica a conectividade com o backend
    private void checkBackendConnectivity() {
        try {
            Call<HealthCheckResponse> call = apiService.getBackendHealth();
            call.enqueue(new Callback<HealthCheckResponse>() {
                @Override
                public void onResponse(Call<HealthCheckResponse> call, Response<HealthCheckResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        HealthCheckResponse backendHealth = response.body();

                        HealthStatus status = backendHealth.isHealthy() ? HealthStatus.UP : HealthStatus.DOWN;
                        String message = String.format("Backend: %s | DB: %s",
                                backendHealth.getStatus(),
                                backendHealth.getDatabase());

                        results.add(new HealthCheckResult(
                                "Backend API",
                                status,
                                message,
                                System.currentTimeMillis()
                        ));
                    } else {
                        results.add(new HealthCheckResult(
                                "Backend API",
                                HealthStatus.DOWN,
                                "Backend responded with error: " + response.code(),
                                System.currentTimeMillis()
                        ));
                    }
                }

                @Override
                public void onFailure(Call<HealthCheckResponse> call, Throwable t) {
                    results.add(new HealthCheckResult(
                            "Backend API",
                            HealthStatus.DOWN,
                            "Cannot reach backend: " + t.getMessage(),
                            System.currentTimeMillis()
                    ));
                }
            });
        } catch (Exception e) {
            results.add(new HealthCheckResult(
                    "Backend API",
                    HealthStatus.DOWN,
                    "Error checking backend: " + e.getMessage(),
                    System.currentTimeMillis()
            ));
        }
    }
}
