package com.example.orient_me.badges;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orient_me.R;

@SuppressLint("ViewHolder")
public class BadgesListAdapter extends ArrayAdapter<Badge> {

	List<Badge> badges;
	
	public BadgesListAdapter(Context context, List<Badge> badges) {
		super(context, R.layout.custom_badges_list, badges);
		this.badges = badges;
	}

	@Override
	// Rakhita (2011) Custom Adapter For List View. [Online]. Available from: http://stackoverflow.com/questions/8166497/custom-adapter-for-list-view [Accessed: 22 May 2015].
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null){
		
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.custom_badges_list, parent, false);
			
			viewHolder = new ViewHolder();
			viewHolder.pic = (ImageView) convertView.findViewById(R.id.cbIV_badgepic);
			viewHolder.name = (TextView) convertView.findViewById(R.id.cbIV_name);
			viewHolder.desc = (TextView) convertView.findViewById(R.id.cbIV_desc);
			viewHolder.time = (TextView) convertView.findViewById(R.id.cbIV_unlocked_at);
			convertView.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		switch (position+1) {
		case 1:
			viewHolder.pic.setImageResource(R.drawable.badge_1);
			break;
		case 2:
			viewHolder.pic.setImageResource(R.drawable.badge_2);
			break;
		case 3:
			viewHolder.pic.setImageResource(R.drawable.badge_3);
			break;
		case 4:
			viewHolder.pic.setImageResource(R.drawable.badge_4);
			break;
		case 5:
			viewHolder.pic.setImageResource(R.drawable.badge_5);
			break;
		case 6:
			viewHolder.pic.setImageResource(R.drawable.badge_6);
			break;
		case 7:
			viewHolder.pic.setImageResource(R.drawable.badge_7);
			break;
		case 8:
			viewHolder.pic.setImageResource(R.drawable.badge_8);
			break;
		}
		
		Badge badge = getItem(position);
		viewHolder.name.setText(badge.getName());
		viewHolder.desc.setText(badge.getDesc());
		String timeMessage, badgeTime = badge.getUnlocked_at();
		
		if (badgeTime.isEmpty()) {
			viewHolder.pic.setImageResource(R.drawable.badge_0);
			timeMessage = "Still at large! Explore to unlock!";
		} else {
			timeMessage = "Congrats! Unlocked on: " + badgeTime;
		}
		viewHolder.time.setText(timeMessage);
		return convertView;
	}
	
	private static class ViewHolder{
		TextView name, desc, time;
		ImageView pic;
	}
}
