package com.santhosh.jarvis.fence;

import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by santhosh-3366 on 06/07/17.
 */

public class FenceActions {
     public static void setRingtoneLevel(Context context,float ringerPercentage){
        AudioManager audioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        int volume = (int) ((ringerPercentage * maxVolume)/100);
        Log.e("max","volume  "+maxVolume);
        audioManager.setStreamVolume(AudioManager.STREAM_RING,volume,AudioManager.FLAG_PLAY_SOUND);
    }

    public static void changeWiFiState(Context context,boolean state){
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(state);
    }
}
