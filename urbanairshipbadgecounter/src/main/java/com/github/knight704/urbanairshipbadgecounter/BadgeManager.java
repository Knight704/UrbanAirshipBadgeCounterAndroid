package com.github.knight704.urbanairshipbadgecounter;

import android.content.Context;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.knight704.urbanairshipbadgecounter.storage.BadgeStorage;
import com.github.knight704.urbanairshipbadgecounter.storage.SharedPreferenceBadgeStorage;
import com.urbanairship.push.PushMessage;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * The main entry point to work with badge counter on android.
 */
public class BadgeManager {
    private static final String DEFAULT_EXTRA_BADGE_KEY = "com.github.knight704.urbanairshipbadgecounter.BADGE_COUNT";
    private static BadgeManager instance;

    private Context context;
    private BadgeStorage badgeStorage;
    private String badgeExtraKey = DEFAULT_EXTRA_BADGE_KEY;

    @MainThread
    public static void init(Context context, String badgeExtraKey) {
        if (instance != null) {
            throw new RuntimeException("BadgeManager already initialized");
        }
        instance = getInstance(context);
        instance.badgeExtraKey = badgeExtraKey;
    }

    @MainThread
    public static BadgeManager getInstance(Context context) {
        if (instance == null) {
            Context appContext = context.getApplicationContext();
            instance = new BadgeManager(appContext, new SharedPreferenceBadgeStorage(appContext));
        }
        return instance;
    }

    private BadgeManager(Context context, BadgeStorage badgeStorage) {
        this.context = context.getApplicationContext();
        this.badgeStorage = badgeStorage;
    }

    protected void handlePushReceived(@NonNull PushMessage message) {
        try {
            String badgeCountMsg = message.getExtra(badgeExtraKey, null);
            if (badgeCountMsg == null) {
                return;
            }
            boolean isIncrement = badgeCountMsg.indexOf("+") == 0;
            boolean isDecrement = badgeCountMsg.indexOf("-") == 0;
            int value = Integer.parseInt(isIncrement || isDecrement ? badgeCountMsg.substring(1) : badgeCountMsg);

            if (isIncrement || isDecrement) {
                shiftWith(isIncrement ? value : -value);
            } else {
                setCount(value);
            }
        } catch (Exception e) {
            // something went wrong during parsing, do nothing with badge counter
            Log.w(BadgeManager.class.getName(), "Failed to handle push receive event due to", e);
        }
    }

    public int getCount() {
        return badgeStorage.getBadgeCount();
    }

    public void setCount(int count) {
        badgeStorage.setBadgeCount(count);
        ShortcutBadger.applyCount(context, count);
    }

    public void clearCount() {
        badgeStorage.setBadgeCount(0);
        ShortcutBadger.removeCount(context);
    }

    public void shiftWith(int howMany) {
        int newValue = badgeStorage.shiftWith(howMany);
        ShortcutBadger.applyCount(context, newValue);
    }

    public boolean isBadgeCounterSupported() {
        return ShortcutBadger.isBadgeCounterSupported(context);
    }
}
