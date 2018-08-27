package cn.morfans.chenjunyu19.mediavolume;

import android.service.quicksettings.TileService;

public class MediaPanelService extends TileService {
    VolMan volMan;

    @Override
    public void onClick() {
        volMan.showVol();
    }

    @Override
    public void onStartListening() {
        volMan = new VolMan(this);
    }
}