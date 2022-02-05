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
    private AudioManager am;
    private Tile tile;
    private double k;
    private int targetIndex;
    private final BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setTile();
        }
    };

    MediaTileService(double k) {
        this.k = k;
    }

    MediaTileService(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void onStartListening() {
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        tile = getQsTile();
        if (k != 0.0) {
            targetIndex = (int) Math.round(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * k);
        }
        setTile();
        for (final String action : ACTIONS) {
            registerReceiver(br, new IntentFilter(action));
        }
    }

    @Override
    public void onStopListening() {
        unregisterReceiver(br);
    }

    @Override
    public void onClick() {
        if (targetIndex == 0 && isTargetVolume()) {
            am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);
            if (isTargetVolume()) {
                am.setStreamVolume(AudioManager.STREAM_MUSIC, 1, AudioManager.FLAG_SHOW_UI);
            }
        } else if (targetIndex == 0 || isTargetVolume()) {
            am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);
        } else {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, targetIndex, AudioManager.FLAG_SHOW_UI);
        }
    }

    private boolean isTargetVolume() {
        return am.getStreamVolume(AudioManager.STREAM_MUSIC) == targetIndex;
    }

    private void setTile() {
        tile.setState(isTargetVolume() ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.updateTile();
    }
}
