package cn.morfans.chenjunyu19.mediavolume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.service.quicksettings.Tile;

class Util {
    private AudioManager am;
    private BroadcastReceiver br;
    private Context context;
    private int target;
    private Tile tile;

    Util(Context context) {
        this.context = context;
        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    Util(Context context, Tile tile, double n) {
        this.context = context;
        this.tile = tile;
        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (am != null) {
            target = (int) Math.round(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 3.0 * n);
        }
    }

    void setVol() {
        if (target == 0 && isVol()) {
            am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);
        } else if (target == 0 || isVol()) {
            am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);
        } else {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, target, AudioManager.FLAG_SHOW_UI);
        }
    }

    private boolean isVol() {
        return am.getStreamVolume(AudioManager.STREAM_MUSIC) == target;
    }

    void showVol() {
        am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
    }

    void setTile() {
        int targetState;
        if (isVol()) {
            targetState = Tile.STATE_ACTIVE;
        } else {
            targetState = Tile.STATE_INACTIVE;
        }
        if (tile.getState() != targetState) {
            tile.setState(targetState);
            tile.updateTile();
        }
    }

    void regBR() {
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setTile();
            }
        };
        context.registerReceiver(br, new IntentFilter("android.media.VOLUME_CHANGED_ACTION"));
    }

    void unregBR() {
        context.unregisterReceiver(br);
    }
}
