package com.example.addictlock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 1/29/2017.
 */
public class SQliteDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "AppsManager";

    private static final String TABLE_APPS = "Appdetails";

    private static final String KEY_PACKAGE = "packagename";
    private static final String KEY_APP = "appname";
    private static final String KEY_APPLOCKED = "applock";
    private static final String KEY_ADDICTLOCKED = "addictlock";
    private static final String KEY_STARTTIME = "starttime";
    private static final String KEY_ENDTIME = "endtime";
    private static final String KEY_DAYSREPEAT = "daysrepeat";

    public SQliteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_APPS_TABLE = "CREATE TABLE " + TABLE_APPS + "("
                + KEY_PACKAGE + " TEXT," + KEY_APP + " TEXT,"
                + KEY_APPLOCKED + " TEXT," + KEY_ADDICTLOCKED + " TEXT,"
                + KEY_STARTTIME + " TEXT," + KEY_ENDTIME + " TEXT," + KEY_DAYSREPEAT + " TEXT" + ")";
        db.execSQL(CREATE_APPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPS);

        onCreate(db);
    }

    void addApp(Apprecord app) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PACKAGE, app.getpackagename());
        values.put(KEY_APP, app.getappname());
        values.put(KEY_APPLOCKED, app.getisApplocked());
        values.put(KEY_ADDICTLOCKED, app.getIsAddictlocked());
        values.put(KEY_STARTTIME, app.getStartTime());
        values.put(KEY_ENDTIME, app.getEndtime());
        values.put(KEY_DAYSREPEAT, app.getDaysRepeat());
        db.insert(TABLE_APPS, null, values);
        db.close();
    }

    public Apprecord getAppDetails(String packagename) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_APPS, new String[] { KEY_PACKAGE,
                        KEY_APP, KEY_APPLOCKED, KEY_ADDICTLOCKED, KEY_STARTTIME, KEY_ENDTIME, KEY_DAYSREPEAT }, KEY_PACKAGE + "=?",
                new String[] { packagename }, null, null, null, null);
        if (cursor.moveToFirst())
        {
            Apprecord app = new Apprecord(cursor.getString(0),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6));
            return app;
        }
        else
        {
            return null;
        }
    }

    public List<Apprecord> getAllApps() {
        List<Apprecord> appsList = new ArrayList<Apprecord>();
        String selectQuery = "SELECT  * FROM " + TABLE_APPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Apprecord app = new Apprecord();
                app.setappname(cursor.getString(1));
                app.setpackagename(cursor.getString(0));
                app.setisApplocked(cursor.getString(2));
                app.setIsAddictlocked(cursor.getString(3));
                app.setStartTime(cursor.getString(4));
                app.setEndtime(cursor.getString(5));
                app.setDaysRepeat(cursor.getString(6));
                appsList.add(app);
            } while (cursor.moveToNext());
        }

        return appsList;
    }

    public int updateApp(Apprecord app) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PACKAGE, app.getpackagename());
        values.put(KEY_APP, app.getappname());
        values.put(KEY_APPLOCKED, app.getisApplocked());
        values.put(KEY_ADDICTLOCKED, app.getIsAddictlocked());
        values.put(KEY_STARTTIME, app.getStartTime());
        values.put(KEY_ENDTIME, app.getEndtime());
        values.put(KEY_DAYSREPEAT, app.getDaysRepeat());

        return db.update(TABLE_APPS, values, KEY_PACKAGE + " = ?",
                new String[] { app.getpackagename() });
    }

    public void deleteApp(Apprecord app) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APPS, KEY_PACKAGE + " = ?",
                new String[] { app.getpackagename() });
        db.close();
    }

    public int getAppsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_APPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}

