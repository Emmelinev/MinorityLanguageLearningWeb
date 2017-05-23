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

import com.bacaling.dao.MessageDao;
import com.bacaling.entity.Message;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class WordServlet
 */
//@WebServlet("/WordServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int method;
	private Message msg = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
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
			this.getMessages(request, response);
		}
		if(method==2){
			this.newMessage(request, response);
		}
		if(method==3){
			this.hasNewMsg(request, response);
		}
	}
//	加载通知列表
	private void getMessages(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
//		JSONObject json = new JSONObject();
		MessageDao msgDao = new MessageDao();
		String user_id= String.valueOf(request.getSession().getAttribute("user_id"));
//		String content=request.getParameter("content");
		System.out.println("user_id" + user_id);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		List<Message> list = msgDao.getMessages(user_id);
		
		JSONArray jsonArray =JSONArray.fromObject(list);
		out.print(jsonArray);
		System.out.println(jsonArray);
	}
	private void newMessage(HttpServletRequest request, HttpServletResponse response){
		
	}
	private void hasNewMsg(HttpServletRequest request, HttpServletResponse response) throws IOException{
		MessageDao msgDao = new MessageDao();
		JSONObject json = new JSONObject();
		String user_id= String.valueOf(request.getSession().getAttribute("user_id"));
		System.out.println("user_id" + user_id);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		int ret = msgDao.hasNewMsg(user_id);
		System.out.println("ret--"+ret);
		if(ret > 0){
			json.put("valid",true);
		}else if(ret == 0){
			json.put("valid", false);
		}
		out.print(json);
		System.out.println(json);
	}
}
