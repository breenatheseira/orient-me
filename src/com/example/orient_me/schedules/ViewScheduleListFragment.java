package com.example.orient_me.schedules;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orient_me.R;
import com.example.orient_me.badges.Badge;
import com.example.orient_me.badges.BadgeDatabaseHelper;
import com.example.orient_me.contacts.ContactsDatabaseHelper;

public class ViewScheduleListFragment extends ListFragment {
	List<Schedule> schedules;
		
	FragmentActivity context;
	ScheduleDatabaseHelper db;
	boolean isVisible = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (FragmentActivity) super.getActivity();
        // initialize the items list
        schedules = new ArrayList<Schedule>();
        
        db = new ScheduleDatabaseHelper(context);
        schedules = db.getAllSchedules();
        
        if (schedules.size() == 5)
        	showAchievement(5);
        
		if (schedules.isEmpty() && isVisible){
			Log.d("VSLF", "display toast");
			Toast.makeText(context, "Add a Schedule to View Your List", Toast.LENGTH_SHORT).show();
			return;
		}
        // initialize and set the list adapter
		ScheduleListAdapter sla = new ScheduleListAdapter(getActivity(), schedules); 
        setListAdapter(sla);
        sla.setNotifyOnChange(true);
    }
	
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
        Schedule schedule = schedules.get(position);
        
        // do something
		Intent intent = new Intent(context, ViewScheduleActivity.class);
		intent.putExtra("id", schedule.getId());
		startActivity(intent);
    }
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    
	    isVisible = isVisibleToUser;
	    if (isVisible)
	    	setListAdapter(new ScheduleListAdapter(getActivity(), schedules));
	}
	
    private void showAchievement(int id) {

		BadgeDatabaseHelper db = new BadgeDatabaseHelper(context);

		Badge badge = db.getOneBadgeRow(String.valueOf(id));

		if (badge.getUnlocked_at().isEmpty()) {
			badge.setUnlocked_at(badge.getTimeNow());
			Log.d("VScheduleListA - Checking time format", badge.getUnlocked_at());
			db.updateBadge(badge);
			
			LayoutInflater inflater = context.getLayoutInflater();
			View layout = inflater.inflate(R.layout.customtoast,
					(ViewGroup) context.findViewById(R.id.toast_container));

			ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
			image.setImageResource(R.drawable.badge_5);
			TextView badgeName = (TextView) layout.findViewById(R.id.toast_text);
			badgeName.setText(badge.getName());

			Toast toast = new Toast(context);
			toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
		}
	}
}
