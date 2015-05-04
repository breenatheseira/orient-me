package com.example.orient_me.schedules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.orient_me.R;

public class EditScheduleActivity extends ActionBarActivity {

	EditText titleET, locationET, notesET, startDateTV, startTimeTV, endDateTV,
			endTimeTV;
	String id, title, location, notes, alert = "0";
	ToggleButton alertButton;
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
	SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	
	Calendar startCal = Calendar.getInstance();
	Calendar endCal = Calendar.getInstance();
	Schedule schedule;
	ScheduleDatabaseHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_schedule);
		
		titleET = (EditText) findViewById(R.id.asaTB_title);
		locationET = (EditText) findViewById(R.id.asaTB_location);
		notesET = (EditText) findViewById(R.id.asaTA_notes);
		startDateTV = (EditText) findViewById(R.id.asaDB_startDate);
		startTimeTV = (EditText) findViewById(R.id.asaDB_startTime);
		endDateTV = (EditText) findViewById(R.id.asaDB_endDate);
		endTimeTV = (EditText) findViewById(R.id.asaDB_endTime);
		alertButton = (ToggleButton) findViewById(R.id.asaSw_alert);
		
		db = new ScheduleDatabaseHelper(this);
		
		id = getIntent().getStringExtra("id");
		
		schedule = db.getOneScheduleRow(id);
		schedule.setId(id);
		
		titleET.setText(schedule.getTitle());
		locationET.setText(schedule.getLocation());
		notesET.setText(schedule.getNotes());
		
		startCal.setTimeInMillis(Long.parseLong(schedule.getStart()));
		endCal.setTimeInMillis(Long.parseLong(schedule.getEnd()));
		
		startDateTV.setText(df.format(startCal.getTime())); 
		startTimeTV.setText(tf.format(startCal.getTime()));
		endDateTV.setText(df.format(endCal.getTime())); 
		endTimeTV.setText(tf.format(endCal.getTime()));
		
		if (schedule.getAlert().compareTo("1") == 0)
			alertButton.setChecked(true);
		else
			alertButton.setChecked(false);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_record, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.save) {
			
			assignCalDates();
			if (startCal.compareTo(endCal) < 0){
				assignSchedule();
				// codaddict (2010) Java, Check Whether a String is not Null and not Empty? [Online]. Available from: http://stackoverflow.com/questions/3598770/java-check-whether-a-string-is-not-null-and-not-empty [Accessed: 3 May 2015].
				if (title != null && !title.equals("") && location != null && !location.equals("")){
					if (db.updateSchedule(schedule) == 1){
						Toast.makeText(this, "Your Schedule is updated.", Toast.LENGTH_LONG).show();
						
						Intent intent = new Intent(this, ViewScheduleListActivity.class);
						startActivity(intent);
						finish();
						
					} else
						Toast.makeText(this, "Error: Schedule could not be updated", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(this, "Please ensure Title & Location is not empty.", Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(this, "Please ensure Start Time is before End Time.", Toast.LENGTH_LONG).show();
			}
		}
		return super.onOptionsItemSelected(item);
	}

	public void onToggleClicked(View view) {
		// Is the toggle on?
		boolean on = ((ToggleButton) view).isChecked();

		if (on) {
			alert = "1";
		} else {
			alert = "0";
		}
	}
	
	private void assignSchedule(){
		title = this.titleET.getText().toString();
		location = this.locationET.getText().toString();
		notes = this.notesET.getText().toString();
		
		String start = String.valueOf(startCal.getTimeInMillis());
		String end = String.valueOf(endCal.getTimeInMillis());		
		
		schedule = new Schedule(id, alert, "0", title, location, notes, start, end);
	}
	
	private void assignCalDates(){
		try {
			startCal.setTime(dtf.parse(startDateTV.getEditableText().toString() + " " + startTimeTV.getEditableText().toString()));
			endCal.setTime(dtf.parse(endDateTV.getEditableText().toString() + " " + endTimeTV.getEditableText().toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
