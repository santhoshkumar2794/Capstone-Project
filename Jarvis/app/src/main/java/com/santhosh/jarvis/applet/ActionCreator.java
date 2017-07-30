package com.santhosh.jarvis.applet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.santhosh.jarvis.Constants;
import com.santhosh.jarvis.R;

/**
 * Created by santhosh-3366 on 09/07/17.
 */

public class ActionCreator extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_creator);
    }

    public void onActionClick(View view){
        if (view instanceof TextView) {
            Intent intent = new Intent();
            switch (view.getContentDescription().toString()){
                case Constants.WIFI_ON:
                    intent.putExtra(Constants.ACTION,Constants.WIFI);
                    intent.putExtra(Constants.ACTION_TYPE,Constants.WIFI_ON);
                    break;
                case Constants.WIFI_OFF:
                    intent.putExtra(Constants.ACTION,Constants.WIFI);
                    intent.putExtra(Constants.ACTION_TYPE,Constants.WIFI_OFF);
                    break;
                default:
                    intent.putExtra(Constants.ACTION,Constants.NO_ACTION);
                    intent.putExtra(Constants.ACTION_TYPE,Constants.NO_ACTION);
                    break;
            }
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
