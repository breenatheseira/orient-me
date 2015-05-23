package com.example.orient_me.badges;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.orient_me.R;

public class ViewBadgesListActivity extends AppCompatActivity {

	ListView badgesList;
	LinearLayout emptyLayout;
	List<Badge> badges;
	BadgeDatabaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_badges_list);
		
		badgesList = (ListView) findViewById(R.id.avblLV_badges_list);
		emptyLayout = (LinearLayout) findViewById(R.id.avblLL_list_layout);

		loadListView();
	}
	
	private void loadListView() {
		db = new BadgeDatabaseHelper(this);

		//Tamada, R. (2013) Android SQLite Database with Multiple Tables. [Online]. Available from: http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/ [Accessed: 1 May 2015].
		badges = db.getAllBadges();
		
		// Developers (n.d.) Layouts. [Online]. Available from: http://developer.android.com/guide/topics/ui/declaring-layout.html#AdapterViews [Accessed: 1 May 2015]. 		
		BadgesListAdapter adapter = new BadgesListAdapter(this, R.layout.custom_badges_list, badges);
		
		badgesList.setAdapter(adapter);
		badgesList.setEmptyView(emptyLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.view_badges_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
