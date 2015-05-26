package com.example.orient_me.notes;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.orient_me.R;

public class ViewNotesListActivity extends AppCompatActivity {

	ListView noteList;
	LinearLayout emptyLayout;
	List<Note> notes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_notes_list);
		
		Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
		
		toolbar.setTitle("Notes List");
		setSupportActionBar(toolbar);
		
		noteList = (ListView) findViewById(R.id.vnalLV_listview);
		emptyLayout = (LinearLayout) findViewById(R.id.vnalLL_emptyList);

		loadListView();
	}

	private void loadListView() {
		NoteDatabaseHelper db = new NoteDatabaseHelper(ViewNotesListActivity.this);
		
		ArrayList<String> titles = new ArrayList<String>(); 
		
		//Tamada, R. (2013) Android SQLite Database with Multiple Tables. [Online]. Available from: http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/ [Accessed: 1 May 2015].
		notes = db.getAllNote();
		for (Note eachNote : notes){
			titles.add(eachNote.getTitle());
		}
		
		// Developers (n.d.) Layouts. [Online]. Available from: http://developer.android.com/guide/topics/ui/declaring-layout.html#AdapterViews [Accessed: 1 May 2015]. 		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewNotesListActivity.this, android.R.layout.simple_list_item_1,titles);
		
		noteList.setAdapter(adapter);
		noteList.setEmptyView(emptyLayout);
		
		noteList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long l) {
				
				// victor (2010) Android - Getting Database ID from ListView Selection. [Online]. Available from: http://stackoverflow.com/questions/12268721/android-getting-database-id-from-listview-selection [Accessed: 2 May 2015].
				String id = notes.get(position).getId(); 
				
				Intent intent = new Intent(ViewNotesListActivity.this, EditNoteActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_only, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.add) {
			Intent intent = new Intent(this, AddNoteActivity.class);
			startActivity(intent);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostResume() {
		loadListView();
		super.onPostResume();
	}
}