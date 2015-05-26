package com.example.orient_me.schedules;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.orient_me.R;

public class ScheduleListAdapter extends ArrayAdapter<Schedule> {

	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public ScheduleListAdapter(Context context, List<Schedule> schedules) {
		super(context, R.layout.custom_schedules_list, schedules);
	}
	
	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		
		if (convertView == null){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.custom_schedules_list,
					parent, false);	
			
			viewHolder = new ViewHolder(); 
			
			viewHolder.title = (TextView) convertView.findViewById(R.id.cslT_title); 
			viewHolder.time = (TextView) convertView.findViewById(R.id.cslT_time);
			viewHolder.loc = (TextView) convertView.findViewById(R.id.cslT_loc);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
				
		Schedule schedule = getItem(position);
		viewHolder.title.setText(schedule.getTitle());
		viewHolder.loc.setText(schedule.getLocation());
		
		Calendar start = Calendar.getInstance();
		start.setTimeInMillis(Long.parseLong(schedule.getStart()));
		Calendar end = Calendar.getInstance();
		end.setTimeInMillis(Long.parseLong(schedule.getEnd()));
		
		viewHolder.time.setText(dtf.format(start.getTime()) + "\nto\n" + dtf.format(end.getTime()));
		
		return convertView;
	}
	
	private static class ViewHolder {
		TextView title, time, loc;
	}
}