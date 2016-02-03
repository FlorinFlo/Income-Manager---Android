package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MonetaryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "monetarytable.db";

    private static final int DATABASE_VERSION = 1;

    public MonetaryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        MonetaryTable.onCreate(database);
        CategoryTable.onCreate(database);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        MonetaryTable.onUpgrade(database, oldVersion, newVersion);
        CategoryTable.onUpgrade(database, oldVersion, newVersion);
    }


}
