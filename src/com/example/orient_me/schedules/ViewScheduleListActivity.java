package com.example.orient_me.schedules;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.orient_me.R;

public class ViewScheduleListActivity extends ActionBarActivity {

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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_records_list, menu);
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
	
	
}
