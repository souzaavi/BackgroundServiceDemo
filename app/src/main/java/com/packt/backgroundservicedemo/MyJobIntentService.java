package com.packt.backgroundservicedemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class MyJobIntentService extends JobIntentService {

    private static final String TAG = "MyJobIntentService";

    public static void enqueueWork(Context context,Intent intent) {
        enqueueWork(context, MyJobIntentService.class, 17, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Task Execution Started", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        Log.i(TAG, "onHandleWork: " + Thread.currentThread().getName());

        Intent localIntent = new Intent("my.own.broadcast");
        int ctr = intent.getIntExtra("sleepTime", -1);
        int duration = 0;

        while (duration <= ctr) {
            Log.i(TAG, "onHandleIntent: Time elapsed: " + duration + " secs");
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
        Toast.makeText(this, "Task Execution Finished", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
