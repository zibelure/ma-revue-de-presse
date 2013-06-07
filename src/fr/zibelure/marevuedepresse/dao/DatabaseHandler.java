package fr.zibelure.marevuedepresse.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	public static final String SUJET_KEY = "id";
	public static final String SUJET_TITRE = "titre";

	public static final String SUJET_TABLE_NAME = "sujet";
	public static final String SUJET_TABLE_CREATE = "CREATE TABLE "
			+ SUJET_TABLE_NAME + " (" + SUJET_KEY
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUJET_TITRE + " TEXT);";

	public static final String SUJET_TABLE_DROP = "DROP TABLE IF EXISTS " + SUJET_TABLE_NAME + ";";
	
	public DatabaseHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SUJET_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SUJET_TABLE_DROP);
		onCreate(db);
	}
}