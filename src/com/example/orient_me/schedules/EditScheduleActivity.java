package com.example.orient_me.schedules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.orient_me.R;

@SuppressLint("SimpleDateFormat")
public class EditScheduleActivity extends AppCompatActivity implements
OnClickListener {

	EditText titleET, locationET, notesET;
	TextView startDateTV, startTimeTV, endDateTV,
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
	
	DatePickerDialog dateDialog;
	TimePickerDialog timeDialog;
	boolean startDate, startTime;
	String tempDate;

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
		
		dateDialog = new DatePickerDialog(this,datePickerListener, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
		timeDialog = new TimePickerDialog(this, timePickerListener, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);
		
		if (schedule.getAlert().compareTo("1") == 0)
			alertButton.setChecked(true);
		else
			alertButton.setChecked(false);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.save_and_discard, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id){
		case R.id.save: 
			assignCalDates();
			if (startCal.compareTo(endCal) < 0){
				assignSchedule();
				// codaddict (2010) Java, Check Whether a String is not Null and not Empty? [Online]. Available from: http://stackoverflow.com/questions/3598770/java-check-whether-a-string-is-not-null-and-not-empty [Accessed: 3 May 2015].
				if (title != null && !title.equals("") && location != null && !location.equals("")){
					if (db.updateSchedule(schedule) == 1){
						Toast.makeText(this, "Your Schedule is updated.", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(this, ViewScheduleActivity.class);
						intent.putExtra("id", schedule.getId());
						intent.putExtra("destroy?", false);
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
			break;
		case R.id.discard:
			//Sukarno, C. (2011) How to Show a Dialog Confirm that the User Wishes to Exit an Android Activity? [Online]. Available: http://stackoverflow.com/questions/2257963/how-to-show-a-dialog-to-confirm-that-the-user-wishes-to-exit-an-android-activity [Accessed: 2 May 2015].
			  new AlertDialog.Builder(this)
		      .setMessage("Are you sure you want to delete this schedule?")
		      .setCancelable(false)
		      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		          public void onClick(DialogInterface dialog, int id) {

		        	//Delete the note
		        	if (db.deleteSchedule(schedule.getId()) == 1)
		        		finish();
		          }
		      })
		      .setNegativeButton("No", null)
		      .show();
			break;
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
		//Saryada, W. (2006) How Do I Convert String to Date Object in Java? [Online]. Available from: kodejava.org/how-do-i-convert-string-to-date-object/ [Accessed: 4 May 2015].
		try {
			startCal.setTime(dtf.parse(startDateTV.getText().toString() + " " + startTimeTV.getText().toString()));
			endCal.setTime(dtf.parse(endDateTV.getText().toString() + " " + endTimeTV.getText().toString()));
		} catch (ParseException e) {
			e.printStackTrace();
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
			tempDate = dtf.format(sDate.getTime());
			
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
