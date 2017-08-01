package com.santhosh.jarvis;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.santhosh.jarvis.applet.SituationCreator;
import com.santhosh.jarvis.database.AppletData;
import com.santhosh.jarvis.database.AppletDataset;
import com.santhosh.jarvis.database.SQLDBLoader;
import com.santhosh.jarvis.database.room.AppletEntity;
import com.santhosh.jarvis.database.DBLoader;
import com.santhosh.jarvis.database.DBTask;
import com.santhosh.jarvis.database.JarvisDB;
import com.santhosh.jarvis.fence.Fence;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, android.support.v4.app.LoaderManager.LoaderCallbacks<List<AppletData>> {

    private GoogleApiClient googleApiClient;
    public static final String TAG = "Jarvis";
    public static final String FENCE_RXR_ACTION = "FENCE_RECEIVER_ACTION";
    private PendingIntent pendingIntent;


    @BindView(R.id.applet_listing_view)
    RecyclerView appletListingView;
    private AppletListingAdapter appletListingAdapter;
    private JarvisDB jarvisDB;
    private DBTask dbTask;
    private AppletDataset appletDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Awareness.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,calculateNoOfColumns(this));
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        appletListingView.setLayoutManager(gridLayoutManager);

        appletListingAdapter = new AppletListingAdapter();
        appletListingView.setAdapter(appletListingAdapter);

        appletDataset = new AppletDataset(this);
        getSupportLoaderManager().initLoader(0,null,this).forceLoad();
        //createDb();

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float holderWidth = context.getResources().getDimension(R.dimen.applet_holder_size)/displayMetrics.density;
        int noOfColumns = (int) (dpWidth / holderWidth);
        return noOfColumns;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient!=null){
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e(TAG,"onConnected");
        Intent intent = new Intent(FENCE_RXR_ACTION);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        initFence();
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        ifilter.addAction(Intent.ACTION_BATTERY_LOW);
        registerReceiver(new FenceReceiver(),ifilter);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void initFence(){
        checkPermission();
        Fence.setupFence(googleApiClient,pendingIntent);
    }

    private void checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

    public void onAppletAdd(View view){
        Intent intent = new Intent(this,SituationCreator.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main activity","result");
        if (resultCode == RESULT_OK){
            AppletData appletData = new AppletData();
            appletData.applet_id = new Random().nextInt(25);
            appletData.applet_name = data.getStringExtra("APPLET TITLE");
            appletData.situation = data.getStringExtra(Constants.SITUATION);
            appletData.situation_type = data.getStringExtra(Constants.SITUATION_TYPE);
            appletData.action = data.getStringExtra(Constants.ACTION);
            appletData.action_type = data.getStringExtra(Constants.ACTION_TYPE);
            appletData.applet_enabled = 1;
            appletData.need_notification = data.getBooleanExtra("NEED NOTIFICATION",false) ? 1 : 0;

            appletDataset.insertApplet(appletData);
            getSupportLoaderManager().getLoader(0).forceLoad();
           /* AppletEntity appletEntity = new AppletEntity();
            appletEntity.appletID = new Random().nextInt(25);
            appletEntity.appletName = data.getStringExtra("APPLET TITLE");
            appletEntity.situation = data.getStringExtra(Constants.SITUATION);
            appletEntity.situationType = data.getStringExtra(Constants.SITUATION_TYPE);
            appletEntity.action = data.getStringExtra(Constants.ACTION);
            appletEntity.actionType = data.getStringExtra(Constants.ACTION_TYPE);
            appletEntity.appletEnabled = true;
            appletEntity.needNotification = data.getBooleanExtra("NEED NOTIFICATION",false);

            dbTask.execute(appletEntity);
            getSupportLoaderManager().getLoader(0).forceLoad();*/
            //jarvisDB.appletDao().insertAppletData(appletEntity);


        }
    }

    public void createDb() {
        jarvisDB = JarvisDB.getInMemoryDatabase(getApplication());
        getSupportLoaderManager().initLoader(0,null,this).forceLoad();

        dbTask = new DBTask(jarvisDB);
    }

    @Override
    public Loader<List<AppletData>> onCreateLoader(int i, Bundle bundle) {
        //return new DBLoader(this,jarvisDB);
        if (appletDataset==null){
            appletDataset = new AppletDataset(this);
        }
        return new SQLDBLoader(this,appletDataset);
    }

    @Override
    public void onLoadFinished(Loader<List<AppletData>> loader, List<AppletData> appletEntityList) {
        Log.e("load","finished  "+appletEntityList.size()+"  ");
        appletListingAdapter.setAppletEntityList(appletEntityList);
        appletListingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<AppletData>> loader) {

    }
}
