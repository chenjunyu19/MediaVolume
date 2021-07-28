package cn.morfans.chenjunyu19.mediavolume;

import android.service.quicksettings.TileService;

abstract class MediaTileService extends TileService {
    Util util;
    double k;
    int targetIndex;

    MediaTileService(double k) {
        this.k = k;
    }

    MediaTileService(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public void onClick() {
        util.setVolume();
    }

    @Override
    public void onStartListening() {
        if (k == 0.0) {
            util = new Util(this, getQsTile(), targetIndex);
        } else {
            util = new Util(this, getQsTile(), k);
        }
        util.setTile();
        util.registerBroadcastReceiver();
    }

    @Override
    public void onStopListening() {
        util.unregisterBroadcastReceiver();
    }
}
