package com.example.orient_me.schedules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.orient_me.R;

public class ViewScheduleActivity extends ActionBarActivity {

	TextView title_locTV, startTV, endTV, alertTV, notesTV;
	Schedule schedule;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_schedule);
		
		title_locTV = (TextView) findViewById(R.id.vsaTV_title_loc);
		startTV = (TextView) findViewById(R.id.vsaTV_startsAt);
		endTV = (TextView) findViewById(R.id.vsaTV_endsAt);
		alertTV = (TextView) findViewById(R.id.vsaTV_alertSetting);
		notesTV = (TextView) findViewById(R.id.vsaTV_noteContent);

		setContent();
	}
	private void setContent(){
		ScheduleDatabaseHelper db = new ScheduleDatabaseHelper(this);
		String id = String.valueOf((getIntent().getStringExtra("id")));
	
		schedule = new Schedule(id, db.getOneScheduleRow("alert", id),"0", db.getOneScheduleRow("title", id), db.getOneScheduleRow("location",id),db.getOneScheduleRow("note", id),db.getOneScheduleRow("startTime", id),db.getOneScheduleRow("endTime",id));
		title_locTV.setText(schedule.getTitle() + " @ " + schedule.getLocation());
		notesTV.setText(schedule.getNotes());
		startTV.setText(schedule.getStart());
		endTV.setText(schedule.getEnd());
		alertTV.setText(schedule.getAlert());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_record_with_edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.edit:
			Intent intent = new Intent(this,EditScheduleActivity.class);
			startActivity(intent);
			intent.putExtra("id", id);
			break;
		case R.id.discard:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
