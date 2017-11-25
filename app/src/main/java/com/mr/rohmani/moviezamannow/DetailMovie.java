package com.mr.rohmani.moviezamannow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mr.rohmani.moviezamannow.adapter.VideoAdapter;
import com.mr.rohmani.moviezamannow.models.MyMovieDetail;
import com.mr.rohmani.moviezamannow.models.MyVideo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovie extends AppCompatActivity {

    TextView tvTitle, tvRilis, tvDuration, tvRating, tvCompany, tvGenre, tvOtherview;
    ImageView imgPoster;
    RecyclerView recyclerView;
    VideoAdapter adapter;
    ApiClient apiClient;
    private ProgressDialog mProgressDialog;
    public int id;
    public String poster;
    public List<MyVideo.Result> results = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_triler);
        tvTitle= (TextView) findViewById(R.id.title);
        tvRilis= (TextView) findViewById(R.id.rilis);
        tvDuration= (TextView) findViewById(R.id.duration);
        tvRating= (TextView) findViewById(R.id.rating);
        tvCompany= (TextView) findViewById(R.id.company);
        tvGenre= (TextView) findViewById(R.id.genre);
        tvOtherview= (TextView) findViewById(R.id.otherview);
        imgPoster= (ImageView) findViewById(R.id.poster);

        Intent i = getIntent();
        if(i.getExtras()!=null){
            id = i.getIntExtra("id",0);
            poster = i.getStringExtra("poster");
        }
        showProgressDialog();
        listData();
        getDetails();
    }

    private void listData() {
        apiClient = new ApiClient();
        Call<MyVideo> call = apiClient.getApiInterface().getVideoList(id, Constants.API_KEY);
        call.enqueue(new Callback<MyVideo>() {
            @Override
            public void onResponse(Call<MyVideo> call, Response<MyVideo> response) {
                if (response.isSuccessful()) {
                    MyVideo movie = response.body();
                    results = movie.getResults();
                    adapter = new VideoAdapter(DetailMovie.this, results);
                    recyclerView.setLayoutManager(new LinearLayoutManager(DetailMovie.this));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MyVideo> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

    private void getDetails() {
        apiClient = new ApiClient();
        Call<MyMovieDetail> call = apiClient.getApiInterface().getMovieDetails(id, Constants.API_KEY);
        call.enqueue(new Callback<MyMovieDetail>() {
            @Override
            public void onResponse(Call<MyMovieDetail> call, Response<MyMovieDetail> response) {
                if (response.isSuccessful()) {
                    MyMovieDetail movie = response.body();
                    tvTitle.setText(movie.getTitle());
                    tvRilis.setText(movie.getReleaseDate().substring(0, 4));
                    tvDuration.setText(String.valueOf(movie.getRuntime())+" Minutes");
                    tvRating.setText(String.valueOf(movie.getVoteAverage())+"/10");
                    tvCompany.setText("Company: "+movie.getProductionCompanies().get(0).getName());
                    tvGenre.setText("Genre: "+getGenres(movie.getGenres()));
                    tvOtherview.setText(movie.getOverview());
                    final String imageURL = "http://image.tmdb.org/t/p/w185/"+poster;
                    Picasso.with(DetailMovie.this).load(imageURL).into(imgPoster);
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<MyMovieDetail> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

    private String getGenres(List<MyMovieDetail.Genre> genre){
        int i;
        String result ="";
        for (i=0;i<genre.size()-1;i++){
            result = result+genre.get(i).getName()+", ";
        }

        return result;
    }

    //show progres dialog when btn login clicked
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }
    //hidding progress dialog
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
