package com.example.orient_me.schedules;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.orient_me.helpers.DatabaseHelper;

public class ScheduleDatabaseHelper extends DatabaseHelper {

	public ScheduleDatabaseHelper(Context context) {
		super(context);
	}
	
	public void addSchedule(Schedule schedule){
		ContentValues values = new ContentValues();
		values.put(SCHEDULE_ID, schedule.getId());
		values.put(SCHEDULE_TITLE, schedule.getTitle());
		values.put(SCHEDULE_START, schedule.getStart());
		values.put(SCHEDULE_END, schedule.getEnd());
		values.put(SCHEDULE_LOCATION, schedule.getLocation());
		values.put(SCHEDULE_ALERT, schedule.getAlert());
		values.put(SCHEDULE_NOTES, schedule.getNotes());
		values.put(SCHEDULE_TRANSPARENT, schedule.getTransparent());
		
		wdb.insert(TABLE_SCHEDULES, null, values);
		wdb.close();
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
}
