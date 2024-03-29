package com.breenatheseira.orient_me.schedules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.breenatheseira.orient_me.R;

public class ViewScheduleListFragment extends Fragment implements OnClickListener {
	FragmentActivity context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	     context = (FragmentActivity) super.getActivity();

	     LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_view_schedule_list, container, false);
	     Button addButton = (Button) layout.findViewById(R.id.vslfB_addSchedule);
	     
	     ScheduleListFragment scheduleList = (ScheduleListFragment) getChildFragmentManager().findFragmentById(R.id.vslfB_scheduleList);
         scheduleList.getListView();
         scheduleList.setListShownNoAnimation(true);
	     addButton.setOnClickListener(this);
	     
	     return layout; 
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(context, AddScheduleActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) { 
	    super.setUserVisibleHint(isVisibleToUser);
	    context = (FragmentActivity) super.getActivity();
	    ScheduleDatabaseHelper sdb = new ScheduleDatabaseHelper(context);
	    
	    if (isVisibleToUser && sdb.getAllSchedules().isEmpty()) { 
	    	Toast.makeText(context, "Add a Schedule to View Your Schedule List", Toast.LENGTH_SHORT).show();
	        Log.d("VSLF", "this fragment is now visible");
	    }
	}
}
