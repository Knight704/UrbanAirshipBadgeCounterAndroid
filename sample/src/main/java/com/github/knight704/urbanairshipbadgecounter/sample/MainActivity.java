package com.github.knight704.urbanairshipbadgecounter.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.urbanairship.AirshipReceiver;
import com.urbanairship.UAirship;
import com.urbanairship.push.PushMessage;
import com.urbanairship.util.UAStringUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private TextView tvChannelId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvChannelId = findViewById(R.id.tvChannelId);
    }

    private void updateChannelId(String channelId) {
        tvChannelId.setText(channelId);
        Log.d(TAG, String.format("ChannelId: %s", channelId));
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter;
        filter = new IntentFilter();
        filter.addAction("com.urbanairship.push.CHANNEL_UPDATED");
        registerReceiver(channelIdUpdateReceiver, filter);
        refreshChannelId();
    }

    private void refreshChannelId() {
        String channelIdString = UAirship.shared().getPushManager().getChannelId();
        channelIdString = UAStringUtil.isEmpty(channelIdString) ? "" : channelIdString;
        updateChannelId(channelIdString);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(channelIdUpdateReceiver);
    }

    private final BroadcastReceiver channelIdUpdateReceiver = new AirshipReceiver() {
        @Override
        protected void onChannelCreated(@NonNull Context context, @NonNull String channelId) {
            updateChannelId(channelId);
        }

        @Override
        protected void onChannelUpdated(@NonNull Context context, @NonNull String channelId) {
            updateChannelId(channelId);
        }

        @Override
        protected void onPushReceived(@NonNull Context context, @NonNull PushMessage message, boolean notificationPosted) {
            Log.d(TAG, "PushReceived!");
        }
    };
}
