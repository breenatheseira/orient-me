package com.breenatheseira.orient_me.schedules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.breenatheseira.orient_me.R;

@SuppressLint("SimpleDateFormat")
public class AddScheduleActivity extends AppCompatActivity implements
		OnClickListener {

	EditText titleET, locationET, notesET;
	TextView startDateTV, startTimeTV, endDateTV, endTimeTV;
	String title, location, notes, alert = "0", tempDate;
	Switch alertButton;
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
	SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	Calendar startCal = Calendar.getInstance();
	Calendar endCal = Calendar.getInstance();
	Schedule schedule;
	ScheduleDatabaseHelper db;

	DatePickerDialog dateDialog;
	TimePickerDialog timeDialog;
	boolean startDate, startTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_schedule);

		Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
		setSupportActionBar(toolbar);
		
		titleET = (EditText) findViewById(R.id.asaTB_title);
		locationET = (EditText) findViewById(R.id.asaTB_location);
		notesET = (EditText) findViewById(R.id.asaTA_notes);
		startDateTV = (TextView) findViewById(R.id.asaTV_startDate);
		startTimeTV = (TextView) findViewById(R.id.asaTV_startTime);
		endDateTV = (TextView) findViewById(R.id.asaTV_endDate);
		endTimeTV = (TextView) findViewById(R.id.asaTV_endTime);
		alertButton = (Switch) findViewById(R.id.asaSw_alert);

		dateDialog = new DatePickerDialog(this,datePickerListener, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
		timeDialog = new TimePickerDialog(this, timePickerListener, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);
		
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
			startCal.setTime(dtf.parse(startDateTV.getText().toString() + " " + startTimeTV.getText().toString()));
			endCal.setTime(dtf.parse(endDateTV.getText().toString() + " " + endTimeTV.getText().toString()));
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

	@SuppressLint("NewApi")
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

						Intent intent = new Intent(Intent.ACTION_EDIT);
						intent.setData(CalendarContract.Events.CONTENT_URI);
						intent.putExtra("beginTime", startCal.getTimeInMillis());
						intent.putExtra("endTime", endCal.getTimeInMillis());
						intent.putExtra("title", schedule.getTitle());
						intent.putExtra("eventLocation", schedule.getLocation());
						intent.putExtra("description", schedule.getNotes());
						intent.putExtra("alarm", schedule.getAlert());
						startActivity(intent);
						
						Toast.makeText(this, "Export and save your schedule in your calendar", Toast.LENGTH_LONG).show();
						
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

	// Developer (n.d.) Toggle Buttons [Online]. Available from: http://developer.android.com/guide/topics/ui/controls/togglebutton.html [Accessed: 3 May 2015].
	public void onSwitchClicked(View view) {
		// Is the toggle on?
		boolean on = ((Switch) view).isChecked();

		if (on) {
			alert = "1";
		} else {
			alert = "0";
		}
	}

	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.asaTV_startDate:
			startDate = true;
			dateDialog.updateDate(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DAY_OF_MONTH));
			dateDialog.show();
			break;
		case R.id.asaTV_endDate:
			startDate = false;
			dateDialog.updateDate(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH), endCal.get(Calendar.DAY_OF_MONTH));
			dateDialog.show();
			break;
		case R.id.asaTV_startTime:
			startTime = true;
			timeDialog.updateTime(startCal.get(Calendar.HOUR_OF_DAY), startCal.get(Calendar.MINUTE));
			timeDialog.show();
			break;
		case R.id.asaTV_endTime:
			startTime = false;
			timeDialog.updateTime(endCal.get(Calendar.HOUR_OF_DAY), endCal.get(Calendar.MINUTE));
			timeDialog.show();
			break;
		}
	}
	
	// Dialog boxes for setting Date and Time
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			Calendar sDate = Calendar.getInstance();
			sDate.set(selectedYear, selectedMonth, selectedDay);
			Log.d("stime", "dtf.format(sDate.getTime()):" + String.valueOf(sDate));
			tempDate = df.format(sDate.getTime());
			
			if (startDate){
				startDateTV.setText(tempDate);
			} else {
				endDateTV.setText(tempDate);
			}
		}
	};
	
	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			Calendar time = Calendar.getInstance();
			time.set(Calendar.HOUR_OF_DAY, hourOfDay);
			time.set(Calendar.MINUTE, minute);
			tempDate = tf.format(time.getTime());
			
			if (startTime){
				startTimeTV.setText(tempDate);
			} else {
				endTimeTV.setText(tempDate);
			}
			
		}
	};
}
