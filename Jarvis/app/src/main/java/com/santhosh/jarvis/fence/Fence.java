package com.santhosh.jarvis.fence;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.awareness.fence.HeadphoneFence;
import com.google.android.gms.awareness.fence.LocationFence;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.android.gms.awareness.state.HeadphoneState;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import static com.santhosh.jarvis.MainActivity.TAG;

/**
 * Created by santhosh-3366 on 05/07/17.
 */

public class Fence {

    public static final String HEADPHONE = "HEADPHONE";

    public static void setupFence(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        setupHeadphoneFence(googleApiClient, pendingIntent);
        //getCurrentLocation(googleApiClient,pendingIntent);
    }


    private static void setupHeadphoneFence(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        AwarenessFence headPhoneFence = HeadphoneFence.during(HeadphoneState.PLUGGED_IN);

        Awareness.FenceApi.updateFences(googleApiClient,
                new FenceUpdateRequest.Builder()
                        .addFence(HEADPHONE, headPhoneFence, pendingIntent)
                        .build()).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Log.e(TAG, "headPhoneFence connected result " + status.isSuccess());
            }
        });
    }

    private static void setupInLocationFence(GoogleApiClient googleApiClient, PendingIntent pendingIntent, Location location) {
        if (ActivityCompat.checkSelfPermission(googleApiClient.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //AwarenessFence inLocationFence = LocationFence.in(12.830651, 80.070196, 5,10000);
        AwarenessFence inLocationFence = LocationFence.in(location.getLatitude(), location.getLongitude(), 10, 10000);
        Awareness.FenceApi.updateFences(googleApiClient,
                new FenceUpdateRequest.Builder()
                        .addFence("LOCATION IN", inLocationFence, pendingIntent).build())
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });
    }

    private static void getCurrentLocation(final GoogleApiClient googleApiClient, final PendingIntent pendingIntent) {
        if (ActivityCompat.checkSelfPermission(googleApiClient.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions((Activity) googleApiClient.getContext(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            //return;
        }
        Awareness.SnapshotApi.getLocation(googleApiClient)
                .setResultCallback(new ResultCallback<LocationResult>() {
                    @Override
                    public void onResult(@NonNull LocationResult locationResult) {
                        setupInLocationFence(googleApiClient,pendingIntent,locationResult.getLocation());
                    }
                });
    }
}
