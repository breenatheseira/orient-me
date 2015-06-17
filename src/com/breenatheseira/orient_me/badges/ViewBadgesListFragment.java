package com.breenatheseira.orient_me.badges;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.breenatheseira.orient_me.R;

public class ViewBadgesListFragment extends Fragment {
	FragmentActivity context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	     context = (FragmentActivity) super.getActivity();

	     LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_view_badges_list, container, false);
	     TextView ins = (TextView) layout.findViewById(R.id.vblfT_instructions);
	     
	     ins.setText("Unlock the Badges below! \nCan you collect them all?");
	     
	     BadgesListFragment badgelist = (BadgesListFragment) getChildFragmentManager().findFragmentById(R.id.vblfT_badgeList);
	     badgelist.getListView();
	     
	     return layout; 
	}

}
