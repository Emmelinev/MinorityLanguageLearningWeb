package com.bacaling.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bacaling.entity.Client;
import com.bacaling.entity.User;
import com.bacaling.entity.UserWord;
import com.bacaling.util.DateClass;
import com.bacaling.util.LessonInfo;;


public class UserDao extends BaseDao {

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
	 * 查询用户信息设置
	 */
	public Client queryProfile(String userId){
		Client client = null;
		String sql="select * from bacaling.user_info where " +
				"user_id='" + userId;
		ResultSet rs=super.executeQuery(sql);
		try {
			while(rs.next()){
				client = new Client(userId);
				client.setAutoplay(rs.getInt("autoplay"));
				client.setEffect(rs.getInt("effect"));
				client.setMailNotice(rs.getInt("mail_notice"));
				client.setDailyGoal(rs.getInt("daily_goal"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return client;
	}
	/*
	 * 语言经验值
	 */
	public Client queryExp(String userId, String language){
		Client client = null;
		String sql="select user_id,language_id,exp from bacaling.user_sr_static where " +
				"user_id='" + userId + "' and language_id = " + language;
		System.out.println(sql);
		ResultSet rs=super.executeQuery(sql);
		try {
			while(rs.next()){
				client = new Client(userId);
				client.setExp(rs.getInt("exp"));
				client.setLevel(LessonInfo.setLevel(rs.getInt("exp")));
				client.setCurrentLanguage(rs.getInt("language_id"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return client;
	}
	/*
	 * 语言经验值列表
	 */
	public List<Client> queryExpList(String userId){
		List<Client> list = new ArrayList<Client>();
		String sql="select user_id,language_id,exp from bacaling.user_sr_static where " +
				"user_id='" + userId + "';";
		System.out.println(sql);
		ResultSet rs=super.executeQuery(sql);
		try {
			while(rs.next()){
				Client client = null;
				client = new Client(userId);
				client.setExp(rs.getInt("exp"));
				client.setLevel(LessonInfo.setLevel(rs.getInt("exp")));
				client.setCurrentLanguage(rs.getInt("language_id"));
				list.add(client);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * 更改手机号
	 */
	public int updateUserTel(String oldTel, String newTel) throws SQLException{
		String sql = "update bacaling.user_info " +
			     	 "set user_tel = "+ newTel +
			     	 "where user_tel = " + oldTel;
		return super.executeUpdate(sql);
	}
	
	/*
	 * 更改密码
	 */
	public int updatePassword(String userId, String password) throws SQLException{
		String sql = " update bacaling.user_info " +
			     	 "set user_password = "+ password +
			     	 "where user_id = " + userId;
		return super.executeUpdate(sql);
	}
	/*
	 * 更改用户名
	 */
	public int updateUserName(String userId, String userName) throws SQLException{
		String sql = "update bacaling.user_info " +
			     	 "set user_name = "+ userName +
			     	 "where user_id = " + userId; 
		return super.executeUpdate(sql);
	}
	/*
	 * 更改邮箱
	 */
	public int updateEmail(String userId, String userEmail) throws SQLException{
		String sql = "update bacaling.user_info " +
			     	 "set user_email = "+ userEmail +
			     	 "where user_id = " +userId; 
		return super.executeUpdate(sql);
	}	
	/*
	 * 更改头像
	 */
	public int updateImg(String userId, String url) throws SQLException{
		String sql = "update bacaling.user_info " +
			     	 "set profile_img = "+ url +
			     	 "where user_id = " + userId; 
		return super.executeUpdate(sql);
	}
	/*
	 * 更改每日目标
	 */
	public int updateGoal(String userId,String goal) throws SQLException{
		String sql = "update bacaling.user_info " +
			     	 "set daily_goal = "+ goal +
			     	 "where user_id = " + userId; 
		return super.executeUpdate(sql);
	}
	/*
	 * 更改邮件提醒
	 */
	public int updateNotice(String userId,String result) throws SQLException{
		String sql = "update bacaling.user_info " +
			     	 "set mail_notice = "+ result +
			     	 "where user_id = " + userId; 
		return super.executeUpdate(sql);
	}
	/*
	 * 更改自动播放
	 */
	public int updateAutoplay(String userId,String result) throws SQLException{
		String sql = "update bacaling.user_info " +
			     	 "set autoplay = "+ result +
			     	 "where user_id = " + userId; 
		return super.executeUpdate(sql);
	}
	/*
	 * 更改音效
	 */
	public int updateEffect(String userId,String result) throws SQLException{
		String sql = "update bacaling.user_info " +
			     	 "set effect = "+ result +
			     	 "where user_id = " + userId; 
		return super.executeUpdate(sql);
	}
	/*
	 * 更改邮件通知
	 */
	public int updateMail(String userId,String notice) throws SQLException{
		String sql = "update bacaling.user_info " +
			     	 "set mail_notice = "+ notice +
			     	 "where user_id = " + userId; 
		return super.executeUpdate(sql);
	}
	/*
	 * 注销用户
	 */
	public int deactiveAccount(String userId) throws SQLException{
		String sql = "update user_info "
				+ "set status = -1 "
				+ "where user_id = " + userId;
		return super.executeUpdate(sql);
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
