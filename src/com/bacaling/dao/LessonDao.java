package com.bacaling.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bacaling.entity.Lesson;


public class LessonDao extends BaseDao{
	/*
	 *加载课程
	 */
	public List<Lesson> lessonList(String user_id,String language){
		List<Lesson> lessons = null;
//		Lesson lesson = null;
		String sql = "select c.lesson_name,c.lesson_icon,d.progress progress,ifnull(uid,2) uid "
				+ "from lesson_list c left join (select ifnull(b.user_id,"
				+ user_id +") uid,a.lesson_id lesson_id,ifnull(progress,0) progress "
				+ "from lesson_list a left join v_lesson_user b on a.lesson_id = b.lesson_id "
				+ "where a.of_language = " 
				+ language + ") d on c.lesson_id = d.lesson_id "
				+ "where c.isvisible = 1 and c.of_language = " 
				+ language + " and uid = " + user_id + ";";
		
		ResultSet rs=super.executeQuery(sql);
		try {
			while(rs.next()){
				Lesson lesson = new Lesson();
				lesson.setLessonId(rs.getInt("lesson_id"));
				lesson.setLessonName(rs.getString("lesson_name"));
				lesson.setLessonImg(rs.getString("lesson_icon"));
				lesson.setProgress(rs.getDouble("progress"));
				lessons.add(lesson);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return lessons;
	}
	/*
	 *课程进度
	 */
	public Lesson lessonProgress(String language,String user_id){
		Lesson lessons = null;
//		Lesson lesson = null;
		String sql = "select lesson_id,passed_times,bar_num,progress "
				+ "from v_lesson_user "
				+ "where language = ? and user_id = ?";
		Object[] parm = new Object[2];
		parm[0] = language;
		parm[1] = user_id;
		
		ResultSet rs=super.executeQuery(sql,parm);
		try {
			while(rs.next()){
				Lesson lesson = new Lesson();
				lesson.setLessonId(rs.getInt("lesson_id"));
				lesson.setLessonName(rs.getString("lesson_name"));
				lesson.setLessonImg(rs.getString("lesson_icon"));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return lessons;
	}
}
