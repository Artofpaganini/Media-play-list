package by.andresen.intern.dobrov.contentprovider.entities;



import android.net.Uri;
import android.util.Log;


import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import by.andresen.intern.dobrov.contentprovider.R;


public class SongListData {
    private static final String TAG = "SongListData";

    @NonNull
    public List<Song> createSongListForDB(@NotNull String packageName) {
        List<Song> songListForDb = new ArrayList<>();


        String uriSong1 = Uri.parse("android.resource://" + packageName + "/" + R.raw.song1).toString();
        String uriSong2 = Uri.parse("android.resource://" + packageName + "/" + R.raw.song2).toString();
        String uriSong3 = Uri.parse("android.resource://" + packageName + "/" + R.raw.song3).toString();
        String uriSong4 = Uri.parse("android.resource://" + packageName + "/" + R.raw.song4).toString();
        String uriSong5 = Uri.parse("android.resource://" + packageName + "/" + R.raw.song5).toString();
        String uriSong6 = Uri.parse("android.resource://" + packageName + "/" + R.raw.song6).toString();
        String uriSong7 = Uri.parse("android.resource://" + packageName + "/" + R.raw.song7).toString();
        String uriSong8 = Uri.parse("android.resource://" + packageName + "/" + R.raw.song8).toString();
        String uriSong9 = Uri.parse("android.resource://" + packageName + "/" + R.raw.song9).toString();
        String uriSong10 = Uri.parse("android.resource://" + packageName + "/" + R.raw.song10).toString();

        Song song1 = new Song("What He Wrote", "Laura Marling", "Classic", uriSong1);
        Song song2 = new Song("C'mon Billy", "PJ Harvey", "Classic", uriSong2);
        Song song3 = new Song("Catherine", "PJ Harvey", "Classic", uriSong3);
        Song song4 = new Song("Working For the Man", "PJ Harvey", "Classic", uriSong4);
        Song song5 = new Song("All My Tears", "Arctic monkeys", "Rock", uriSong5);
        Song song6 = new Song("Arabella", "Arctic monkeys", "Rock", uriSong6);
        Song song7 = new Song("Do i know", "Ane", "Classic", uriSong7);
        Song song8 = new Song("River styx", "Black Rebel", "Rock", uriSong8);
        Song song9 = new Song("Come on over", "Royal blood", "Rock", uriSong9);
        Song song10 = new Song("Fried my little brains", "The kills", "Rock", uriSong10);

        songListForDb.add(song1);
        songListForDb.add(song2);
        songListForDb.add(song3);
        songListForDb.add(song4);
        songListForDb.add(song5);
        songListForDb.add(song6);
        songListForDb.add(song7);
        songListForDb.add(song8);
        songListForDb.add(song9);
        songListForDb.add(song10);

        Log.d(TAG, "SongListData: Fill Data songs to song list was  completed");

        return songListForDb;
    }

}
