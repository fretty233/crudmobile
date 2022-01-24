package mmv.devlops.datakelas.activities;


import android.app.Service;
import android.content.Intent;

import android.os.IBinder;
import android.util.Log;

import android.widget.Toast;


public class MyService extends Service {
    private static final String TAG = "MyService";
    private boolean isRunning = false;

    @Override
    public void onCreate(){
        Log.i(TAG, "Service onCreate");
        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i(TAG, "Service onStartCommand");
        Toast.makeText(this, "Service Startred", Toast.LENGTH_LONG).show();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG,"Service onBind");
        return null;
    }

    @Override
    public void onDestroy(){
        isRunning = false;
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Service onDestroy");
    }
}

