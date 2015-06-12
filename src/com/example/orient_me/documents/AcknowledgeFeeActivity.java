package com.example.orient_me.documents;

import java.io.IOException;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.orient_me.MainActivity;
import com.example.orient_me.R;
import com.example.orient_me.helpers.PreferencesHelper;
import com.savagelook.android.UrlJsonAsyncTask;

public class AcknowledgeFeeActivity extends AppCompatActivity implements OnClickListener {

	Button acknowledgeButton;
	private final static String ACKFEE_API_ENDPOINT_URL = "http://apu-orientation.herokuapp.com/api/tasks";	
	PreferencesHelper prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acknowledge_fee);
		prefs = new PreferencesHelper(this);
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
	        	  loadTasksFromAPI(ACKFEE_API_ENDPOINT_URL);
	          }
	      })
	      .setNegativeButton("No", null)
	      .show();
	}
	
	private void loadTasksFromAPI(String url) {
	    AcknowledgeTask task = new AcknowledgeTask(this);
	    task.execute(url);
	}
	
	private class AcknowledgeTask extends UrlJsonAsyncTask {
	    public AcknowledgeTask(Context context) {
	       super(context);
	    }

	    @Override
	    protected JSONObject doInBackground(String... urls) {
	        DefaultHttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost(urls[0]);
	        JSONObject holder = new JSONObject();
	        JSONObject studentObj = new JSONObject();
	        String response = null;
	        JSONObject json = new JSONObject();
	        
	        try {
	            try {
	                // setup the returned values in case
	                // something goes wrong
	                json.put("success", false);
	                json.put("info", "Something went wrong. Retry!");

	                // add the users's info to the post params
	                studentObj.put("auth_token", prefs.GetPreferences("AuthToken"));
	                holder.put("student", studentObj);
	                StringEntity se = new StringEntity(holder.toString());
	                post.setEntity(se);

	                // setup the request headers
	                post.setHeader("Accept", "application/json");
	                post.setHeader("Content-Type", "application/json");

	                ResponseHandler<String> responseHandler = new BasicResponseHandler();
	                response = client.execute(post, responseHandler);
	                json = new JSONObject(response);

	            } catch (HttpResponseException e) {
	                e.printStackTrace();
	                Log.e("ClientProtocol", "" + e);
	            } catch (IOException e) {
	                e.printStackTrace();
	                Log.e("IO", "" + e);
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();
	            Log.e("JSON", "" + e);
	        }

	        return json;
	    }

	    @Override
	    protected void onPostExecute(JSONObject json) {
	        try {
	            if (json.getBoolean("success")) {
	                // everything is ok
	            	prefs.SavePreferences("FeeAck?", "Yes");
	            	prefs.DeletePreferences("AuthToken");
	                Intent intent = new Intent(AcknowledgeFeeActivity.this, MainActivity.class);
	                startActivity(intent);
	                finish();
	            	Log.d("AckAct", "Successfully updated the value");
	            }
	        } catch (Exception e) {
	            // something went wrong: show a Toast
	            // with the exception message
	            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
	        } finally {
	            super.onPostExecute(json);
	        }
	    }
	}
}
