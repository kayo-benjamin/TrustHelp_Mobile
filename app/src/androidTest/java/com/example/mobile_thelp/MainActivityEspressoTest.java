package com.example.mobile_thelp;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testLoginComSucesso() {
        Espresso.onView(ViewMatchers.withId(R.id.tbx_user_login))
                .perform(ViewActions.replaceText("admin@thelp.com.br"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.tbx_senha_login))
                .perform(ViewActions.replaceText("123456"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btn_entrar_login)).perform(ViewActions.click());
        
        // Nota: Este teste pode falhar se o servidor não estiver rodando
    }

    @Test
    public void testFluxoDeCadastro() {
        Espresso.onView(ViewMatchers.withId(R.id.tv_cadastro)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.btn_cadastrar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.tbx_nome))
                .perform(ViewActions.replaceText("Usuário Teste"), ViewActions.closeSoftKeyboard());
                
        // ... outros campos ...
    }

    // ✅ NOVO TESTE ADICIONADO: HEALTH CHECK
    @Test
    public void testNavegacaoHealthCheck() {
        // 1. Verifica se o botão de Health Check está visível na tela de login
        Espresso.onView(ViewMatchers.withId(R.id.btn_health_check))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // 2. Clica no botão
        Espresso.onView(ViewMatchers.withId(R.id.btn_health_check))
                .perform(ViewActions.click());

        // 3. Verifica se a tela de Health Check abriu
        // Procuramos pelo ID 'tv_overall_status' que só existe na HealthCheckActivity
        Espresso.onView(ViewMatchers.withId(R.id.tv_overall_status))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
