package com.example.orient_me.badges;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.orient_me.helpers.DatabaseHelper;

public class BadgeDatabaseHelper extends DatabaseHelper {

	public BadgeDatabaseHelper(Context context) {
		super(context);
	}

	public Badge getOneBadgeRow (String id) {
		String sql = "Select * FROM " + TABLE_BADGES + " WHERE id = "
				+ id;
		Badge badge = new Badge();
		Cursor c = rdb.rawQuery(sql, null);

		if (c.moveToNext()) {
			badge.setId(id);
			badge.setName(c.getString(c.getColumnIndex(BADGE_NAME)));
			badge.setDesc(c.getString(c.getColumnIndex(BADGE_DESC)));
			badge.setPicture(c.getString(c.getColumnIndex(BADGE_PICTURE)));
			badge.setUnlocked_at(c.getString(c.getColumnIndex(BADGE_UNLOCKED_AT)));
		}
		return badge;
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
