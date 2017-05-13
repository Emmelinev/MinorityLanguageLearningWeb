package com.bacaling.entity;

import java.util.ArrayList;

public class Bars extends Lesson{
	private String barId;
	private String barName;
	private String barMember;
	private boolean status;
	
	public Bars() {
//		super(lessonName);
		this.barId = null;
		this.barName = null;
		this.barMember = null;
	}
	public String getBarId() {
		return barId;
	}
	public void setBarId(String barId) {
		this.barId = barId;
	}
	public String getBarName() {
		return barName;
	}
	public void setBarName(String barName) {
		this.barName = barName;
	}
	public String getBarMember() {
		return barMember;
	}
	public void setBarMember(String barMember) {
		this.barMember = barMember;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
