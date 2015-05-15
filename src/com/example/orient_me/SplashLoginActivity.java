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
import android.view.Menu;
import android.view.MenuItem;

import com.example.orient_me.helpers.FileDownloader;
import com.example.orient_me.helpers.PreferencesHelper;

public class SplashLoginActivity extends AppCompatActivity {
	String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_login);
		
		PreferencesHelper prefs = new PreferencesHelper(getApplicationContext());
		
		String[] files = new String[8]; 
		files[0] = prefs.GetPreferences("StudentHandbook");
		files[1] = prefs.GetPreferences("ModuleList");
		files[2] = prefs.GetPreferences("OrientationSchedule");
		files[3] = prefs.GetPreferences("FeeSchedule");
		files[4] = "StudentHandbook.pdf";
		files[5] = "ModuleList.pdf";
		files[6] = "OrientationSchedule.pdf";
		files[7] = "FeeSchedule.pdf";
				
    	new DownloadFile().execute(files);
    	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_login, menu);
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
	
    // Zakaria, M. (2014) Android Download PDF From Url Then Open It With a PDF Reader. [Online]. Available from: http://stackoverflow.com/questions/24740228/android-download-pdf-from-url-then-open-it-with-a-pdf-reader. [Accessed: 25 April 2015].
    public class DownloadFile extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... files) {           

            File folder = new File(extStorageDirectory, "orientmepdf");
            if (!folder.exists())
            	folder.mkdir();

            for (int i = 0; i < 4; i++){
	            File pdfFile = new File(folder, files[i+4]);
	
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
        	prefs.SavePreferences("ModuleList", extStorageDirectory + "/orientmepdf/ModuleList.pdf");
        	prefs.SavePreferences("FeeSchedule", extStorageDirectory + "/orientmepdf/FeeSchedule.pdf");
            
            Intent intent = new Intent (getApplicationContext(), DocumentActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
