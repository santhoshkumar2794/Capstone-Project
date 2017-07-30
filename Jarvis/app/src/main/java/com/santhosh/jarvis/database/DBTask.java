package com.santhosh.jarvis.database;

import android.os.AsyncTask;
import android.util.Log;

import com.santhosh.jarvis.database.room.AppletEntity;

import java.util.List;

/**
 * Created by Santhosh on 23/07/17.
 */

public class DBTask extends AsyncTask<AppletEntity,Void,Integer> {
    private List<AppletEntity> appletEntityList;
    private JarvisDB jarvisDB;

    public DBTask(JarvisDB jarvisDB) {
        this.jarvisDB = jarvisDB;
    }

    public List<AppletEntity> getAppletEntityList() {
        return appletEntityList;
    }

    public void setAppletEntityList(List<AppletEntity> appletEntityList) {
        this.appletEntityList = appletEntityList;
    }

    @Override
    protected Integer doInBackground(AppletEntity... appletEntities) {
        for (AppletEntity appletEntity : appletEntities) {
            Log.e("fired","here insert  "+appletEntity.appletName);
            jarvisDB.appletDao().insertAppletData(appletEntity);
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Log.e("int","result  "+integer);

    }
}
