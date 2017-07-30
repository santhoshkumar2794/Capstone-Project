package com.santhosh.jarvis.applet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.santhosh.jarvis.AppletInterface;
import com.santhosh.jarvis.R;

/**
 * Created by santhosh-3366 on 09/07/17.
 */

public class AppletCreator extends AppCompatActivity implements AppletInterface {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applet_creator);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.applet_listing_view);
        AppletAdapter appletAdapter = new AppletAdapter(this);
        recyclerView.setAdapter(appletAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,calculateNoOfColumns(this));
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float holderWidth = context.getResources().getDimension(R.dimen.holder_width)/displayMetrics.density;
        int noOfColumns = (int) (dpWidth / holderWidth);
        return noOfColumns;
    }

    @Override
    public void onSituationCreate(String situationType) {
        Intent intent;
        switch (situationType){
            case "Battery":
                intent = new Intent(this,BatterySituationCreator.class);
                startActivityForResult(intent,1);
                break;
            case "HeadPhones":
                intent = new Intent(this,HeadPhoneSituationCreator.class);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (resultCode == RESULT_OK){
                setResult(RESULT_OK,data);
                finish();
            }
        }
    }
}
