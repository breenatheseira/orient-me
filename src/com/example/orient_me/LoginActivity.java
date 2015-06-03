package com.example.orient_me;

import java.io.IOException;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orient_me.helpers.PreferencesHelper;
import com.savagelook.android.UrlJsonAsyncTask;

public class LoginActivity extends AppCompatActivity {

	private final static String LOGIN_API_ENDPOINT_URL = "http://apu-orientation.herokuapp.com/api/sessions.json";	
	Button submitButton;
	EditText usernameTextF, passwordTextF;
	String s_username, s_password;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        submitButton = (Button) findViewById(R.id.laB_Submit);
        usernameTextF = (EditText) findViewById(R.id.laTF_Username);
        passwordTextF = (EditText) findViewById(R.id.laTF_Password);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void login(View button){    	
    	s_username = usernameTextF.getText().toString();
    	s_password = passwordTextF.getText().toString();
    	
    	if (s_username.length() == 0 || s_password.length() == 0){
    		Toast.makeText(this, "Please complete the username and password fields", Toast.LENGTH_LONG).show();
    		return;
    	} else {
    		LoginTask loginTask = new LoginTask(LoginActivity.this);
    		loginTask.setMessageLoading("Logging In...");
    		loginTask.execute(LOGIN_API_ENDPOINT_URL);
    	}
    }
    
    private class LoginTask extends UrlJsonAsyncTask {
    	public LoginTask(Context context){
    		super(context);
    	}
    	
    	@Override
    	protected JSONObject doInBackground(String... urls){
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject holder = new JSONObject();
            JSONObject studentObj = new JSONObject();
            String response = null;
            JSONObject json = new JSONObject();
            
            try {
            	try {
            		studentObj.put("email", s_username);
            		studentObj.put("password", s_password);
            		holder.put("student", studentObj);
            		
            		StringEntity se = new StringEntity(holder.toString());
            		
            		post.setEntity(se);
            		
            		post.setHeader("Accept","application/json");
            		post.setHeader("Content-Type", "application/json");
            		
            		ResponseHandler<String> responseHandler = new BasicResponseHandler();
            		response = client.execute(post, responseHandler);
            		json =  new JSONObject(response);            		
            	} catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                    Toast.makeText(getApplicationContext(), "Email and/or password are invalid. Retry!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }

            return json;
        }
    	@SuppressWarnings("deprecation")
		protected void onPostExecute(JSONObject json) {
    		PreferencesHelper prefs = new PreferencesHelper(getApplicationContext());
    		
    		try {
                if (json.getBoolean("success")) {
                	prefs.SavePreferences("AuthToken", json.getJSONObject("data").getString("auth_token"));
                	prefs.SavePreferences("Name", json.getJSONObject("data").getString("name"));
                	prefs.SavePreferences("Username", json.getJSONObject("data").getString("username"));
                	prefs.SavePreferences("IntakeCode", json.getJSONObject("data").getString("intake_code"));

                	// Shared Preferences to get the URLs of the documents
                	JSONObject doc = new JSONObject();
                	doc = json.getJSONObject("data").getJSONObject("documents");
                	
                	prefs.SavePreferences("OrientationSchedule", doc.getJSONArray("orientation_schedule").getJSONObject(0).getJSONObject("document_url").getString("url"));
                	prefs.SavePreferences("StudentHandbook", doc.getJSONArray("handbook").getJSONObject(0).getJSONObject("document_url").getString("url"));
                	prefs.SavePreferences("ImportantDetails", doc.getJSONArray("important_details").getJSONObject(0).getJSONObject("document_url").getString("url"));
                	prefs.SavePreferences("FeeSchedule",  doc.getJSONArray("fee_schedule").getJSONObject(0).getJSONObject("document_url").getString("url"));
                	prefs.SavePreferences("CourseSchedule",  "http://titan.apiit.edu.my/courseschedule/" + getCourseType(prefs.GetPreferences("IntakeCode")) + "/" + prefs.GetPreferences("IntakeCode") + ".pdf");
                }
               
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
                Log.d("Login Activity", prefs.GetPreferences("CourseSchedule"));
                Intent intent = new Intent (context, SplashLoginActivity.class);
                startActivity(intent);
            	finish();
            }
    	}
    }   
    private String getCourseType(String course){
    	if (course.startsWith("UCF"))
    		return "Foundation";
    	else
    		return "Diploma";
    }
}
