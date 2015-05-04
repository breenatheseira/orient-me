package com.example.orient_me.schedules;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.orient_me.R;
import com.example.orient_me.notes.Note;

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
	
		schedule = new Schedule(id, db.getOneNoteRow("title", id),
				db.getOneNoteRow("note", id));
		title_locTV.setText(schedule.getTitle() + " @ " + schedule.getLocation());
		notesTV.setText(schedule.getNotes());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_record_with_edit, menu);
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
}
