package com.mr.rohmani.moviezamannow.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mr.rohmani.moviezamannow.DetailMovie;
import com.mr.rohmani.moviezamannow.R;
import com.mr.rohmani.moviezamannow.models.MovieList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 30/12/2017.
 */

public class DbMovieAdapter extends RecyclerView.Adapter<DbMovieAdapter.MovieViewHolder>{
    private List<MovieList> movieList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context mContext;
    private View v;

    public DbMovieAdapter(Context mContext, List<MovieList> movieList) {
        this.movieList = movieList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private RelativeLayout rl;
        public MovieViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            rl = (RelativeLayout)itemView.findViewById(R.id.rl_movie);
        }
    }

    @Override public DbMovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_movie,parent ,false);
        DbMovieAdapter.MovieViewHolder vh=new DbMovieAdapter.MovieViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final DbMovieAdapter.MovieViewHolder holder, final int position) {

        final String imageURL = "http://image.tmdb.org/t/p/w185/"+movieList.get(position).url;

        Picasso.with(mContext).load(imageURL).fetch();
        Picasso.with(mContext).load(imageURL).into(holder.ivPhoto);
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailMovie.class);
                intent.putExtra("id", Integer.parseInt(movieList.get(position).mid));
                intent.putExtra("title", movieList.get(position).title);
                intent.putExtra("poster", movieList.get(position).url);
                mContext.startActivity(intent);
            }
        });
    }


    @Override public int getItemCount() {
        return movieList.size();
    }
}
