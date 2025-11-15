package com.example.mobile_thelp.model;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("usuEmail")
    private String email;

    @SerializedName("usuSenha")
    private String senha;

    public LoginRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
