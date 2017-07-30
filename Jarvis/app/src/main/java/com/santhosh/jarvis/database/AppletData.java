package com.santhosh.jarvis.database;

import android.util.Log;

/**
 * Created by Santhosh on 30/07/17.
 */

public class AppletData {
    public int applet_id;
    public String applet_name;
    public String situation;
    public String situation_type;
    public String action;
    public String action_type;
    public int need_notification;
    public int applet_enabled;

    public void setApplet_id(int applet_id) {
        this.applet_id = applet_id;
    }

    public void setApplet_name(String applet_name) {
        this.applet_name = applet_name;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public void setSituation_type(String situation_type) {
        this.situation_type = situation_type;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public void setNeed_notification(int need_notification) {
        this.need_notification = need_notification;
    }

    public void setApplet_enabled(int applet_enabled) {
        this.applet_enabled = applet_enabled;
    }
}
