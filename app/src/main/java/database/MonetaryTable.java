package database;

import android.database.sqlite.SQLiteDatabase;

public class MonetaryTable {

	public static final String TABLE_MONETARY = "monetary";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CATEGORY = "category";
	public static final String COLUMN_AMOUNT = "amount";
	public static final String COLUMN_NOTES = "notes";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_RULE = "rule";
	public static final String COLUMN_TYPE="type";
	public static final String COLUMN_STATUS="status";

	private static String DATABASE_CREATE = "create table " + TABLE_MONETARY
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_CATEGORY + " text not null," + COLUMN_AMOUNT
			+ " real not null," + COLUMN_NOTES + " text not null," + COLUMN_DATE
			+ " DATETIME DEFAULT CURRENT_TIMESTAMP," + COLUMN_RULE + " text not null," +COLUMN_TYPE
			+" text not null,"+COLUMN_STATUS+" smallint not null default 0 "+");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {

		database.execSQL("DROP TABLE IF EXISTS " + TABLE_MONETARY);
		onCreate(database);
	}
}
