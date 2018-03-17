package cn.morfans.chenjunyu19.mediavolume;

import android.content.Context;
import android.media.AudioManager;
import android.service.quicksettings.TileService;

public class MediaMuteService extends TileService {
    @Override
    public void onClick() {
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
    }
}
