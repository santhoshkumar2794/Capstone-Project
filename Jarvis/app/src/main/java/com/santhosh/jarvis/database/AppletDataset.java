package com.santhosh.jarvis.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santhosh on 30/07/17.
 */

public class AppletDataset {

    private Context mContext;

    public AppletDataset(Context mContext) {
        this.mContext = mContext;
    }

    public void insertApplet(AppletData appletData){
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppletDatabase.APPLET_ID,appletData.applet_id);
        contentValues.put(AppletDatabase.APPLET_NAME,appletData.applet_name);
        contentValues.put(AppletDatabase.SITUATION_NAME,appletData.situation);
        contentValues.put(AppletDatabase.SITUATION_TYPE,appletData.situation_type);
        contentValues.put(AppletDatabase.ACTION_NAME,appletData.action);
        contentValues.put(AppletDatabase.ACTION_TYPE,appletData.action_type);
        contentValues.put(AppletDatabase.APPLET_ENABLED,appletData.applet_enabled);
        contentValues.put(AppletDatabase.NEED_NOTIFICATION,appletData.need_notification);

        mContext.getContentResolver().insert(AppletContentProvider.CONTENT_URI,contentValues);
    }

    public List<AppletData> getAppletListBySituation(String situation){
        Cursor cursor = mContext.getContentResolver().query(AppletContentProvider.CONTENT_URI,null, AppletDatabase.SITUATION_NAME+ "=?",new String[]{String.valueOf(situation)},null);
        return converToAppletData(cursor);
    }

    public List<AppletData> getAppletList(){
        Cursor cursor = mContext.getContentResolver().query(AppletContentProvider.CONTENT_URI,null,null,null,null);
        return converToAppletData(cursor);
    }

    private List<AppletData> converToAppletData(Cursor cursor){
        List<AppletData> appletDataList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            try {
                AppletData movieData = new AppletData();

                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    Field name = movieData.getClass().getDeclaredField(cursor.getColumnName(i));
                    name.setAccessible(true);
                    switch (cursor.getType(i)) {
                        case Cursor.FIELD_TYPE_INTEGER:
                            Log.e("name  "+name,"  "+cursor.getInt(i));
                            name.set(movieData,cursor.getInt(i));
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            name.set(movieData, cursor.getString(i));
                            break;
                    }

                }
                cursor.moveToNext();
                appletDataList.add(movieData);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        cursor.close();
        return appletDataList;
    }
}
