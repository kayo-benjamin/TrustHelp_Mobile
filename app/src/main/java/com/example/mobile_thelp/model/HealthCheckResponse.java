package com.example.mobile_thelp.model;

public class HealthCheckResponse {
    private String status;
    private String database;
    private long timestamp;

    // Constructors
    public HealthCheckResponse() {}

    public HealthCheckResponse(String status, String database, long timestamp) {
        this.status = status;
        this.database = database;
        this.timestamp = timestamp;
    }

    // Getters e Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // Helper methods
    public boolean isHealthy() {
        return "UP".equalsIgnoreCase(status);
    }

    public boolean isDatabaseConnected() {
        return "CONNECTED".equalsIgnoreCase(database);
    }

    @Override
    public String toString() {
        return "HealthCheckResponse{" +
                "status='" + status + '\'' +
                ", database='" + database + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
