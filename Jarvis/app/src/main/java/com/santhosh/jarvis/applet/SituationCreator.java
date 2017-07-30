package com.santhosh.jarvis.applet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.santhosh.jarvis.AppletPreview;
import com.santhosh.jarvis.Constants;
import com.santhosh.jarvis.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Santhosh on 11/07/17.
 */

public class SituationCreator extends AppCompatActivity {

    private String situationType;
    private String situation;
    private String actionType;
    private String action;

    @BindView(R.id.situation_create)
    TextView situation_create;

    @BindView(R.id.action_create)
    TextView action_create;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.situation_creator);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ButterKnife.bind(this);
    }

    public void onConditionClick(View view){
        Intent intent = new Intent(this,AppletCreator.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ButterKnife.bind(this);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                situation = data.getStringExtra(Constants.SITUATION);
                situationType = data.getStringExtra(Constants.SITUATION_TYPE);

                situation_create.setText("("+situationType.toLowerCase()+")");
                situation_create.setCompoundDrawables(null,null,null,null);
                action_create.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                action_create.setEnabled(true);
                action_create.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_color_add),null,null,null);
            }
        }else if (requestCode == 2){
            if (resultCode == RESULT_OK){
                action = data.getStringExtra(Constants.ACTION);
                actionType = data.getStringExtra(Constants.ACTION_TYPE);

                action_create.setText(actionType.toLowerCase());


                Intent intent = new Intent(this,AppletPreview.class);
                intent.putExtra(Constants.SITUATION,situation);
                intent.putExtra(Constants.SITUATION_TYPE,situationType);
                intent.putExtra(Constants.ACTION,action);
                intent.putExtra(Constants.ACTION_TYPE,actionType);
                startActivityForResult(intent,3);
            }
        }else if (requestCode == 3){
            if (resultCode == RESULT_OK){
                setResult(RESULT_OK,data);
                finish();
            }
        }
    }

    public void onActionClick(View view){
        Intent intent = new Intent(this,ActionCreator.class);
        startActivityForResult(intent,2);
    }
}
