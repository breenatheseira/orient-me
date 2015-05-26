package com.example.orient_me.notes;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.orient_me.R;

public class NoteListAdapter extends ArrayAdapter<Note>{

	public NoteListAdapter(Context context, List<Note> notes) {
		super(context, R.layout.custom_notes_list, notes);
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		
		if (convertView == null){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.custom_notes_list,
					parent, false);	
			
			viewHolder = new ViewHolder(); 
			
			viewHolder.title = (TextView) convertView.findViewById(R.id.cnl_title); 
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
				
		Note note = getItem(position);
		viewHolder.title.setText(note.getTitle());
		
		return convertView;
	}
	
	private static class ViewHolder {
		TextView title;
	}
}
