package com.bacaling.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.xml.registry.infomodel.User;

import com.bacaling.dao.UserDao;
import com.bacaling.dao.WordDao;
import com.bacaling.entity.Client;
import com.bacaling.entity.ExampleSentences;
import com.bacaling.entity.UserWord;
import com.bacaling.entity.Word;
import com.bacaling.util.SendMsgUtil;
import com.bacaling.util.TTSUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class WordServlet
 */
//@WebServlet("/WordServlet")
public class WordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int method;
	private ExampleSentences wordInfo = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		method=Integer.parseInt(request.getParameter("method"));
		if(method==1){
			this.getWords(request, response);
		}
		if(method==2){
			try {
				this.newRecords(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(method==3){
			this.queryWord(request, response);
		}
		if(method==4){
			try {
				this.getMedia(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(method==5){
			this.randomWord(request, response);
		}
	}
//	加载表单
	private void getWords(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		WordDao worddao = new WordDao();
//		JSONObject json = new JSONObject();

		String user_id= String.valueOf(request.getSession().getAttribute("user_id"));
		String current_language = String.valueOf(request.getSession().getAttribute("current_language"));
//		String current_language = (String) request.getSession().getAttribute("current_language");
//		String user_id=request.getParameter("userId");
//		String current_language=request.getParameter("cLanguage");
		System.out.println("user_id" + user_id + " language:"  + current_language);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		List<UserWord> list = worddao.wordList(user_id,current_language);
		
		JSONArray jsonArray =JSONArray.fromObject(list);
		out.print(jsonArray);
		System.out.println(jsonArray);
	}
	
//	插入新数据
	private void newRecords(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException{
		JSONObject json = new JSONObject();
		WordDao worddao = new WordDao();
		int ret = 0;

		String user_id = (String) request.getSession().getAttribute("user_id");
		String current_language = (String) request.getSession().getAttribute("current_language");	
//		String word_id = String.valueOf(worddao.wordQuery(user_id, current_language).getWordId());
		String word_id=request.getParameter("wordId");
		
		System.out.println("user_id-" + user_id + ", lang-" + current_language + ", word_id-" + word_id);

		ret = worddao.newRecord(user_id, word_id);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		if( ret > 0){
			json.put("valid",true);
		}else{
			json.put("valid",false);					
		}
		out.print(json);
		System.out.println(json);
	}
//	单词查询
	private void queryWord(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		JSONObject json = new JSONObject();
		WordDao worddao = new WordDao();
		
		String word=request.getParameter("word");
		String current_language = String.valueOf(request.getSession().getAttribute("current_language"));	

		System.out.println("word-" + word + " language-" + current_language);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		wordInfo = worddao.wordQuery(word, current_language);
		
		if(wordInfo != null){
			json.put("id", wordInfo.getWordId());
			json.put("word",wordInfo.getWord());
			json.put("word_tanslation", wordInfo.getWordTranslation());
			json.put("word_class", wordInfo.getWordClass());
			json.put("lesson", wordInfo.getOfBar());
			json.put("example", wordInfo.getContent());
			json.put("translation", wordInfo.getTranslation());
		}
		out.print(json);
		System.out.println(json);
	}
	private void randomWord(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JSONObject json = new JSONObject();
		WordDao worddao = new WordDao();

		String current_language = String.valueOf(request.getSession().getAttribute("current_language"));	

		System.out.println("language-" + current_language);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		wordInfo = worddao.randomWord(current_language);
		
		if(wordInfo != null){
			json.put("word",wordInfo.getWord());
			json.put("word_tanslation", wordInfo.getWordTranslation());
			json.put("word_class", wordInfo.getWordClass());
			json.put("example", wordInfo.getContent());
			json.put("translation", wordInfo.getTranslation());
		}
		out.print(json);
		System.out.println(json);
	}
	
	private void getMedia(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		JSONObject json = new JSONObject();
		String word=request.getParameter("word");
		String current_language = String.valueOf(request.getSession().getAttribute("current_language"));	
		
		System.out.println("word-" + word + " language-" + current_language);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		TTSUtil tts = new TTSUtil();
		String media = tts.tts(word);
		
		json.put("media", media);
		out.print(json);
		System.out.println(json);
	}
}
