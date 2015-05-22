package com.example.orient_me.schedules;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	String[] titleArr, sTimeArr, eTimeArr, locArr;
	ArrayList<String> titles = new ArrayList<String>();
	ArrayList<String> sTime = new ArrayList<String>(); 
	ArrayList<String> eTime = new ArrayList<String>();
	ArrayList<String> loc = new ArrayList<String>();
	List<Schedule> schedules;
	SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public ScheduleListAdapter(Context context, int resource, List<Schedule> schedules) {
		super(context, resource, schedules);
		this.schedules = schedules;
		this.context = context;
		
		for (Schedule schedule : schedules){
			titles.add(schedule.getTitle());
			sTime.add(schedule.getStart());
			eTime.add(schedule.getEnd());
			loc.add(schedule.getLocation());
		}
		
		titleArr = new String[schedules.size()];
		titleArr = titles.toArray(titleArr);
		sTimeArr = new String[schedules.size()];
		sTimeArr = sTime.toArray(sTimeArr);
		eTimeArr = new String[schedules.size()];
		eTimeArr = eTime.toArray(eTimeArr);
		locArr = new String[schedules.size()];
		locArr = loc.toArray(locArr);
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
		start.setTimeInMillis(Long.parseLong(sTimeArr[position]));
		Calendar end = Calendar.getInstance();
		end.setTimeInMillis(Long.parseLong(eTimeArr[position]));
		
		title.setText(titleArr[position]);
		time.setText(dtf.format(start.getTime()) + "\nto\n" + dtf.format(end.getTime()));
		loc.setText(locArr[position]);
		
		return scheduleListItem;
	}
}
