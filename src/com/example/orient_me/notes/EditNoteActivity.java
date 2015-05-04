package com.example.orient_me.notes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orient_me.R;

public class EditNoteActivity extends ActionBarActivity {

	String oldTitle;
	Note noteViewed;
	EditText title, note;
	NoteDatabaseHelper db;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_view_note);

		title = (EditText) findViewById(R.id.vna_title);
		note = (EditText) findViewById(R.id.vna_note);
		db = new NoteDatabaseHelper(EditNoteActivity.this);

		setNotesContents();
	}

	private void setNotesContents() {
		// Rafael T. (2011) Load data to android list view with record id and
		// pass the exact record id onListItemClick. [Online]. Available from:
		// http://stackoverflow.com/questions/7406123/load-data-to-android-list-view-with-record-id-and-pass-the-exact-record-id-onlis
		// [Accessed: 2 May 2015].
		String id = String.valueOf((getIntent().getStringExtra("id")));

		noteViewed = new Note(id, db.getOneNoteRow("title", id),
				db.getOneNoteRow("note", id));
		title.setText(noteViewed.getTitle());
		note.setText(noteViewed.getNote());
		oldTitle = noteViewed.getTitle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.save_and_discard, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.save:
			noteViewed.setTitle(title.getEditableText().toString());
			noteViewed.setNote(note.getEditableText().toString());
			
			if (noteViewed.getTitle().length() > 0){
				if (db.updateNote(noteViewed) == 1) {
					Toast.makeText(this, oldTitle + " updated", Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(this,"Error: " + noteViewed.getTitle()+ " could not be updated", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, "Please enter a title before saving this note.", Toast.LENGTH_SHORT).show();
			}
			
			break;
		case R.id.discard:
			deleteNote();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void deleteNote(){
	//Sukarno, C. (2011) How to Show a Dialog Confirm that the User Wishes to Exit an Android Activity? [Online]. Available: http://stackoverflow.com/questions/2257963/how-to-show-a-dialog-to-confirm-that-the-user-wishes-to-exit-an-android-activity [Accessed: 2 May 2015].
	  new AlertDialog.Builder(this)
      .setMessage("Are you sure you want to delete this note?")
      .setCancelable(false)
      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {

        	//Delete the note
        	if (db.deleteNote(noteViewed.getId()) == 1)
				finish();        	  
          }
      })
      .setNegativeButton("No", null)
      .show();
	}
}