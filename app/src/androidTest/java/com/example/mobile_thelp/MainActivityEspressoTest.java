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
    public void testNavegacaoParaCadastro() {
        // Clica no link de cadastro
        Espresso.onView(ViewMatchers.withId(R.id.tv_cadastro)).perform(ViewActions.click());

        // Verifica se a tela de cadastro foi aberta (verificando um elemento da tela de cadastro)
        Espresso.onView(ViewMatchers.withId(R.id.btn_cadastrar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testLoginComFalha() {
        // Digita um email inválido
        Espresso.onView(ViewMatchers.withId(R.id.tbx_user))
                .perform(ViewActions.replaceText("usuario@errado.com"), ViewActions.closeSoftKeyboard());

        // Digita uma senha inválida
        Espresso.onView(ViewMatchers.withId(R.id.tbx_senha))
                .perform(ViewActions.replaceText("senhaerrada"), ViewActions.closeSoftKeyboard());

        // Clica no botão de entrar
        Espresso.onView(ViewMatchers.withId(R.id.btn_entrar)).perform(ViewActions.click());

        // Verifica se a tela de login ainda está visível
        Espresso.onView(ViewMatchers.withId(R.id.btn_entrar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testLoginComSucesso() {
        // Digita o email
        Espresso.onView(ViewMatchers.withId(R.id.tbx_user))
                .perform(ViewActions.replaceText("admin@admin.com"), ViewActions.closeSoftKeyboard());

        // Digita a senha
        Espresso.onView(ViewMatchers.withId(R.id.tbx_senha))
                .perform(ViewActions.replaceText("admin123"), ViewActions.closeSoftKeyboard());

        // Clica no botão de entrar
        Espresso.onView(ViewMatchers.withId(R.id.btn_entrar)).perform(ViewActions.click());

        // Verifica se a HomeActivity foi aberta (verificando o TextView de boas-vindas)
        Espresso.onView(ViewMatchers.withId(R.id.tv_bem_vindo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
