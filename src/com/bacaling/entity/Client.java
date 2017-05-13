package com.bacaling.entity;

public class Client extends User {
	/***
	 * create table user_info(
		user_id int auto_increment primary key not null,
	    user_name varchar(20),
	    user_tel char(11),
	    user_password varchar(50) not null,
	    user_email varchar(100),
	    user_state int,
	    user_privilege int,
	    user_reg_date  datetime
	);
	 */
	private String userEmail;
	private String regDate;
	private int state;
	private int userPrivilege;	
	private int currentLanguage;
	private int dailyGoal;
	private String profileImg;
	private int exp;

	public Client(String userTel) {
		super(userTel);
		//可以加数字的限制和手机号码位数的限制，可以重写getUserName函数，加上设置条件
	}
	
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String reg_Date) {
		this.regDate = reg_Date;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getUserPrivilege() {
		return userPrivilege;
	}

	public void setUserPrivilege(int userPrivilege) {
		this.userPrivilege = userPrivilege;
	}

	public int getCurrentLanguage() {
		return currentLanguage;
	}

	public void setCurrentLanguage(int currentLanguage) {
		this.currentLanguage = currentLanguage;
	}

	public int getDailyGoal() {
		return dailyGoal;
	}

	public void setDailyGoal(int dailyGoal) {
		this.dailyGoal = dailyGoal;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
	
}

