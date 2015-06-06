// wael (2014) How To Hide the File Download with Splash Screen Activity. [Online]. Available from: http://stackoverflow.com/questions/22585155/how-to-hide-the-file-download-with-splash-screen-activity [Accessed: 25 April 2015]. 
package com.example.orient_me;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orient_me.badges.Badge;
import com.example.orient_me.badges.BadgeDatabaseHelper;
import com.example.orient_me.documents.AcknowledgeFeeActivity;
import com.example.orient_me.helpers.FileDownloader;
import com.example.orient_me.helpers.PreferencesHelper;

public class SplashLoginActivity extends AppCompatActivity {
	String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_login);
		
		PreferencesHelper prefs = new PreferencesHelper(getApplicationContext());
		
		String[] files = new String[10]; 
		files[0] = prefs.GetPreferences("StudentHandbook");
		files[1] = prefs.GetPreferences("ImportantDetails");
		files[2] = prefs.GetPreferences("OrientationSchedule");
		files[3] = prefs.GetPreferences("FeeSchedule");
		files[4] = prefs.GetPreferences("CourseSchedule");
		files[5] = "StudentHandbook.pdf";
		files[6] = "ImportantDetails.pdf";
		files[7] = "OrientationSchedule.pdf";
		files[8] = "FeeSchedule.pdf";
		files[9] = "CourseSchedule.pdf";
				
    	new DownloadFile().execute(files);
    	showAchievement(8);
	}
	
    // Zakaria, M. (2014) Android Download PDF From Url Then Open It With a PDF Reader. [Online]. Available from: http://stackoverflow.com/questions/24740228/android-download-pdf-from-url-then-open-it-with-a-pdf-reader. [Accessed: 25 April 2015].
    public class DownloadFile extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... files) {           

            File folder = new File(extStorageDirectory, "orientmepdf");
            if (!folder.exists())
            	folder.mkdir();

            for (int i = 0; i < 5; i++){
	            File pdfFile = new File(folder, files[i+5]);
	
	            try{
	                pdfFile.createNewFile();
	            }catch (IOException e){
	                e.printStackTrace();
	            }
	            FileDownloader.downloadFile(files[i], pdfFile);
	            Log.d("FileDownload", "Success" + i);
            }
            return null;
        }
        
        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            PreferencesHelper prefs = new PreferencesHelper(getApplicationContext());
        	prefs.SavePreferences("OrientationSchedule", extStorageDirectory + "/orientmepdf/OrientationSchedule.pdf");
        	prefs.SavePreferences("StudentHandbook", extStorageDirectory + "/orientmepdf/StudentHandbook.pdf");
        	prefs.SavePreferences("ImportantDetails", extStorageDirectory + "/orientmepdf/ImportantDetails.pdf");
        	prefs.SavePreferences("FeeSchedule", extStorageDirectory + "/orientmepdf/FeeSchedule.pdf");
        	prefs.SavePreferences("CourseSchedule", extStorageDirectory + "/orientmepdf/CourseSchedule.pdf");
            
            Intent intent = new Intent (getApplicationContext(), MainActivity.class);
            showAchievement(8);
            startActivity(intent);
            finish();
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
			image.setImageResource(R.drawable.badge_8);
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
}
