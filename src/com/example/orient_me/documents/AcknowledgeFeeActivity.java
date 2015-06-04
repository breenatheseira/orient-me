package com.example.orient_me.documents;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.orient_me.R;
import com.example.orient_me.helpers.PreferencesHelper;

public class AcknowledgeFeeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acknowledge_fee);
		
//		WebView layout = (WebView) findViewById(R.id.aafWV_fee_schedule);
		WebView w = new WebView(this);
	    
	    w = (WebView) findViewById(R.id.aafWV_fee_schedule); 
	    
	    //Cristian (2010) What's the Difference Between setWebViewClient vs setWebChromeClient? [Online]. Available from: http://stackoverflow.com/questions/2835556/whats-the-difference-between-setwebviewclient-vs-setwebchromeclient [Accessed: 19 May 2015].
	    w.setWebChromeClient(new WebChromeClient()); 
	    w.setWebViewClient(new WebViewClient()); 
	    w.getSettings().setJavaScriptEnabled(true);
		
	    PreferencesHelper prefs = new PreferencesHelper(this); 
	     
	    String url = prefs.GetPreferences("CourseScheduleURL");
	    w.loadUrl(url);
	}

}
