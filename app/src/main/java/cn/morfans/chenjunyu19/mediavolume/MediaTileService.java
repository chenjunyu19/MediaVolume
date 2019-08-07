package cn.morfans.chenjunyu19.mediavolume;

import android.service.quicksettings.TileService;

abstract class MediaTileService extends TileService {
    Util util;
    double n;

    MediaTileService(double n) {
        this.n = n;
    }

    @Override
    public void onClick() {
        util.setVol();
        util.setTile();
    }

    @Override
    public void onStartListening() {
        util = new Util(this, getQsTile(), n);
        util.setTile();
        util.regBR();
    }

    @Override
    public void onStopListening() {
        util.unregBR();
    }
}