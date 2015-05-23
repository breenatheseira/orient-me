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

import com.example.orient_me.R;

public class ContactsListAdapter extends ArrayAdapter<Contact>{
	
	Context context;
	List<Contact> contacts;
	
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
		
		TextView name = (TextView) contactListItem.findViewById(R.id.cclT_name);
		ImageView phone = (ImageView) contactListItem.findViewById(R.id.cclIV_import);
		
		if ("1".equals(contacts.get(position).getImported()))
			phone.setImageResource(R.drawable.ic_action_accept);
		
		name.setText(contacts.get(position).getName());
		
		return contactListItem;
	}
}
