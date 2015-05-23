package com.example.orient_me.contacts;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orient_me.R;

public class ContactsListAdapter extends ArrayAdapter<Contact>{
	
	Context context;
	List<Contact> contacts;
	int position;
		
	public ContactsListAdapter(Context context, int resource, List<Contact> contacts) {
		super(context, resource, contacts);
		this.context = context;
		this.contacts = contacts;
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contactListItem = inflater.inflate(R.layout.custom_contacts_list,
				parent, false);
		
		Contact contact = new Contact();
		contact = contacts.get(position);
		
		TextView name = (TextView) contactListItem.findViewById(R.id.cclT_name);
		ImageView imported = (ImageView) contactListItem.findViewById(R.id.cclIV_import);
		
		name.setText(contact.getName());
		
		// Arlan (2011) Click ImageView Within a ListView ListItem and Get the Position? [Online]. Available from: http://stackoverflow.com/questions/8571166/click-imageview-within-a-listview-listitem-and-get-the-position [Accessed: 23 May 2014].
		imported.setTag(contact);
		
		
		if ("1".equals(contact.getImported())) {
			imported.setImageResource(R.drawable.ic_action_accept);
		} else {
			imported.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					
					Contact innerContact = (Contact) v.getTag();
					ContactsProviderHelper cph = new ContactsProviderHelper(innerContact);
					cph.insertDataToContactsContractTable(context);
					
					if (cph.applyBatchInsertOperations(context) == 1) {
						Toast.makeText(context, "Error inserting contact!", Toast.LENGTH_SHORT).show();
					} else {
						innerContact.setImported("1");
						new ContactsDatabaseHelper(context).updateContact(innerContact);
						((ImageView) v).setImageResource(R.drawable.ic_action_accept);
					}
				}
			});
		}
		
		return contactListItem;
	}
}
