package com.mr.rohmani.moviezamannow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
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

    TextView tvTitle, tvDuration, tvRating, tvCompany, tvGenre, tvOtherview;
    ImageView imgPoster, imageBanner;
    RecyclerView recyclerView;
    VideoAdapter adapter;
    ApiClient apiClient;
    private ProgressDialog mProgressDialog;
    public int id;
    public String poster;
    public String title;
    public List<MyVideo.Result> results = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.toolbarsize);
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.white));

        recyclerView = (RecyclerView) findViewById(R.id.recycle_triler);
        tvTitle= (TextView) findViewById(R.id.title);
        tvDuration= (TextView) findViewById(R.id.duration);
        tvRating= (TextView) findViewById(R.id.rating);
        tvCompany= (TextView) findViewById(R.id.company);
        tvGenre= (TextView) findViewById(R.id.genre);
        tvOtherview= (TextView) findViewById(R.id.otherview);
        imgPoster= (ImageView) findViewById(R.id.image2);
        imageBanner = (ImageView) findViewById(R.id.image);
        Intent i = getIntent();
        if(i.getExtras()!=null){
            id = i.getIntExtra("id",0);
            title = i.getStringExtra("title");
            poster = i.getStringExtra("poster");
            collapsingToolbarLayout.setTitle(title);
        }
        showProgressDialog();
        listData();
        getDetails();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                    tvTitle.setText(movie.getReleaseDate().substring(0, 4));
                    tvDuration.setText(String.valueOf(movie.getRuntime())+" M");
                    tvRating.setText(String.valueOf(movie.getVoteAverage())+"/10");
                    tvCompany.setText(movie.getProductionCompanies().get(0).getName());
                    tvGenre.setText(getGenres(movie.getGenres()));
                    tvOtherview.setText(movie.getOverview());
                    final String imageURL = "http://image.tmdb.org/t/p/w185/"+poster;
                    Picasso.with(DetailMovie.this).load(imageURL).resize(600, 400)
                            .centerCrop().into(imageBanner);
                    Picasso.with(DetailMovie.this).load(imageURL).fit().centerCrop().into(imgPoster);
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
