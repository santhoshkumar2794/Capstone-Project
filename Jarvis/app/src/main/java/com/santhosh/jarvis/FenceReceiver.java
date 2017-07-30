package com.santhosh.jarvis;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceState;
import com.santhosh.jarvis.fence.Fence;
import com.santhosh.jarvis.fence.FenceActions;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.santhosh.jarvis.MainActivity.TAG;

/**
 * Created by santhosh-3366 on 05/07/17.
 */

public class FenceReceiver extends BroadcastReceiver {
    public FenceReceiver() {
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"Rxr OnReceive "+intent.getAction());
        if (intent.getAction().equals(MainActivity.FENCE_RXR_ACTION)){
            Log.e(MainActivity.TAG,"Fence Action");
            JarvisActions.checkFence(context,FenceState.extract(intent));
            //checkFence(context,FenceState.extract(intent));
        }else if (intent.getAction().equals("android.intent.action.BATTERY_CHANGED")){
            //batteryState(context,intent);
            JarvisActions.performBatteryOperation(context,intent);
        }
    }

    private void checkFence(Context context,FenceState fenceState){
        String state;
        switch (fenceState.getFenceKey()){
            case Fence.HEADPHONE:
                state = fenceState.getCurrentState() == FenceState.TRUE ? "CONNECTED" : "DISCONNECTED";
                Log.e(TAG,"headPhone Fence State "+state);
                String message = "HeadPhone Fence Triggered.`` STATE: "+state;
                triggerNotification(context,"HEADPHONE FENCE",message,1);
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

    private void triggerNotification(Context context,String title,String message,int notificationID){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message);

        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(notificationID, notificationBuilder.build());
    }

    private void batteryState(Context context,Intent intent){
        BatteryManager batteryManager = (BatteryManager) context.getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        if (acCharge && isCharging){
            triggerNotification(context,"PHONE AC CHARGING","",3);
            FenceActions.setRingtoneLevel(context,100);
        } else if (usbCharge && isCharging){
            triggerNotification(context,"PHONE USB CHARGING","",3);
            //FenceActions.setRingtoneLevel(context,100);
        }
        Log.e("ac charge  "+acCharge," usb charge  "+usbCharge+"  "+isCharging+"  "+status+"  "+chargePlug);
    }
}
