package com.example.orient_me.documents;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.orient_me.R;

public class AcknowledgeFeeActivity extends AppCompatActivity implements OnClickListener {

	Button acknowledgeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acknowledge_fee);

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		Fragment fragment = new Fragment();
		fragmentTransaction.add(R.id.aaf_feeFragment, fragment);
		fragmentTransaction.commit();

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		acknowledgeButton = (Button) findViewById(R.id.aafB_acknowledgeButton);
		acknowledgeButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		new AlertDialog.Builder(this)
	      .setMessage("Are you sure you acknowledge the fee statement?")
	      .setCancelable(false)
	      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int id) {
	        	  makeToastMsg();
	          }
	      })
	      .setNegativeButton("No", null)
	      .show();
	}
	public void makeToastMsg(){
		Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
	}
}
