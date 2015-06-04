package com.example.orient_me;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.orient_me.documents.AcknowledgeFeeActivity;
import com.example.orient_me.helpers.PreferencesHelper;

// Tamada, R. (2013): http://www.androidhive.info/2013/07/how-to-implement-android-splash-screen-2/
public class SplashActivity extends AppCompatActivity {
	private static int splash_timeout = 3000;
	
	ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		 
		image = (ImageView) findViewById(R.id.saIV_background);
		
		// Get actual image width and height from res/drawable
		// BJK (2012) Android Get Width And Height Of Image From Resource. [Online]. Available from: http://spacetech.dk/android-get-width-and-height-of-image-from-resource.html [Accessed: 22 May 2015].
		BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(R.drawable.apu_logo_final);
		int imageHeight = bd.getBitmap().getHeight();
		int imageWidth = bd.getBitmap().getWidth();
		
		// Erdfelt, J. (2011) Android Image View Matrix Scale + Translate. [Online]. Available from: http://stackoverflow.com/questions/6075363/android-image-view-matrix-scale-translate [Accessed: 22 May 2015].
		// Matrix for proportional scaling
		Matrix m = image.getImageMatrix();
		RectF drawableRect = new RectF(0, 0, imageWidth, imageHeight);
		RectF viewRect = new RectF(0, 0, image.getWidth(), image.getHeight());
		m.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
		image.setImageMatrix(m);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {						
				PreferencesHelper pref = new PreferencesHelper(SplashActivity.this);
				Intent intent_login = new Intent(SplashActivity.this,LoginActivity.class);
				Intent intent_menu = new Intent(SplashActivity.this, AcknowledgeFeeActivity.class);
				
				if (pref.GetPreferences("AuthToken").length() == 0)
					startActivity(intent_login);
				else
					startActivity(intent_menu);
				finish();
			}
		}, splash_timeout);
	}

}
