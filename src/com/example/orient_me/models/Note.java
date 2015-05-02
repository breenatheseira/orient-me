package com.example.orient_me.models;

public class Note {
String id, title, note;
	
	public Note(){}
	
	public Note(String id, String title, String note){
		this.id = id;
		this.title = title;
		this.note = note;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setNote(String note){
		this.note = note;
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getDocId(){
		return this.title;
	}
	
	public String getNote(){
		return this.note;
	}
}
