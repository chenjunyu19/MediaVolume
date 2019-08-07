package cn.morfans.chenjunyu19.mediavolume;

import android.service.quicksettings.TileService;

abstract class MediaTileService extends TileService {
    Util util;
    double n;
    int target;

    MediaTileService(double n) {
        this.n = n;
    }

    MediaTileService(int target) {
        this.target = target;
    }

    @Override
    public void onClick() {
        util.setVol();
        util.setTile();
    }

    @Override
    public void onStartListening() {
        if (n == 0.0) {
            util = new Util(this, getQsTile(), target);
        } else {
            util = new Util(this, getQsTile(), n);
        }
        util.setTile();
        util.regBR();
    }

    @Override
    public void onStopListening() {
        util.unregBR();
    }
}