package com.github.knight704.urbanairshipbadgecounter;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNBadgeCounterModule extends ReactContextBaseJavaModule {
    private BadgeManager badgeManager;

    public RNBadgeCounterModule(ReactApplicationContext context, String badgeExtraKey) {
        super(context);
        BadgeManager.init(context, badgeExtraKey);
        badgeManager = BadgeManager.getInstance(context);
    }

    @Override
    public String getName() {
        return "RNBadgeCounter";
    }

    @ReactMethod
    public void getCount(Promise promise) {
        promise.resolve(badgeManager.getCount());
    }

    @ReactMethod
    public void setCount(int value, Promise promise) {
        badgeManager.setCount(value);
        promise.resolve(true);
    }

    @ReactMethod
    public void clearCount(Promise promise) {
        badgeManager.clearCount();
        promise.resolve(true);
    }

    @ReactMethod
    public void shiftWith(int howMany, Promise promise) {
        badgeManager.shiftWith(howMany);
        promise.resolve(true);
    }

    @ReactMethod
    public void isBadgeCounterSupported(Promise promise) {
        promise.resolve(badgeManager.isBadgeCounterSupported());
    }
}
