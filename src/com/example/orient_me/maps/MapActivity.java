package com.example.orient_me.maps;

import java.util.List;

import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.orient_me.R;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onMapReady(GoogleMap map) {
        LatLng APU = getLocationFromAddress("Asia Pacific University of Technology & Innovation (APU)");

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(APU, 17));

        map.addMarker(new MarkerOptions()
                .title("APU TPM Campus")
                .snippet("Main Campus of APU in Malaysia")
                .position(APU));
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
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
	
	// Gupte, N. A. (2015) How Can I Find the Latitude and Longitude From Address? [Online]. Available from: http://stackoverflow.com/questions/3574644/how-can-i-find-the-latitude-and-longitude-from-address/27834110#27834110 [Accessed: 20 May 2015].
	public LatLng getLocationFromAddress(String strAddress) {

	    Geocoder coder = new Geocoder(this);
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
}
