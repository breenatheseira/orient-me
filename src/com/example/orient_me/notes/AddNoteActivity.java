package com.example.orient_me.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orient_me.R;
import com.example.orient_me.badges.Badge;
import com.example.orient_me.badges.BadgeDatabaseHelper;

public class AddNoteActivity extends AppCompatActivity {

	EditText title_text, note_text;
	String title, note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_note);

		Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
		toolbar.setTitle("Add Notes");
		setSupportActionBar(toolbar);
		
		title_text = (EditText) findViewById(R.id.anaTB_Title);
		note_text = (EditText) findViewById(R.id.anaTA_Note);
	}

	private void save() {

		title = title_text.getText().toString();
		note = note_text.getText().toString();
		
		if (title.length() > 0) {
			Note newNote = new Note();
	
			newNote.setTitle(title);
			newNote.setNote(note);
			
			NoteDatabaseHelper db = new NoteDatabaseHelper(AddNoteActivity.this);
			int note_id = db.getLastNoteId(); 
			
			if (note_id > 0){
				newNote.setId(String.valueOf(note_id + 1));
			} else {
				newNote.setId(String.valueOf(1));
			}
			db.addNote(newNote);
			
			Toast.makeText(this, newNote.getTitle() + " saved!", Toast.LENGTH_SHORT).show();
			
			if (newNote.getId().equals("3"))
				showAchievement(6);
			
			Intent intent = new Intent(AddNoteActivity.this,ViewNotesListFragment.class);
			startActivity(intent);
			finish();
			
		} else {
			Toast.makeText(this, "Please enter a title before saving this note.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.save_only, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.save) {
			save();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    private void showAchievement(int id) {

		BadgeDatabaseHelper db = new BadgeDatabaseHelper(this);

		Badge badge = db.getOneBadgeRow(String.valueOf(id));

		if (badge.getUnlocked_at().isEmpty()) {
			badge.setUnlocked_at(badge.getTimeNow());
			Log.d("DA - Checking time format", badge.getUnlocked_at());
			db.updateBadge(badge);
			
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.customtoast,
					(ViewGroup) findViewById(R.id.toast_container));

			ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
			image.setImageResource(R.drawable.badge_6);
			TextView badgeName = (TextView) layout
					.findViewById(R.id.toast_text);
			badgeName.setText(badge.getName());

			Toast toast = new Toast(getApplicationContext());
			toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
		}
	}
}
