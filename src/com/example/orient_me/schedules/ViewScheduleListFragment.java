package com.example.orient_me.schedules;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

public class ViewScheduleListFragment extends Fragment implements OnClickListener {

	ListView scheduleList;
	LinearLayout emptyLayout;
	List<Schedule> schedules;
	Button addSchedule;
	FragmentActivity context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	     context = (FragmentActivity) super.getActivity();

	    LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_view_schedule_list, container, false);
	    
		scheduleList = (ListView) layout.findViewById(R.id.vnsLV_listview);
		emptyLayout = (LinearLayout) layout.findViewById(R.id.vnslLL_emptyList);
		addSchedule = (Button) layout.findViewById(R.id.vslfB_addSchedule);
		
		addSchedule.setOnClickListener(this);
		
		loadListView();
		scheduleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long l) {
				
				String id = schedules.get(position).getId(); 
				
				Intent intent = new Intent(context, ViewScheduleActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});
	    
	    return layout ; 
	}
	
	private void loadListView(){
		ScheduleDatabaseHelper db = new ScheduleDatabaseHelper(context);
		
		if (db.getAllSchedules().isEmpty()){
			Toast.makeText(context, "Add a Schedule to View Your List", Toast.LENGTH_LONG).show();
			return;
		}
		
		schedules = db.getAllSchedules();
		if (schedules.size() == 5)
			showAchievement(5);
		
		ScheduleListAdapter adapter = new ScheduleListAdapter(context, R.layout.custom_schedules_list, schedules);
		scheduleList.setAdapter(adapter);
		scheduleList.setEmptyView(emptyLayout);
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

	@Override
	
	public void onClick(View v) {
		Intent intent = new Intent(context, AddScheduleActivity.class);
		startActivity(intent);
	}
}
