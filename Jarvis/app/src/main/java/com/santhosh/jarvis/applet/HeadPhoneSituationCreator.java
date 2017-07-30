package com.santhosh.jarvis.applet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.santhosh.jarvis.Constants;
import com.santhosh.jarvis.R;

/**
 * Created by Santhosh on 15/07/17.
 */

public class HeadPhoneSituationCreator extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headphone_situation_creator);
    }

    public void onActionClick(View view){
        if (view instanceof TextView) {
            Intent intent = new Intent();
            intent.putExtra(Constants.SITUATION,Constants.HEADPHONES);
            switch (view.getContentDescription().toString()){
                case Constants.HEADPHONES_PLUGGED:
                    intent.putExtra(Constants.SITUATION_TYPE,Constants.HEADPHONES_PLUGGED);
                    break;
                case Constants.HEADPHONES_UNPLUGGED:
                    intent.putExtra(Constants.SITUATION_TYPE,Constants.HEADPHONES_UNPLUGGED);
                    break;
            }
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
