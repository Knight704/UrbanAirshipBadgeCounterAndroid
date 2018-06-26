package com.github.knight704.urbanairshipbadgecounter.sample;

import android.app.Application;

import com.github.knight704.urbanairshipbadgecounter.BadgeManager;
import com.urbanairship.UAirship;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BadgeManager.init(this, null);
        UAirship.takeOff(this);
        UAirship.shared().getPushManager().setUserNotificationsEnabled(true);
    }
}
