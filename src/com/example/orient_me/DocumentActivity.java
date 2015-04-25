package com.example.orient_me;

import java.io.File;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
		modList.setOnClickListener(this);
		campMap.setOnClickListener(this);
		orientSch.setOnClickListener(this);
		myNotes.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);	
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onClick(View v) {
		PreferencesHelper prefs = new PreferencesHelper(getApplicationContext());
		switch (v.getId()){
		case R.id.daB_studentHandbook:
			ViewPDF(prefs.GetPreferences("StudentHandbook"),v);
			break;
		case R.id.daB_moduleList:
			ViewPDF(prefs.GetPreferences("ModuleList"),v);
			break;
		case R.id.daB_campusMap:
			ViewPDF(prefs.GetPreferences("StudentHandbook"),v);
			break;
		case R.id.daB_orientSchedule:
			ViewPDF(prefs.GetPreferences("OrientationSchedule"),v);
			break;
		case R.id.daB_myNotes:
			ViewPDF(prefs.GetPreferences("StudentHandbook"),v);
			break;
		}
	}

    public void ViewPDF(String filepath, View v)
    {
        File pdfFile = new File(filepath);
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try{
            startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(DocumentActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

}
