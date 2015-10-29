package contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import database.CategoryTable;
import database.MonetaryDatabaseHelper;
import database.MonetaryTable;
import model.Category;
import model.Money;

public class MyMonetaryContentProvider extends ContentProvider {

	// database
	private static SQLiteDatabase db;




	private ArrayList<Category> categoriesList = new ArrayList<Category>();
	private service.Service service_= service.Service.getInstance();

	// UriMatcher
	private static final int MONETARYS = 1;
	private static final int MONETARY_ID = 2;

	private static final int CATEGORYS = 3;
	private static final int CATEGORY_ID = 4;

	private static final String AUTHORITHY = "com.example.contentprovider";

	private static final String BASE_PATH = "money";

	private static final String BASE_PATH_CAT = "category";

	public static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITHY + "/"
			+ BASE_PATH);

	public static Uri CONTENT_URI_CAT = Uri.parse("content://" + AUTHORITHY
			+ "/" + BASE_PATH_CAT);

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/money";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/money";

	public static final String CONTENT_TYPE_CAT = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/category";

	public static final String CONTENT_ITEM_TYPE_CAT = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/category";

	public static final UriMatcher sURI_MATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);

	static {
		sURI_MATCHER.addURI(AUTHORITHY, BASE_PATH, MONETARYS);
		sURI_MATCHER.addURI(AUTHORITHY, BASE_PATH + "/#", MONETARY_ID);
		sURI_MATCHER.addURI(AUTHORITHY, BASE_PATH_CAT, CATEGORYS);
		sURI_MATCHER.addURI(AUTHORITHY, BASE_PATH_CAT + "/#", CATEGORY_ID);

	}


	@Override
	public boolean onCreate() {
		MonetaryDatabaseHelper database=new MonetaryDatabaseHelper(getContext());
		db=database.getWritableDatabase();

		return (db==null)?false:true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();


		checkColumns(projection);

		int uriType = sURI_MATCHER.match(uri);

		switch (uriType) {

		case MONETARY_ID:
			queryBuilder.appendWhere(MonetaryTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());

			break;
		case MONETARYS:
			queryBuilder.setTables(MonetaryTable.TABLE_MONETARY);

			break;

		case CATEGORY_ID:
			queryBuilder.appendWhere(CategoryTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());

			break;
		case CATEGORYS:
			queryBuilder.setTables(CategoryTable.TABLE_CATEGORY);

			break;

		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);

		}



		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);


		return cursor;
	}

	private void checkColumns(String[] projection) {
		String[] available = { MonetaryTable.COLUMN_ID,
				MonetaryTable.COLUMN_CATEGORY, MonetaryTable.COLUMN_AMOUNT,
				MonetaryTable.COLUMN_NOTES, MonetaryTable.COLUMN_DATE,
				MonetaryTable.COLUMN_RULE, MonetaryTable.COLUMN_TYPE };

		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(
					Arrays.asList(projection));
			HashSet<String> availableCollumns = new HashSet<String>(
					Arrays.asList(available));
			if (!availableCollumns.contains(requestedColumns)) {
				throw new IllegalArgumentException(
						"Unknown columns in projection");
			}

		}

	}

	@Override
	public String getType(Uri uri) {

		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURI_MATCHER.match(uri);
		Uri doneUri;



		int rowsDeleted = 0;
		long id = 0;

		switch (uriType) {
		case MONETARYS:

			id = db.insert(MonetaryTable.TABLE_MONETARY, null, values);
			break;

		case CATEGORYS:

			id = db.insert(CategoryTable.TABLE_CATEGORY, null, values);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI" + uri);



		}

		getContext().getContentResolver().notifyChange(uri, null);

		if (uriType == MONETARYS) {

			doneUri = Uri.parse(BASE_PATH + "/" + id);
		} else {
			doneUri = Uri.parse(BASE_PATH_CAT + "/" + id);
		}

		return doneUri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURI_MATCHER.match(uri);

		int rowsDeleted = 0;
		switch (uriType) {
		case MONETARYS:

			rowsDeleted = db.delete(MonetaryTable.TABLE_MONETARY, selection,
					selectionArgs);
			break;

		case MONETARY_ID:
			//sqlDB = database.getWritableDatabase();
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = db.delete(MonetaryTable.TABLE_MONETARY,
						MonetaryTable.COLUMN_ID + "=" + id, null);

			} else {
				rowsDeleted = db.delete(MonetaryTable.TABLE_MONETARY,
						MonetaryTable.COLUMN_ID + "=" + id + "and" + selection,
						selectionArgs);
			}
			break;

		case CATEGORYS:

			rowsDeleted = db.delete(MonetaryTable.TABLE_MONETARY, selection,
					selectionArgs);
			break;

		case CATEGORY_ID:

			String id_cat = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = db.delete(CategoryTable.TABLE_CATEGORY,
						CategoryTable.COLUMN_ID + "=" + id_cat, null);

			} else {
				rowsDeleted = db.delete(CategoryTable.TABLE_CATEGORY,
						CategoryTable.COLUMN_ID + "=" + id_cat + "and"
								+ selection, selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI :" + uri);

		}

		getContext().getContentResolver().notifyChange(uri, null);

		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int uriType = sURI_MATCHER.match(uri);

		int rowsUpdadated = 0;
		switch (uriType) {
		case MONETARYS:

			rowsUpdadated = db.update(MonetaryTable.TABLE_MONETARY, values,
					selection, selectionArgs);

			break;
		case MONETARY_ID:

			String id = uri.getLastPathSegment();

			if (TextUtils.isEmpty(selection)) {
				rowsUpdadated = db.update(MonetaryTable.TABLE_MONETARY,
						values, MonetaryTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdadated = db.update(MonetaryTable.TABLE_MONETARY,
						values, MonetaryTable.COLUMN_ID + "=" + id + "and"
								+ selection, selectionArgs);
			}
			break;
		case CATEGORYS:

			rowsUpdadated = db.update(CategoryTable.TABLE_CATEGORY, values,
					selection, selectionArgs);
			break;

		case CATEGORY_ID:

			String id_cat = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdadated = db.update(CategoryTable.TABLE_CATEGORY,
						values, CategoryTable.COLUMN_ID + "=" + id_cat, null);
			} else {
				rowsUpdadated = db.update(CategoryTable.TABLE_CATEGORY,
						values, CategoryTable.COLUMN_ID + "=" + id_cat + "and"
								+ selection, selectionArgs);
			}
			break;

		default:

			throw new IllegalArgumentException("Unknown URI :" + uri);

		}
		getContext().getContentResolver().notifyChange(uri, null);

		return rowsUpdadated;
	}

	public boolean tableExists(String tableName) {

		Cursor cursor = null;
		boolean created = false;

		try {

			cursor = db.rawQuery("Select * FROM " + tableName, null);

			created = true;
		} catch (Exception e) {

			Log.i("Exception caught in tableExists method :", e.toString());

		}
		cursor.close();

		return created;

	}

	public void dropTable(Context context) {



		String sql = "drop table " + "category";
		try {

			db.execSQL(sql);

		} catch (SQLException e) {
			Log.w("Problem drop category ", "" + e);
		}
	}

	public void createCategoriesForFirstTime(Context context, String tablename) {

		if (this.tableExists(tablename)) {

			try {

				 String[] colorList = {"#00ffff", "#8b008b", "#add8e6", "#90ee90",
						"#d3d3d3", "#ffb6c1", "#ffffe0", "#0000ff", "#a52a2a", "#00008b",
						"#008b8b", "#a9a9a9", "#006400", "#bdb76b", "#556b2f", "#ff8c00",
						"#9932cc", "#8b0000", "#e9967a", "#9400d3", "#ff00ff", "#ffd700",
						"#008000", "#4b0082", "#f0e68c", "#00ff00", "#800000", "#000080",
						"#ffa500", "#ffc0cb", "#800080", "#ff0000", "#c0c0c0", "#ffff00"};


				String[] categoryNames = {"Other", "Food and drinks",
						"Health care", "Leisure", "Transportation", "Fuel",
						"Hotel", "Household", "Insurance", "Utilities"};

				for (int i = 0; i < categoryNames.length; i++) {
					Log.w("SERVICE", "categories size" + categoryNames.length);
					ContentValues values = new ContentValues();
					values.put(CategoryTable.COLUMN_NAME, categoryNames[i]);
					values.put(CategoryTable.COLUMN_COLOR, colorList[i]);
				Uri	uri = context.getContentResolver().insert(
							MyMonetaryContentProvider.CONTENT_URI_CAT, values);
					Log.w("Categories CREATED", "yes");
				}

			} catch (Exception e) {
				Log.w("ERRRROR", "" + e);
			}
		}

	}

	public ArrayList<Category> getCategories(Context context) {


		ArrayList<Category> listCat = new ArrayList<Category>();

		Cursor cursor = db
				.query("category", null, null, null, null, null, null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {

			long catId = Long.parseLong(cursor.getString(cursor
					.getColumnIndex(CategoryTable.COLUMN_ID)));
			String nameCat = cursor.getString(cursor
					.getColumnIndex(CategoryTable.COLUMN_NAME));
			String colorCat = (cursor.getString(cursor
					.getColumnIndex(CategoryTable.COLUMN_COLOR)));

			Category category = new Category(catId, nameCat, colorCat);
			cursor.moveToNext();

			listCat.add(category);
		}
		cursor.close();

		return listCat;

	}

	public Category getCategoryForId(long categoryId){

		Category category=null;


		Cursor cursor=db.query(CategoryTable.TABLE_CATEGORY,
				new String[]{CategoryTable.COLUMN_ID, CategoryTable.COLUMN_COLOR, CategoryTable.COLUMN_NAME},
				CategoryTable.COLUMN_ID + "=?",
				new String[]{String.valueOf(categoryId)}
				, null, null, null);
		if(cursor.moveToFirst()){
			do {

				category=new Category();

				category.setCategory_id(cursor.getLong(cursor.getColumnIndex(CategoryTable.COLUMN_ID)));

				category.setColor(cursor.getString(cursor.getColumnIndex(CategoryTable.COLUMN_COLOR)));

				category.setName(cursor.getString(cursor.getColumnIndex(CategoryTable.COLUMN_NAME)));
			}while (cursor.moveToNext());

		}if(cursor!=null && !cursor.isClosed()){
			cursor.close();
		}
		return category;
	}

	public boolean createNewCategory(String categoryName,String color,Context context ){

	try {
		ContentValues values = new ContentValues();
		values.put(CategoryTable.COLUMN_NAME, categoryName);
		values.put(CategoryTable.COLUMN_COLOR, color);
		Uri	uri = context.getContentResolver().insert(
				MyMonetaryContentProvider.CONTENT_URI_CAT, values);
		return true;
	}catch (Exception ex){
		return false;
	}


}

	public void createIncomeExpense(String amount, String notes, String date,
									long category, String rule, Context context, String type) {
		Uri uri=null;
		ContentValues values = new ContentValues();
		values.put(MonetaryTable.COLUMN_AMOUNT, amount);
		values.put(MonetaryTable.COLUMN_NOTES, notes);
		values.put(MonetaryTable.COLUMN_DATE, date);
		values.put(MonetaryTable.COLUMN_CATEGORY, category);
		values.put(MonetaryTable.COLUMN_RULE, rule);
		if (type.contains("Income")) {
			values.put(MonetaryTable.COLUMN_TYPE, "Income");
		} else {
			values.put(MonetaryTable.COLUMN_TYPE, "Expenses");
		}


			uri = context.getContentResolver().insert(
					MyMonetaryContentProvider.CONTENT_URI, values);


	}

	public void getExpenses(){
		ArrayList<Money> expenses=new ArrayList<Money>();
		Log.w("HELLO Expenses", "Monetary contetnprovicer");
		Cursor cursor = db
				.query("monetary", null, null, null, null, null, null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {


			long moneyId = Long.parseLong(cursor.getString(cursor
					.getColumnIndex(MonetaryTable.COLUMN_ID)));
			String catMoney = cursor.getString(cursor
					.getColumnIndex(MonetaryTable.COLUMN_CATEGORY));
			Double amount=(cursor.getDouble(cursor
					.getColumnIndex(MonetaryTable.COLUMN_AMOUNT)));
			String date = (cursor.getString(cursor
					.getColumnIndex(MonetaryTable.COLUMN_DATE)));



			Log.w("HELLO Expenses",""+moneyId+date+",,,,,,,,,,,"+catMoney+"...."+amount);

			cursor.moveToNext();


		}
		cursor.close();



	}

	public ArrayList<Money> getExpensesGivingMonth(int month){
		Log.w("?????????????????????","ZZZZZZZZZZZZZZZZZZ"+month);
		ArrayList<Money> expenses=new ArrayList<>();
		Cursor cursor=db.query(MonetaryTable.TABLE_MONETARY,
				new String[]{MonetaryTable.COLUMN_ID, MonetaryTable.COLUMN_CATEGORY,
						MonetaryTable.COLUMN_AMOUNT, MonetaryTable.COLUMN_NOTES,
						MonetaryTable.COLUMN_DATE, MonetaryTable.COLUMN_RULE, MonetaryTable.COLUMN_TYPE},
				"strftime('%m',date)" + "=?",
				new String[]{String.valueOf(month)}
				, null, null, null);
		if(cursor.moveToFirst()){
			do {

				Money money=new Money();
				money.setMoney_id(cursor.getLong(cursor.getColumnIndex(MonetaryTable.COLUMN_ID)));

				money.setCategory(cursor.getLong(cursor.getColumnIndex(MonetaryTable.COLUMN_CATEGORY)));

				money.setAmount(cursor.getDouble(cursor.getColumnIndex(MonetaryTable.COLUMN_AMOUNT)));

				money.setNotes(cursor.getString(cursor.getColumnIndex(MonetaryTable.COLUMN_NOTES)));

				String date=cursor.getString(cursor.getColumnIndex(MonetaryTable.COLUMN_DATE));
				money.setDate(service_.returnDateFromString(date));

				money.setRule(cursor.getString(cursor.getColumnIndex(MonetaryTable.COLUMN_RULE)));
				money.setType(cursor.getString(cursor.getColumnIndex(MonetaryTable.COLUMN_TYPE)));

				expenses.add(money);

			}while (cursor.moveToNext());

		}if(cursor!=null && !cursor.isClosed()){
			cursor.close();
		}
		return expenses;

	}





}
