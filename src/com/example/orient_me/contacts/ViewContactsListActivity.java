package com.example.orient_me.contacts;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orient_me.R;
import com.example.orient_me.badges.Badge;
import com.example.orient_me.badges.BadgeDatabaseHelper;

public class ViewContactsListActivity extends AppCompatActivity {
	
	ListView contactList;
	LinearLayout emptyLayout;
	List<Contact> contacts;
	ContactsDatabaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_contacts_list);
		
		contactList = (ListView) findViewById(R.id.avcLV_contact_list);
		emptyLayout = (LinearLayout) findViewById(R.id.avcLL_list_layout);

		loadListView();
		
	}

	private void loadListView() {
		db = new ContactsDatabaseHelper(this);

		//Tamada, R. (2013) Android SQLite Database with Multiple Tables. [Online]. Available from: http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/ [Accessed: 1 May 2015].
		contacts = db.getAllContacts();
		
		// Developers (n.d.) Layouts. [Online]. Available from: http://developer.android.com/guide/topics/ui/declaring-layout.html#AdapterViews [Accessed: 1 May 2015]. 		
		ContactsListAdapter adapter = new ContactsListAdapter(this, R.layout.custom_contacts_list,contacts);
		
		contactList.setAdapter(adapter);
		contactList.setEmptyView(emptyLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_only, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.add){
			for (Contact eachContact : contacts){
				ContactsProviderHelper contactsCP = new ContactsProviderHelper(eachContact);
				contactsCP.insertDataToContactsContractTable(this);
				
				if (contactsCP.applyBatchInsertOperations(this) == 1) {
					Toast.makeText(this, "Error inserting all contacts!", Toast.LENGTH_SHORT).show();
				} else {
					showAchievement(2);
				}
				
				eachContact.setImported("1");
				db.updateContact(eachContact);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostResume() {
		loadListView();
		super.onPostResume();
	}
	
    private void showAchievement(int id) {

		BadgeDatabaseHelper db = new BadgeDatabaseHelper(this);

		Badge badge = db.getOneBadgeRow(String.valueOf(id));

		if (badge.getUnlocked_at().isEmpty()) {
			badge.setUnlocked_at(badge.getTimeNow());
			Log.d("VContactsLA - Checking time format", badge.getUnlocked_at());
			db.updateBadge(badge);
			
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.customtoast,
					(ViewGroup) findViewById(R.id.toast_container));

			ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
			image.setImageResource(R.drawable.badge_2);
			TextView badgeName = (TextView) layout
					.findViewById(R.id.toast_text);
			badgeName.setText(badge.getName());

			Toast toast = new Toast(getApplicationContext());
			toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
		}
	}
}
