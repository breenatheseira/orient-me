package com.example.orient_me;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DocumentActivity extends ActionBarActivity implements OnClickListener {	
	
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
		
		stuHandbook.setOnClickListener(this);
		
//      stuHandbook.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Toast.makeText(LoginActivity.this, "Clicked Submit!", Toast.LENGTH_SHORT).show();
//			}
//		});		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);	
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.daB_studentHandbook:
			
			break;
		case R.id.daB_moduleList:
			
			break;
		case R.id.daB_campusMap:
			
			break;
		case R.id.daB_orientSchedule:
			
			break;
		case R.id.daB_myNotes:
			
			break;
		}
	}


}
