package com.example.mobile_thelp.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import java.io.File;

public class HealthCheckManagerTest {

    private HealthCheckManager manager;
    private Context context;
    private File mockFile;

    @Before
    public void setUp() {
        // Mock do contexto
        context = mock(Context.class);
        mockFile = mock(File.class);
        
        // Configurar comportamento do mock para checkStorage
        when(context.getFilesDir()).thenReturn(mockFile);
        when(mockFile.getFreeSpace()).thenReturn(1000L);
        when(mockFile.getTotalSpace()).thenReturn(2000L); // 50% livre

        manager = new HealthCheckManager(context);
    }

    @Test
    public void testRunAllChecks() {
        HealthCheckReport report = manager.runAllChecks();

        assertNotNull("O relatório não deve ser nulo", report);
        assertNotNull("A lista de resultados não deve ser nula", report.getResults());
        
        // Esperamos 5 verificações baseadas no código do HealthCheckManager:
        // Database, Permissions, Storage, Memory, Connectivity
        assertEquals("Deve haver 5 verificações executadas", 5, report.getResults().size());
    }
}
