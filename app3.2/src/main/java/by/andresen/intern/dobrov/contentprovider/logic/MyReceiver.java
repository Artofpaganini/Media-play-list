package by.andresen.intern.dobrov.contentprovider.logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "EBR triggered", Toast.LENGTH_SHORT).show();

        intent.setClass(context, NewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        Log.d(TAG, "onReceive: OPEN SECOND APP ");

    }
}
