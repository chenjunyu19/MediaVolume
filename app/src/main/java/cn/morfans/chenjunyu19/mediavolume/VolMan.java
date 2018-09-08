package cn.morfans.chenjunyu19.mediavolume;

import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.service.quicksettings.Tile;

class VolMan {
    private Context c;
    private Tile t;
    private AudioManager am;
    private int target;
    private BRReceiver br;

    VolMan(Context c) {
        this.c = c;
        am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
    }

    VolMan(Context c, Tile t, int n) {
        this.c = c;
        this.t = t;
        am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        target = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 3 * n;
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
        if (isVol()) {
            t.setState(Tile.STATE_ACTIVE);
        } else {
            t.setState(Tile.STATE_INACTIVE);
        }
        t.updateTile();
    }

    void regBR() {
        br = new BRReceiver(this);
        c.registerReceiver(br, new IntentFilter("android.media.VOLUME_CHANGED_ACTION"));
    }

    void unregBR() {
        c.unregisterReceiver(br);
    }
}
