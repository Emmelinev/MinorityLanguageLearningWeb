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
	public List<Lesson> lessonList(String language){
		List<Lesson> lessons = null;
//		Lesson lesson = null;
		String sql = "select lesson_id,lesson_name,lesson_icon from lesson_list where of_language = ? and isvisible = 1";
		Object[] parm = new Object[1];
		parm[0] = language;
		
		ResultSet rs=super.executeQuery(sql,parm);
		try {
			while(rs.next()){
				Lesson lesson = new Lesson();
				lesson.setLessonId(rs.getInt("lesson_id"));
				lesson.setLessonName(rs.getString("lesson_name"));
				lesson.setLessonImg(rs.getString("lesson_icon"));
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
