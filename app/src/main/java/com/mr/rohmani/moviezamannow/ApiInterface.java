package com.mr.rohmani.moviezamannow;


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
    Call<MyMovie> getMovieList(@Query("api_key") String apiKey);
    @GET("{id}/videos")
    Call<MyVideo> getVideoList(@Path("id")int id, @Query("api_key") String apiKey);


}
