package com.example.orient_me;

import com.example.orient_me.helpers.PreferencesHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

// Tamada, R. (2013): http://www.androidhive.info/2013/07/how-to-implement-android-splash-screen-2/
public class SplashActivity extends ActionBarActivity {
	private static int splash_timeout = 3000;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {						
				PreferencesHelper pref = new PreferencesHelper(SplashActivity.this);
				Intent intent_login = new Intent(SplashActivity.this,LoginActivity.class);
				Intent intent_menu = new Intent(SplashActivity.this, DocumentActivity.class);
				
				if (pref.GetPreferences("AuthToken").length() == 0)
					startActivity(intent_login);
				else
					startActivity(intent_menu);
				finish();
			}
		}, splash_timeout);
	}

}
