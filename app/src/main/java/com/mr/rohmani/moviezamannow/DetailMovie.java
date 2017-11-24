package com.mr.rohmani.moviezamannow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovie extends AppCompatActivity {

    public int id;
    public float vote;
    public boolean nsfw;
    public String title, overview, poster, rilis;
    ApiClient apiClient;
    ImageView imgVideo;
    private List<MyVideo.Result> results = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Intent i = getIntent();
        if(i.getExtras()!=null){
            id = i.getIntExtra("id",0);
            nsfw = i.getBooleanExtra("nsfw", true);
            title = i.getStringExtra("title");
            overview = i.getStringExtra("overview");
            poster = i.getStringExtra("poster");
            rilis = i.getStringExtra("rilis");
            vote = i.getFloatExtra("vote", 0);
        }

        TextView tvTitle, tvNsfw, tvRilis, tvVote, tvOterview;
        ImageView imgPoster;
        tvTitle = (TextView) findViewById(R.id.title);
        tvNsfw = (TextView) findViewById(R.id.nsfw);
        tvRilis = (TextView) findViewById(R.id.rilis);
        tvVote = (TextView) findViewById(R.id.vote);
        tvOterview =(TextView) findViewById(R.id.otherview);
        imgPoster =(ImageView) findViewById(R.id.poster);
        imgVideo = (ImageView) findViewById(R.id.triler);

        listData();

        tvTitle.setText(title);
        tvRilis.setText(rilis);
        tvNsfw.setText("SU");
        if (nsfw){
            tvNsfw.setText("D");
        }
        tvVote.setText(String.valueOf(vote));
        tvOterview.setText("Oterview: "+overview);
        Picasso.with(DetailMovie.this).load(poster).into(imgPoster);

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
                    String video = "http://img.youtube.com/vi/"+results.get(0).getKey()+"/0.jpg";
                    Picasso.with(DetailMovie.this).load(video).into(imgVideo);
                }
            }

            @Override
            public void onFailure(Call<MyVideo> call, Throwable t) {

            }
        });
    }

}
