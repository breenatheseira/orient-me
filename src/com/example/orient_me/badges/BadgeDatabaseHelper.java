package com.example.orient_me.badges;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.orient_me.helpers.DatabaseHelper;

public class BadgeDatabaseHelper extends DatabaseHelper {

	public BadgeDatabaseHelper(Context context) {
		super(context);
	}

	private Badge setBadgesFromCursor(Cursor c){
		Badge badge = new Badge();
		
		badge.setId(c.getString(c.getColumnIndex(BADGE_ID)));
		badge.setName(c.getString(c.getColumnIndex(BADGE_NAME)));
		badge.setDesc(c.getString(c.getColumnIndex(BADGE_DESC)));
		badge.setPicture(c.getString(c.getColumnIndex(BADGE_PICTURE)));
		badge.setUnlocked_at(c.getString(c.getColumnIndex(BADGE_UNLOCKED_AT)));
		
		return badge;
	}
	
	public Badge getOneBadgeRow (String id) {
		String sql = "Select * FROM " + TABLE_BADGES + " WHERE id = "
				+ id;
		Badge badge = new Badge();
		Cursor c = rdb.rawQuery(sql, null);

		if (c.moveToNext()) {
			 return setBadgesFromCursor(c);
		}
		return badge;
	}
	
	public List<Badge> getAllBadges(){
		List<Badge> badges = new ArrayList<Badge>();
		String sql = "SELECT * FROM " + TABLE_BADGES;
		
		try {
			Cursor c = rdb.rawQuery(sql, null);
	
			if (c.moveToFirst()) {
				do {
					badges.add(setBadgesFromCursor(c));
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			badges = null;
			Log.d(this.getClass().getSimpleName(), "Error returning all Badges: " + e.getMessage());
		}
		
		return badges;
	}
	
	public int updateBadge(Badge badge){
		ContentValues values = new ContentValues();
		values.put(BADGE_UNLOCKED_AT, badge.getUnlocked_at());

		// updating row
		int i = wdb.update(TABLE_BADGES, values, BADGE_ID + " = ?",
				new String[] { String.valueOf(badge.getId()) });
		Log.d("update values", i + " > badge id: " + badge.getId() + ", unlocked at: "
				+ badge.getUnlocked_at());
		return i;
	}
}
