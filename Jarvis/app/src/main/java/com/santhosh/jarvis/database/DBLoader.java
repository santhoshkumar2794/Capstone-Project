package com.santhosh.jarvis.database;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.santhosh.jarvis.database.room.AppletEntity;

import java.util.List;

/**
 * Created by Santhosh on 23/07/17.
 */

public class DBLoader extends AsyncTaskLoader<List<AppletEntity>> {

    private JarvisDB jarvisDB;

    public DBLoader(Context context, JarvisDB jarvisDB) {
        super(context);
        this.jarvisDB = jarvisDB;
    }

    @Override
    public List<AppletEntity> loadInBackground() {
        Log.e("load","in background");
        return jarvisDB.appletDao().getAppletList();
    }

    @Override
    public void deliverResult(List<AppletEntity> data) {
        Log.e("data","sizr  "+data.size());
        super.deliverResult(data);
    }

}
