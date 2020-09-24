package by.andersen.intern.serviceplaymusicfile.service;

import android.app.Service;
import android.content.Intent;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.net.URI;

public class MyService extends Service implements Serializable {

    private static final String TAG = "MyService";

    private MediaPlayer mediaPlayer;
    private boolean isMusicStopped = true;
    private SharedPreferences sharedPreferences;
    private final String SHARED_ITEM = "sharedItem";
    private int positionTime;
    private Uri uri;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        playMusic(intent);
        return START_STICKY;
    }


    public void playMusic(@NonNull Intent intent) {
        Log.d(TAG, "playMusic: TRY TO PLAY");

        uri = Uri.parse(intent.getStringExtra("uriSong"));
        saveData(uri);

        if (mediaPlayer == null) {

            loadData();
            mediaPlayer = MediaPlayer.create(this, uri);
            isMusicStopped = false;
            saveData(uri);

            Log.d(TAG, "playMusic: PLAYER was null " + uri.toString());
        } else {

            mediaPlayer.release();
            loadData();
            mediaPlayer = MediaPlayer.create(this, uri);
            saveData(uri);

            Log.d(TAG, "playMusic: PLAYER was not NULL" + uri.toString());
        }
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {

            mediaPlayer.release();
            loadData();
            mediaPlayer = MediaPlayer.create(this, uri);

            Log.d(TAG, "playMusic: PLAYER IS NOT PLAYING");
        }
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(mediaPlayer -> {

            mediaPlayer.seekTo(positionTime);

            onDestroy();

            Log.d(TAG, "onCompletion: FINISH media file ");
        });
        Log.d(TAG, "onCreate: service on STARTED");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
        saveData(getUri());
        mediaPlayer.release();
        loadData();
        Log.d(TAG, "onDestroy: DESTROY  " + getUri().toString());
    }

    @Nullable
    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void saveData(Uri uri) {

        String saveSong = uri.toString();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_ITEM, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("saveSong", saveSong);
        editor.putInt("savePositionTime", positionTime);
        editor.apply();

        Log.d(TAG, "saveData: SAVE URI " + getUri().toString() + " and " + getPositionTime());

    }


    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_ITEM, MODE_PRIVATE);
        setUri(Uri.parse(sharedPreferences.getString("saveSong", null)));
        setPositionTime(sharedPreferences.getInt("savePositionTime", 0));

        Log.d(TAG, "loadData: URI IS LOAD!! " + getUri().toString() + " and " + getPositionTime());
    }


    public int getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(int positionTime) {
        this.positionTime = positionTime;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
