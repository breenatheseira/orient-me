package com.example.orient_me.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.orient_me.R;

public class ViewNotesListFragment extends Fragment implements OnClickListener {

	FragmentActivity context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	     context = (FragmentActivity) super.getActivity();

	     LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_view_notes_list, container, false);
	     Button addButton = (Button) layout.findViewById(R.id.vnlfB_addNote);
	     
	     NoteListFragment noteList = (NoteListFragment) getChildFragmentManager().findFragmentById(R.id.vnlfB_listFragment);
	     noteList.getListView();
	     noteList.setListShownNoAnimation(true);
	     addButton.setOnClickListener(this);
	     
	     return layout; 
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(context, AddNoteActivity.class);
		startActivity(intent);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) { 
	    super.setUserVisibleHint(isVisibleToUser);
	    NoteDatabaseHelper ndb = new NoteDatabaseHelper(context);
	    
	    if (isVisibleToUser && ndb.getAllNote().isEmpty()) { 
	    	Toast.makeText(context, "Add a Note to View Your Notes List", Toast.LENGTH_SHORT).show();
	        Log.d("NSLF", "this fragment is now visible");
	    }
	}
}