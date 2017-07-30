package com.santhosh.jarvis.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Santhosh on 15/07/17.
 */

public class AppletDatabase extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "jarvis.db";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "applet_data";

    static final String APPLET_ID = "applet_id";
    static final String APPLET_NAME = "applet_name";
    static final String SITUATION_NAME = "situation";
    static final String ACTION_NAME = "action";
    static final String SITUATION_TYPE = "situation_type";
    static final String ACTION_TYPE = "action_type";
    static final String NEED_NOTIFICATION = "need_notification";
    static final String APPLET_ENABLED = "applet_enabled";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            APPLET_ID + " INTEGER PRIMARY KEY, " +
            APPLET_NAME + " TEXT, " +
            SITUATION_NAME + " TEXT, " +
            SITUATION_TYPE+ " TEXT,"+
            ACTION_NAME+" TEXT,"+
            ACTION_TYPE+" TEXT,"+
            APPLET_ENABLED+" INTEGER,"+
            NEED_NOTIFICATION+" INTEGER);";

    AppletDatabase(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public AppletDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public AppletDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
