package com.example.mobile_thelp.health;



public class HealthCheckResult {
    private String component;
    private HealthStatus status;
    private String message;
    private long timestamp;

    public HealthCheckResult(String component, HealthStatus status,
                             String message, long timestamp) {
        this.component = component;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters
    public String getComponent() { return component; }
    public HealthStatus getStatus() { return status; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
}

