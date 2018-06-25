package cn.morfans.chenjunyu19.mediavolume;

import android.service.quicksettings.TileService;

public class MediaPanelService extends TileService {
    @Override
    public void onClick() {
        VolMan.showVol(this);
    }
}
