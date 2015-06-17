package com.breenatheseira.orient_me.badges;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Badge {
	String id, name, desc, picture, unlocked_at;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getUnlocked_at() {
		return unlocked_at;
	}

	public void setUnlocked_at(String unlocked_at) {
		this.unlocked_at = unlocked_at;
	}
	
	public String getTimeNow(){
		SimpleDateFormat dtf = new SimpleDateFormat("dd MMM yyyy, HH:mm");
		return dtf.format(Calendar.getInstance().getTime());
	}
	
}
