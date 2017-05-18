package com.bacaling.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bacaling.entity.Bars;
import com.bacaling.entity.Lesson;


public class LessonDao extends BaseDao{
	/*
	 *加载课程
	 */
	public List<Lesson> lessonList(String user_id,String language){
		List<Lesson> lessons = new ArrayList<Lesson>();
//		Lesson lesson = null;
		String sql = "select c.lesson_id lesson_id,c.lesson_name,c.lesson_icon,c.background_color color,d.progress progress,ifnull(uid,2) uid "
				+ "from lesson_list c left join (select ifnull(b.user_id,"
				+ user_id +") uid,a.lesson_id lesson_id,ifnull(progress,0) progress "
				+ "from lesson_list a left join v_lesson_user b on a.lesson_id = b.lesson_id "
				+ "where a.of_language = " 
				+ language + ") d on c.lesson_id = d.lesson_id where c.isvisible = 1 and c.of_language = " 
				+ language + " and uid = " + user_id + ";";
//		System.out.println(sql);
		ResultSet rs=super.executeQuery(sql);
		try {
			while(rs.next()){
				Lesson lesson = new Lesson();
				lesson.setLessonId(rs.getInt("lesson_id"));
				lesson.setLessonName(rs.getString("lesson_name"));
				lesson.setLessonImg(rs.getString("lesson_icon"));
				lesson.setProgress(rs.getDouble("progress"));
				lesson.setColor(rs.getString("color"));
				lessons.add(lesson);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return lessons;
	}
	/*
	 *加载小节列表
	 */
	public List<Bars> loadBarList(String language,String user_id,String lesson_id){
		List<Bars> list = new ArrayList<Bars>();
		String sql = "select b.bar_id id,b.bar_name name,a.word_text word,a.word_id word_id,b.status status "
				+ "from vocabulary_warehouse a,"
				+ "(select e.bar_id,e.bar_name ,e.of_lesson,ifnull(d.bar_id,0) status "
				+ "from lesson_bar_list e left join "
				+ "(select bar_id from user_study_record where user_id = " + user_id + ") d "
				+ "on e.bar_id = d.bar_id group by d.bar_id) b,"
				+ "lesson_list c "
				+ "where a.of_bar = b.bar_id and b.of_lesson = c.lesson_id "
				+ "and c.lesson_id = " + lesson_id + " and a.language_id = " + language
				+ " order by b.bar_id;";
		
		ResultSet rs=super.executeQuery(sql);
		try {
			String member = "";
			String preBarId = null;
			String preName = null;
			int preStatus = 0;
			int barId = 0;
			while(rs.next()){
//				按id分组，变量barId为第一个成员的id，下面的成员与它对比
//				如果和barId相同，则表示同一个bar，执行bar对象的赋值方法
//				如果不同，则表示该bar已结束，直接进入下一循环，新增一个bar对象
				if(barId == 0){
					barId = rs.getInt("id");
				}
				if(barId == rs.getInt("id") ){
					member += rs.getString("word") + ", ";
					preBarId = String.valueOf(rs.getInt("id"));
					preName = rs.getString("name");
					preStatus = rs.getInt("status")>0 ? 1:0;
				}
				else{
					barId = rs.getInt("id");
					Bars bar = new Bars();
					bar.setBarId(preBarId);
					bar.setBarName(preName);
					bar.setBarMember(member);
					bar.setStatus(preStatus);
					member =  rs.getString("word") + ", ";
					list.add(bar);
				}				
			}
			Bars bar = new Bars();
			bar.setBarId(preBarId);
			bar.setBarName(preName);
			bar.setBarMember(member);
			bar.setStatus(preStatus);
			list.add(bar);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	/*
	 *课程进度
	 */
	public Bars lessonProgress(String language,String user_id,String lesson_id){
		Bars bar = new Bars();
		String sql = "select b.lesson_name name,a.passed_times passed,a.bar_num num,"
				+ "a.progress progress,b.lesson_icon img "
				+ "from v_lesson_user a,lesson_list b "
				+ "where a.lesson_id = b.lesson_id and a.of_language=" 
				+ language + " and a.user_id = " 
				+ user_id + " and a.lesson_id= " 
				+ lesson_id + ";";
		System.out.println(sql);
		ResultSet rs=super.executeQuery(sql);
		try {
			while(rs.next()){
//				按id分组，变量barId为第一个成员的id，下面的成员与它对比
//				如果和barId相同，则表示同一个bar，执行bar对象的赋值方法
//				如果不同，则表示该bar已结束，直接进入下一循环，新增一个bar对象
				bar.setLessonName(rs.getString("name"));
				bar.setPassed(rs.getInt("passed"));
				bar.setNumber(rs.getInt("num"));
				bar.setProgress(rs.getDouble("progress"));
				bar.setLessonImg(rs.getString("img"));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return bar;
	}
}
