package com.mr.rohmani.moviezamannow;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 25/12/2017.
 */

public class dbHandler extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "movie_now";
    protected static final String TABLE_NAME = "movie";
    private static final int DATABASE_VERSION = 1;

    public dbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME); //untuk delete database
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,INTEGER categori, title TEXT,rilis TEXT,duration INTEGER," +
                " rating DOUBLE,company TEXT, genre TEXT, otherview TEXT, imgurl TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + "";
        db.execSQL(sql);
        onCreate(db);
    }
}
