package com.mr.rohmani.moviezamannow;


import com.mr.rohmani.moviezamannow.models.MyMovie;
import com.mr.rohmani.moviezamannow.models.MyMovieDetail;
import com.mr.rohmani.moviezamannow.models.MyVideo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gideon on 12/11/17.
 */

public interface ApiInterface {

    //------------GET------------------
    //movie
    @GET("popular")
    Call<MyMovie> getMoviePopular(@Query("api_key") String apiKey);
    @GET("top_rated")
    Call<MyMovie> getMovieTop(@Query("api_key") String apiKey);
    @GET("now_playing")
    Call<MyMovie> getMovieNow(@Query("api_key") String apiKey);
    @GET("upcoming")
    Call<MyMovie> getMovieUpcoming(@Query("api_key") String apiKey);
    @GET("{id}")
    Call<MyMovieDetail> getMovieDetails(@Path("id")int id, @Query("api_key") String apiKey);
    @GET("{id}/videos")
    Call<MyVideo> getVideoList(@Path("id")int id, @Query("api_key") String apiKey);


}
