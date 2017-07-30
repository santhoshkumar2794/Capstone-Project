package com.santhosh.jarvis.database;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.santhosh.jarvis.database.room.AppletEntity;

import java.util.List;

/**
 * Created by Santhosh on 30/07/17.
 */

public class SQLDBLoader extends AsyncTaskLoader<List<AppletData>> {

    private AppletDataset appletDataset;
    public SQLDBLoader(Context context,AppletDataset appletDataset) {
        super(context);
        this.appletDataset = appletDataset;
    }

    @Override
    public List<AppletData> loadInBackground() {
        Log.e("load","in background");
        return appletDataset.getAppletList();
    }

    @Override
    public void deliverResult(List<AppletData> data) {
        Log.e("data","sizr  "+data.size());
        super.deliverResult(data);
    }
}
