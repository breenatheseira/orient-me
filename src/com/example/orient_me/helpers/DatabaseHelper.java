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
		
		// Table Names
		protected static final String TABLE_NOTES = "notes";
		protected static final String TABLE_SCHEDULES = "schedules";
		protected static final String TABLE_CONTACTS = "contacts";
		protected static final String TABLE_PLACES = "places";
		protected static final String TABLE_BADGES = "badges";
		
		// Notes Column names
		protected static final String NOTE_ID = "id";
		protected static final String TITLE = "title";
		protected static final String NOTE = "note";
		protected static final String NOTE_TIME = "note_time";

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
		protected static final String CONTACT_COMPANY = "c_company";
		protected static final String CONTACT_TITLE = "c_title";
		protected static final String CONTACT_EMAIL = "c_email";
		protected static final String CONTACT_IMPORTED = "imported";
		
		// Places Column Names
		protected static final String PLACE_ID = "id";
		protected static final String PLACE_TITLE = "title";
		protected static final String PLACE_SNIPPET = "snippet";
		protected static final String PLACE_ADDRESS = "address";
		protected static final String PLACE_LAT = "lat";
		protected static final String PLACE_LNG = "lng";
		protected static final String PLACE_TYPE = "type";
		
		// Badges Column Names
		protected static final String BADGE_ID = "id";
		protected static final String BADGE_NAME = "name";
		protected static final String BADGE_DESC = "desc";
		protected static final String BADGE_PICTURE = "picture";
		protected static final String BADGE_UNLOCKED_AT = "unlocked_at";
		
		// Table Create Statements
			// Note table create statement
			private static final String CREATE_TABLE_NOTES = "CREATE TABLE "
					+ TABLE_NOTES + "(" + NOTE_ID + " INTEGER PRIMARY KEY,"
					+ TITLE + " TEXT," + NOTE + " TEXT,"
					+ NOTE_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL" + ")";
			
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
					+ CONTACT_COMPANY + " TEXT,"
					+ CONTACT_TITLE + " TEXT,"
					+ CONTACT_EMAIL + " TEXT,"
					+ CONTACT_IMPORTED + " INTEGER" + ")";
			
			// Place table create statement
			private static final String CREATE_TABLE_PLACES = "CREATE TABLE "
					+ TABLE_PLACES + " ("
					+ PLACE_ID + " INTEGER PRIMARY KEY,"
					+ PLACE_TITLE + " TEXT,"
					+ PLACE_SNIPPET + " TEXT,"
					+ PLACE_ADDRESS + " ADDRESS,"
					+ PLACE_LAT + " TEXT,"
					+ PLACE_LNG + " TEXT,"
					+ PLACE_TYPE + " TEXT " + ")";
			
			// Badge table create statement
			private static final String CREATE_TABLE_BADGES = "CREATE TABLE "
					+ TABLE_BADGES + " ("
					+ BADGE_ID + " INTEGER PRIMARY KEY,"
					+ BADGE_NAME + " TEXT,"
					+ BADGE_DESC + " TEXT,"
					+ BADGE_PICTURE + " TEXT,"
					+ BADGE_UNLOCKED_AT + " TEXT" + ")"; 
			
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// creating required tables
			db.execSQL(CREATE_TABLE_NOTES);
			db.execSQL(CREATE_TABLE_SCHEDULES);
			db.execSQL(CREATE_TABLE_CONTACTS);
			db.execSQL(CREATE_TABLE_PLACES);
			db.execSQL(CREATE_TABLE_BADGES);
			
			initContacts(db);
			initPlaces(db);
			initBadges(db);		
		}
		
		private void initBadges(SQLiteDatabase db){
			try {
				db.execSQL("INSERT INTO " + TABLE_BADGES + " VALUES ('1','Handbook Discovery','Found Student Handbook. Make sure to read it ;)','badge_1','')");
				db.execSQL("INSERT INTO " + TABLE_BADGES + " VALUES ('2','Resourceful Dialler','Obtained important contacts!','badge_2','')");
				db.execSQL("INSERT INTO " + TABLE_BADGES + " VALUES ('3','Never Alone','Be My Friend!','badge_3','')");
				db.execSQL("INSERT INTO " + TABLE_BADGES + " VALUES ('4','Better Nerd Than Dead','Opened more than 2 documents consecutively! Open one more...','badge_4','')");
				db.execSQL("INSERT INTO " + TABLE_BADGES + " VALUES ('5','Always Early!','Scheduled 5 Events','badge_5','')");
				db.execSQL("INSERT INTO " + TABLE_BADGES + " VALUES ('6','I Take Notes','Saved 3 Notes; Saved 1 Tree','badge_6','')");
				db.execSQL("INSERT INTO " + TABLE_BADGES + " VALUES ('7','Just Checking My Environment...','Viewed Map for More Than 5 Minutes','badge_7','')");
				db.execSQL("INSERT INTO " + TABLE_BADGES + " VALUES ('8','Officially APUian!','Hello Junior! - Logged in for the first time','badge_8','')");
				Log.d("BDH", "Badges inserted successfully");
			} catch (Exception e){
				Log.d("BDH", "Error @ Badges inserted: " + e.getMessage());
			}	
		}

		private void initContacts(SQLiteDatabase db){
			try {
				db.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('1','APU Admin Services','03-8994 2880','Asia Pacific University (APU)','APU Service Office General Line','admin@apu.edu.my','0')");
				db.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('2','Ms Nancy Quah','03-8992 5009','Asia Pacific University (APU)','Student Relations Manager','nancy.quah@apu.edu.my','0')");
				db.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('3','Ms Nurjahan Binti Othman','03-8992 5072','Asia Pacific University (APU)','Foundation Programme Leader','nurjahan@apu.edu.my','0')");
				db.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('4','Ms Namita','03-8992 5159','Asia Pacific University (APU)','APU TPM Campus Central Library','namita@apu.edu.my','0')");
				db.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('5','A.P.U (Night) Hotline','017-2381300','Asia Pacific University (APU)','APU Emergency','0','0')");
				db.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('6','A.P.U (Off day) Hotline','017-3791700','Asia Pacific University (APU)','APU Emergency','0','0')");
				Log.d("CDH", "Contacts inserted successfully");
			} catch (Exception e){
				Log.d("CDH", "Error @ Contacts inserted: " + e.getMessage());
			}	
		}
		
		private void initPlaces(SQLiteDatabase db){
			try {
				db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('1','APU Main Campus', 'Main Campus of APU in Malaysia','Asia Pacific University of Technology & Innovation (APU)', '3.048050', '101.692782', 'U')");
				db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('2','ENT 3', 'Enterprise 3','0', '3.048133','101.690647', 'U')");
				db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('3', 'Vista A', 'Student Accommodation', 'Vista Komenwel A', '3.0556863', '101.684131', 'A')");
				db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('4', 'Vista B', 'Student Accommodation', 'Vista Komenwel B', '3.0541981', '101.6865875', 'A')");
				db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('5', 'Endah Promenade', 'Student Accommodation', 'Endah Promenade', '3.063308', '101.696935', 'A')");
				db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('6', 'Student Car Park', 'Car Park East 4', '0', '3.044908', '101.691508', 'P')");
				db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('7', 'Parking Office', 'Office to Pay Monthly for Car Park East 4', 'Technology Park Malaysia Corporation Sdn Bhd', '3.04674', '101.68852', 'P')");
				db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('8', 'Incubator 2', 'Lecturer Office', '0', '3.047167', '101.690328', 'U')");
				db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('9', 'Mines Campus', 'APU Mines Campus in Malaysia', '0', '3.038023', '101.709364', 'U')");
				db.execSQL("INSERT INTO " + TABLE_PLACES + " VALUES ('10', 'APU Examination Hall', 'South City Plaza', '0', '3.028359', '101.709357', 'U')");
				Log.d("PDH", "Places inserted successfully");
			} catch (Exception e){
				Log.d("PDH", "Error @ Places inserted: " + e.getMessage());
			}	
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// on upgrade drop older tables
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULES);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_BADGES);
			// create new tables
			onCreate(db);
		}

		public void closeDB() {
			SQLiteDatabase db = this.getReadableDatabase();
			if (db != null && db.isOpen())
				db.close();
		}
}