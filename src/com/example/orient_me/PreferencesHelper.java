package com.example.orient_me;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesHelper {	
	private SharedPreferences sharedP;
	private Editor ed;

	public PreferencesHelper(Context context) {
		this.sharedP = context.getSharedPreferences("CASPreferences",
				Context.MODE_PRIVATE);
		this.ed = sharedP.edit();
	}

	public String GetPreferences(String key) {
		return sharedP.getString(key, "");
	}

	public void SavePreferences(String key, String val) {
		ed.putString(key, val);
		ed.commit();
	}
}
