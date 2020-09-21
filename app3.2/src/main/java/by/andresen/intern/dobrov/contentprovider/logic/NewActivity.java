package by.andresen.intern.dobrov.contentprovider.logic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.andresen.intern.dobrov.contentprovider.R;
import by.andresen.intern.dobrov.contentprovider.db.SongContractData;
import by.andresen.intern.dobrov.contentprovider.entities.SongListData;
import by.andresen.intern.dobrov.contentprovider.db.DBHelper;
import by.andresen.intern.dobrov.contentprovider.entities.Song;

public class NewActivity extends AppCompatActivity {
    private static final String TAG = "NewActivity";

    private static final String EMPTY = "EMPTY";
    public static final String MEDIA_PLAYER = "by.andersen.intern.serviceplaymusicfile.GetSong";

    private List<Song> songListForDB;
    private List<Song> songListFromDB;
    private Song song;
    private String songName;
    private String songArtist;
    private String songStyle;
    private String songUriAddress;

    private SongListData songListData;

    private RecyclerView recyclerView;
    private MyAdapter adapterRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private MyReceiver myReceiver;
    private SharedPreferences sharedPreferences;

    private Spinner chooseByArtist;
    private Spinner chooseByStyle;
    private Button fullListButton;

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");

        songListForDB = new ArrayList<>();
        songListFromDB = new ArrayList<>();
        songListData = new SongListData();

        dbHelper = new DBHelper(this);

        database = dbHelper.getWritableDatabase();

        checkIfListForDbIsEmpty();

        addSongToDB(database);

        initViews();

        clickAndInitRecyclerView();

        songListFromDB = getSongsFromDB(database);

        adapterSpinner(chooseByArtist, getArtistSpinner(songListFromDB));
        adapterSpinner(chooseByStyle, getStyleSpinner(songListFromDB));

        getSongsByChoosenItemByStyleFromSpinner(chooseByStyle);
        getSongsByChoosenItemByArtistFromSpinner(chooseByArtist);

        IntentFilter filter = new IntentFilter(MEDIA_PLAYER);
        registerReceiver(myReceiver, filter);


