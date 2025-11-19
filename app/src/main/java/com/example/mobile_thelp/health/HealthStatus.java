package com.example.mobile_thelp.health;

public enum HealthStatus {
    UP("Healthy"),
    DOWN("Critical"),
    WARNING("Warning"),
    UNKNOWN("Unknown");

    private String description;

    HealthStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
