package com.example.orient_me.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orient_me.R;
import com.example.orient_me.badges.Badge;
import com.example.orient_me.badges.BadgeDatabaseHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapPlaceFragment extends Fragment implements OnMapReadyCallback {
	
	FragmentActivity context;
	private static View view;
	List<Marker> marker;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		context = (FragmentActivity) super.getActivity();
		
		// Wahlberg, V. (2013) Duplicate ID, Tag Null, or Parent Id with Another Fragment For com.google.android.gms.maps.MapFragment [Online]. Available from: http://stackoverflow.com/questions/14083950/duplicate-id-tag-null-or-parent-id-with-another-fragment-for-com-google-androi/19815266#19815266 [Accessed: 24 May 2015]. 
		if (view != null) {
		    ViewGroup parent = (ViewGroup) view.getParent();
		    if (parent != null){
		    	// Removing markers to prevent memory leaks
		    	for (int i = 0; i < marker.size(); i++){
		    		marker.get(i).remove();
		    	}
		        parent.removeView(view);
		    }
		}
		try {
		    view = inflater.inflate(R.layout.fragment_map_place, container, false);
		    // Prvn (2013) MapView Casting Exception Bug? [Online]. Available from: http://stackoverflow.com/questions/16886183/mapview-casting-exception-bug [Accessed: 24 May 2015]. 
		    GoogleMap mMap = ((SupportMapFragment) getChildFragmentManager()
	                .findFragmentById(R.id.mapFragment)).getMap();
		    onMapReady(mMap);
		    		    
		    BadgeDatabaseHelper db = new BadgeDatabaseHelper(context);
		    Badge badge = db.getOneBadgeRow("7");
		    
		    if (badge.getUnlocked_at().isEmpty())
		    	setAchievementTimer();
		    
		 } catch (InflateException e) {
		     /* map is already there, just return view as it is */
			 Log.d("MPFrag Inflate Exception", e.toString());
		 }
		 return view;
	}
    
	public void setAchievementTimer(){
		//Arshed, F. (2013) How Can I Create Timer Tick in Android? [Online]. Available from: http://stackoverflow.com/questions/14384016/how-can-i-create-timer-tick-in-android [Accessed: 20 May 2015].
		final Handler handler = new Handler();
		Timer timer = new Timer(false);
		TimerTask timerTask = new TimerTask() {
		    @Override
		    public void run() {
		        handler.post(new Runnable() {
		            @Override
		            public void run() {
		                showAchievement(7);
		            }
		        });
		    }
		};
		timer.schedule(timerTask, 1000*60*5); // 1000 = 1 second; so 1000*60*5 = 5 mins
	}
	
    public void onMapReady(GoogleMap map) {
    	marker = new ArrayList<Marker>();
    	List<Place> places = new ArrayList<Place>();
    	LatLng pos;
    	map.setMyLocationEnabled(true);
    	
    	PlaceDatabaseHelper pdb = new PlaceDatabaseHelper(context);
		places = pdb.getAllPlaces();	
		
		for (Place eachPlace : places){
			if (eachPlace.getAddress().equalsIgnoreCase("0"))
				pos = new LatLng(Double.parseDouble(eachPlace.getLat()), Double.parseDouble(eachPlace.getLng()));
			else 
				pos = getLocationFromAddress(eachPlace.getAddress());
			
	        Marker pin = map.addMarker(new MarkerOptions()
					            .title(eachPlace.getTitle())
					            .snippet(eachPlace.getSnippet())
					            .position(pos));
	        marker.add(pin);
			
			if (eachPlace.getId().equalsIgnoreCase("1"))
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 17));	
		}
    }

    // Gupte, N. A. (2015) How Can I Find the Latitude and Longitude From Address? [Online]. Available from: http://stackoverflow.com/questions/3574644/how-can-i-find-the-latitude-and-longitude-from-address/27834110#27834110 [Accessed: 20 May 2015].
	private LatLng getLocationFromAddress(String strAddress) {
	    Geocoder coder = new Geocoder(context);
	    List<android.location.Address> address;
	    LatLng p1 = null;

	    try {
	        address = coder.getFromLocationName(strAddress, 5);
	        if (address == null) {
	            return null;
	        }
	        android.location.Address location = address.get(0);
	        location.getLatitude();
	        location.getLongitude();

	        p1 = new LatLng(location.getLatitude(), location.getLongitude() );

	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return p1;
	}
	
	 private void showAchievement(int id) {
		BadgeDatabaseHelper db = new BadgeDatabaseHelper(context); 
		Badge badge = db.getOneBadgeRow(String.valueOf(id));

		badge.setUnlocked_at(badge.getTimeNow());
		Log.d("MA - Checking time format", badge.getUnlocked_at());
		db.updateBadge(badge);		
		
		LayoutInflater inflater = context.getLayoutInflater();
		View layout = inflater.inflate(R.layout.customtoast,
				(ViewGroup) context.findViewById(R.id.toast_container));

		ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
		image.setImageResource(R.drawable.badge_7);
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
