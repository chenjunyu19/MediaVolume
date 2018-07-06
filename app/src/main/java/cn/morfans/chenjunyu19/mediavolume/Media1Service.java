package cn.morfans.chenjunyu19.mediavolume;

import android.service.quicksettings.TileService;

public class Media1Service extends TileService {
    int n = 1;

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
