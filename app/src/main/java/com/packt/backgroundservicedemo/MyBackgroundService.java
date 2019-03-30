package com.packt.backgroundservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyBackgroundService extends Service {

    private static final String TAG = "MyBackgroundService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: Thread Name: " + Thread.currentThread().getName());
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: Thread Name: " + Thread.currentThread().getName());
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: Thread Name: " + Thread.currentThread().getName());
        int duration = intent.getIntExtra("sleepTime",-1);
        new MyAsynTask(duration).execute();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: Thread Name: " + Thread.currentThread().getName());

        super.onDestroy();
    }

    class MyAsynTask extends AsyncTask<Void, String,  Void> {

        int duration;

        public MyAsynTask(int duration) {
            this.duration = duration;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "onPreExecute: Thread Name: " + Thread.currentThread().getName());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.i(TAG, "doInBackground: Thread Name: " + Thread.currentThread().getName());
            int ctr = 1;
            while (ctr <= duration) {
                publishProgress("Time elapsed: " + ctr + " secs");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctr++;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.i(TAG, "onProgressUpdate: Thread Name: " + Thread.currentThread().getName()+" Counter Value: " + values[0]);
            super.onProgressUpdate(values);

            Toast.makeText(MyBackgroundService.this, "" + values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(TAG, "onPostExecute: Thread Name: " + Thread.currentThread().getName());
            super.onPostExecute(aVoid);
            stopSelf();
        }
    }
}
