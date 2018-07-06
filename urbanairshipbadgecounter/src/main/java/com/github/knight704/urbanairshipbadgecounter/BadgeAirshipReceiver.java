package com.github.knight704.urbanairshipbadgecounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.urbanairship.AirshipReceiver;
import com.urbanairship.push.PushMessage;

/**
 * Hooks to 'PushReceived' event, analyze contents of {@link PushMessage} and delegates to
 * {@link BadgeManager} to change badge count if necessary.
 */
public class BadgeAirshipReceiver extends AirshipReceiver {
    @Override
    protected void onPushReceived(@NonNull Context context, @NonNull PushMessage message, boolean notificationPosted) {
        if (notificationPosted) {
            handlePushReceived(context, message);
        }
    }

    private void handlePushReceived(Context context, PushMessage message) {
        try {
            BadgeManager badgeManager = BadgeManager.getInstance(context);
            String badgeExtraKey = badgeManager.getBadgeExtraKey();

            String badgeCountMsg = message.getExtra(badgeExtraKey, null);
            if (badgeCountMsg == null) {
                return;
            }
            boolean isIncrement = badgeCountMsg.indexOf("+") == 0;
            boolean isDecrement = badgeCountMsg.indexOf("-") == 0;
            int value = Integer.parseInt(isIncrement || isDecrement ? badgeCountMsg.substring(1) : badgeCountMsg);

            if (isIncrement || isDecrement) {
                badgeManager.shiftWith(isIncrement ? value : -value);
            } else {
                badgeManager.setBadgeCount(value);
            }
        } catch (Exception e) {
            // something went wrong during parsing, do nothing with badge counter
            Log.w(BadgeAirshipReceiver.class.getName(), "Failed to handle push receive event", e);
        }
    }
}
