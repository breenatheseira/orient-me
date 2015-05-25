package com.example.orient_me.maps;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orient_me.helpers.DatabaseHelper;

public class PlaceDatabaseHelper extends DatabaseHelper {

	public PlaceDatabaseHelper(Context context) {
		super(context);
	}
	
	public List<Place> getAllPlaces() {
		List<Place> places = new ArrayList<Place>();
		String sql = "SELECT * FROM " + TABLE_PLACES;
		SQLiteDatabase rdb = this.getReadableDatabase();
		Cursor c = rdb.rawQuery(sql, null);

		if (c.moveToFirst()) {
			do {
				Place place = new Place();
				place.setId(c.getString(c.getColumnIndex(PLACE_ID )));
				place.setTitle(c.getString(c.getColumnIndex(PLACE_TITLE)));
				place.setSnippet(c.getString(c.getColumnIndex(PLACE_SNIPPET)));
				place.setAddress(c.getString(c.getColumnIndex(PLACE_ADDRESS)));
				place.setLat(c.getString(c.getColumnIndex(PLACE_LAT)));
				place.setLng(c.getString(c.getColumnIndex(PLACE_LNG)));
				place.setType(c.getString(c.getColumnIndex(PLACE_TYPE)));

				places.add(place);
			} while (c.moveToNext());
		}
		rdb.close();
		return places;
	}
}
