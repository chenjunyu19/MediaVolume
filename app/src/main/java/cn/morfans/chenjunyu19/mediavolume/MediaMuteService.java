package cn.morfans.chenjunyu19.mediavolume;

import android.service.quicksettings.TileService;

public class MediaMuteService extends TileService {
    Util util;

    @Override
    public void onClick() {
        util.setVol();
        util.setTile();
    }

    @Override
    public void onStartListening() {
        util = new Util(this, getQsTile(), 0);
        util.setTile();
        util.regBR();
    }

    @Override
    public void onStopListening() {
        util.unregBR();
    }
}
