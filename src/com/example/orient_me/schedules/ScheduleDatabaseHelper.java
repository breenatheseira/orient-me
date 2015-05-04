package com.example.orient_me.schedules;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.orient_me.helpers.DatabaseHelper;

public class ScheduleDatabaseHelper extends DatabaseHelper {

	public ScheduleDatabaseHelper(Context context) {
		super(context);
	}
	
	public int addSchedule(Schedule schedule){
		ContentValues values = new ContentValues();
		setContentFromSchedule(schedule, values);
		
		try {
			wdb.insert(TABLE_SCHEDULES, null, values);
			return 1;
		} catch (Exception e) {
			Log.d(this.getClass().getName(), "Error Inserting Schedule: " + e.getMessage());
			
		}
		wdb.close();
		return 0;
	}
	
	public int getNewScheduleId(){
		String sql = "SELECT " + SCHEDULE_ID + " FROM " + TABLE_SCHEDULES + " ORDER BY " + SCHEDULE_ID + " DESC LIMIT 1";
		Cursor c = rdb.rawQuery(sql, null);
		
		try {
			if (c.moveToFirst()){
				return (int) c.getLong(c.getColumnIndex(SCHEDULE_ID)) + 1;
			} else {
				return 1;
			}
		} catch (Exception e) {
			Log.d("ScheduleDatabaseHelper", "Error getting last schedule id: " + e.getMessage());
		}
		return 0;		
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
		return schedules;
	}
	
	public Schedule getOneScheduleRow (String id) {
		String sql = "Select * FROM " + TABLE_SCHEDULES + " WHERE id = "
				+ id;
		Schedule schedule = new Schedule();
		Cursor c = rdb.rawQuery(sql, null);

		if (c.moveToNext()) {
			return setScheduleFromCursor(c);
		}
		return schedule;
	}
	public int updateSchedule(Schedule schedule){
		ContentValues values = new ContentValues();
		setContentFromSchedule(schedule, values);

		// updating row
		int i = wdb.update(TABLE_SCHEDULES, values, NOTE_ID + " = ?",
				new String[] { String.valueOf(schedule.getId()) });
//		Log.d("update values", i + " > doc id: " + schedule.getTitle() + ", note: "
//				+ schedule.getNotes());
		return i;
	}
}
