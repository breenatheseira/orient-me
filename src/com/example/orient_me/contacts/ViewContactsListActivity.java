package contacts;

import java.util.ArrayList;
import java.util.List;

import com.example.orient_me.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ViewContactsListActivity extends ActionBarActivity {
	
	ListView contactList;
	LinearLayout emptyLayout;
	List<Contact> contacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_contacts_list);
		
		contactList = (ListView) findViewById(R.id.avcLV_contact_list);
		emptyLayout = (LinearLayout) findViewById(R.id.avcLL_list_layout);

		loadListView();
	}

	private void loadListView() {
		ContactsDatabaseHelper db = new ContactsDatabaseHelper(this);
		
		ArrayList<String> titles = new ArrayList<String>(); 
		
		//Tamada, R. (2013) Android SQLite Database with Multiple Tables. [Online]. Available from: http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/ [Accessed: 1 May 2015].
		contacts = db.getAllContacts();
		for (Contact eachContact : contacts){
			titles.add(eachContact.getName());
		}
		
		// Developers (n.d.) Layouts. [Online]. Available from: http://developer.android.com/guide/topics/ui/declaring-layout.html#AdapterViews [Accessed: 1 May 2015]. 		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,titles);
		
		contactList.setAdapter(adapter);
		contactList.setEmptyView(emptyLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_only, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostResume() {
		loadListView();
		super.onPostResume();
	}

}
