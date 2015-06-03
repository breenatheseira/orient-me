package com.example.orient_me.contacts;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.orient_me.helpers.DatabaseHelper;

public class ContactsDatabaseHelper extends DatabaseHelper {

	public ContactsDatabaseHelper(Context context) {
		super(context);
	}
	
	public List<Contact> getAllContacts(){
		List<Contact> contacts = new ArrayList<Contact>();
		String sql = "SELECT * FROM " + TABLE_CONTACTS;
		SQLiteDatabase rdb = this.getReadableDatabase();
		try {
			Cursor c = rdb.rawQuery(sql, null);
	
			if (c.moveToFirst()) {
				do {
					contacts.add(setContactsFromCursor(c));
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			contacts = null;
			Log.d(this.getClass().getSimpleName(), "Error returning all Contacts: " + e.getMessage());
		}
		rdb.close();
		return contacts;
	}
	
	public int updateContact(Contact contact){
		ContentValues values = new ContentValues();
		values.put(CONTACT_NAME, contact.getName());
		values.put(CONTACT_NUMBER, contact.getNumber());
		values.put(CONTACT_IMPORTED, contact.getImported());
		int error = 0;
		Log.d("update values", String.valueOf(contact.getId()) + " > contact name: " + contact.getName() + ", imported: "
				+ contact.getImported());
		SQLiteDatabase wdb = this.getWritableDatabase();
		error = wdb.update(TABLE_CONTACTS, values, CONTACT_ID + " = ?",
				new String[] { String.valueOf(contact.getId()) });
		wdb.close();
		return error;
	}
	
	private Contact setContactsFromCursor(Cursor c){
		Contact contact = new Contact();
		
		contact.setId(String.valueOf(c.getInt(c.getColumnIndex(CONTACT_ID ))));
		contact.setName(c.getString(c.getColumnIndex(CONTACT_NAME)));
		contact.setNumber(c.getString(c.getColumnIndex(CONTACT_NUMBER)));
		contact.setCompany(c.getString(c.getColumnIndex(CONTACT_COMPANY)));
		contact.setTitle(c.getString(c.getColumnIndex(CONTACT_TITLE)));
		contact.setEmail(c.getString(c.getColumnIndex(CONTACT_EMAIL)));
		contact.setImported(String.valueOf(c.getInt(c.getColumnIndex(CONTACT_IMPORTED))));
		
		return contact;
	}
}
