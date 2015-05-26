package com.example.orient_me.badges;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;

public class BadgesListFragment extends ListFragment {

	List<Badge> badges;
	BadgeDatabaseHelper db;
	FragmentActivity context;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = (FragmentActivity) super.getActivity();
		
		badges = new ArrayList<Badge>();
		db = new BadgeDatabaseHelper(context);
		
		badges = db.getAllBadges();
		setListAdapter(new BadgesListAdapter(getActivity(), badges));
	}
}
