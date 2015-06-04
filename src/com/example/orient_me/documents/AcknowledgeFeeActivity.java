package com.example.orient_me.documents;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.orient_me.R;

public class AcknowledgeFeeActivity extends AppCompatActivity {
	
	Button acknowledgeButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acknowledge_fee);
		acknowledgeButton = (Button) findViewById(R.id.aafB_acknowledgeButton);
		FragmentManager fragmentManager = getFragmentManager(); 
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		Fragment fragment = new Fragment();
		fragmentTransaction.add(R.id.aaf_feeFragment, fragment); 
		fragmentTransaction.commit();
	}

}
