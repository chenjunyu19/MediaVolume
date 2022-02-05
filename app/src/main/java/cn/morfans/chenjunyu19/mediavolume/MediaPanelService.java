package cn.morfans.chenjunyu19.mediavolume;

import android.content.Context;
import android.media.AudioManager;
import android.service.quicksettings.TileService;

public class MediaPanelService extends TileService {
    private AudioManager am;

    @Override
    public void onStartListening() {
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void onClick() {
        am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
    }
}
