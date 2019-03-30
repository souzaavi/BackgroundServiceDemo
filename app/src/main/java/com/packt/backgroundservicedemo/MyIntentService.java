package com.packt.backgroundservicedemo;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class MyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    //private boolean ctr;

    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyBackgroundIntentThread");
        //this.ctr = true;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent: Thread: " + Thread.currentThread().getName());

        Intent localIntent = new Intent("my.own.broadcast");
        int ctr = intent.getIntExtra("sleepTime",-1);
        int duration = 0;

        while (duration <= ctr) {
            Log.i(TAG, "onHandleIntent: Time elapsed: " + ctr + " secs");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            duration++;
            localIntent.putExtra("result", duration);
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: Thread: " + Thread.currentThread().getName());
        Toast.makeText(this, "Task Execution Finished", Toast.LENGTH_SHORT).show();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myLocalBroadCastReciver);
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Task Execution Started", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onCreate: Thread: " + Thread.currentThread().getName());
        IntentFilter intentFilter = new IntentFilter("my.own.ServiceBroadCast");
        LocalBroadcastManager.getInstance(this).registerReceiver(myLocalBroadCastReciver,intentFilter);
    }

    private BroadcastReceiver myLocalBroadCastReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //ctr = intent.getBooleanExtra("result",false);
        }
    };
}
