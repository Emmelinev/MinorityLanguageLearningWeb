package com.bacaling.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bacaling.entity.Bars;
import com.bacaling.entity.Lesson;
import com.bacaling.entity.Question;
import com.bacaling.entity.UserWord;
public class ExerciseDao extends BaseDao{
	/*
	 * 获取小节词汇信息
	 */
	public List<UserWord> getBarWordInfo(String userId,String language,String barId){
		List<UserWord> list = new ArrayList<UserWord>();
		String sql = "select a.word_id id,word_text text,"
				+ "ifnull(b.exercise_times,0) times,"
				+ "datediff(now(),ifnull(b.last_practiced,'1900-01-01')) last_practiced"
				+ " from vocabulary_warehouse a left join "
				+ "(select *  from v_user_word_list where user_id = "
				+ userId + ") b on a.word_id = b.word_id"
				+ " where a.language_id = " 
				+ language + " and a.of_bar = " 
				+ barId + ";";
		System.out.println(sql);
		ResultSet rs=super.executeQuery(sql);
		try {
			while(rs.next()){
				UserWord uw = new UserWord("new");
				uw.setWordId(rs.getInt("id"));
				uw.setWord(rs.getString("text"));
				uw.setPracticeCount(rs.getInt("times"));
				uw.setLastPracticed(rs.getInt("last_practiced"));
				uw.setStrengthLevel();
				list.add(uw);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	/*
	 * 按熟练度随机抽取
	 */
	public UserWord rateRandomWord(int strength){
		UserWord uw = new UserWord("new");
		
		return uw;
	}
	
	/*
	 * 小节的状态
	 */
	public int barStatus(String userId,String language,String barId){
//		List<UserWord> list = new ArrayList<UserWord>();
		int status = 0;
		String sql = "select b.bar id,b.bar_name name,a.word_text word,a.word_id word_id,b.status status "
				+ " from vocabulary_warehouse a, "
				+ " (select e.bar_id bar,e.bar_name ,e.of_lesson,ifnull(d.bar_id,0) status "
				+ "from lesson_bar_list e left join  "
				+ "(select bar_id from user_study_record where user_id = " 
				+ userId + ") d on e.bar_id = d.bar_id group by d.bar_id) b "
				+ "where a.of_bar = b.bar and b.bar = " 
				+ barId + " and a.language_id = " 
				+ language + " order by b.bar;";
		System.out.println(sql);
		ResultSet rs=super.executeQuery(sql);
		try {
			if(rs.next()){
				if(rs.getInt("status")!=0){
					status = 1;
				}else{
					status = 0;
				}
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return status;
	}
	/*
	 * 
	 */
	public ArrayList<Integer> newBarList(String language,String barId,int type){
		ArrayList<Integer> a = new ArrayList<Integer>();
		int count = 0;
		if(type ==1){
			String sql = "select bar_id, word_id "
					+ "from lesson_bar_list a, vocabulary_warehouse b "
					+ "where a.bar_id = b.of_bar and bar_id = " 
					+ barId + " and language_id = " 
					+ language + " and (pic_src is not null);";
			System.out.println(sql);
			ResultSet rs=super.executeQuery(sql);
			try {
				while(rs.next()){
					a.add(rs.getInt("word_id"));
					count ++;
				}
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}else if(type == 2){
			String sql2 = "select word_id "
					+ "from vocabulary_warehouse "
					+ "where of_bar = " 
					+ barId + " and language_id = " 
					+ language + " order by rand() LIMIT " 
					+ (10-count) + ";";
			System.out.println(sql2);
			ResultSet rs2=super.executeQuery(sql2);
			try {
				while(rs2.next()){
					a.add(rs2.getInt("word_id"));
//					count ++;
				}
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}	
		return a;
	}
	/*
	 * 生成题目列表
	 */
	public Question qaGeneration(String language,String barId,String wordId,int type){
		Question q = new Question("new");
		if(type == 1){
			String sql = "select word_id id,word_text word,b.exp_id eid,"
					+ "b.exp_content expample,b.standard_translation answer, a.pic_src img "
					+ "from vocabulary_warehouse a,example_sentences b "
					+ "where a.word_id=b.of_word and language_id = " 
					+ language + " and of_bar = " 
					+ barId + " and word_id = " 
					+ wordId + " and b.allow_pic = 1";
			System.out.println(sql);
			ResultSet rs=super.executeQuery(sql);
			try {
				while(rs.next()){					
					q.setQuestionID(rs.getInt("eid"));
					q.setWordId(rs.getInt("id"));
					q.setWord(rs.getString("word"));
					q.setExampleSentence(rs.getString("example"));
					q.setAnswer(rs.getString("answer"));
					q.setImg(rs.getString("img"));
					q.setType(randomType(2));
				}
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}else if(type == 2){
//			type = 2 为普通题
			String sql = "select word_id id,word_text word,b.exp_id eid,b.exp_content expample,b.standard_translation answer "
					+ "from vocabulary_warehouse a,example_sentences b where language_id = " 
					+ language + " and of_bar = " 
					+ barId + " and word_id = " 
					+ wordId + " and b.allow_pic <> 1 order by rand() limit 1;";
			System.out.println(sql);
			ResultSet rs=super.executeQuery(sql);
			try {
				while(rs.next()){					
					q.setQuestionID(rs.getInt("eid"));
					q.setWordId(rs.getInt("id"));
					q.setWord(rs.getString("word"));
					q.setExampleSentence(rs.getString("example"));
					q.setAnswer(rs.getString("answer"));
					q.setType(randomType(2));
				}
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}
		return q;
	}
	private int randomType(int word){
		int ret = 0;
		int max = 0;
		int min=0;
		if(word == 1){
			max= 3;
		}else{
			max = 2; 
		}        
        Random random = new Random();
        ret = random.nextInt(max)%(max-min+1) + min;
        System.out.println(ret);
		return ret;
	}
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
