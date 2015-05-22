package com.example.orient_me;

import java.io.File;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orient_me.badges.Badge;
import com.example.orient_me.badges.BadgeDatabaseHelper;
import com.example.orient_me.badges.ViewBadgesListActivity;
import com.example.orient_me.contacts.ViewContactsListActivity;
import com.example.orient_me.helpers.PreferencesHelper;
import com.example.orient_me.maps.MapActivity;
import com.example.orient_me.notes.ViewNotesListActivity;
import com.example.orient_me.schedules.ViewScheduleListActivity;
import com.example.orient_me.social.FacebookActivity;

public class DocumentActivity extends AppCompatActivity implements
		OnClickListener {
	Button stuHandbook, modList, campMap, orientSch, myNotes;
	Intent intent;
	PreferencesHelper prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_document);
		prefs = new PreferencesHelper(getApplicationContext());
		
		stuHandbook = (Button) findViewById(R.id.daB_studentHandbook);
		modList = (Button) findViewById(R.id.daB_moduleList);
		campMap = (Button) findViewById(R.id.daB_campusMap);
		orientSch = (Button) findViewById(R.id.daB_orientSchedule);
		myNotes = (Button) findViewById(R.id.daB_myNotes);

		stuHandbook.setOnClickListener(this);
		modList.setOnClickListener(this);
		campMap.setOnClickListener(this);
		orientSch.setOnClickListener(this);
		myNotes.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.mi_badges:
			intent = new Intent(this, ViewBadgesListActivity.class);
			break;
		case R.id.mi_doc:
			break;
		case R.id.mi_import:
			intent = new Intent(this, ViewContactsListActivity.class);
			break;
		case R.id.mi_sMedia:
			intent = new Intent(this, FacebookActivity.class);
			break;
		case R.id.mi_schedule:
			intent = new Intent(this, ViewScheduleListActivity.class);
			break;
		}
		startActivity(intent);
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		Intent intent;		
		switch (v.getId()) {
		case R.id.daB_studentHandbook:
			prefs.SavePreferences("sh_count", "v");
			showAchievement(1);
			docViewCheck();
			ViewPDF(prefs.GetPreferences("StudentHandbook"), v);
			break;
		case R.id.daB_moduleList:
			prefs.SavePreferences("ml_count", "v");
			docViewCheck();
			ViewPDF(prefs.GetPreferences("ModuleList"), v);
			break;
		case R.id.daB_campusMap:
			intent = new Intent(this, MapActivity.class);
			startActivity(intent);
			break;
		case R.id.daB_orientSchedule:
			prefs.SavePreferences("os_count", "v");
			docViewCheck();
			ViewPDF(prefs.GetPreferences("OrientationSchedule"), v);
			break;
		case R.id.daB_myNotes:
			intent = new Intent(this, ViewNotesListActivity.class);
			startActivity(intent);
			break;
		}
	}

	public void ViewPDF(String filepath, View v) {
		File pdfFile = new File(filepath);
		Uri path = Uri.fromFile(pdfFile);
		Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
		pdfIntent.setDataAndType(path, "application/pdf");
		pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		try {
			startActivity(pdfIntent);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(DocumentActivity.this,
					"No Application available to view PDF", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void showAchievement(int id) {

		BadgeDatabaseHelper db = new BadgeDatabaseHelper(this);

		Badge badge = db.getOneBadgeRow(String.valueOf(id));

		if (badge.getUnlocked_at().isEmpty()) {
			badge.setUnlocked_at(badge.getTimeNow());
			Log.d("DA - Checking time format", badge.getUnlocked_at());
			db.updateBadge(badge);
			
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.customtoast,
					(ViewGroup) findViewById(R.id.toast_container));

			ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
			if (badge.getId().equals("1"))
				image.setImageResource(R.drawable.badge_1);
			else
				image.setImageResource(R.drawable.badge_4);
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
	private void docViewCheck(){
		if (prefs.GetPreferences("sh_count").contains("v") && prefs.GetPreferences("ml_count").contains("v") && prefs.GetPreferences("os_count").contains("v"))
			showAchievement(4);
	}
}
