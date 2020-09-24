package by.andersen.intern.serviceplaymusicfile.activies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import by.andersen.intern.serviceplaymusicfile.broadcast.MyReceiver;
import by.andersen.intern.serviceplaymusicfile.R;
import by.andersen.intern.serviceplaymusicfile.service.MyService;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Intent serviceIntent;
    private MyService myService;
    private MyReceiver myReceiver;

    public static final String SEND_MESSAGE = "set song";

    public static final String SHARED_ITEM = "saveMediaFile";
    public static final String SONG_NAME = "songName";
    public static final String SONG_ARTIST = "songArtist";
    public static final String SONG_STYLE = "songStyle";
    public static final String SONG_URI = "songUri";

    public static final String SAVE_SONG_NAME = "saveSongName";
    public static final String SAVE_SONG_ARTIST = "saveSongArtist";
    public static final String SAVE_SONG_STYLE = "saveSongStyle";
    public static final String SAVE_SONG_URI = "saveSongUri";

    public static final String URI_SONG_FOR_SENDING_SERVICE = "uriSong";






    private TextView name;
    private TextView artist;
    private TextView styleOfMusic;

    private TextView musicName;
    private TextView musicArtist;
    private TextView musicStyle;

    private Button play;
    private Button stop;
    private Button chooseTheArtist;

    private SongData songData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        artist = findViewById(R.id.artist);
        styleOfMusic = findViewById(R.id.style);

        musicName = findViewById(R.id.music_name);
        musicArtist = findViewById(R.id.music_artist);
        musicStyle = findViewById(R.id.music_style);

        play = findViewById(R.id.play);
        stop = findViewById(R.id.stop);
        chooseTheArtist = findViewById(R.id.choose_artist);

        serviceIntent = new Intent(this, MyService.class);
        myReceiver = new MyReceiver();

        refillFieldsInfoAboutSong();
        saveMediaFile();

        play.setOnClickListener(view -> {

            loadMediaFile();
            play();

        });

        stop.setOnClickListener(view -> {
            stop();
            saveMediaFile();
        });

        chooseTheArtist.setOnClickListener(MainActivity.this::openActivityFromBroadcast
        );


    }


    private void play() {

        if (songData.getSongUriAddress() == null) {
            Toast.makeText(this, "Please choose the artist", Toast.LENGTH_LONG).show();
        } else {

            sendMediaFileToService();
            startService(serviceIntent);
            saveMediaFile();

            Log.d(TAG, "play: SEND URI  TO SERVICE " + songData.getSongUriAddress());
            Log.d(TAG, "play: START to PLAY MUSIC");
        }
    }


    private void stop() {
        stopService(serviceIntent);
        saveMediaFile();

        Log.d(TAG, "stop: STOP AND SAVE THE DATA");
    }


    @NonNull
    public void refillFieldsInfoAboutSong() {

        Intent intent = getIntent();
        songData = new SongData();
        songData.setSongName(intent.getStringExtra(SONG_NAME));
        songData.setSongArtist(intent.getStringExtra(SONG_ARTIST));
        songData.setSongStyle(intent.getStringExtra(SONG_STYLE));
        songData.setSongUriAddress(intent.getStringExtra(SONG_URI));

        musicName.setText(songData.getSongName());
        musicArtist.setText(songData.getSongArtist());
        musicStyle.setText(songData.getSongStyle());

        Log.d(TAG, "refillFieldsInfoAboutSong: DATA REFILL " + songData.getSongName() + " " + songData.getSongUriAddress());
    }


    public void sendMediaFileToService() {

        serviceIntent.putExtra(URI_SONG_FOR_SENDING_SERVICE, songData.getSongUriAddress());

        Log.d(TAG, "sendMediaFileToService: SEND MEDIA FILE TO SERVICE");
    }


    public void saveMediaFile() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_ITEM, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_SONG_NAME, songData.getSongName());
        editor.putString(SAVE_SONG_ARTIST, songData.getSongArtist());
        editor.putString(SAVE_SONG_STYLE, songData.getSongArtist());
        editor.putString(SAVE_SONG_URI, songData.getSongUriAddress());

        editor.apply();

        Log.d(TAG, "saveMediaFile: SAVE URI  AND DATA " + songData.getSongName());

    }

    public void loadMediaFile() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_ITEM, MODE_PRIVATE);
        songData.setSongName(sharedPreferences.getString(SAVE_SONG_NAME, null));
        songData.setSongArtist(sharedPreferences.getString(SAVE_SONG_ARTIST, null));
        songData.setSongStyle(sharedPreferences.getString(SAVE_SONG_STYLE, null));
        songData.setSongUriAddress(sharedPreferences.getString(SAVE_SONG_URI, null));

        Log.d(TAG, "loadMediaFile: LOAD URI AND DATA ");
    }

    @NonNull
    public void openActivityFromBroadcast(View v) {
        Intent intent = new Intent("by.andersen.intern.contentprovider.MAKE_A_CHOISE");

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> infos = packageManager.queryBroadcastReceivers(intent, 0);

        for (ResolveInfo info : infos) {
            ComponentName cn = new ComponentName(info.activityInfo.packageName,
                    info.activityInfo.name);
            intent.setComponent(cn);
            sendBroadcast(intent);

            Log.d(TAG, "openActivityFromBroadcast: SEND BROADCAST");
        }


    }

}

