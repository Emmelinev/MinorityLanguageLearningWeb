package com.bacaling.entity;


public class User {
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
	private int userId;
	private String userName;
	private String phoneNum;
	private String password;
	
	public User(String phoneNum) {
		phoneNum = phoneNum;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
 