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
public class CadastroActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<CadastroActivity> activityRule = new ActivityScenarioRule<>(CadastroActivity.class);

    @Test
    public void testCadastroComCamposVazios() {
        // Clica no botão de cadastrar sem preencher os campos
        Espresso.onView(ViewMatchers.withId(R.id.btn_cadastrar)).perform(ViewActions.click());

        // Verifica se a tela de cadastro ainda está visível
        Espresso.onView(ViewMatchers.withId(R.id.btn_cadastrar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testCadastroComSucesso() {
        // Preenche os campos de cadastro
        Espresso.onView(ViewMatchers.withId(R.id.tbx_nome))
                .perform(ViewActions.replaceText("Novo Usuário"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.tbx_email_cadastro))
                .perform(ViewActions.replaceText("novo@usuario.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.tbx_senha_cadastro))
                .perform(ViewActions.replaceText("123456"), ViewActions.closeSoftKeyboard());

        // Clica no botão de cadastrar
        Espresso.onView(ViewMatchers.withId(R.id.btn_cadastrar)).perform(ViewActions.click());

        // Verifica se a tela de login é exibida novamente
        Espresso.onView(ViewMatchers.withId(R.id.btn_entrar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
