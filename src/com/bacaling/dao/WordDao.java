package com.bacaling.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bacaling.entity.Word;
import com.bacaling.entity.ExampleSentences;
import com.bacaling.entity.UserWord;

public class WordDao extends BaseDao {
	/*
	 *单词查询
	 */
	public ExampleSentences wordQuery(String word1,String language){
		ExampleSentences word = null;
//		String sql = "select a.word_id id,a.word_text word,a.of_bar bar,b.class_name class,c.exp_content content,c.standard_translation translation"
//				+ "from (select * from vocabulary_warehouse where word_text = ?) a,"
//				+ "(select * from word_class_dictionary where of_language = ?) b,"
//				+ "(select max(exp_id) exp_id, exp_content, standard_translation, of_word"
//				+ "from example_sentences"
//				+ "where exp_language = 3"
//				+ "group by of_word) c"
//				+ "where a.word_class = b.class_id and a.word_id = c.of_word";
//		Object[] parm = new Object[2];
//		parm[0] = word1;
//		parm[1] = language;
		String sql ="select ifnull(a.word_id,'No record') id,"
				+ "ifnull(a.word_text,'No reord') word,"
				+ "ifnull(d.lesson_name,'No record') lesson,"
				+ "ifnull(b.class_name,'No record') class,"
				+ "ifnull(c.exp_content,'No record') content,"
				+ "ifnull(c.standard_translation,'No record') translation,"
				+ "ifnull(e.translation_content,'No record') word_translation  "
				+ "from ((((select * from vocabulary_warehouse where word_text = '" + word1 + "') a "
						+ "left join (select max(exp_id) exp_id, exp_content, standard_translation, of_word from example_sentences where exp_language = " + language + " group by of_word) c on a.word_id = c.of_word) "
								+ "inner join (select * from word_class_dictionary where of_language = 3) b on a.word_class = b.class_id) "
								+ "inner join (select lesson_name,bar_id from lesson_list,lesson_bar_list where lesson_list.lesson_id = lesson_bar_list.of_lesson) d on d.bar_id = a.of_bar) "
								+ "left join word_translation_warehouse e on a.word_id = e.of_word;";

		ResultSet rs=super.executeQuery(sql);
		try {
			while(rs.next()){
				word = new ExampleSentences(word1);
				word.setWordId(rs.getInt("id"));
				word.setWord(rs.getString("word"));
				word.setWordTranslation(rs.getString("word_translation"));
				word.setWordClass(rs.getString("class"));
				word.setContent(rs.getString("content"));
				word.setTranslation(rs.getString("translation"));
				word.setOfBar(rs.getString("lesson"));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return word;
	}
	
	/*
	 * 词汇列表
	 */
	public List<UserWord> wordList(String user_id,String language){	
		List<UserWord> list = new ArrayList<UserWord>();
		String sql = "select a.word_id id,a.word_text word,b.class_name class,"
				+ "datediff(now(),c.last_practiced) last_practiced,c.exercise_times strength "
				+ "from vocabulary_warehouse a, "
				+ "word_class_dictionary b, "
				+ "v_user_word_list c "
				+ "where a.word_id = c.word_id and a.word_class = b.class_id "
				+ "and a.language_id = " + language + " and c.user_id = " + user_id + ";";
		Object[] parm = new Object[2];
		parm[0] = language;
		parm[1] = user_id;
		System.out.println(sql);
		ResultSet rs=super.executeQuery(sql);
		System.out.println(rs);
		try {
			while(rs.next()){
				UserWord words = new UserWord(rs.getString("word"));
				words.setWordId(rs.getInt("id"));
				words.setWordClass(rs.getString("class"));
				words.setLastPracticed(rs.getInt("last_practiced"));
				words.setPracticeCount(rs.getInt("strength"));
				words.setStrengthLevel();
				list.add(words);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}
	/*
	 * 插入新纪录
	 */
	public int newRecord(String user_id,String word_id) throws SQLException{
		String sql = "insert into bacaling.user_word_exercise_list(user_id,word_id,exercise_time) values(?,?,now());";
		Object[] parm = new Object[2];
		parm[0] = user_id;
		parm[1] = word_id;
		return super.executeUpdate(sql, parm);
	}
}
