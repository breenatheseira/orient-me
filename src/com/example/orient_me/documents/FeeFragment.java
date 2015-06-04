package com.example.orient_me.documents;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.orient_me.R;
import com.example.orient_me.helpers.PreferencesHelper;

public class FeeFragment extends Fragment {
	
	FragmentActivity context;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	     context = (FragmentActivity) super.getActivity();

	     WebView layout = (WebView) inflater.inflate(R.layout.fragment_fee, container, false);
	     WebView w = new WebView(context);
	    
	     w = (WebView) layout.findViewById(R.id.feeWebView); 
	     
	     //Cristian (2010) What's the Difference Between setWebViewClient vs setWebChromeClient? [Online]. Available from: http://stackoverflow.com/questions/2835556/whats-the-difference-between-setwebviewclient-vs-setwebchromeclient [Accessed: 19 May 2015].
	     w.setWebChromeClient(new WebChromeClient()); 
	     w.setWebViewClient(new WebViewClient()); 
		
	     PreferencesHelper prefs = new PreferencesHelper(context); 
	     
	     //Banks, K. W. (2015) How To Load a PDF in an Android WebView. [Online]. Available from: src/com/example/orient_me/SplashActivity.java [Accessed: 4 June 2015].
	     String url = "http://docs.google.com/gview?embedded=true&url=" + prefs.GetPreferences("FeeScheduleURL");	     
	     Log.d("FeeFragment", url);
	     
	     // Javascript must be enabled in order to display the PDF.
	     w.getSettings().setJavaScriptEnabled(true);
	     w.loadUrl(url);
	     return layout; 
	}

}
