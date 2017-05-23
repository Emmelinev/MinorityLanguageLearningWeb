package com.bacaling.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bacaling.entity.Message;
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
public class MessageDao extends BaseDao {
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
	 * 向用户发送新信息
	 */
	public int newMessage(String user_id, String content) throws SQLException{
		Message msg = null;
		String sql = "insert into " + 
					 "message(send_id,rec_id,content_id,status,post_date) " + 
					 "values('0','"+user_id+"','"+content+"',0,'" + DateClass.getDateNow() + "');";
		int r=super.executeUpdate(sql);
		return r;
	}
	/*
	 * 获取信息
	 */
	public List<Message> getMessages(String user_id)
	{
		List<Message> msgList = new ArrayList<Message>();
		String sql="select message_id,content, date_format(b.post_date,'%Y-%m-%d') post_date,status"
				+ " from message_content a,message b"
				+ " where a.content_id = b.content_id and rec_id = " + user_id 
				+ " order by b.post_date desc limit 0,5;";
		ResultSet rs=super.executeQuery(sql);
		try {
			while(rs.next()){
				Message msg = new Message();
				msg.setMessageContent(rs.getString("content"));
				msg.setSendDate(rs.getDate("post_date").toString());
				msg.setStatus(rs.getInt("status"));
				if(rs.getInt("status") == 0){
					String sql2 = "update message set status = 1 where message_id = " 
								+ rs.getInt("message_id");
					int r = super.executeUpdate(sql2);
					System.out.println("update result-"+r);
				}
				System.out.println("content-"+msg.getMessageContent()+" date-"+msg.getSendDate()+"status-"+rs.getInt("status"));
				msgList.add(msg);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return msgList;
	}	
	public int hasNewMsg(String user_id){
		int ret = 0;
		String sql = "select count(*) newCount from message where rec_id = "
				+ user_id +" and status = 0"
				+ " order by post_date desc limit 0,5;";
		ResultSet rs=super.executeQuery(sql);
		try {
			while(rs.next()){
				ret = rs.getInt("newCount");
				System.out.println("newcount-" + ret);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
//	public int newMessage(String user_id,String message){
//		int ret = 0;
//		String sql = "insert into message(send_id,rec_id,content_id,status,post_date)"
//				+ "values(0,"+user_id+","+message+",0,'"+DateClass.getDateNow()+"');";
//		
//		return ret;
//	}
}
