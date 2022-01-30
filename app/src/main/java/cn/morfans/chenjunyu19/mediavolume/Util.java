package cn.morfans.chenjunyu19.mediavolume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.service.quicksettings.Tile;

class Util {
    private final AudioManager am;
    private final Context context;
    private final String[] actions = {
            "android.media.VOLUME_CHANGED_ACTION",
            "android.media.STREAM_DEVICES_CHANGED_ACTION",
            "android.media.STREAM_MUTE_CHANGED_ACTION"
    };
    private int targetIndex;
    private Tile tile;
    private final BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setTile();
        }
    };

    Util(Context context) {
        this.context = context;
        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    Util(Context context, Tile tile, double k) {
        this.context = context;
        this.tile = tile;
        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        targetIndex = (int) Math.round(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * k);
    }

    Util(Context context, Tile tile, int targetIndex) {
        this.context = context;
        this.tile = tile;
        this.targetIndex = targetIndex;
        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    private boolean isTargetVolume() {
        return am.getStreamVolume(AudioManager.STREAM_MUSIC) == targetIndex;
    }

    void showVolume() {
        am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
    }

    void setVolume() {
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

    void setTile() {
        tile.setState(isTargetVolume() ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.updateTile();
    }

    void registerBroadcastReceiver() {
        for (final String action : actions) {
            context.registerReceiver(br, new IntentFilter(action));
        }
    }

    void unregisterBroadcastReceiver() {
        context.unregisterReceiver(br);
    }
}
