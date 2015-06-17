package com.breenatheseira.orient_me.notes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.breenatheseira.orient_me.helpers.DatabaseHelper;

public class NoteDatabaseHelper extends DatabaseHelper {

	public NoteDatabaseHelper(Context context) {
		super(context);
	}

	public void addNote(Note note) {
		ContentValues values = new ContentValues();
		values.put(NOTE_ID , note.getId());
		values.put(TITLE, note.getTitle());
		values.put(NOTE, note.getNote());
		values.put(NOTE_TIME, Calendar.getInstance().getTimeInMillis());

		// insert row
		SQLiteDatabase wdb = this.getWritableDatabase();
		wdb.insert(TABLE_NOTES, null, values);
		wdb.close();
	}

	public int getLastNoteId() {
		String sql = "SELECT " + NOTE_ID + " FROM " + TABLE_NOTES;
		int id = -1;
		SQLiteDatabase rdb = this.getReadableDatabase();
		try {
			Cursor c = rdb.rawQuery(sql, null);

			if (c.moveToLast()) {
				id = Integer.parseInt(c.getString(0));
				rdb.close();
				return id;
			}
			Log.d("Get Last ID", "Success");
		} catch (Exception e) {
			Log.d("Get Last ID", e.toString());
		}
		rdb.close();
		return id;
	}

	// Tamada, R. (2013) Android SQLite Database with Multiple Tables. [Online].
	// Available from:
	// http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
	// [Accessed: 1 May 2015].
	public List<Note> getAllNote() {
		List<Note> notes = new ArrayList<Note>();
		String sql = "SELECT * FROM " + TABLE_NOTES + " ORDER BY " + NOTE_TIME + " DESC";
		
		SQLiteDatabase rdb = this.getReadableDatabase();
		Cursor c = rdb.rawQuery(sql, null);

		if (c.moveToFirst()) {
			do {
				Note note = new Note();
				note.setId(c.getString(c.getColumnIndex(NOTE_ID )));
				note.setTitle(c.getString(c.getColumnIndex(TITLE)));
				note.setNote(c.getString(c.getColumnIndex(NOTE)));

				notes.add(note);
			} while (c.moveToNext());
		}
		rdb.close();
		return notes;
	}

	public String getOneNoteRow(String key, String id) {
		String value = null;
		String sql = "Select " + key + " FROM " + TABLE_NOTES + " WHERE id = "
				+ id;
		SQLiteDatabase rdb = this.getReadableDatabase();
		Cursor c = rdb.rawQuery(sql, null);

		if (c.moveToNext()) {
			value = c.getString(0);
		}
		rdb.close();
		return value;
	}

	public int updateNote(Note note) {
		ContentValues values = new ContentValues();
		values.put(TITLE, note.getTitle());
		values.put(NOTE, note.getNote());
		values.put(NOTE_TIME, Calendar.getInstance().getTimeInMillis());
		
		SQLiteDatabase wdb = this.getWritableDatabase();
		// updating row
		int i = wdb.update(TABLE_NOTES, values, NOTE_ID + " = ?",
				new String[] { String.valueOf(note.getId()) });
		Log.d("update values", i + " > doc id: " + note.getTitle() + ", note: "
				+ note.getNote());
		wdb.close();
		return i;
	}

	public int deleteNote(String id) {
		SQLiteDatabase wdb = this.getWritableDatabase();
		int i = wdb.delete(TABLE_NOTES, NOTE_ID + " = ?",new String[] { String.valueOf(id) });
		wdb.close();
		return i;
	}
}

