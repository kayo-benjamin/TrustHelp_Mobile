package com.example.mobile_thelp.health;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class HealthCheckReportTest {

    @Test
    public void testHealthCheckReportGeneration() {
        // Simular resultados
        List<HealthCheckResult> results = new ArrayList<>();
        results.add(new HealthCheckResult("Test", HealthStatus.UP,
                "OK", System.currentTimeMillis()));

        HealthCheckReport report = new HealthCheckReport(results);

        assertEquals(HealthStatus.UP, report.getOverallStatus());
        assertNotNull(report.getResults());
        assertTrue(report.getGeneratedAt() > 0);
    }

    @Test
    public void testHealthStatusDownOverridesWarning() {
        List<HealthCheckResult> results = new ArrayList<>();
        results.add(new HealthCheckResult("Component1",
                HealthStatus.WARNING, "Warning", System.currentTimeMillis()));
        results.add(new HealthCheckResult("Component2",
                HealthStatus.DOWN, "Error", System.currentTimeMillis()));

        HealthCheckReport report = new HealthCheckReport(results);

        // DOWN deve ter prioridade sobre WARNING
        assertEquals(HealthStatus.DOWN, report.getOverallStatus());
    }

    @Test
    public void testHealthStatusWarningOverridesUp() {
        List<HealthCheckResult> results = new ArrayList<>();
        results.add(new HealthCheckResult("Component1",
                HealthStatus.UP, "OK", System.currentTimeMillis()));
        results.add(new HealthCheckResult("Component2",
                HealthStatus.WARNING, "Warning", System.currentTimeMillis()));

        HealthCheckReport report = new HealthCheckReport(results);

        // WARNING deve ter prioridade sobre UP
        assertEquals(HealthStatus.WARNING, report.getOverallStatus());
    }
    
    @Test
    public void testAllUpReturnsUp() {
        List<HealthCheckResult> results = new ArrayList<>();
        results.add(new HealthCheckResult("Component1",
                HealthStatus.UP, "OK", System.currentTimeMillis()));
        results.add(new HealthCheckResult("Component2",
                HealthStatus.UP, "OK", System.currentTimeMillis()));

        HealthCheckReport report = new HealthCheckReport(results);

        assertEquals(HealthStatus.UP, report.getOverallStatus());
    }
}
