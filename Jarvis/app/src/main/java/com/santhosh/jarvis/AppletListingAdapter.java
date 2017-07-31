package com.santhosh.jarvis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.santhosh.jarvis.database.AppletData;
import com.santhosh.jarvis.database.room.AppletEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santhosh on 12/07/17.
 */

public class AppletListingAdapter extends RecyclerView.Adapter<AppletListingAdapter.AppletListingHolder> {

    private List<AppletData> appletEntityList = new ArrayList<>();

    public AppletListingAdapter() {
    }

    public AppletListingAdapter(List<AppletData> appletDataList) {
        this.appletEntityList = appletDataList;
    }

    public void addToAdapter(AppletData appletData){
        appletEntityList.add(appletData);
    }

    public void setAppletEntityList(List<AppletData> appletEntityList) {
        this.appletEntityList = appletEntityList;
    }

    @Override
    public AppletListingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.applet_listing_holder,viewGroup,false);
        return new AppletListingHolder(view);
    }

    @Override
    public void onBindViewHolder(AppletListingHolder appletListingHolder, int i) {
        AppletData appletEntity = appletEntityList.get(i);
        Context context = appletListingHolder.appletTitle.getContext();
        appletListingHolder.appletTitle.setText(appletEntity.applet_name);
        appletListingHolder.appletStatus.setChecked(appletEntity.applet_enabled == 1);
        appletListingHolder.appletStatus.setText(appletEntity.applet_enabled==1 ? context.getString(R.string.applet_on) : context.getString(R.string.applet_off));
    }

    @Override
    public int getItemCount() {
        return appletEntityList.size();
    }

    public class AppletListingHolder extends RecyclerView.ViewHolder{
        TextView appletTitle;
        Switch appletStatus;
        public AppletListingHolder(View itemView) {
            super(itemView);
            appletTitle = (TextView) itemView.findViewById(R.id.applet_title);
            appletStatus = (Switch) itemView.findViewById(R.id.applet_enabled);
        }
    }
}
