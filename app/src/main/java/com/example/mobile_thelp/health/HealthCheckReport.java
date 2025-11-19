package com.example.mobile_thelp.health;

import java.util.List;

public class HealthCheckReport {
    private List<HealthCheckResult> results;
    private HealthStatus overallStatus;
    private long generatedAt;

    public HealthCheckReport(List<HealthCheckResult> results) {
        this.results = results;
        this.generatedAt = System.currentTimeMillis();
        this.overallStatus = calculateOverallStatus();
    }

    private HealthStatus calculateOverallStatus() {
        boolean hasDown = false;
        boolean hasWarning = false;

        for (HealthCheckResult result : results) {
            if (result.getStatus() == HealthStatus.DOWN) {
                hasDown = true;
            } else if (result.getStatus() == HealthStatus.WARNING) {
                hasWarning = true;
            }
        }

        if (hasDown) return HealthStatus.DOWN;
        if (hasWarning) return HealthStatus.WARNING;
        return HealthStatus.UP;
    }

    public List<HealthCheckResult> getResults() { return results; }
    public HealthStatus getOverallStatus() { return overallStatus; }
    public long getGeneratedAt() { return generatedAt; }
}

