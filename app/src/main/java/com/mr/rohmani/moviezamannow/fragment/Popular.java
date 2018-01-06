package com.mr.rohmani.moviezamannow.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mr.rohmani.moviezamannow.ApiClient;
import com.mr.rohmani.moviezamannow.Constants;
import com.mr.rohmani.moviezamannow.R;
import com.mr.rohmani.moviezamannow.adapter.DbMovieAdapter;
import com.mr.rohmani.moviezamannow.models.MovieList;
import com.mr.rohmani.moviezamannow.models.MyMovie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by USER on 18/11/2017.
 */

public class Popular extends Base {

    ApiClient apiClient;
    List<MyMovie.Result> results = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_layout, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        listData();

        return rootView;
    }

    private void listData() {
        showProgressDialog("Update data..");
        apiClient = new ApiClient();
        Call<MyMovie> call = apiClient.getApiInterface().getMoviePopular(Constants.API_KEY);
        call.enqueue(new Callback<MyMovie>() {
            @Override
            public void onResponse(Call<MyMovie> call, Response<MyMovie> response) {
                if (response.isSuccessful()) {
                    MyMovie movie = response.body();
                    results = movie.getResults();
                    updateDatabase(results, "popular");

                }
            }

            @Override
            public void onFailure(Call<MyMovie> call, Throwable t) {
                hideProgressDialog();
                showToast("an error occured plase check your internet connection!");
            }
        });

        initDatabase();
        List<MovieList> moviedb = db.getAllMovie("popular");
        adapter = new DbMovieAdapter(getActivity(), moviedb);
        if (adapter!=null){
            recyclerView.setAdapter(adapter);
        }else {
            showToast("Data is empty");
        }
    }

}
