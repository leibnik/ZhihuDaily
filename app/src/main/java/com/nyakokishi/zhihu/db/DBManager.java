package com.nyakokishi.zhihu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nyakokishi.zhihu.constant.Constant;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public class DBManager {

    private static int sVersion = 1;
    private static Context sContext;
    public static int count;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private DBManager(Context context, int version) {
        dbHelper = new DBHelper(context, version);
    }

    public static DBManager getInstance(Context context) {
        count++;
        sContext = context;
        return SingletonHolder.sDBManager;
    }

    /**
     * 单例模式-静态内部类实现
     */
    private static class SingletonHolder {
        private static DBManager sDBManager = new DBManager(sContext, sVersion);
    }


    public void saveThemes(String data) {
        db = dbHelper.getWritableDatabase();
        if (null != data) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", Constant.MENU_COLUMN);
            contentValues.put("json", data);
            db.replace(DBHelper.THEMES_TABLE_NAME, null, contentValues);
        }
        db.close();
    }

    public String getThemes() {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from themes where id = ?", new String[]{String.valueOf(Constant.MENU_COLUMN)});
        String result = null;
        while (cursor.moveToNext()) {
            result = cursor.getString(cursor.getColumnIndex("json"));
        }
        return result;
    }

    public void saveDaySummary(String data) {
        db = dbHelper.getWritableDatabase();
        if (data != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("json", data);
            contentValues.put("date", Constant.DAY_SUMMARY_COLUMN);
            db.replace(DBHelper.SUMMARY_TABLE_NAME, null, contentValues);
        }
        db.close();
    }

    public String getDaySummary() {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from summary where date = " + Constant.DAY_SUMMARY_COLUMN, null);
        String result = null;
        while (cursor.moveToNext()) {
            result = cursor.getString(cursor.getColumnIndex("json"));
        }
        return result;
    }

    public void saveThemeSummary(String data, int id) {
        db = dbHelper.getWritableDatabase();
        if (data != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("json", data);
            contentValues.put("id", Constant.THEME_SUMMARY_COLUMN + id);
            db.replace(DBHelper.SUMMARY_TABLE_NAME, null, contentValues);
        }
        db.close();
    }

    public String getThemeSummary(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from summary where id = " + (Constant.THEME_SUMMARY_COLUMN + id), null);
        String result = null;
        while (cursor.moveToNext()) {
            result = cursor.getString(cursor.getColumnIndex("json"));
        }
        return result;
    }

    public void saveDetail(String data, int id) {
        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("json", data);
        contentValues.put("id", id);
        db.replace(DBHelper.DETAIL_TABLE_NAME,null,contentValues);
        db.close();
    }

    public String getDetail(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from detail where id = ?", new String[]{id + ""});
        String result = null;
        while (cursor.moveToNext()) {
            result = cursor.getString(cursor.getColumnIndex("json"));
        }
        return result;
    }

    public void release() {
        count--;
        if (count == 0) {
            db.close();
            SingletonHolder.sDBManager = null;
        }
    }

}
