package com.example.orient_me.notes;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class NoteListFragment extends ListFragment {
	List<Note> notes;
	
	FragmentActivity context;
	NoteDatabaseHelper db;
	NoteListAdapter nla;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (FragmentActivity) super.getActivity();
        // initialize the items list
        notes = new ArrayList<Note>();
        
        loadList();        
    }
    
    private void loadList(){
        db = new NoteDatabaseHelper(context);
        notes = db.getAllNote();
        
        // initialize and set the list adapter
        nla = new NoteListAdapter(getActivity(), notes);
		nla.notifyDataSetChanged();
		setListAdapter(nla);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
        Note note = notes.get(position);
        
		Intent intent = new Intent(context, EditNoteActivity.class);
		intent.putExtra("id", note.getId());
		startActivity(intent);
		loadList();
    }
	
	public void onResume(){
		loadList();
		super.onResume();
	}
}
