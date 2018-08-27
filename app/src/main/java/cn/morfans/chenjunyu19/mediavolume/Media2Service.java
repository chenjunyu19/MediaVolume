package cn.morfans.chenjunyu19.mediavolume;

import android.service.quicksettings.TileService;

public class Media2Service extends TileService {
    VolMan volMan;

    @Override
    public void onClick() {
        volMan.setVol();
        volMan.setTile();
    }

    @Override
    public void onStartListening() {
        volMan = new VolMan(this, getQsTile(), 2);
        volMan.setTile();
    }
}
