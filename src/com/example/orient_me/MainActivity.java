package com.example.orient_me;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		PreferencesHelper pref = new PreferencesHelper(MainActivity.this);	
		TextView welcomeText = (TextView) findViewById(R.id.maT_welcome);
		
		String welcomeUser = welcomeText.getText() + ", " +  pref.GetPreferences("Name").toString();
		welcomeText.setText(welcomeUser);
	}

}
