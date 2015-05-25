package com.example.orient_me.schedules;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.orient_me.helpers.DatabaseHelper;

public class ScheduleDatabaseHelper extends DatabaseHelper {

	public ScheduleDatabaseHelper(Context context) {
		super(context);
	}
	
	public int addSchedule(Schedule schedule){
		ContentValues values = new ContentValues();
		setContentFromSchedule(schedule, values);
		SQLiteDatabase wdb = this.getWritableDatabase();
		int success = 0;
		try {
			wdb.insert(TABLE_SCHEDULES, null, values);
			success = 1;
		} catch (Exception e) {
			Log.d(this.getClass().getName(), "Error Inserting Schedule: " + e.getMessage());
			success = 0;
		}		
		wdb.close();
		return success;
	}
	
	public int getNewScheduleId(){
		String sql = "SELECT " + SCHEDULE_ID + " FROM " + TABLE_SCHEDULES + " ORDER BY " + SCHEDULE_ID + " DESC LIMIT 1";
		SQLiteDatabase rdb = this.getReadableDatabase();
		Cursor c = rdb.rawQuery(sql, null);
		int error = 0;
		try {
			if (c.moveToFirst()){
				rdb.close();
				return (int) c.getLong(c.getColumnIndex(SCHEDULE_ID)) + 1;
			} else {
				error = 1;
			}
		} catch (Exception e) {
			Log.d("ScheduleDatabaseHelper", "Error getting last schedule id: " + e.getMessage());
		}
		rdb.close();
		return error;		
	}
	
	private Schedule setScheduleFromCursor(Cursor c){
		Schedule schedule = new Schedule();
		
		schedule.setId(String.valueOf(c.getInt(c.getColumnIndex(SCHEDULE_ID ))));
		schedule.setTitle(c.getString(c.getColumnIndex(SCHEDULE_TITLE)));
		schedule.setLocation(c.getString(c.getColumnIndex(SCHEDULE_LOCATION)));
		schedule.setStart(String.valueOf(c.getLong(c.getColumnIndex(SCHEDULE_START))));
		schedule.setEnd(String.valueOf(c.getLong(c.getColumnIndex(SCHEDULE_END))));
		schedule.setNotes(c.getString(c.getColumnIndex(SCHEDULE_NOTES)));
		schedule.setAlert(String.valueOf(c.getInt(c.getColumnIndex(SCHEDULE_ALERT))));
		schedule.setTransparent(String.valueOf(c.getInt(c.getColumnIndex(SCHEDULE_TRANSPARENT))));
		
		return schedule;
	}
	private ContentValues setContentFromSchedule(Schedule schedule, ContentValues values){
		values.put(SCHEDULE_ID, Integer.parseInt(schedule.getId()));
		values.put(SCHEDULE_TITLE, schedule.getTitle());
		values.put(SCHEDULE_START, Long.parseLong(schedule.getStart()));
		values.put(SCHEDULE_END, Long.parseLong(schedule.getEnd()));
		values.put(SCHEDULE_LOCATION, schedule.getLocation());
		values.put(SCHEDULE_ALERT, Integer.parseInt(schedule.getAlert()));
		values.put(SCHEDULE_NOTES, schedule.getNotes());
		values.put(SCHEDULE_TRANSPARENT, Integer.parseInt(schedule.getTransparent()));
		
		return values;
	}
	public List<Schedule> getAllSchedules(){
		List<Schedule> schedules = new ArrayList<Schedule>();
		String sql = "SELECT * FROM " + TABLE_SCHEDULES;
		SQLiteDatabase rdb = this.getReadableDatabase();
		try {
			Cursor c = rdb.rawQuery(sql, null);
	
			if (c.moveToFirst()) {
				do {
					schedules.add(setScheduleFromCursor(c));
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			schedules = null;
			Log.d(this.getClass().getSimpleName(), "Error returning all Schedules: " + e.getMessage());
		}
		rdb.close();
		return schedules;
	}
	
	public Schedule getOneScheduleRow (String id) {
		String sql = "Select * FROM " + TABLE_SCHEDULES + " WHERE id = "
				+ id;
		Schedule schedule = new Schedule();
		SQLiteDatabase rdb = this.getReadableDatabase();
		Cursor c = rdb.rawQuery(sql, null);

		if (c.moveToNext()) {
			schedule = setScheduleFromCursor(c);
		}
		rdb.close();
		return schedule;
	}
	public int updateSchedule(Schedule schedule){
		ContentValues values = new ContentValues();
		setContentFromSchedule(schedule, values);
		SQLiteDatabase wdb = this.getWritableDatabase();
		// updating row
		int i = wdb.update(TABLE_SCHEDULES, values, NOTE_ID + " = ?",
				new String[] { String.valueOf(schedule.getId()) });
//		Log.d("update values", i + " > doc id: " + schedule.getTitle() + ", note: "
//				+ schedule.getNotes());
		wdb.close();
		return i;
	}
	public int deleteSchedule(String id){
		SQLiteDatabase wdb = this.getWritableDatabase();
		int i = wdb.delete(TABLE_SCHEDULES, SCHEDULE_ID + " = ?",new String[] { String.valueOf(id) });
		wdb.close();
		return i;
	}
}
