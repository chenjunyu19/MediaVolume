package cn.morfans.chenjunyu19.mediavolume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

abstract class MediaTileService extends TileService {
    private static final String[] ACTIONS = {
            "android.media.VOLUME_CHANGED_ACTION",
            "android.media.STREAM_DEVICES_CHANGED_ACTION",
            "android.media.STREAM_MUTE_CHANGED_ACTION"
    };
    private static final int STREAM = AudioManager.STREAM_MUSIC;
    private AudioManager am;
    private Tile tile;
    private float ratio;
    private int targetIndex;
    private final BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setTile();
        }
    };

    MediaTileService(float ratio) {
        this.ratio = ratio;
    }

    MediaTileService(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void onStartListening() {
        am = getSystemService(AudioManager.class);
        tile = getQsTile();
        if (ratio != 0.0) {
            targetIndex = Math.round(am.getStreamMaxVolume(STREAM) * ratio);
        }
        IntentFilter filter = new IntentFilter();
        for (final String action : ACTIONS) {
            filter.addAction(action);
        }
        registerReceiver(br, filter);
        setTile();
    }

    @Override
    public void onStopListening() {
        unregisterReceiver(br);
    }

    @Override
    public void onClick() {
        if (targetIndex == 0 && isTargetVolume()) {
            am.adjustStreamVolume(STREAM, AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);
            if (isTargetVolume()) {
                am.setStreamVolume(STREAM, 1, AudioManager.FLAG_SHOW_UI);
            }
        } else if (targetIndex == 0 || isTargetVolume()) {
            am.adjustStreamVolume(STREAM, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);
        } else {
            am.setStreamVolume(STREAM, targetIndex, AudioManager.FLAG_SHOW_UI);
        }
    }

    private boolean isTargetVolume() {
        return am.getStreamVolume(STREAM) == targetIndex;
    }

    private void setTile() {
        tile.setState(isTargetVolume() ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.updateTile();
    }
}
