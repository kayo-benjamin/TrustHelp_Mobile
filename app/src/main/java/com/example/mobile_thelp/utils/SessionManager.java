package com.example.mobile_thelp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "TrustHelpSession";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_IS_LOGGED = "is_logged";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // ✅ Salvar sessão do usuário
    public void createLoginSession(String token, Integer userId, String userName, String userEmail) {
        editor.putString(KEY_TOKEN, token);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_EMAIL, userEmail);
        editor.putBoolean(KEY_IS_LOGGED, true);
        editor.commit();
    }

    // ✅ Verificar se está logado
    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED, false);
    }

    // ✅ Pegar token
    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    // ✅ Pegar ID do usuário
    public Integer getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    // ✅ Pegar nome do usuário
    public String getUserName() {
        return prefs.getString(KEY_USER_NAME, null);
    }

    // ✅ Pegar email do usuário
    public String getUserEmail() {
        return prefs.getString(KEY_USER_EMAIL, null);
    }

    // ✅ Fazer logout
    public void logout() {
        editor.clear();
        editor.commit();
    }
}