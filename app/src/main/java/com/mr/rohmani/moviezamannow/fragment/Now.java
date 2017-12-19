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
import com.mr.rohmani.moviezamannow.adapter.MovieAdapter;
import com.mr.rohmani.moviezamannow.models.MyMovie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by USER on 25/11/2017.
 */

public class Now extends Base {
    RecyclerView recyclerView;

    MovieAdapter adapter;

    ApiClient apiClient;
    List<MyMovie.Result> results = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_layout, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle);
        showProgressDialog();
        listData();

        return rootView;
    }

    private void listData() {
        apiClient = new ApiClient();
        Call<MyMovie> call = apiClient.getApiInterface().getMovieNow(Constants.API_KEY);
        call.enqueue(new Callback<MyMovie>() {
            @Override
            public void onResponse(Call<MyMovie> call, Response<MyMovie> response) {
                if (response.isSuccessful()) {
                    MyMovie movie = response.body();
                    results = movie.getResults();
                    adapter = new MovieAdapter(getActivity(), results);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(adapter);
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<MyMovie> call, Throwable t) {
                hideProgressDialogToast();
            }
        });
    }
}
