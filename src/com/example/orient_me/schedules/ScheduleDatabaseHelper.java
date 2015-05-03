package com.example.orient_me.schedules;

import android.content.ContentValues;
import android.content.Context;

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
}
