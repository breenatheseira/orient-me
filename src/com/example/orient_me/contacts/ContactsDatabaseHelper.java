package com.example.orient_me.contacts;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.orient_me.helpers.DatabaseHelper;
import com.example.orient_me.schedules.Schedule;

public class ContactsDatabaseHelper extends DatabaseHelper {

	public ContactsDatabaseHelper(Context context) {
		super(context);
	}
	
	public List<Contact> getAllContacts(){
		List<Contact> schedules = new ArrayList<Contact>();
		String sql = "SELECT * FROM " + TABLE_CONTACTS;

		try {
			Cursor c = rdb.rawQuery(sql, null);
	
			if (c.moveToFirst()) {
				do {
					schedules.add(setContactsFromCursor(c));
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			schedules = null;
			Log.d(this.getClass().getSimpleName(), "Error returning all Contacts: " + e.getMessage());
		}
		return schedules;
	}
	
	public int updateContact(Contact contact){
		ContentValues values = new ContentValues();
		values.put(CONTACT_NAME, contact.getName());
		values.put(CONTACT_NUMBER, contact.getNumber());
		values.put(CONTACT_IMPORTED, contact.getImported());
		
		Log.d("update values", String.valueOf(contact.getId()) + " > contact name: " + contact.getName() + ", imported: "
				+ contact.getImported());
		
		return wdb.update(TABLE_CONTACTS, values, CONTACT_ID + " = ?",
				new String[] { String.valueOf(contact.getId()) });
	}
	
	private Contact setContactsFromCursor(Cursor c){
		Contact contact = new Contact();
		
		contact.setId(String.valueOf(c.getInt(c.getColumnIndex(CONTACT_ID ))));
		contact.setName(c.getString(c.getColumnIndex(CONTACT_NAME)));
		contact.setNumber(String.valueOf(c.getInt(c.getColumnIndex(CONTACT_NUMBER))));
		contact.setImported(String.valueOf(c.getInt(c.getColumnIndex(CONTACT_IMPORTED))));
		
		return contact;
	}
}
