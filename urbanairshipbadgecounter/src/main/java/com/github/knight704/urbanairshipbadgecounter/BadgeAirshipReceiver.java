package com.github.knight704.urbanairshipbadgecounter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.urbanairship.AirshipReceiver;
import com.urbanairship.push.PushMessage;

/**
 * Hooks to 'PushReceived' event to track badge counter number
 */
public class BadgeAirshipReceiver extends AirshipReceiver {
    @Override
    protected void onPushReceived(@NonNull Context context, @NonNull PushMessage message, boolean notificationPosted) {
        super.onPushReceived(context, message, notificationPosted);
        if (notificationPosted) {
            BadgeManager.getInstance(context).handlePushReceived(message);
        }
    }
}
