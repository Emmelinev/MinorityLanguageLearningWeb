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

import com.bacaling.dao.LessonDao;
import com.bacaling.dao.UserDao;
import com.bacaling.dao.WordDao;
import com.bacaling.entity.Client;
import com.bacaling.entity.ExampleSentences;
import com.bacaling.entity.Lesson;
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
public class LessonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int method;
	private ExampleSentences wordInfo = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LessonServlet() {
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
			this.getLessonList(request, response);
		}
		if(method==2){
			try {
				this.newRecords(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
//	加载表单
	private void getLessonList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		LessonDao lessondao = new LessonDao();
//		JSONObject json = new JSONObject();

		String user_id= String.valueOf(request.getSession().getAttribute("user_id"));
		String language = String.valueOf(request.getSession().getAttribute("current_language"));

		System.out.println("user_id" + user_id + " language:"  + language);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		List<Lesson> list = lessondao.lessonList(user_id, language);
		
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
}
