package com.santhosh.jarvis.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.santhosh.jarvis.MainActivity;
import com.santhosh.jarvis.R;
import com.santhosh.jarvis.database.AppletData;
import com.santhosh.jarvis.database.AppletDataset;

import java.util.List;

/**
 * Created by santhosh-3366 on 01/08/17.
 */

public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetID : appWidgetIds){
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.removeAllViews(R.id.widget_parent);
            AppletDataset appletDataset = new AppletDataset(context);
            List<AppletData> appletDataList = appletDataset.getAppletList();
            for (AppletData appletData : appletDataList){
                RemoteViews holder = new RemoteViews(context.getPackageName(),R.layout.widget_holder);
                holder.setTextViewText(R.id.widget_holder,appletData.applet_name);
                views.addView(R.id.widget_parent,holder);
            }
            views.setOnClickPendingIntent(R.id.widget_parent, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetID, views);
        }
    }
}
