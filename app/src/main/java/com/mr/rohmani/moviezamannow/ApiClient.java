package com.mr.rohmani.moviezamannow;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gideon on 12/11/17.
 */

public class ApiClient {


    private ApiInterface mApiInterface;


    public ApiInterface getApiInterface() {
        if (mApiInterface == null) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder client = new OkHttpClient.Builder();

            client.addInterceptor(loggingInterceptor);

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mApiInterface = retrofit.create(ApiInterface.class);
            Log.d("GIDEON", String.valueOf(retrofit));
            Log.d("GIDEON", Constants.BASE_URL);
        }
        return mApiInterface;
    }

}
