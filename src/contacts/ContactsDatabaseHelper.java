package contacts;

import android.content.Context;
import android.util.Log;

import com.example.orient_me.helpers.DatabaseHelper;

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
	
	
}
