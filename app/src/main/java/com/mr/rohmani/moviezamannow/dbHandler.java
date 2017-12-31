package com.mr.rohmani.moviezamannow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mr.rohmani.moviezamannow.models.MovieDB;
import com.mr.rohmani.moviezamannow.models.MovieList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 25/12/2017.
 */

public class dbHandler extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "moviedb";
    protected static final int DATABASE_VERSION = 1;
    //table list
    protected static final String TABLE_NAME = "movie";
    protected static final String TABLE_NAME2 = "movielist";

    //column
    public static final String TITLE = "title";
    public static final String RILIS = "rilis";
    public static final String DURATION = "duration";
    public static final String RATING = "rating";
    public static final String COMPANY = "company";
    public static final String GENRE = "genre";
    public static final String OTHERVIEW = "otherview";
    public static final String IMG_URL = "imgurl";

    public dbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME); //untuk delete database
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "" +
                "(id INTEGER PRIMARY KEY,"+TITLE+" TEXT,"+RILIS+" TEXT,"+DURATION+" INTEGER," +
                RATING +" DOUBLE,"+COMPANY+" TEXT, "+GENRE+" TEXT, "+OTHERVIEW+" TEXT,"+IMG_URL+" TEXT" + ")";
        String CREATE_USER_TABLE2 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + "" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,mid TEXT, categori TEXT,"+TITLE+" TEXT,"+IMG_URL+" TEXT" + ")";


        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ", " + TABLE_NAME2;
        db.execSQL(sql);
        onCreate(db);
    }

    public void addMovieDetails(MovieDB movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", movie.id);
        values.put(TITLE, movie.title);
        values.put(RILIS, movie.rilis);
        values.put(DURATION, movie.duration);
        values.put(RATING, movie.rating);
        values.put(COMPANY, movie.company);
        values.put(GENRE, movie.genre);
        values.put(OTHERVIEW, movie.otherview);
        values.put(IMG_URL, movie.imgurl);
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public void addMovie(String mid, String categori, String title, String url){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("mid", mid);
        values.put("categori", categori);
        values.put(TITLE, title);
        values.put(IMG_URL, url);
        db.insert(TABLE_NAME2, null, values);
        db.close(); // Closing database connection
    }

    public boolean deleteMovie(String cat){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String sql = "DELETE FROM "+TABLE_NAME2 +" where categori = '"+ cat +"'";
            db.execSQL(sql);
            return true;
        }catch(Exception e){
            // here you can catch all the exceptions
            e.printStackTrace();
            return false;
        }
    }

    public List<MovieList> getAllMovie(String categori){
        List<MovieList> movie = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME2 +" WHERE categori = '"+ categori+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                MovieList story = new MovieList(cursor.getString(1),cursor.getString(3), cursor.getString(4));
                movie.add(story);
            } while (cursor.moveToNext());
        }
        return movie;
    }
}
