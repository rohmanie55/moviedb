package com.mr.rohmani.moviezamannow.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mr.rohmani.moviezamannow.R;
import com.mr.rohmani.moviezamannow.models.MyVideo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gideon on 12/11/17.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MovieViewHolder> {
    private List<MyVideo.Result> movieList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context mContext;
    private View v;

    public VideoAdapter(Context mContext, List<MyVideo.Result> movieList) {
        this.movieList = movieList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private RelativeLayout rl;
        private TextView title;
        private ImageView play;
        public MovieViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_video);
            rl = (RelativeLayout)itemView.findViewById(R.id.rl_video);
            title = (TextView) itemView.findViewById(R.id.judul);
            play = (ImageView) itemView.findViewById(R.id.play);
        }
    }

    @Override public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_video,parent ,false);
        MovieViewHolder vh=new MovieViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {

        final String imageURL = "http://img.youtube.com/vi/"+movieList.get(position).getKey()+"/0.jpg";
        Picasso.with(mContext).load(imageURL).into(holder.ivPhoto);
        holder.title.setText(movieList.get(position).getName());
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://youtube.com/watch?v="+movieList.get(position).getKey();
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                mContext.startActivity(launchBrowser);
            }
        });
    }


    @Override public int getItemCount() {
        return movieList.size();
    }
}