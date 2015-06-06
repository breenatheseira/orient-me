package com.example.orient_me.contacts;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;

public class ContactsListFragment extends ListFragment{
	
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
}
