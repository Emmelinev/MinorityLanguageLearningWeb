package com.bacaling.entity;

import java.util.ArrayList;

public class Bars extends Lesson{
	private String barId;
	private String barName;
	private String barMember;
	private int status;
	
	public Bars() {
//		super(lessonName);
		this.barId = null;
		this.barName = null;
		this.barMember = null;
		this.status = 0;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
