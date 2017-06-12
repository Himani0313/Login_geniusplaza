package com.example.geniusplaza.login;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geniusplaza.login.Pojo.AuthToken;
import com.example.geniusplaza.login.Retrofit.GeniusApi;
import com.example.geniusplaza.login.Retrofit.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    GeniusApi geniusApi;
    EditText uname, pword;
    TextView timer;
    String userName, password, secretKey;
    ApiConstants apiConstants;
    public AuthToken authToken;
    public CountDownTimer cTimer = null;
    public  String refreshToken;
    public String refreshToken(AuthToken authToken){
        return authToken.getRefreshToken();
    }
    //Retrofit retrofit;
    //RestClient restClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiConstants = new ApiConstants();
        authToken = new AuthToken();
//        uname = (EditText)findViewById(R.id.username);
//        pword = (EditText)findViewById(R.id.password);

        //geniusApi = retrofit.create(GeniusApi.class);

    }
    public void loginButtonClicked(View v){
        uname = (EditText)findViewById(R.id.username);
        pword = (EditText)findViewById(R.id.password);
        timer = (TextView)findViewById(R.id.timer);
        userName = uname.getText().toString();
        password = pword.getText().toString();
        Log.d("aaaaaaaa", userName);
        Log.d("aaaaaaaa", password);
        if (userName == null || password == null){
            Toast.makeText(this, "Enter the Credentials", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("Main Activity","API call");
            secretKey = apiConstants.getBase64();
            RestClient.getExampleApi().postCredentials("Basic "+secretKey, userName, password, "password").enqueue(tokenCallback);
        }
    }
    Callback<AuthToken> tokenCallback = new Callback<AuthToken>() {
        @Override
        public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
            if(response.isSuccessful()){
                Log.d("in main activity","SUCCCESSSS");
                timer.setVisibility(View.VISIBLE);
                refreshToken = response.body().getRefreshToken();
                startTimer(refreshToken);
            }
            else{
                Toast.makeText(MainActivity.this, "1st token request fail", Toast.LENGTH_LONG).show();
            }

        }
        @Override
        public void onFailure(Call<AuthToken> call, Throwable t) {
            t.printStackTrace();
            Log.i("error",t.toString());
        }
    };
    Callback<AuthToken> refreshCallback = new Callback<AuthToken>() {

        @Override
        public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {

            Log.d("AAAAAAAAAAA",response.body().toString());
            if (response.isSuccessful()){
                //Log.d("in main actvity 2","SUCCCESSSS");
                //Log.d("in main actvity 2",response.body().getRefreshToken());
                // Log.d("vvvvvvvvvvv", cTimer.);
                //cTimer.cancel();
                startTimer(response.body().getRefreshToken());
            }
            else{
                Toast.makeText(MainActivity.this, "1st token request fail", Toast.LENGTH_LONG).show();
            }


        }

        @Override
        public void onFailure(Call<AuthToken> call, Throwable t) {
            t.printStackTrace();
            Log.i("error",t.toString());
        }
    };
    public void startTimer(final String refreshToken) {
//        cTimer = new CountDownTimer(3000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                timer.setText("Seconds remaining till refresh token: " + millisUntilFinished / 1000);
//                //here you can have your logic to set text to edittext
//            }
//
//            public void onFinish() {
//                //apicall to refresh token
//                //Log.d("vvvvvvvvvvv", cTimer.toString());
//                cTimer.cancel();
//                RestClient.getExampleApi().postRefreshToken("Basic " + secretKey, refreshToken,"refresh_token").enqueue(refreshCallback);
//            }
//
//        }.start();
        
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("Inside delay",refreshToken);
                RestClient.getExampleApi().postRefreshToken("Basic " + secretKey, refreshToken,"refresh_token").enqueue(refreshCallback);
            }
        },3000);
    }
}
