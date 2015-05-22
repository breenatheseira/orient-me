package com.example.orient_me.badges;

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
public class BadgesListAdapter extends ArrayAdapter<String> {

	Context context;
	Badge badge;

	public BadgesListAdapter(Context context, int resource, Badge badge) {
		super(context, resource);
		this.context = context;
		this.badge = badge;
	}

	@Override
	// Rakhita (2011) Custom Adapter For List View. [Online]. Available from: http://stackoverflow.com/questions/8166497/custom-adapter-for-list-view [Accessed: 22 May 2015].
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View badgeListItem = inflater.inflate(R.layout.custom_badges_list,
				parent, false);
		ImageView pic = (ImageView) badgeListItem
				.findViewById(R.id.cbIV_badgepic);
		TextView name = (TextView) badgeListItem.findViewById(R.id.cbIV_name);
		TextView desc = (TextView) badgeListItem.findViewById(R.id.cbIV_desc);

		switch (Integer.parseInt(badge.getId())) {
		case 1:
			pic.setImageResource(R.drawable.badge_1);
			break;
		case 2:
			pic.setImageResource(R.drawable.badge_2);
			break;
		case 3:
			pic.setImageResource(R.drawable.badge_3);
			break;
		case 4:
			pic.setImageResource(R.drawable.badge_4);
			break;
		case 5:
			pic.setImageResource(R.drawable.badge_5);
			break;
		case 6:
			pic.setImageResource(R.drawable.badge_6);
			break;
		case 7:
			pic.setImageResource(R.drawable.badge_7);
			break;
		case 8:
			pic.setImageResource(R.drawable.badge_8);
			break;
		}
		name.setText(badge.getName());
		desc.setTag(badge.getDesc());
		return badgeListItem;
	}
}
