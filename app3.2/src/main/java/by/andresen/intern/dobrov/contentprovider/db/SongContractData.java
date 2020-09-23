package by.andresen.intern.dobrov.contentprovider.db;

import android.provider.BaseColumns;

public class SongContractData implements BaseColumns {

    public static final String TABLE_SONGS = "songs";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ARTIST = "artist";
    public static final String COLUMN_STYLE = "style";
    public static final String COLUMN_URI = "uri";

    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_INTEGER = "INTEGER";

    static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " +
            TABLE_SONGS + "(" +
            COLUMN_NAME + " " + TYPE_TEXT + " PRIMARY KEY, " +
            COLUMN_ARTIST + " " + TYPE_TEXT + ", " +
            COLUMN_STYLE + " " + TYPE_TEXT + ", " +
            COLUMN_URI + " " + TYPE_TEXT + "" +
            ")";

    static final String SQL_DELETE_ENTITIES = "DROP TABLE IF EXIST "
            + TABLE_SONGS;
}

