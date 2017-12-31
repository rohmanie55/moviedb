package com.mr.rohmani.moviezamannow.fragment;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.mr.rohmani.moviezamannow.dbHandler;
import com.mr.rohmani.moviezamannow.models.MyMovie;

import java.util.List;

/**
 * Created by USER on 26/11/2017.
 */

public class Base extends Fragment {
    public ProgressDialog mProgressDialog;
    public List<MyMovie.Result> movie;
    public dbHandler db;
    public String categori;


    public void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(message);
        }

        mProgressDialog.show();
    }
    //hidding progress dialog
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), text,Toast.LENGTH_LONG).show();
    }

    public void initDatabase(){
        db = new dbHandler(getActivity());
    }

    public void updateDatabase(List<MyMovie.Result> movieList, String cat){
        movie = movieList;
        categori = cat;
        db = new dbHandler(getActivity());
        new doBackground().execute();
//        int id, String title, String rilis, int duration, double rating, String company, String genre, String otherview, String imgurl
//
//        MovieDB mdb = new MovieDB(movieList.get(i).id, movieList.get(i).title, movieList.get(i).releaseDate, movieList.get(i));

    }

    class doBackground extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("Update data..");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //looping
            if (db.deleteMovie(categori)) {
                for (int counter = 0; counter < movie.size(); counter++) {
                    db.addMovie(String.valueOf(movie.get(counter).id), categori, movie.get(counter).title,
                            movie.get(counter).getPosterPath());
                }
            }else {
                showToast("An error occured");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hideProgressDialog();
        }
    }



}
