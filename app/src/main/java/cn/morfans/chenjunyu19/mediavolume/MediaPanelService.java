package cn.morfans.chenjunyu19.mediavolume;

import android.service.quicksettings.TileService;

public class MediaPanelService extends TileService {
    Util util;

    @Override
    public void onClick() {
        util.showVolume();
    }

    @Override
    public void onStartListening() {
        util = new Util(this);
    }
}
