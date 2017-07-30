package com.santhosh.jarvis;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceState;
import com.santhosh.jarvis.database.AppletData;
import com.santhosh.jarvis.database.AppletDataset;
import com.santhosh.jarvis.database.room.AppletEntity;
import com.santhosh.jarvis.database.JarvisDB;
import com.santhosh.jarvis.fence.Fence;
import com.santhosh.jarvis.fence.FenceActions;

import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.santhosh.jarvis.MainActivity.TAG;

/**
 * Created by Santhosh on 29/07/17.
 */

public class JarvisActions {

    public static void checkFence(Context context,FenceState fenceState){
        String state;
        switch (fenceState.getFenceKey()){
            case Fence.HEADPHONE:
                performHeadPhoneOperations(context,fenceState);
                break;
            case "LOCATION IN":
                state = fenceState.getCurrentState() == FenceState.TRUE ? "INSIDE" : "OUTSIDE";
                Log.e(TAG,"location Fence State "+state);
                float ringtonePercentage = fenceState.getCurrentState() == FenceState.TRUE ? 80 : 20;
                //boolean wifiState = fenceState.getCurrentState() == FenceState.FALSE;
                triggerNotification(context,"HOME LOCATION","Home Fence Triggered. STATE: "+state,2);
                FenceActions.setRingtoneLevel(context,ringtonePercentage);
                if (fenceState.getCurrentState() == FenceState.TRUE) {
                    FenceActions.changeWiFiState(context, false);
                }
                break;
        }
    }

    public static void performHeadPhoneOperations(Context context,FenceState fenceState){
        String state = fenceState.getCurrentState() == FenceState.TRUE ? Constants.HEADPHONES_PLUGGED : Constants.HEADPHONES_UNPLUGGED;
        Log.e(TAG,"headPhone Fence State "+state);
        String message = "HeadPhone Fence Triggered. STATE: "+state;
        triggerNotification(context,"HEADPHONE FENCE",message,1);

        AppletDataset appletDataset = new AppletDataset(context);
        JarvisDB jarvisDB = JarvisDB.getInMemoryDatabase(context);
        //List<AppletEntity> appletEntityList = jarvisDB.appletDao().getAppletBySituationType(state);
        List<AppletData> appletDataList = appletDataset.getAppletList();
        performOperation(context,appletDataList);
    }

    public static void performBatteryOperation(Context context, Intent intent){


        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;
        Log.e("battery","percentage  "+batteryPct);
        if (batteryPct<=0.15f){
            performBatteryLowOperation(context);
            return;
        }

        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        if (acCharge && isCharging){
            batteryChargingOperation(context);
        }
    }

    private static void performBatteryLowOperation(Context context){
        JarvisDB jarvisDB = JarvisDB.getInMemoryDatabase(context);
        //List<AppletEntity> appletEntityList = jarvisDB.appletDao().getAppletBySituationType(Constants.BATTERY_15_PERCENT);

        triggerNotification(context,"BATTERY BELOW 15%","",3);
        AppletDataset appletDataset = new AppletDataset(context);
        List<AppletData> appletDataList = appletDataset.getAppletList();
        performOperation(context,appletDataList);
    }

    private static void batteryChargingOperation(Context context){
        JarvisDB jarvisDB = JarvisDB.getInMemoryDatabase(context);
        //List<AppletEntity> appletEntityList = jarvisDB.appletDao().getAppletBySituationType(Constants.BATTERY_PLUGGED);

        triggerNotification(context,"PHONE AC CHARGING","",3);

        AppletDataset appletDataset = new AppletDataset(context);
        List<AppletData> appletDataList = appletDataset.getAppletList();
        performOperation(context,appletDataList);

        //FenceActions.setRingtoneLevel(context,100);

    }

    /*private static void performOperation(Context context,List<AppletEntity> appletEntityList){
        for (AppletEntity appletEntity:appletEntityList){
            switch (appletEntity.action){
                case Constants.WIFI:
                    boolean wifiState = appletEntity.actionType.equals(Constants.WIFI_ON);
                    FenceActions.changeWiFiState(context,wifiState);
                    break;
                case Constants.BLUETOOTH:
                    boolean bluetoothState = appletEntity.actionType.equals(Constants.BLUETOOTH_ON);
                    break;

            }
        }
    }*/

    private static void performOperation(Context context,List<AppletData> appletEntityList){
        for (AppletData appletEntity:appletEntityList){
            switch (appletEntity.action){
                case Constants.WIFI:
                    boolean wifiState = appletEntity.action_type.equals(Constants.WIFI_ON);
                    FenceActions.changeWiFiState(context,wifiState);
                    break;
                case Constants.BLUETOOTH:
                    boolean bluetoothState = appletEntity.action_type.equals(Constants.BLUETOOTH_ON);
                    break;

            }
        }
    }

    private static void triggerNotification(Context context,String title,String message,int notificationID){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message);

        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(notificationID, notificationBuilder.build());
    }

}
