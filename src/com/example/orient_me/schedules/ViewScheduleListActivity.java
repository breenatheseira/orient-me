package com.example.orient_me.schedules;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_records_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.add) {
			Intent intent = new Intent(this, AddScheduleActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
