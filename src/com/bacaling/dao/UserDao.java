package com.bacaling.dao;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.bacaling.entity.Client;
import com.bacaling.entity.User;
import com.bacaling.util.DateClass;;

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
public class UserDao extends BaseDao {
	/**
	 * @param userName
	 * @param password
	 * @return true 成功登录 false 登录失败
	 * @throws SQLException 
	 */
//	public boolean login(String userName,String upwd){
//		
//		boolean ret=false;
//		String sql="select * from user_info where " +
//				"user_name='"+userName+"' and pwd='"+upwd+"'";
//		ResultSet rs=super.executeQuery(sql);
//		try {
//			if(rs.next()){
//				ret=true;
////				 String tel = rs.getString("tel");  
////	                String pwd = rs.getString("pwd");  
////	                System.out.println("tel:"+tel+" pwd:"+pwd);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ret;
//	}
	/*
	 * 新用户注册
	 */
	public int newUserReg2(String phonenum, String pwd) throws SQLException{
//		Client client = null;
		String sql = "insert into " + 
					 "user_info(user_name,user_tel,user_password,user_privilege,user_reg_date,current_language) " + 
					 "values('"+phonenum+"','"+phonenum+"','"+pwd+"',0,'" + DateClass.getDateNow() + "',3);";
		System.out.println(phonenum);
		System.out.println(pwd);
		System.out.println(sql);
//		Object[] parm = new Object[2];
//		parm[0] = phonenum;-
//		parm[1] = phonenum;
//		parm[2] = pwd;
		return super.executeUpdate(sql);
	}
	public Client newUserReg(String phonenum, String pwd) throws SQLException{
		Client client = null;
		String sql = "insert into " + 
					 "user_info(user_name,user_tel,user_password,user_privilege,user_reg_date,current_language) " + 
					 "values('"+phonenum+"','"+phonenum+"','"+pwd+"',0,'" + DateClass.getDateNow() + "',3);";
		System.out.println(phonenum);
		System.out.println(pwd);
		System.out.println(sql);
//		Object[] parm = new Object[2];
//		parm[0] = phonenum;
//		parm[1] = phonenum;
//		parm[2] = pwd;
		
		int r=super.executeUpdate(sql);
		if(r>0){
			System.out.println(r);
			String sql2 = "select a.user_id,a.user_name,a.user_tel,a.user_password,"
					+ "a.user_state,b.language_name,c.exp "
					+ "from user_info a,"
					+ "language_dictionary b,"
					+ "user_sr_static c"
					+ "where user_info.user_id = user_sr_static.user_id"
					+ "and user_info.current_language = language_dictionary.language_id"
					+ "and user_tel='"+phonenum+"' and current_language = 3";
//			Object[] parm2 = new Object[2];
//			parm[0] = phonenum;
			ResultSet rs = super.executeQuery(sql2);
			try {
				while(rs.next()){
					client = new Client(phonenum);
					client.setUserId(rs.getInt("user_id"));
					client.setPhoneNum(rs.getString("user_tel"));
					client.setUserName(rs.getString("user_name"));
					client.setPassword(rs.getString("user_password"));
					client.setState(rs.getInt("user_state"));
					client.setCurrentLanguage(3);
				}
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}		
		return client;
	}
	/*
	 * 用户名查重
	 */
	public boolean userIdentify(Client client){
		String sql="select * from bacaling.user_info where user_tel = ?";
		boolean re = true;
		Object[] parm = new Object[1];
		parm[0] = client.getPhoneNum();

		ResultSet rs = super.executeQuery(sql, parm);
		try {
			if(rs.next()){
				re = false;
			}else{
				re = true;
			}
		}catch (SQLException e) {
			re = true;
			e.printStackTrace();
		}
		return re;
	}
	/*
	 * 更改手机号
	 */
	public int updateUserTel(String oldTel, String newTel) throws SQLException{
		String sql = "update bacaling.user_info " +
			     	 "set user_tel = ? "+
			     	 "where user_tel = ? ";
		Object[] parm = new Object[2];
		parm[0] = newTel;
		parm[1] = oldTel;

		return super.executeUpdate(sql, parm);
	}
	
	/*
	 * 更改密码
	 */
	public int updatePassword(String userId, String password) throws SQLException{
		String sql = " update bacaling.user_info " +
			     	 "set user_password = ? "+
			     	 "where user_id = ? ";
		Object[] parm = new Object[2];
		parm[0] = password;
		parm[1] = userId;

		return super.executeUpdate(sql, parm);
	}
	/*
	 * 更改用户名
	 */
	public int updateUserName(String userId, String userName) throws SQLException{
		String sql = "update bacaling.user_info " +
			     	 "set user_name = ? "+
			     	 "where user_id = ? "; 
		Object[] parm = new Object[2];
		parm[0] = userName;
		parm[1] = userId;

		return super.executeUpdate(sql, parm);
	}
	/*
	 * 更改邮箱
	 */
	public int updateEmail(String userId, String userEmail) throws SQLException{
		String sql = "update bacaling.user_info " +
			     	 "set user_email = ? "+
			     	 "where user_id = ? "; 
		Object[] parm = new Object[2];
		parm[0] = userEmail;
		parm[1] = userId;

		return super.executeUpdate(sql, parm);
	}	
	/*
	 * 更改头像
	 */
	public int updateImg(String userId, String url) throws SQLException{
		String sql = "update bacaling.user_info " +
			     	 "set profile_img = ? "+
			     	 "where user_id = ? "; 
		Object[] parm = new Object[2];
		parm[0] = url;
		parm[1] = userId;

		return super.executeUpdate(sql, parm);
	}	
	public Client queryUser(String userInfo)
	{
		Client client = null;
		String sql="select * from bacaling.user_info where " +
				"user_name='" + userInfo + "' or user_tel = '" + userInfo + "'";
		ResultSet rs=super.executeQuery(sql);
		try {
			while(rs.next()){
				client = new Client(userInfo);
				client.setUserId(rs.getInt("user_id"));
				client.setUserName(rs.getString("user_name"));
				client.setPhoneNum(rs.getString("user_tel"));
				client.setPassword(rs.getString("user_password"));
				client.setUserEmail(rs.getString("user_email"));
				client.setCurrentLanguage(rs.getInt("current_language"));
				client.setState(rs.getInt("user_state"));
				client.setProfileImg(rs.getString("profile_img"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return client;
	}	
}
