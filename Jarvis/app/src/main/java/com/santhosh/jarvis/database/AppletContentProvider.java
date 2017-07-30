package com.santhosh.jarvis.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Santhosh on 15/07/17.
 */

public class AppletContentProvider extends ContentProvider {

    static final String AUTHORITY = "com.santhosh.jarvis";
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY).buildUpon().appendPath(AppletDatabase.TABLE_NAME).build();
    static UriMatcher uriMatcher = buildUriMatcher();

    static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,AppletDatabase.TABLE_NAME,100);
        uriMatcher.addURI(AUTHORITY,AppletDatabase.TABLE_NAME+"/"+AppletDatabase.APPLET_ID+"/#",101);
        uriMatcher.addURI(AUTHORITY,AppletDatabase.TABLE_NAME+"/"+AppletDatabase.SITUATION_NAME+"/#",102);
        uriMatcher.addURI(AUTHORITY,AppletDatabase.TABLE_NAME+"/"+AppletDatabase.ACTION_NAME+"/#",103);
        uriMatcher.addURI(AUTHORITY,AppletDatabase.TABLE_NAME+"/"+AppletDatabase.SITUATION_TYPE+"/#",104);
        uriMatcher.addURI(AUTHORITY,AppletDatabase.TABLE_NAME+"/"+AppletDatabase.ACTION_TYPE+"/#",105);
        return uriMatcher;
    }

    private AppletDatabase appletDatabaseHelper;

    @Override
    public boolean onCreate() {
        appletDatabaseHelper = new AppletDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase = appletDatabaseHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);
        Cursor cursor;
        switch (match){
            case 100:
                cursor = sqLiteDatabase.query(AppletDatabase.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case 101:
                cursor = sqLiteDatabase.query(AppletDatabase.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case 102:
                cursor = sqLiteDatabase.query(AppletDatabase.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case 103:
                cursor = sqLiteDatabase.query(AppletDatabase.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case 104:
                cursor = sqLiteDatabase.query(AppletDatabase.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case 105:
                cursor = sqLiteDatabase.query(AppletDatabase.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI : "+uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase sqLiteDatabase = appletDatabaseHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case 100:
                long ID = sqLiteDatabase.insert(AppletDatabase.TABLE_NAME,null,values);
                if (ID>0){
                    returnUri = ContentUris.withAppendedId(CONTENT_URI,ID);
                }else {
                    throw new android.database.SQLException("Failed to insert row into "+uri);
                }
                break;
            /*case 101:
                break;*/
            default:
                throw new UnsupportedOperationException("Unknown URI : "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