        Log.d(TAG, "onCreate: SAVE DATA ");
    }

    @NonNull
    private void adapterSpinner(@NonNull Spinner spinner, @Nullable List<String> songList) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, songList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    // выводим песни с выбранным артистом
    private void getSongsByChoosenItemByArtistFromSpinner(@NonNull Spinner spinner) {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {

                String item = (String) parent.getItemAtPosition(selectedItemPosition);

                adapterRecyclerView = new MyAdapter(getSongNameByChoosenArtist(item));
                recyclerView.setAdapter(adapterRecyclerView);

                getPositionElementFromRecyclerView(getSongNameByChoosenArtist(item));

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    // выводим песни с выбранным стилем
    private void getSongsByChoosenItemByStyleFromSpinner(@NonNull Spinner spinner) {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {

                String item = (String) parent.getItemAtPosition(selectedItemPosition);
                adapterRecyclerView = new MyAdapter(getSongNameByChoosenStyle(item));
                recyclerView.setAdapter(adapterRecyclerView);

                getPositionElementFromRecyclerView(getSongNameByChoosenStyle(item));

                Log.d(TAG, "onItemSelected: USE Styles");

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    //получение списка песен , по выбранной категории артиста
    @NonNull
    private List<Song> getSongNameByChoosenArtist(@NonNull String selectedArtist) {

        List<Song> songsByChoisenArtist = new ArrayList<>();

        for (Song song : songListFromDB) {
            if (song.getArtist().equalsIgnoreCase(selectedArtist)) {
                songsByChoisenArtist.add(song);
            }
        }
        return songsByChoisenArtist;

    }

    //получение списка песен , по выбранной категории стилей
    @NonNull
    private List<Song> getSongNameByChoosenStyle(@NonNull String selectedStyle) {

        List<Song> songsByChoosenStyle = new ArrayList<>();

        for (Song song : songListFromDB) {
            if (song.getStyleOfMusic().equalsIgnoreCase(selectedStyle)) {
                songsByChoosenStyle.add(song);
            }
        }
        return songsByChoosenStyle;

    }

    //получение списка артистов для спинера
    @NonNull
    private List<String> getArtistSpinner(@NonNull List<Song> allSongsFromDb) {
        Set<String> set = new HashSet<>();
        List<String> songListByArtist = new ArrayList<>();
        for (Song song : allSongsFromDb) {
            set.add(song.getArtist());
        }
        songListByArtist.add(EMPTY);
        songListByArtist.addAll(set);
        return songListByArtist;
    }

    //получение списка стилей для спинера
    @NonNull
    private List<String> getStyleSpinner(@NonNull List<Song> allSongsFromDb) {
        Set<String> set = new HashSet<>();
        List<String> songListByStyle = new ArrayList<>();
        for (Song song : allSongsFromDb) {
            set.add(song.getStyleOfMusic());
        }
        songListByStyle.add(EMPTY);
        songListByStyle.addAll(set);

        return songListByStyle;
    }

    //добавление данныйх в бд
    @NonNull
    private void addSongToDB(@Nullable SQLiteDatabase database) {
        Log.d(TAG, "addSongToDB: ");

        for (Song song : songListForDB) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SongContractData.COLUMN_NAME, song.getName());
            contentValues.put(SongContractData.COLUMN_ARTIST, song.getArtist());
            contentValues.put(SongContractData.COLUMN_STYLE, song.getStyleOfMusic());
            contentValues.put(SongContractData.COLUMN_URI, song.getUriAddress());

            database.insert(SongContractData.TABLE_SONGS, null, contentValues);

            Log.d(TAG, "addSongToDB: ADDED TO DB");
        }
    }

    //получение  данный из бд
    @NonNull
    private List<Song> getSongsFromDB(@NonNull SQLiteDatabase database) {
        Log.i(TAG, "getAllSongs ... ");

        String selectQuery = SongContractData.TABLE_SONGS;

        Cursor cursor = database.query(selectQuery, null, null,
                null, null,
                null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString((cursor.getColumnIndex(SongContractData.COLUMN_NAME)));
            String artist = cursor.getString((cursor.getColumnIndex(SongContractData.COLUMN_ARTIST)));
            String styleOfMusic = cursor.getString((cursor.getColumnIndex(SongContractData.COLUMN_STYLE)));
            String uriAddressMediaFile = cursor.getString((cursor.getColumnIndex(SongContractData.COLUMN_URI)));

            Song song = new Song(name, artist, styleOfMusic, uriAddressMediaFile);

            songListFromDB.add(song);
        }
        cursor.close();

        Log.d(TAG, "getAllSongs: GOT FROM DB");

        return songListFromDB;
    }

    private void checkIfListForDbIsEmpty() {
        if (songListForDB.isEmpty()) {
            songListForDB.addAll(songListData.createSongListForDB(getPackageName()));
        }
    }

    private void clickAndInitRecyclerView() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        fullListButton.setOnClickListener(v -> {
            adapterRecyclerView = new MyAdapter(songListFromDB);
            recyclerView.setAdapter(adapterRecyclerView);
            getPositionElementFromRecyclerView(songListFromDB);

            Log.d(TAG, "onCreate: GET ALL SONGS");
        });
    }

    //получение элемента  из списка РВ, поменять с воид на  сонг
    public void getPositionElementFromRecyclerView(List<Song> songListFromRecyclerView) {

        adapterRecyclerView.setOnNoteClickListener(position -> {

            song = songListFromRecyclerView.get(position);
            sendBroadcastWithData();

            Log.d(TAG, "getIntentWithData: INTENT ACTIVED!" + song.getName() + song.getArtist() + song.getStyleOfMusic());

            Log.d(TAG, "onNoteClick: POSITION " + position);

        });

    }

    // инициализируем  кнопки
    private void initViews() {
        chooseByArtist = findViewById(R.id.сhoose_artist);
        chooseByStyle = findViewById(R.id.choose_style);
        fullListButton = findViewById(R.id.full_list);

        recyclerView = findViewById(R.id.song_list);
    }

    public void sendBroadcastWithData() {
        Intent intent = new Intent("by.andersen.intern.contentprovider.GET_SONG_DATA");

        intent.putExtra("songName", song.getName());
        intent.putExtra("songArtist", song.getArtist());
        intent.putExtra("songStyle", song.getStyleOfMusic());
        intent.putExtra("songUri", song.getUriAddress());

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