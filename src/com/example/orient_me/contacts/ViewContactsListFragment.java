package com.example.orient_me.contacts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.orient_me.R;

public class ViewContactsListFragment extends Fragment {
	FragmentActivity context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	     context = (FragmentActivity) super.getActivity();

	     LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_view_contact_list, container, false);
	     TextView ins = (TextView) layout.findViewById(R.id.vclfT_instructions);
	     
	     ins.setText("Touch the download icons to \nsave important APU contacts.");
	     
	     ContactsListFragment contactslist = (ContactsListFragment) getChildFragmentManager().findFragmentById(R.id.vclf_contactList);
	     contactslist.getListView();
	     
	     return layout; 
	}

}
