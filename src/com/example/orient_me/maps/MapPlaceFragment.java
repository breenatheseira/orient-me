package com.example.orient_me.maps;

import java.util.ArrayList;
import java.util.List;

import android.location.Geocoder;
import android.os.Bundle;
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
import com.example.orient_me.helpers.PreferencesHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapPlaceFragment extends Fragment implements OnMapReadyCallback, OnMarkerClickListener {
	
	FragmentActivity context;
	private static View view;
	List<Marker> marker;
	PreferencesHelper prefs;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		context = (FragmentActivity) super.getActivity();
		
		// Wahlberg, V. (2013) Duplicate ID, Tag Null, or Parent Id with Another Fragment For com.google.android.gms.maps.MapFragment [Online]. Available from: http://stackoverflow.com/questions/14083950/duplicate-id-tag-null-or-parent-id-with-another-fragment-for-com-google-androi/19815266#19815266 [Accessed: 24 May 2015]. 
		if (view != null) {
		    ViewGroup parent = (ViewGroup) view.getParent();
		    if (parent != null){
		    	try {
					// Removing markers to prevent memory leaks
					for (int i = 0; i < marker.size(); i++){
						marker.get(i).remove();
					}
					parent.removeView(view);
				} catch (Exception e) {
					Log.d("Map P Fragment: onCreateView", "Error: " + e.getMessage());
				}
		    }
		}
		try {
		    view = inflater.inflate(R.layout.fragment_map_place, container, false);
		    // Prvn (2013) MapView Casting Exception Bug? [Online]. Available from: http://stackoverflow.com/questions/16886183/mapview-casting-exception-bug [Accessed: 24 May 2015]. 
		    GoogleMap mMap = ((SupportMapFragment) getChildFragmentManager()
	                .findFragmentById(R.id.mapFragment)).getMap();
		    onMapReady(mMap);
		    mMap.setOnMarkerClickListener(this);
		 } catch (InflateException e) {
		     /* map is already there, just return view as it is */
			 Log.d("MPFrag Inflate Exception", e.toString());
		 }
		 return view;
	}
    
    public void onMapReady(GoogleMap map) {
    	marker = new ArrayList<Marker>();
    	List<Place> places = new ArrayList<Place>();
    	LatLng pos;
    	map.setMyLocationEnabled(true);
    	
    	try {
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
			
    	} catch (Exception e) {
    		Log.d("MapPF Adding Markers", "Error: " + e.getMessage());
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
	    	Log.d("MapPF get GPS from Address", "Error: " + ex.getMessage());
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
	 
	 // Andr.oid Eric (2013) Google Maps Android API v2 Example: Detect MarkerClick and Add Polyline. [Online]. Available from: http://android-er.blogspot.in/2013/01/google-maps-android-api-v2-example_5213.html [Accessed: 30 May 2015].
	@Override
	public boolean onMarkerClick(Marker arg0) {
		BadgeDatabaseHelper db = new BadgeDatabaseHelper(context);
	    Badge badge = db.getOneBadgeRow("7");
	    Log.d("onMarkerClick", "Clicked Marker");
	    Log.d("onMarkerClick", "Marker ID:" + arg0.getId());
	    Log.d("onMarkerClick", "Marker Title:" + arg0.getTitle());
	    
		if (badge.getUnlocked_at().isEmpty() && "10".equals(arg0.getId())){
			showAchievement(7);
			return true;
		}
		return false;
	}
}
