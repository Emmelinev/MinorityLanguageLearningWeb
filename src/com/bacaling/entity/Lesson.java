package com.bacaling.entity;

public class Lesson {
	private int lessonId;
	private String lessonName;
	private String lessonImg;
	private String ofLanguage;
	private int allowPic;
	private double progress;
	private String color;
	private int passed;
	private int number;
	

	public Lesson() {
//		this.lessonName = lessonName;
		this.lessonId = 0;
		this.lessonName = null;
		this.lessonImg = null;
		this.ofLanguage = null;
		this.allowPic = 0;
	}
	public int getLessonId() {
		return lessonId;
	}
	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}
	public String getLessonName() {
		return lessonName;
	}
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	public String getLessonImg() {
		return lessonImg;
	}
	public void setLessonImg(String lessonImg) {
		this.lessonImg = lessonImg;
	}
	public String getOfLanguage() {
		return ofLanguage;
	}
	public void setOfLanguage(String ofLanguage) {
		this.ofLanguage = ofLanguage;
	}
	public int getAllowPic() {
		return allowPic;
	}
	public void setAllowPic(int allowPic) {
		this.allowPic = allowPic;
	}
	public double getProgress() {
		return progress;
	}
	public void setProgress(double progress) {
		this.progress = progress;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getPassed() {
		return passed;
	}
	public void setPassed(int passed) {
		this.passed = passed;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
