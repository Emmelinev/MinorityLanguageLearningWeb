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
	 * 获取单词
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
					+ "b.exp_content example,b.standard_translation answer, a.pic_src img "
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
//					int rtype = randomType(3);
					q.setType(3);
				}
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			String option = "select exp_content,pic_src img "
					+ "from vocabulary_warehouse a,example_sentences b "
					+ "where a.word_id=b.of_word and language_id = "
					+ language + " and b.allow_pic = 1 order by rand() limit 2;" ;
			System.out.println(option);
			ResultSet rsOption=super.executeQuery(option);
			try {
				if(rsOption.next()){					
					q.setOption1(rsOption.getString("exp_content")+","+rsOption.getString("img"));
				}
				if(rsOption.next()){					
					q.setOption2(rsOption.getString("exp_content")+","+rsOption.getString("img"));
				}
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}else if(type == 2){
//			type = 2 为普通题
			String sql = "select word_id id,word_text word,b.exp_id eid,b.exp_content example,"
					+ "b.standard_translation answer "
					+ "from vocabulary_warehouse a,example_sentences b where a.word_id=b.of_word and language_id = " 
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
					int rtype = randomType(2);
					q.setType(rtype);
				}
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			if(q.getType()==1){
				String option = "select word_text"
						+ "from vocabulary_warehouse a,example_sentences b "
						+ "where a.word_id=b.of_word and language_id = "
						+ language + " and b.allow_pic = 1 order by rand() limit 2;" ;
				System.out.println(option);
				ResultSet rsOption=super.executeQuery(option);
				try {
					if(rsOption.next()){					
						q.setOption1(rsOption.getString("exp_content"));
					}
					if(rsOption.next()){					
						q.setOption2(rsOption.getString("exp_content"));
					}
				} catch (SQLException e) {			
					e.printStackTrace();
				}
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
	/*
	 * 随机抽取两道图片选项
	 */
	public List<Question> imgRandom(String language){
		List<Question> list = new ArrayList<Question>();
		String sql = "select word_id id,word_text word,b.exp_id eid,b.exp_content example,"
				+ "b.standard_translation answer,a.pic_src img "
				+ "from vocabulary_warehouse a,example_sentences b  "
				+ "where a.word_id=b.of_word and language_id = " 
				+ language + " and b.allow_pic = 1 order by rand() limit 1;";
		System.out.println(sql);
		ResultSet rs=super.executeQuery(sql);
		try {
			while(rs.next()){	
				Question q = new Question("new");
				q.setQuestionID(rs.getInt("eid"));
				q.setWordId(rs.getInt("id"));
				q.setWord(rs.getString("word"));
				q.setExampleSentence(rs.getString("example"));
				q.setAnswer(rs.getString("answer"));
				q.setImg(rs.getString("img"));
				q.setType(randomType(3));
				list.add(q);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	public int uploadToExp(String userId,String language) throws SQLException{
//		SELECT * FROM bacaling.user_sr_static;
		String sql = "update bacaling.user_sr_static set exp = exp+10 "
				+ "where user_id = " + userId + " and language_id = " + language +";";
		System.out.println(sql);
		return super.executeUpdate(sql);
	}
	public int uploadToWordExercise(String userId,String language,String barId){
		int ret = 0;
//		SELECT * FROM bacaling.user_word_exercise_list;
//		SELECT * FROM bacaling.user_word_list;
		return ret;
	}
	public int uploadToLessonExercise(String userId,String language,String barId){
		int ret = 0;
//		SELECT * FROM bacaling.user_study_record;
		return ret;
	}
}
