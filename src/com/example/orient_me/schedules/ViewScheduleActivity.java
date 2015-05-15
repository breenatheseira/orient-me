package com.example.orient_me.schedules;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.orient_me.R;

public class ViewScheduleActivity extends AppCompatActivity {

	TextView title_locTV, startTV, endTV, alertTV, notesTV;
	Schedule schedule;
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
	Calendar startCal = Calendar.getInstance();
	Calendar endCal = Calendar.getInstance();

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
	
		schedule = db.getOneScheduleRow(id);
		
		schedule.setId(id);
		
		title_locTV.setText(schedule.getTitle() + " @ " + schedule.getLocation());
		notesTV.setText(schedule.getNotes());
		
		startCal.setTimeInMillis(Long.parseLong(schedule.getStart()));
		endCal.setTimeInMillis(Long.parseLong(schedule.getEnd()));
		
		startTV.setText(df.format(startCal.getTime()) + " " + tf.format(startCal.getTime()));
		endTV.setText(df.format(endCal.getTime()) + " " + tf.format(endCal.getTime()));
		
		if (schedule.getAlert().compareTo("1") == 0)
			alertTV.setText("On");
		else
			alertTV.setText("Off");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_only, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.edit:
			Intent intent = new Intent(this,EditScheduleActivity.class);
			intent.putExtra("id", schedule.getId());
			startActivity(intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
