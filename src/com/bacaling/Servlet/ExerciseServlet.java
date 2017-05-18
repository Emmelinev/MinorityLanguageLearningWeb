package com.bacaling.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.xml.registry.infomodel.User;

import com.bacaling.dao.ExerciseDao;
import com.bacaling.dao.LessonDao;
import com.bacaling.dao.UserDao;
import com.bacaling.dao.WordDao;
import com.bacaling.entity.Bars;
import com.bacaling.entity.Client;
import com.bacaling.entity.ExampleSentences;
import com.bacaling.entity.Lesson;
import com.bacaling.entity.Question;
import com.bacaling.entity.UserWord;
import com.bacaling.entity.Word;
import com.bacaling.util.SendMailUtil;
import com.bacaling.util.SendMsgUtil;
import com.bacaling.util.TTSUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class WordServlet
 */
//@WebServlet("/WordServlet")
public class ExerciseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int method;
	private ExampleSentences wordInfo = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExerciseServlet() {
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
			this.qaGeneration(request, response);
		}
		if(method==2){
			this.getBarList(request, response);
		}
		if(method==3){
			this.getProgress(request, response);
		}
		if(method==4){
			try {
				this.newRecords(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(method==5){
			try {
				SendMailUtil.sendMail();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject json = new JSONObject();
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
				json.put("valid",true);
				out.print(json);
		}
	}
	/*
	 * 生成题目
	 */
//	response.setContentType("application/json; charset=utf-8");
//	PrintWriter out = response.getWriter();
//	List<Lesson> list = lessondao.lessonList(user_id, language);
//	
//	JSONArray jsonArray =JSONArray.fromObject(list);
//	out.print(jsonArray);
	private void qaGeneration(HttpServletRequest request, HttpServletResponse response) throws IOException{
		ExerciseDao ed = new ExerciseDao();
		LessonDao ld = new LessonDao();
		List<Question> qList = new ArrayList<Question>();
		String userId= String.valueOf(request.getSession().getAttribute("user_id"));
		String language = String.valueOf(request.getSession().getAttribute("current_language"));
		String barId = String.valueOf(request.getSession().getAttribute("barId"));		
		System.out.println("userId-" + userId + " language-"  + language + " barId-" +barId);
		if(ed.barStatus(userId, language, barId) > 0){
			List<Bars> bars = ld.loadBarList(language, userId, barId);
			List<UserWord> list = ed.getBarWordInfo(userId, language, barId);
			List<UserWord> randList_4 = new ArrayList<UserWord>();
			List<UserWord> randList_3 = new ArrayList<UserWord>();
			List<UserWord> randList_2 = new ArrayList<UserWord>();
			List<UserWord> randList_1 = new ArrayList<UserWord>();
			List<UserWord> randList_0 = new ArrayList<UserWord>();
//			将单词信息按熟练度分类
			for(UserWord uw : list){
				switch(uw.getStrengthLevel()){
				case 4:
					randList_4.add(uw);
					break;
				case 3:
					randList_3.add(uw);
					break;
				case 2:
					randList_2.add(uw);
					break;
				case 1:
					randList_1.add(uw);
					break;
				default:
					randList_0.add(uw);
					break;
				}
			}
//			每个分类抽取一个单词id,没有该等级的值为0
			int word_4 = randList_4.isEmpty() ? 0 : randList_4.get(randomNum(randList_4)).getWordId();
			int word_3 = randList_3.isEmpty() ? 0 : randList_3.get(randomNum(randList_3)).getWordId();
			int word_2 = randList_2.isEmpty() ? 0 : randList_2.get(randomNum(randList_2)).getWordId();
			int word_1 = randList_1.isEmpty() ? 0 : randList_1.get(randomNum(randList_1)).getWordId();
			int word_0 = randList_0.isEmpty() ? 0 : randList_0.get(randomNum(randList_0)).getWordId();
		}else{
//			先加载图题
			ArrayList<Integer> a = ed.newBarList(language, barId,1);
			for(int i : a){
				qList.add(ed.qaGeneration(language, barId,String.valueOf(i), 1));
			}
			ArrayList<Integer> b = ed.newBarList(language, barId,2);
			for(int i : b){
				qList.add(ed.qaGeneration(language, barId,String.valueOf(i), 2));
			}
		}
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		JSONArray jsonArray =JSONArray.fromObject(qList);
		out.print(jsonArray);
		System.out.println(jsonArray);
		
	}
	private int randomNum(List<UserWord> l){
		int ret = 0;
		int max= l.size()-1;
        int min=0;
        Random random = new Random();
        ret = random.nextInt(max)%(max-min+1) + min;
        System.out.println(ret);
        return ret;
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
//	加载bar列表
	private void getBarList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		LessonDao lessondao = new LessonDao();

		String userId= String.valueOf(request.getSession().getAttribute("user_id"));
		String language = String.valueOf(request.getSession().getAttribute("current_language"));
		String lessonId = request.getParameter("lesson");
//		System.out.println("user_id" + userId + " language:"  + language);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		List<Bars> list = lessondao.loadBarList(language, userId, lessonId);
		
		JSONArray jsonArray =JSONArray.fromObject(list);
		out.print(jsonArray);
		System.out.println(jsonArray);
	}
//	获取进度
	private void getProgress(HttpServletRequest request, HttpServletResponse response) throws IOException{
		LessonDao lessondao = new LessonDao();
		JSONObject json = new JSONObject();
		String userId= String.valueOf(request.getSession().getAttribute("user_id"));
		String language = String.valueOf(request.getSession().getAttribute("current_language"));
		String lessonId = request.getParameter("lesson");
//		System.out.println("user_id" + userId + " language:"  + language);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		Bars bar = lessondao.lessonProgress(language, userId, lessonId);

		json.put("name",bar.getLessonName());
		json.put("passed",bar.getPassed());
		json.put("number",bar.getNumber());
		json.put("progress",bar.getProgress());
		json.put("img",bar.getLessonImg());
		out.print(json);
		System.out.println(json);
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
