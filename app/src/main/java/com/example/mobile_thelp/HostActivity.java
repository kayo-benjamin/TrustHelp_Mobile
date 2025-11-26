package com.example.mobile_thelp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navHost);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

            // Conecta o menu com o controlador de navegação
            NavigationUI.setupWithNavController(bottomNav, navController);

            // CORREÇÃO: Usando o método moderno setOnItemReselectedListener
            bottomNav.setOnItemReselectedListener(item -> {
                // Se o item re-selecionado não for o início, não faz nada.
                // Se for o início, ele limpa a pilha de volta para o Dashboard.
                if (item.getItemId() == R.id.navigation_home) {
                    navController.popBackStack(R.id.navigation_home, false);
                }
            });
        }
    }
}
