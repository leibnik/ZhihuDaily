package com.nyakokishi.zhihu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "zhihu.db";
    public static String SUMMARY_TABLE_NAME = "summary";
    public static String DETAIL_TABLE_NAME = "detail";
    public static String THEMES_TABLE_NAME = "themes";
    public static String CREATE_SUMMARY_TABLE =
            "CREATE TABLE IF NOT EXISTS summary (" +
                    "id INTEGER primary key," +
                    "date INTEGER unique," +
                    "json TEXT);";
    public static String CREATE_DETAIL_TABLE =
            "CREATE TABLE IF NOT EXISTS detail (" +
                    "id INTEGER primary key," +
                    "json TEXT );";
    public static String CREATE_THEMES_TABLE =
            "CREATE TABLE IF NOT EXISTS themes (" +
                    "id INTEGER primary key," +
                    "json TEXT );";


    public DBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SUMMARY_TABLE);
        db.execSQL(CREATE_DETAIL_TABLE);
        db.execSQL(CREATE_THEMES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
