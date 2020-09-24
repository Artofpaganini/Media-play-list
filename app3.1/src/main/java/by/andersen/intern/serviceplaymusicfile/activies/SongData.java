package by.andersen.intern.serviceplaymusicfile.activies;



import androidx.annotation.NonNull;

import java.util.Objects;

public class SongData {

    private String songName;
    private String songArtist;
    private String songStyle;
    private String songUriAddress;

    @NonNull
    public String getSongName() {
        return songName;
    }

    @NonNull
    public void setSongName(String songName) {
        this.songName = songName;
    }

    @NonNull
    public String getSongArtist() {
        return songArtist;
    }

    @NonNull
    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    @NonNull
    public String getSongStyle() {
        return songStyle;
    }

    @NonNull
    public void setSongStyle(String songStyle) {
        this.songStyle = songStyle;
    }

    @NonNull
    public String getSongUriAddress() {
        return songUriAddress;
    }

    @NonNull
    public void setSongUriAddress(String songUriAddress) {
        this.songUriAddress = songUriAddress;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongData songData = (SongData) o;
        return Objects.equals(songName, songData.songName) &&
                Objects.equals(songArtist, songData.songArtist) &&
                Objects.equals(songStyle, songData.songStyle) &&
                Objects.equals(songUriAddress, songData.songUriAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songName, songArtist, songStyle, songUriAddress);
    }
}
