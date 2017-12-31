package com.mr.rohmani.moviezamannow.models;

/**
 * Created by USER on 25/12/2017.
 */

public class MovieDB {
    public int id;
    public String title;
    public String rilis;
    public int duration;
    public double rating;
    public String company;
    public String genre;
    public String otherview;
    public String imgurl;

    public MovieDB(int id, String title, String rilis, int duration, double rating, String company, String genre, String otherview, String imgurl) {
        this.id = id;
        this.title = title;
        this.rilis = rilis;
        this.duration = duration;
        this.rating = rating;
        this.company = company;
        this.genre = genre;
        this.otherview = otherview;
        this.imgurl = imgurl;
    }

}
