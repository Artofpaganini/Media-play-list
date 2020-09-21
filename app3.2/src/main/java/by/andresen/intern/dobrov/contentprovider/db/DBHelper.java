package by.andresen.intern.dobrov.contentprovider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;


public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "songs1.db";

    public DBHelper(@NonNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    //первое создание бд
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SongContractData.SQL_CREATE_ENTRIES);

    }

    //изменение  к примеру версии бд
    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SongContractData.SQL_DELETE_ENTITIES);

        onCreate(db);
    }

}
