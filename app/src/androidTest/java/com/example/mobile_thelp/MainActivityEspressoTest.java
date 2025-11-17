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
        // Digita o email
        Espresso.onView(ViewMatchers.withId(R.id.tbx_user_login))
                .perform(ViewActions.replaceText("admin@thelp.com.br"), ViewActions.closeSoftKeyboard());

        // Digita a senha
        Espresso.onView(ViewMatchers.withId(R.id.tbx_senha_login))
                .perform(ViewActions.replaceText("123456"), ViewActions.closeSoftKeyboard());

        // Clica no botão de entrar
        Espresso.onView(ViewMatchers.withId(R.id.btn_entrar_login)).perform(ViewActions.click());

        // Verifica se a HomeActivity foi aberta (verificando o TextView de boas-vindas)
        Espresso.onView(ViewMatchers.withId(R.id.tv_bem_vindo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testLoginComFalha() {
        // Digita um email inválido
        Espresso.onView(ViewMatchers.withId(R.id.tbx_user_login))
                .perform(ViewActions.replaceText("usuario@errado.com"), ViewActions.closeSoftKeyboard());

        // Digita uma senha inválida
        Espresso.onView(ViewMatchers.withId(R.id.tbx_senha_login))
                .perform(ViewActions.replaceText("senhaerrada"), ViewActions.closeSoftKeyboard());

        // Clica no botão de entrar
        Espresso.onView(ViewMatchers.withId(R.id.btn_entrar_login)).perform(ViewActions.click());

        // Verifica se a tela de login ainda está visível
        Espresso.onView(ViewMatchers.withId(R.id.btn_entrar_login)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testFluxoDeCadastro() {
        // 1. Clica no link de cadastro na MainActivity
        Espresso.onView(ViewMatchers.withId(R.id.tv_cadastro)).perform(ViewActions.click());

        // 2. Verifica se a CadastroActivity foi aberta
        Espresso.onView(ViewMatchers.withId(R.id.btn_cadastrar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // 3. Preenche os campos de cadastro
        Espresso.onView(ViewMatchers.withId(R.id.tbx_nome))
                .perform(ViewActions.replaceText("Novo Usuário de Teste"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.tbx_email_cadastro))
                .perform(ViewActions.replaceText("teste@thelp.com.br"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.tbx_senha_cadastro))
                .perform(ViewActions.replaceText("123456"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.editIdPapel))
                .perform(ViewActions.replaceText("1"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.editIdOrganizacao))
                .perform(ViewActions.replaceText("1"), ViewActions.closeSoftKeyboard());

        // 4. Clica no botão de cadastrar
        Espresso.onView(ViewMatchers.withId(R.id.btn_cadastrar)).perform(ViewActions.click());

        // 5. Verifica se a tela de login (MainActivity) é exibida novamente
        Espresso.onView(ViewMatchers.withId(R.id.btn_entrar_login)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
