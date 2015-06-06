package com.example.orient_me.contacts;
//PerfectAPK (2014) AndroidListFragment Tutorial [Online]. Available from: http://www.perfectapk.com/android-listfragment-tutorial.html [Accessed: 23 May 2015].
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orient_me.R;
import com.example.orient_me.badges.Badge;
import com.example.orient_me.badges.BadgeDatabaseHelper;

public class ContactsListAdapter extends ArrayAdapter<Contact>{
	
	public ContactsListAdapter(Context context, List<Contact> contacts) {
		super(context, R.layout.custom_contacts_list, contacts);
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		
		if (convertView == null){
		
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.custom_contacts_list, parent, false);
			
			viewHolder = new ViewHolder(); 
			viewHolder.name = (TextView) convertView.findViewById(R.id.cclT_name);
			viewHolder.imported = (ImageView) convertView.findViewById(R.id.cclIV_import);
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Contact contact = getItem(position);
		viewHolder.contact = contact;
		viewHolder.name.setText(contact.getName());
		
		if ("1".equals(contact.getImported())) {
			viewHolder.imported.setImageResource(R.drawable.ic_action_accept);
		} else {
			viewHolder.imported.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					
					Contact innerContact = viewHolder.contact;
					ContactsProviderHelper cph = new ContactsProviderHelper(innerContact);
					cph.insertDataToContactsContractTable(getContext());
					
					if (cph.applyBatchInsertOperations(getContext()) == 1) {
						Toast.makeText(getContext(), "Error inserting contact!", Toast.LENGTH_SHORT).show();
					} else {
						innerContact.setImported("1");
						new ContactsDatabaseHelper(getContext()).updateContact(innerContact);
						((ImageView) v).setImageResource(R.drawable.ic_action_accept);
						showAchievement(2);
					}
				}
			});
		}
		return convertView;
	}
	
	@SuppressLint("InflateParams")
	private void showAchievement(int id) {

		BadgeDatabaseHelper db = new BadgeDatabaseHelper(getContext());

		Badge badge = db.getOneBadgeRow(String.valueOf(id));

		if (badge.getUnlocked_at().isEmpty()) {
			badge.setUnlocked_at(badge.getTimeNow());
			Log.d("DA - Checking time format", badge.getUnlocked_at());
			db.updateBadge(badge);
			
			
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View l = inflater.inflate(R.layout.customtoast,null);
			View layout = inflater.inflate(R.layout.customtoast, (ViewGroup) l.findViewById(R.id.toast_container));

			ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
			if (badge.getId().equals("1"))
				image.setImageResource(R.drawable.badge_1);
			else
				image.setImageResource(R.drawable.badge_4);
			TextView badgeName = (TextView) layout
					.findViewById(R.id.toast_text);
			badgeName.setText(badge.getName());

			Toast toast = new Toast(getContext());
			toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
		}
	}
	
	private static class ViewHolder {
		TextView name;
		ImageView imported;
		Contact contact;
	}
	
	
}
