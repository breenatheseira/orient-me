package com.example.orient_me.social;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orient_me.R;
import com.example.orient_me.badges.Badge;
import com.example.orient_me.badges.BadgeDatabaseHelper;

@SuppressLint("SetJavaScriptEnabled")
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
		 
		showAchievement(3); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.facebook, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showAchievement(int id) {
	
		BadgeDatabaseHelper db = new BadgeDatabaseHelper(this);
	
		Badge badge = db.getOneBadgeRow(String.valueOf(id));
	
		if (badge.getUnlocked_at().isEmpty()) {
			badge.setUnlocked_at(badge.getTimeNow());
			Log.d("FA - Checking time format", badge.getUnlocked_at());
			db.updateBadge(badge);
			
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.customtoast,
					(ViewGroup) findViewById(R.id.toast_container));
	
			ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
			image.setImageResource(R.drawable.ic_action_edit);
			TextView badgeName = (TextView) layout
					.findViewById(R.id.toast_text);
			badgeName.setText(badge.getName());
	
			Toast toast = new Toast(getApplicationContext());
			toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
		}
	}
}
