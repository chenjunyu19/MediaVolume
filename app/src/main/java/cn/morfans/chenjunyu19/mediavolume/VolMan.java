package cn.morfans.chenjunyu19.mediavolume;

import android.content.Context;
import android.media.AudioManager;
import android.service.quicksettings.Tile;

public class VolMan {

    public static void setVol(Context c, int n) {
        AudioManager am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        int target = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 3 * n;
        int vol = am.getStreamVolume(AudioManager.STREAM_MUSIC);

        if(target == 0) {
            if (vol == target) {
                am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);
            } else {
                am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);
            }
        } else {
            if (vol == target) {
                am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);
            } else {
                am.setStreamVolume(AudioManager.STREAM_MUSIC, target, AudioManager.FLAG_SHOW_UI);
            }
        }
    }

    public static boolean isVol(Context c, int n) {
        AudioManager am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        return am.getStreamVolume(AudioManager.STREAM_MUSIC) == am.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 3 * n;
    }

    public static void showVol(Context c) {
        AudioManager am = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
    }

    public static void setTile(Context c, Tile t, int n) {
        if (isVol(c, n)) {
            t.setState(Tile.STATE_ACTIVE);
        } else {
            t.setState(Tile.STATE_INACTIVE);
        }
        t.updateTile();
    }
}
