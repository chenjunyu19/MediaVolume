package cn.morfans.chenjunyu19.mediavolume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BRReceiver extends BroadcastReceiver {
    VolMan volMan;

    BRReceiver(VolMan volMan) {
        this.volMan = volMan;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        volMan.setTile();
    }
}
