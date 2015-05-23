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

	Context context;
	List<Schedule> schedules;
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public ScheduleListAdapter(Context context, int resource, List<Schedule> schedules) {
		super(context, resource, schedules);
		this.schedules = schedules;
		this.context = context;
	}
	
	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View scheduleListItem = inflater.inflate(R.layout.custom_schedules_list,
				parent, false);
		
		TextView title = (TextView) scheduleListItem.findViewById(R.id.cslT_title);
		TextView time = (TextView) scheduleListItem.findViewById(R.id.cslT_time);
		TextView loc = (TextView) scheduleListItem.findViewById(R.id.cslT_loc);
		
		Calendar start = Calendar.getInstance();
		start.setTimeInMillis(Long.parseLong(schedules.get(position).getStart()));
		Calendar end = Calendar.getInstance();
		end.setTimeInMillis(Long.parseLong(schedules.get(position).getEnd()));
		
		title.setText(schedules.get(position).getTitle());
		time.setText(dtf.format(start.getTime()) + "\nto\n" + dtf.format(end.getTime()));
		loc.setText(schedules.get(position).getLocation());
		
		return scheduleListItem;
	}
}
