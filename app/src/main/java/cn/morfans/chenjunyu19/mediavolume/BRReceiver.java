package cn.morfans.chenjunyu19.mediavolume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BRReceiver extends BroadcastReceiver {
    Util util;

    BRReceiver(Util util) {
        this.util = util;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        util.setTile();
    }
}
