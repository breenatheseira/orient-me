package com.example.orient_me.schedules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.orient_me.R;

public class AddScheduleActivity extends ActionBarActivity implements
		OnClickListener {

	EditText titleET, locationET, notesET, startDateTV, startTimeTV, endDateTV,
			endTimeTV;
	String title, location, notes, alert = "0";
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

		startDateTV.setOnClickListener(this);
		startTimeTV.setOnClickListener(this);
		endDateTV.setOnClickListener(this);
		endTimeTV.setOnClickListener(this);

		endCal.add(Calendar.HOUR, 1);
		db = new ScheduleDatabaseHelper(this);
		
		refreshDateLabels();
	}

	private void refreshDateLabels() {
		startDateTV.setText(df.format(startCal.getTime()));
		startTimeTV.setText(tf.format(startCal.getTime()));
		endDateTV.setText(df.format(endCal.getTime()));
		endTimeTV.setText(tf.format(endCal.getTime()));
	}
	
	private void assignSchedule(){
		title = this.titleET.getText().toString();
		location = this.locationET.getText().toString();
		notes = this.notesET.getText().toString();
		
		String start = String.valueOf(startCal.getTimeInMillis());
		String end = String.valueOf(endCal.getTimeInMillis());		
		
		schedule = new Schedule(String.valueOf(db.getNewScheduleId()), alert, "0", title, location, notes, start, end);
	}
	
	private void assignCalDates(){
		try {
			startCal.setTime(dtf.parse(startDateTV.getEditableText().toString() + " " + startTimeTV.getEditableText().toString()));
			endCal.setTime(dtf.parse(endDateTV.getEditableText().toString() + " " + endTimeTV.getEditableText().toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save_only, menu);
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
					if (db.addSchedule(schedule) == 1){
						Toast.makeText(this, "Schedule added", Toast.LENGTH_LONG).show();
						finish();
						
					} else
						Toast.makeText(this, "Error: Schedule could not be added", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(this, "Please Add a Title & Location to Your New Schedule", Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(this, "Please ensure Start Time is before End Time.", Toast.LENGTH_LONG).show();
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	// Developer (n.d.) Toggle Buttons [Online]. Available from: http://developer.android.com/guide/topics/ui/controls/togglebutton.html [Accessed: 3 May 2015].
	public void onToggleClicked(View view) {
		// Is the toggle on?
		boolean on = ((ToggleButton) view).isChecked();

		if (on) {
			alert = "1";
		} else {
			alert = "0";
		}
	}
}
