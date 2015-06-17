package com.breenatheseira.orient_me.schedules;

public class Schedule {
	String id, alert, transparent, title, location, notes, start, end;
	
	Schedule(){}
	Schedule(String id, String alert, String transparent, String title, String location, String notes, String start, String end){
		this.id = id;
		this.alert = alert;
		this.transparent = transparent;
		this.title = title;
		this.location = location;
		this.notes = notes;
		this.start = start;
		this.end = end;
	}
	
	public String getId() {
		return id;
	}
	public String getAlert() {
		return alert;
	}
	public String getTransparent() {
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
	public String getStart() {
		return start;
	}
	public String getEnd() {
		return end;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public void setAlert(String alert){
		this.alert = alert;
	}
	public void setTransparent(String transparent) {
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
	public void setStart(String start) {
		this.start = start;
	}
	public void setEnd(String end) {
		this.end = end;
	}
}
