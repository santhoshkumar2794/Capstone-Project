package com.santhosh.jarvis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Santhosh on 12/07/17.
 */

public class AppletPreview extends AppCompatActivity {

    @BindView(R.id.applet_title)
    EditText applet_title;

    @BindView(R.id.notification_checkbox)
    CheckBox notificationCheckbox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applet_preview);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String situationType = intent.getStringExtra(Constants.SITUATION_TYPE);
        String actionType = intent.getStringExtra(Constants.ACTION_TYPE);
        String appletTitle = "If "+situationType.toLowerCase()+" , "+actionType.toLowerCase();
        applet_title.setText(appletTitle);
        applet_title.setSelection(appletTitle.length());
    }

    @OnClick(R.id.finish_applet)
    public void onClick(View view){
        Intent intent = getIntent();
        intent.putExtra("NEED NOTIFICATION",notificationCheckbox.isChecked());
        intent.putExtra("APPLET TITLE",applet_title.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }
}
