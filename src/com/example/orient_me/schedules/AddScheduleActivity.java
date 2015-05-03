package com.example.orient_me.schedules;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.example.orient_me.R;

public class AddScheduleActivity extends ActionBarActivity implements
		OnClickListener {

	EditText title, location, notes, startDateTV, startTimeTV, endDateTV,
			endTimeTV;
	ToggleButton alertButton;
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
	Calendar startCal = Calendar.getInstance();
	Calendar endCal = Calendar.getInstance();
	int alert;
	Schedule schedule;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_schedule);

		title = (EditText) findViewById(R.id.asaTB_title);
		location = (EditText) findViewById(R.id.asaTB_location);
		notes = (EditText) findViewById(R.id.asaTA_notes);
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
		refreshDateLabels();
	}

	private void refreshDateLabels() {
		startDateTV.setText(df.format(startCal.getTime()));
		startTimeTV.setText(tf.format(startCal.getTime()));
		endDateTV.setText(df.format(endCal.getTime()));
		endTimeTV.setText(tf.format(endCal.getTime()));
	}

	private void save() {
		ScheduleDatabaseHelper db = new ScheduleDatabaseHelper(this);
		long start = startCal.getTimeInMillis();
		long end = endCal.getTimeInMillis();

		schedule = new Schedule(db.getNewScheduleId(), alert, 0, title
				.getText().toString(), location.getText().toString(), notes
				.getText().toString(), start, end);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_record, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public void onToggleClicked(View view) {
		// Is the toggle on?
		boolean on = ((ToggleButton) view).isChecked();

		if (on) {
			alert = 1;
		} else {
			alert = 0;
		}
	}

}
