package cn.morfans.chenjunyu19.mediavolume;

import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.service.quicksettings.Tile;

class VolMan {
    private Context c;
    private Tile t;
    private int n;
    private BRReceiver br;

    VolMan(Context c) {
        this.c = c;
    }

    VolMan(Context c, Tile t, int n) {
        this.c = c;
        this.t = t;
        this.n = n;
    }

    void setVol() {
        AudioManager am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        int target = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 3 * n;

        if (target == 0 && isVol()) {
            am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);
        } else if (target == 0 || isVol()) {
            am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);
        } else {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, target, AudioManager.FLAG_SHOW_UI);
        }
    }

    private boolean isVol() {
        AudioManager am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        return am.getStreamVolume(AudioManager.STREAM_MUSIC) == am.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 3 * n;
    }

    void showVol() {
        AudioManager am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
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
