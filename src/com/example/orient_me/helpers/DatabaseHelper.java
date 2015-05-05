package com.example.orient_me.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
		// Database Version
		protected static final int DATABASE_VERSION = 1;

		// Database Name
		protected static final String DATABASE_NAME = "orientMe";
		
		// Database methods
		protected SQLiteDatabase rdb = this.getReadableDatabase();
		protected SQLiteDatabase wdb = this.getWritableDatabase();
		
		// Table Names
		protected static final String TABLE_NOTES = "notes";
		protected static final String TABLE_SCHEDULES = "schedules";
		protected static final String TABLE_CONTACTS = "contacts";
		
		// Notes Column names
		protected static final String NOTE_ID = "id";
		protected static final String TITLE = "title";
		protected static final String NOTE = "note";

		// Schedule Column names
		protected static final String SCHEDULE_ID = "id";
		protected static final String SCHEDULE_TITLE = "title";
		protected static final String SCHEDULE_NOTES = "note";
		protected static final String SCHEDULE_START = "startTime";
		protected static final String SCHEDULE_END = "endTime";
		protected static final String SCHEDULE_LOCATION = "location";
		protected static final String SCHEDULE_ALERT = "alert";
		protected static final String SCHEDULE_TRANSPARENT = "transparent";
		
		// Contact Column Names
		protected static final String CONTACT_ID = "id";
		protected static final String CONTACT_NAME = "name";
		protected static final String CONTACT_NUMBER = "c_number";
		protected static final String CONTACT_IMPORTED = "imported";
		
		// Table Create Statements
			// Note table create statement
			private static final String CREATE_TABLE_NOTES = "CREATE TABLE "
					+ TABLE_NOTES + "(" + NOTE_ID + " INTEGER PRIMARY KEY,"
					+ TITLE + " TEXT," + NOTE + " TEXT" + ")";
			
			// Schedule table create statement
			private static final String CREATE_TABLE_SCHEDULES = "CREATE TABLE "
					+ TABLE_SCHEDULES + "("
					+ SCHEDULE_ID + " INTEGER PRIMARY KEY,"
					+ SCHEDULE_TITLE + " TEXT,"
					+ SCHEDULE_START + " DATETIME,"
					+ SCHEDULE_END + " DATETIME,"
					+ SCHEDULE_LOCATION + " TEXT,"
					+ SCHEDULE_ALERT + " INTEGER,"
					+ SCHEDULE_NOTES + " TEXT,"
					+ SCHEDULE_TRANSPARENT + " INTEGER" + ")";
			
			// Contact table create statement
			private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE "
					+ TABLE_CONTACTS + "("
					+ CONTACT_ID + " INTEGER PRIMARY KEY,"
					+ CONTACT_NAME + " TEXT,"
					+ CONTACT_NUMBER + " TEXT,"
					+ CONTACT_IMPORTED + " INTEGER" + ")";
			
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// creating required tables
			db.execSQL(CREATE_TABLE_NOTES);
			db.execSQL(CREATE_TABLE_SCHEDULES);
			db.execSQL(CREATE_TABLE_CONTACTS);
			
			initContacts(db);
		}

		private void initContacts(SQLiteDatabase db){
			try {
				db.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('1','Daddy','123412123','0')");
				db.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('2','Mummy','123121233','0')");
				db.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('3','Pappy','123123114','0')");
		
				db.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('4','Granny','98323423','0')");
				db.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('5','Sis-ty','23223321','0')");
				db.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('6','Bro-ey','0123451234','0')");
				Log.d("CDH", "Contacts inserted successfully");
			} catch (Exception e){
				Log.d("CDH", "Error @ Contacts inserted: " + e.getMessage());
			}	
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// on upgrade drop older tables
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULES);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

			// create new tables
			onCreate(db);
		}

		public void closeDB() {
			SQLiteDatabase db = this.getReadableDatabase();
			if (db != null && db.isOpen())
				db.close();
		}
}
