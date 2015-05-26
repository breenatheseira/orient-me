package com.example.orient_me.contacts;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orient_me.R;
import com.example.orient_me.badges.Badge;
import com.example.orient_me.badges.BadgeDatabaseHelper;

public class ContactsListFragment extends ListFragment {
	
	List<Contact> contacts;
	ContactsDatabaseHelper db;
	FragmentActivity context;
	
	// PerfectAPK (2014) AndroidListFragment Tutorial [Online]. Available from: http://www.perfectapk.com/android-listfragment-tutorial.html [Accessed: 23 May 2015].
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (FragmentActivity) super.getActivity();
        
        contacts = new ArrayList<Contact>();
        db = new ContactsDatabaseHelper(context);

		contacts = db.getAllContacts();		
		setListAdapter(new ContactsListAdapter(getActivity(), contacts));
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.add){
			for (Contact eachContact : contacts){
				ContactsProviderHelper contactsCP = new ContactsProviderHelper(eachContact);
				contactsCP.insertDataToContactsContractTable(context);
				
				if (contactsCP.applyBatchInsertOperations(context) == 1) {
					Toast.makeText(context, "Error inserting all contacts!", Toast.LENGTH_SHORT).show();
				} else {
					showAchievement(2);
				}
				
				eachContact.setImported("1");
				db.updateContact(eachContact);
			}
			setListAdapter(new ContactsListAdapter(getActivity(), contacts));
		}
		return super.onOptionsItemSelected(item);
	}

    private void showAchievement(int id) {

		BadgeDatabaseHelper db = new BadgeDatabaseHelper(context);

		Badge badge = db.getOneBadgeRow(String.valueOf(id));

		if (badge.getUnlocked_at().isEmpty()) {
			badge.setUnlocked_at(badge.getTimeNow());
			Log.d("VContactsLA - Checking time format", badge.getUnlocked_at());
			db.updateBadge(badge);
			
			LayoutInflater inflater = context.getLayoutInflater();
			View layout = inflater.inflate(R.layout.customtoast,
					(ViewGroup) context.findViewById(R.id.toast_container));

			ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
			image.setImageResource(R.drawable.badge_2);
			TextView badgeName = (TextView) layout
					.findViewById(R.id.toast_text);
			badgeName.setText(badge.getName());

			Toast toast = new Toast(context);
			toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
		}
	}
}
