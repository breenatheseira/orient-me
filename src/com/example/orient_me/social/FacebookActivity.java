package com.example.orient_me.social;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.orient_me.R;

public class FacebookActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook);
		
		WebView w = new WebView(this);
		w = (WebView) findViewById(R.id.facebookWebView); 
		//Cristian (2010) What's the Difference Between setWebViewClient vs setWebChromeClient? [Online]. Available from: http://stackoverflow.com/questions/2835556/whats-the-difference-between-setwebviewclient-vs-setwebchromeclient [Accessed: 19 May 2015].
		w.setWebChromeClient(new WebChromeClient()); 
		w.setWebViewClient(new WebViewClient()); 
		w.getSettings().setJavaScriptEnabled(true);
		
		String url = "http://m.facebook.com";
		 w.loadUrl(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.facebook, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
