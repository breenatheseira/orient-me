package com.example.orient_me.notes;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
        
		if (notes.isEmpty()){
			Log.d("NLF", "display toast");
			Toast.makeText(context, "Add a Note", Toast.LENGTH_SHORT).show();
			return;
		}
        // initialize and set the list adapter
		nla = new NoteListAdapter(getActivity(), notes); 
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
