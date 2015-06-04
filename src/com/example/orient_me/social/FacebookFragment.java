package com.example.orient_me.social;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
public class FacebookFragment extends Fragment {
	
	FragmentActivity context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	     context = (FragmentActivity) super.getActivity();

	     WebView layout = (WebView) inflater.inflate(R.layout.fragment_facebook, container, false);
	     WebView w = new WebView(context);
	    
	     w = (WebView) layout.findViewById(R.id.facebookWebView); 
	     
	     //Cristian (2010) What's the Difference Between setWebViewClient vs setWebChromeClient? [Online]. Available from: http://stackoverflow.com/questions/2835556/whats-the-difference-between-setwebviewclient-vs-setwebchromeclient [Accessed: 19 May 2015].
	     w.setWebChromeClient(new WebChromeClient()); 
	     w.setWebViewClient(new WebViewClient()); 
	     w.getSettings().setJavaScriptEnabled(true);
		
	     String url = "http://m.facebook.com";
	     w.loadUrl(url);
	    
	     return layout; 
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) { 
	    super.setUserVisibleHint(isVisibleToUser);
	    context = (FragmentActivity) super.getActivity();
	    
	    if (isVisibleToUser)
	    	showAchievement(3); 
	}
	
	private void showAchievement(int id) {
	
		BadgeDatabaseHelper db = new BadgeDatabaseHelper(context);
	
		Badge badge = db.getOneBadgeRow(String.valueOf(id));
	
		if (badge.getUnlocked_at().isEmpty()) {
			badge.setUnlocked_at(badge.getTimeNow());
			Log.d("FA - Checking time format", badge.getUnlocked_at());
			db.updateBadge(badge);
			
			LayoutInflater inflater = context.getLayoutInflater();
			View layout = inflater.inflate(R.layout.customtoast,
					(ViewGroup) context.findViewById(R.id.toast_container));
	
			ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
			image.setImageResource(R.drawable.badge_3);
			TextView badgeName = (TextView) layout
					.findViewById(R.id.toast_text);
			badgeName.setText(badge.getName());
	
			Toast toast = new Toast(context);
			toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
		}
	}
}
