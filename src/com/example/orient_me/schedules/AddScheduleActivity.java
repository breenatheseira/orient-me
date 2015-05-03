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
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.orient_me.R;

public class AddScheduleActivity extends ActionBarActivity implements OnClickListener {

	EditText title, location, notes;
	TextView startDateTV, startTimeTV, endDateTV, endTimeTV;
	ToggleButton alert;
	SimpleDateFormat df, tf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_schedule);
		
		title = (EditText) findViewById(R.id.asaTB_title);
		location = (EditText) findViewById(R.id.asaTB_location);
		notes = (EditText) findViewById(R.id.asaTA_notes);
		startDateTV = (TextView) findViewById(R.id.asaDB_startDate);
		startTimeTV = (TextView) findViewById(R.id.asaDB_startTime);
		endDateTV = (TextView) findViewById(R.id.asaDB_endDate);
		endTimeTV = (TextView) findViewById(R.id.asaDB_endTime);
		alert = (ToggleButton) findViewById(R.id.asaSw_alert);
		
		setDateTime();
		setOnClickListeners();
	}
	
	private void setDateTime(){
		Calendar cal = Calendar.getInstance();
		df = new SimpleDateFormat("dd-MM-yyyy");
		tf = new SimpleDateFormat("hh:mm a");
		
		startDateTV.setText(df.format(cal.getTime()));
		startTimeTV.setText(tf.format(cal.getTime()));
		
		cal.add(Calendar.HOUR, 1);
		endDateTV.setText(df.format(cal.getTime()));
		endTimeTV.setText(tf.format(cal.getTime()));
	}

	private void setOnClickListeners(){
		startDateTV.setOnClickListener(this);
		startTimeTV.setOnClickListener(this);
		endDateTV.setOnClickListener(this);
		endTimeTV.setOnClickListener(this);
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
		switch(v.getId()){
		case R.id.asaDB_startDate:
			
			break;
		case R.id.asaDB_startTime:
			break;
		case R.id.asaDB_endDate:
			break;
		case R.id.asaDB_endTime:
			break;
		}
	}
}
