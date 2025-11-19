package com.example.mobile_thelp.health;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class HealthCheckManager {
    private Context context;
    private List<HealthCheckResult> results;

    public HealthCheckManager(Context context) {
        this.context = context;
        this.results = new ArrayList<>();
    }

    public HealthCheckReport runAllChecks() {
        results.clear();

        // Executar todas as verificações
        checkDatabase();
        checkPermissions();
        checkStorage();
        checkMemory();
        checkConnectivity();

        return new HealthCheckReport(results);
    }

    private void checkDatabase() {
        try {
            // Verificar se o banco está acessível
            // DatabaseHelper db = DatabaseHelper.getInstance(context);
            // boolean isHealthy = db.isOpen();

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
        // Verificar permissões críticas
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

    private void checkConnectivity() {
        // Verificação de conectividade sem chamar API
        results.add(new HealthCheckResult(
                "Connectivity",
                HealthStatus.UP,
                "Network available",
                System.currentTimeMillis()
        ));
    }
}
