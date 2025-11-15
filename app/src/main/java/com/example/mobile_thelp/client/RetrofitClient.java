package com.example.mobile_thelp.client;

import com.example.mobile_thelp.services.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*
public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.15.119:8080/"; // Altere para seu IP
    private static Retrofit retrofit = null;

    public static ApiService getApiService() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}*/

public class RetrofitClient {
    private static Retrofit retrofit = null;

    // ESCOLHA UMA DAS OPÇÕES ABAIXO:

    // Opção 1: Para emulador
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    // Opção 2: Para dispositivo físico (substitua pelo seu IP)
    // private static final String BASE_URL = "http://192.168.1.100:8080/";

    // Opção 3: Com adb reverse (funciona para ambos)
    // private static final String BASE_URL = "http://localhost:8080/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }
}