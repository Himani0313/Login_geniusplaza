package com.example.geniusplaza.login.Retrofit;

import com.example.geniusplaza.login.Pojo.AuthToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by geniusplaza on 6/9/17.
 */

public interface GeniusApi {

    @FormUrlEncoded
    @POST("o/token/")
    Call<AuthToken> postCredentials(@Header("Authorization") String authorization, @Query("username") String username, @Query("password") String password, @Field("grant_type") String grantType);

}
