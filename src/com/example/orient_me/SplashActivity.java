package com.example.orient_me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

public class SplashActivity extends ActionBarActivity {
	private static int splash_timeout = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		}, splash_timeout);
	}

}
