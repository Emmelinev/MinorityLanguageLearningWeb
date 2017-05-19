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
			this.pictureSelection(request, response);
		}
		if(method==3){
			try {
				this.uploadAnswers(request, response);
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
		String barId = String.valueOf(request.getParameter("barId"));	
		String status = String.valueOf(request.getParameter("status"));	
		System.out.println("userId-" + userId + " language-"  + language + " barId-" +barId);
		
		if(Integer.parseInt(status) > 0){
//			List<Bars> bars = ld.loadBarList(language, userId, barId);
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
			System.out.println("4-"+randList_4.size()+" empty?"+randList_4.isEmpty());
			System.out.println("3-"+randList_3.size()+" empty?"+randList_3.isEmpty());
			System.out.println("2-"+randList_2.size()+" empty?"+randList_2.isEmpty());
			System.out.println("1-"+randList_1.size()+" empty?"+randList_1.isEmpty());
			System.out.println("0-"+randList_0.size()+" empty?"+randList_0.isEmpty());
			int r4 = randList_4.size();
			int r3 = randList_3.size();
			int r2 = randList_2.size();
			int r1 = randList_1.size();
			int r0 = randList_0.size();
//			每个分类抽取一个单词id,没有该等级的值为0
			int word_4 = randList_4.isEmpty() ? 0 : randList_4.get(randomNum(r4)).getWordId();
			int word_3 = randList_3.isEmpty() ? 0 : randList_3.get(randomNum(r3)).getWordId();
			int word_2 = randList_2.isEmpty() ? 0 : randList_2.get(randomNum(r2)).getWordId();
			int word_1 = randList_1.isEmpty() ? 0 : randList_1.get(randomNum(r1)).getWordId();
			int word_0 = randList_0.isEmpty() ? 0 : randList_0.get(randomNum(r0)).getWordId();
			
			System.out.println("4-"+word_4+" 3-"+word_3+" 2-"+word_2+" 1-"+word_1+" 0-"+word_0);
			
			int count = 0;
			if(word_4 != 0){
				qList.add(ed.qaGeneration(language, barId,String.valueOf(word_4), 2));
				count++;
			}
			if(word_3 != 0){
				qList.add(ed.qaGeneration(language, barId,String.valueOf(word_3), 2));
				count++;
			}
			if(word_2 != 0){
				qList.add(ed.qaGeneration(language, barId,String.valueOf(word_2), 2));
				count++;
			}
			if(word_1 != 0){
				qList.add(ed.qaGeneration(language, barId,String.valueOf(word_1), 2));
				count++;
			}
			if(word_0 != 0){
				qList.add(ed.qaGeneration(language, barId,String.valueOf(word_0), 2));
				count++;
			}
//			System.out.println("img-q:"+count);
			while(count < 10){
				int rand = randomNum();
				System.out.println("rand-"+rand);
				if(rand < 10 && !randList_4.isEmpty()){
//					level 4
					int word = randList_4.isEmpty() ? 0 : randList_4.get(randomNum(r4)).getWordId();
					qList.add(ed.qaGeneration(language, barId,String.valueOf(word), 2));
				}else if(rand >= 10 && rand < 20 && !randList_3.isEmpty()){
//					level 3
					int word = randList_3.isEmpty() ? 0 : randList_3.get(randomNum(r3)).getWordId();
					qList.add(ed.qaGeneration(language, barId,String.valueOf(word), 2));
				}else if(rand >= 20 && rand < 40 && !randList_2.isEmpty()){
//					level 2
					int word = randList_2.isEmpty() ? 0 : randList_2.get(randomNum(r2)).getWordId();
					qList.add(ed.qaGeneration(language, barId,String.valueOf(word), 2));
				}else if(rand >= 40 && rand < 70 && !randList_1.isEmpty()){
//					level 1
					int word = randList_1.isEmpty() ? 0 : randList_1.get(randomNum(r1)).getWordId();
					qList.add(ed.qaGeneration(language, barId,String.valueOf(word), 2));
				}else if(rand >= 70 && !randList_0.isEmpty()){
//					level 0
					int word = randList_0.isEmpty() ? 0 : randList_0.get(randomNum(r0)).getWordId();
					qList.add(ed.qaGeneration(language, barId,String.valueOf(word), 2));
				}else{
					int word = randList_0.isEmpty() ? 0 : randList_0.get(randomNum(r0)).getWordId();
					qList.add(ed.qaGeneration(language, barId,String.valueOf(word), 2));
				}
				count++;
			}

			
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
	private int randomNum(int l){
//		List<UserWord> l
		int ret = 0;
		if(l==1){
			return 0;
		}else{
			int mmm= l-1;
//			System.out.println("max;;"+mmm);
	        int min=0;
	        Random random = new Random();
	        ret = random.nextInt(mmm) % (mmm-min+1) + min;
//	        System.out.println("random-"+ret);
	        return ret;	
		}
	}
	private int randomNum(){
		int ret = 0;
		int max= 100;
        int min=0;
        Random random = new Random();
        ret = random.nextInt(max)%(max-min+1) + min;
//        System.out.println(ret);
        return ret;
	}
//	随机抽两道图片题作为选项
	private void pictureSelection(HttpServletRequest request, HttpServletResponse response) throws IOException{
		ExerciseDao ed = new ExerciseDao();
		String language = String.valueOf(request.getSession().getAttribute("current_language"));
		List<Question> list = ed.imgRandom(language);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		JSONArray jsonArray =JSONArray.fromObject(list);
		out.print(jsonArray);
		System.out.println(jsonArray);
	}
//	上传答题情况
	private void uploadAnswers(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		ExerciseDao ed = new ExerciseDao();
		String language = String.valueOf(request.getSession().getAttribute("current_language"));
		String userId = String.valueOf(request.getSession().getAttribute("user_id"));
//		{"userAnswers":userAnswers,"types":types,"userAnswerFlag":userAnswerFlag,"expIds":expIds,"wordIds":wordIds,"barId":barId};
		String[] userAnswers=request.getParameterValues("userAnswers");
		String[] types=request.getParameterValues("types");
		String[] userAnswerFlag=request.getParameterValues("userAnswerFlag");
		String[] expIds=request.getParameterValues("expIds");
		String[] wordIds=request.getParameterValues("wordIds");
		String barId = String.valueOf(request.getParameter("barId"));
		
		int up1 = ed.uploadToExp(userId, language);
	}
}
