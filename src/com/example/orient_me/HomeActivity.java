package com.example.orient_me;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.savagelook.android.UrlJsonAsyncTask;

public class HomeActivity extends ActionBarActivity {

	private static final String TASKS_URL = "http://apu-orientation.herokuapp.com/api/tasks.json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		loadTasksFromAPI(TASKS_URL);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
	}

	private void loadTasksFromAPI(String url) {
		GetTasksTask getTasksTask = new GetTasksTask(HomeActivity.this);
		getTasksTask.setMessageLoading("Loading tasks...");
		getTasksTask.execute(url);
	}
	
	private class GetTasksTask extends UrlJsonAsyncTask {
	    public GetTasksTask(Context context) {
	        super(context);
	    }

	    @Override
	        protected void onPostExecute(JSONObject json) {
	            try {
	                JSONArray jsonTasks = json.getJSONObject("data").getJSONArray("tasks");
	                int length = jsonTasks.length();
	                List<String> tasksTitles = new ArrayList<String>(length);

	                for (int i = 0; i < length; i++) {
	                    tasksTitles.add(jsonTasks.getJSONObject(i).getString("title"));
	                }

	                ListView tasksListView = (ListView) findViewById (R.id.haLV_tasks);
	                if (tasksListView != null) {
	                    tasksListView.setAdapter(new ArrayAdapter<String>(HomeActivity.this,
	                      android.R.layout.simple_list_item_1, tasksTitles));
	                }
	            } catch (Exception e) {
	            Toast.makeText(context, e.getMessage(),
	                Toast.LENGTH_LONG).show();
	        } finally {
	            super.onPostExecute(json);
	        }
	    }
	}
}
