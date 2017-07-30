package com.santhosh.jarvis.database.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Santhosh on 23/07/17.
 */

@Entity
public class AppletEntity {

    @PrimaryKey (autoGenerate = true)
    public int appletID;

    @ColumnInfo(name = "applet_name")
    public String appletName;

    @ColumnInfo(name = "situation")
    public String situation;

    @ColumnInfo(name = "situationType")
    public String situationType;

    @ColumnInfo(name = "action")
    public String action;

    @ColumnInfo(name = "actionType")
    public String actionType;

    @ColumnInfo(name = "applet_enabled")
    public boolean appletEnabled;

    @ColumnInfo(name = "need_notification")
    public boolean needNotification;
}
