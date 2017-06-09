package com.example.geniusplaza.login.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by geniusplaza on 6/9/17.
 */

public class RestClient {
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://192.168.1.3:8000/";

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static GeniusApi getExampleApi() {
        return RestClient.getClient(BASE_URL).create(GeniusApi.class);
    }
}
