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
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.savagelook.android.UrlJsonAsyncTask;

public class LoginActivity extends ActionBarActivity {

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
        
//        submitButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Toast.makeText(LoginActivity.this, "Clicked Submit!", Toast.LENGTH_SHORT).show();
//			}
//		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            		json.put("success", false);
            		json.put("info", "Sorry, something went wrong. Please try again.");
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
                    json.put("info", "Email and/or password are invalid. Retry!");
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
    	protected void onPostExecute(JSONObject json) {
    		PreferencesHelper prefs = new PreferencesHelper(getApplicationContext());
    		try {
                if (json.getBoolean("success")) {
                	prefs.SavePreferences("AuthToken", json.getJSONObject("data").getString("auth_token"));
                	prefs.SavePreferences("Name", json.getJSONObject("data").getString("name"));
                	prefs.SavePreferences("Username", json.getJSONObject("data").getString("username"));
                	prefs.SavePreferences("IntakeCode", json.getJSONObject("data").getString("intake_code")); 
                	
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
    	}
    }
}
