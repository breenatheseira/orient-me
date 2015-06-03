package com.example.orient_me;

import java.io.File;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orient_me.badges.Badge;
import com.example.orient_me.badges.BadgeDatabaseHelper;
import com.example.orient_me.helpers.PreferencesHelper;
import com.example.orient_me.maps.MapPlaceFragment;
import com.example.orient_me.notes.ViewNotesListFragment;

public class DocumentFragment extends Fragment implements
		OnClickListener {
	
	Button stuHandbook, impDetails, feeSch, orientSch, myNotes;
	Intent intent;
	PreferencesHelper prefs;
	FragmentActivity context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    
		context = (FragmentActivity) super.getActivity();
	    LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_document, container, false);

    	prefs = new PreferencesHelper(context);
		
		stuHandbook = (Button) layout.findViewById(R.id.daB_studentHandbook);
		impDetails = (Button) layout.findViewById(R.id.daB_imptDetails);
		feeSch = (Button) layout.findViewById(R.id.daB_feeSchedule);
		orientSch = (Button) layout.findViewById(R.id.daB_orientSchedule);
		myNotes = (Button) layout.findViewById(R.id.daB_myNotes);

		stuHandbook.setOnClickListener(this);
		impDetails.setOnClickListener(this);
		feeSch.setOnClickListener(this);
		orientSch.setOnClickListener(this);
		myNotes.setOnClickListener(this);

	    return layout;
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
		case R.id.daB_imptDetails:
			prefs.SavePreferences("id_count", "v");
			docViewCheck();
			ViewPDF(prefs.GetPreferences("ImportantDetails"), v);
			break;
		case R.id.daB_feeSchedule:
			ViewPDF(prefs.GetPreferences("FeeSchedule"), v);
			break;
		case R.id.daB_orientSchedule:
			prefs.SavePreferences("os_count", "v");
			docViewCheck();
			ViewPDF(prefs.GetPreferences("OrientationSchedule"), v);
			break;
		case R.id.daB_myNotes:
			intent = new Intent(context, ViewNotesListFragment.class);
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
			Toast.makeText(context,
					"No Application available to view PDF", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void showAchievement(int id) {

		BadgeDatabaseHelper db = new BadgeDatabaseHelper(context);

		Badge badge = db.getOneBadgeRow(String.valueOf(id));

		if (badge.getUnlocked_at().isEmpty()) {
			badge.setUnlocked_at(badge.getTimeNow());
			Log.d("DA - Checking time format", badge.getUnlocked_at());
			db.updateBadge(badge);
			
			LayoutInflater inflater = context.getLayoutInflater();
			View layout = inflater.inflate(R.layout.customtoast,
					(ViewGroup) context.findViewById(R.id.toast_container));

			ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
			if (badge.getId().equals("1"))
				image.setImageResource(R.drawable.badge_1);
			else
				image.setImageResource(R.drawable.badge_4);
			TextView badgeName = (TextView) layout
					.findViewById(R.id.toast_text);
			badgeName.setText(badge.getName());

			Toast toast = new Toast(context );
			toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
		}
	}
	private void docViewCheck(){
		if (prefs.GetPreferences("sh_count").contains("v") && prefs.GetPreferences("id_count").contains("v") && prefs.GetPreferences("os_count").contains("v"))
			showAchievement(4);
	}
}
