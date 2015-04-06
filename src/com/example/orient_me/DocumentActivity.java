package com.example.orient_me;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

public class DocumentActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_document);
		Toast.makeText(DocumentActivity.this, "Welcome, student!", Toast.LENGTH_SHORT).show();
	}

}
