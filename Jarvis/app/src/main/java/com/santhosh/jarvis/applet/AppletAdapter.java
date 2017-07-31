package com.santhosh.jarvis.applet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santhosh.jarvis.AppletInterface;
import com.santhosh.jarvis.R;

/**
 * Created by santhosh-3366 on 10/07/17.
 */

public class AppletAdapter extends RecyclerView.Adapter<AppletAdapter.RecyclerHolder> {

    int[] drawableID = {R.drawable.ic_applet_battery, R.drawable.ic_applet_headset/*, R.drawable.ic_applet_location*/};
    int[] appletName = {R.string.battery,R.string.headphones,R.string.location};
    int count = drawableID.length;

    private AppletInterface appletInterface;

    AppletAdapter(AppletInterface appletInterface) {
        this.appletInterface = appletInterface;
    }

    public AppletAdapter(int count) {
        this.count = count;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View holder = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_holder,viewGroup,false);
        return new RecyclerHolder(holder);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder recyclerHolder, int i) {
        recyclerHolder.holderImageview.setImageResource(drawableID[i]);
        recyclerHolder.holderTextview.setText(appletName[i]);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView holderTextview;
        ImageView holderImageview;

        public RecyclerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            holderTextview = (TextView) itemView.findViewById(R.id.holder_textview);
            holderImageview = (ImageView) itemView.findViewById(R.id.holder_imageview);
        }

        @Override
        public void onClick(View v) {
            appletInterface.onSituationCreate(holderTextview.getText().toString());
        }
    }


}
