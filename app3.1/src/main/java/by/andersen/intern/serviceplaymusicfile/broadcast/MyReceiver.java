package by.andersen.intern.serviceplaymusicfile.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import androidx.annotation.NonNull;

import by.andersen.intern.serviceplaymusicfile.activies.MainActivity;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(@NonNull Context context,@NonNull Intent intent) {

        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);

        Log.d(TAG, "onReceive: RETURN TO APP 1");
    }

}
