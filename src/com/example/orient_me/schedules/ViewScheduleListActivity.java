package com.example.orient_me.schedules;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orient_me.R;
import com.example.orient_me.badges.Badge;
import com.example.orient_me.badges.BadgeDatabaseHelper;

public class ViewScheduleListActivity extends AppCompatActivity {

	ListView scheduleList;
	LinearLayout emptyLayout;
	List<Schedule> schedules;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_schedule_list);
		
		scheduleList = (ListView) findViewById(R.id.vnsLV_listview);
		emptyLayout = (LinearLayout) findViewById(R.id.vnslLL_emptyList);
		
		loadListView();
		scheduleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long l) {
				
				String id = schedules.get(position).getId(); 
				
				Intent intent = new Intent(ViewScheduleListActivity.this, ViewScheduleActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});
	}
	
	private void loadListView(){
		ScheduleDatabaseHelper db = new ScheduleDatabaseHelper(ViewScheduleListActivity.this);
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> start = new ArrayList<String>();
		ArrayList<String> loc = new ArrayList<String>();
		
		if (!db.getAllSchedules().isEmpty()){
			schedules = db.getAllSchedules();
			for (Schedule eachSchedule : schedules){
				titles.add(eachSchedule.getTitle());
				start.add(eachSchedule.getStart());
				loc.add(eachSchedule.getLocation());
				
				if (eachSchedule.getId().equalsIgnoreCase("5"))
					showAchievement(5);
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewScheduleListActivity.this, android.R.layout.simple_list_item_1,titles);
			scheduleList.setAdapter(adapter);
			scheduleList.setEmptyView(emptyLayout);
		} else {
			Toast.makeText(this, "Add a Schedule to View Your List", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_only, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.add) {
			Intent intent = new Intent(this, AddScheduleActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostResume() {
		loadListView();
		super.onPostResume();
	}
	
	
    private void showAchievement(int id) {

		BadgeDatabaseHelper db = new BadgeDatabaseHelper(this);

		Badge badge = db.getOneBadgeRow(String.valueOf(id));

		if (badge.getUnlocked_at().isEmpty()) {
			badge.setUnlocked_at(badge.getTimeNow());
			Log.d("VScheduleListA - Checking time format", badge.getUnlocked_at());
			db.updateBadge(badge);
			
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.customtoast,
					(ViewGroup) findViewById(R.id.toast_container));

			ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
			image.setImageResource(R.drawable.ic_action_edit);
			TextView badgeName = (TextView) layout
					.findViewById(R.id.toast_text);
			badgeName.setText(badge.getName());

			Toast toast = new Toast(getApplicationContext());
			toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
		}
	}
}
