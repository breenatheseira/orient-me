package com.example.orient_me.schedules;

public class Schedule {
	int id, alert, transparent;
	String title, location, notes;
	long start, end;
	
	Schedule(){}
	Schedule(int id, int alert, int transparent, String title, String location, String notes, long start, long end){
		this.id = id;
		this.alert = alert;
		this.transparent = transparent;
		this.title = title;
		this.location = location;
		this.notes = notes;
		this.start = start;
		this.end = end;
	}
	
	public int getId() {
		return id;
	}
	public int getAlert() {
		return alert;
	}
	public int getTransparent() {
		return transparent;
	}
	public String getTitle() {
		return title;
	}
	public String getLocation() {
		return location;
	}
	public String getNotes() {
		return notes;
	}
	public long getStart() {
		return start;
	}
	public long getEnd() {
		return end;
	}
	
	public void setId(int id){
		this.id = id;
	}
	public void setAlert(int alert){
		this.alert = alert;
	}
	public void setTransparent(int transparent) {
		this.transparent = transparent;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setStart(long string) {
		this.start = string;
	}
	public void setEnd(long end) {
		this.end = end;
	}
}
