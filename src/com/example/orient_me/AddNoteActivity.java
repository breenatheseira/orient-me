package com.example.orient_me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orient_me.models.Note;
import com.example.orient_me.models.NoteDatabaseHelper;

public class AddNoteActivity extends ActionBarActivity {

	EditText title_text, note_text;
	String title, note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_note);

		title_text = (EditText) findViewById(R.id.anaTB_Title);
		note_text = (EditText) findViewById(R.id.anaTA_Note);
	}

	private void save() {
		try {

			title = title_text.getText().toString();
			note = note_text.getText().toString();
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
			
			Intent intent = new Intent(AddNoteActivity.this,ViewNotesListActivity.class);
			startActivity(intent);
			finish();
			
		} catch (Exception e) {
			Log.d("Database insert", e.toString());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_note, menu);
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
}
