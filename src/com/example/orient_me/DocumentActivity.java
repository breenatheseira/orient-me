package com.example.orient_me;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.Toast;

public class DocumentActivity extends ActionBarActivity{	
	
	Button stuHandbook, modList, campMap, orientSch, myNotes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_document);
		Toast.makeText(DocumentActivity.this, "Welcome, student!", Toast.LENGTH_SHORT).show();
		
		stuHandbook = (Button) findViewById(R.id.daB_studentHandbook);
		modList = (Button) findViewById(R.id.daB_moduleList);
		campMap = (Button) findViewById(R.id.daB_campusMap);
		orientSch= (Button) findViewById(R.id.daB_orientSchedule);
		myNotes = (Button) findViewById(R.id.daB_myNotes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);	
		return super.onCreateOptionsMenu(menu);
	}


}
