package com.packt.backgroundservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, MyJobIntentService.class);
            i.putExtra("sleepTime",12);
            //context.startService(i);
            MyJobIntentService.enqueueWork(context,i);
        }
    }
}
