package by.andresen.intern.dobrov.contentprovider.entities;


import androidx.annotation.NonNull;

public class Song {

    private String name;
    private String artist;
    private String styleOfMusic;
    private String uriAddress;


    public Song(@NonNull String name, @NonNull String artist, @NonNull String styleOfMusic, @NonNull String uriAddress) {
        this.name = name;
        this.artist = artist;
        this.styleOfMusic = styleOfMusic;
        this.uriAddress = uriAddress;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getStyleOfMusic() {
        return styleOfMusic;
    }

    public String getUriAddress() {
        return uriAddress;
    }


}
