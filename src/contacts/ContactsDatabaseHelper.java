package contacts;

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
	
	public void onCreate(){
		try {
			wdb.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('1','Daddy','123412123','0')");
			wdb.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('2','Mummy','123121233','0')");
			wdb.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('3','Pappy','123123114','0')");
	
			wdb.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('4','Granny','98323423','0')");
			wdb.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('5','Sis-ty','23223321','0')");
			wdb.execSQL("INSERT INTO " + TABLE_CONTACTS + " VALUES ('6','Bro-ey','0123451234','0')");
			Log.d("CDH", "Contacts inserted successfully");
		} catch (Exception e){
			Log.d("CDH", "Error @ Contacts inserted: " + e.getMessage());
		}
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
	
	private Contact setContactsFromCursor(Cursor c){
		Contact contact = new Contact();
		
		contact.setId(String.valueOf(c.getInt(c.getColumnIndex(CONTACT_ID ))));
		contact.setName(c.getString(c.getColumnIndex(CONTACT_NAME)));
		contact.setNumber(String.valueOf(c.getInt(c.getColumnIndex(CONTACT_NUMBER))));
		contact.setImported(String.valueOf(c.getInt(c.getColumnIndex(CONTACT_IMPORTED))));
		
		return contact;
	}
}
