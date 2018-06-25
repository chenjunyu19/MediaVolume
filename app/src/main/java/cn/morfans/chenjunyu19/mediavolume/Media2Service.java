package cn.morfans.chenjunyu19.mediavolume;

import android.service.quicksettings.TileService;

public class Media2Service extends TileService {
    int n = 2;

    @Override
    public void onClick() {
        VolMan.setVol(this, n);
        VolMan.setTile(this, getQsTile(), n);
    }

    @Override
    public void onStartListening() {
        VolMan.setTile(this, getQsTile(), n);
    }
}
