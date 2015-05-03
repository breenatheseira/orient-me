package com.example.orient_me.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	// Database Version
		protected static final int DATABASE_VERSION = 1;

		// Database Name
		protected static final String DATABASE_NAME = "orientMe";
		
		// Table Names
		protected static final String TABLE_NOTES = "notes";
		protected static final String TABLE_SCHEDULES = "schedules";
		
		// Notes Column names
		protected static final String KEY_NOTE_ID = "id";
		protected static final String KEY_TITLE = "title";
		protected static final String KEY_NOTE = "note";

		// Schedule Column names
		protected static final String KEY_SCHEDULE_ID = "id";
		protected static final String KEY_SCHEDULE_TITLE = "title";
		protected static final String KEY_SCHEDULE_NOTES = "note";
		protected static final String KEY_SCHEDULE_START = "startTime";
		protected static final String KEY_SCHEDULE_END = "endTime";
		protected static final String KEY_SCHEDULE_LOCATION = "location";
		protected static final String KEY_SCHEDULE_ALERT = "alert";
		protected static final String KEY_SCHEDULE_TRANSPARENT = "transparent";
		
		// Table Create Statements
			// Note table create statement
			private static final String CREATE_TABLE_NOTES = "CREATE TABLE "
					+ TABLE_NOTES + "(" + KEY_NOTE_ID + " INTEGER PRIMARY KEY,"
					+ KEY_TITLE + " TEXT," + KEY_NOTE + " TEXT" + ")";
			
			// Schedule table create statement
			private static final String CREATE_TABLE_SCHEDULES = "CREATE TABLE "
					+ TABLE_SCHEDULES + "("
					+ KEY_SCHEDULE_ID + " INTEGER PRIMARY KEY,"
					+ KEY_SCHEDULE_TITLE + " TEXT,"
					+ KEY_SCHEDULE_START + " DATETIME,"
					+ KEY_SCHEDULE_END + " DATETIME,"
					+ KEY_SCHEDULE_LOCATION + " TEXT,"
					+ KEY_SCHEDULE_ALERT + " INTEGER,"
					+ KEY_SCHEDULE_NOTES + " TEXT,"
					+ KEY_SCHEDULE_TRANSPARENT + " INTEGER" + ")";
			
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// creating required tables
			db.execSQL(CREATE_TABLE_NOTES);
			db.execSQL(CREATE_TABLE_SCHEDULES);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// on upgrade drop older tables
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULES);

			// create new tables
			onCreate(db);
		}

		public void closeDB() {
			SQLiteDatabase db = this.getReadableDatabase();
			if (db != null && db.isOpen())
				db.close();
		}
}
