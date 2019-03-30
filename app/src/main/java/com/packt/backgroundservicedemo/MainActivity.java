package com.packt.backgroundservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResult = findViewById(R.id.txvResults);
    }

    public void startBackgroundService(View view) {

        Intent myServiceIntent = new Intent(this,MyBackgroundService.class);
        myServiceIntent.putExtra("sleepTime",12);
        startService(myServiceIntent);
    }

    public void stopBackgroundService(View view) {
        Intent myServiceIntent = new Intent(this,MyBackgroundService.class);
        stopService(myServiceIntent);
    }

    public void startIntentService(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("sleepTime",12);
        startService(intent);
    }

    private BroadcastReceiver myLocalBroadCastReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int result = intent.getIntExtra("result",-1);
            txvResult.setText("Task Executed in " + result + " Sec.");
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("my.own.broadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(myLocalBroadCastReciver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myLocalBroadCastReciver);
    }

    public void stopIntentFilter(View view) {
        Intent localIntent = new Intent("my.own.ServiceBroadCast");
        localIntent.putExtra("result", false);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    public void startJobIntentService(View view) {
        Intent i = new Intent(this, MyJobIntentService.class);
        i.putExtra("sleepTime",12);
        //context.startService(i);
        MyJobIntentService.enqueueWork(this,i);
    }
}
