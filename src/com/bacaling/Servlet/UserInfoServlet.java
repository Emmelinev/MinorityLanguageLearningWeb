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
import com.bacaling.entity.UserWord;
import com.bacaling.util.SendMsgUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserInfoServlet
 */
//@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int method;
	private Client client=null;
	String code;
	String information="";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
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
			this.validateUser(request, response);
		}
		if(method==2){
			this.sendMsg(request, response);
		}
		if(method==3){
			this.findPwd2(request, response);
		}
		if(method==4){
			this.valiUser(request, response);
		}
		if(method==5){
			this.testInfo(request, response);
		}
		if(method==6){
			this.validateCode(request, response);
		}
		if(method==7){
			this.getExp(request, response);
		}
		if(method==8){
			this.getExpList(request, response);
		}
	}
	/*
	 * 输入用户名时检查是否存在
	 */
	private void validateUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("application/json; charset=utf-8");
//		String jsonStr = "{\"valid\":\"true\"}";
		JSONObject json = new JSONObject();
		UserDao userDao = new UserDao();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		String phonenum=request.getParameter("phonenum");
		client = userDao.queryUser(phonenum);
		if(null != client){
			json.put("valid",true);
		}else{
//			response.setContentType("application/json");
			json.put("valid",false);
		}
		out.print(json);
		System.out.println("用户名校验-请求用户"+phonenum+"验证结果："+json);
//		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
	private void getExp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		JSONObject json = new JSONObject();
		UserDao userdao = new UserDao();
		
		String userId = String.valueOf(request.getSession().getAttribute("user_id"));
		String language = String.valueOf(request.getSession().getAttribute("current_language"));	

		System.out.println("userid-" + userId + " language-" + language);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		Client userInfo = userdao.queryExp(userId, language);
		
		if(userdao != null){
			json.put("exp", userInfo.getExp());
			json.put("level", userInfo.getLevel());
		}
		out.print(json);
		System.out.println(json);
	}
	private void getExpList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		UserDao userdao = new UserDao();

		String user_id= String.valueOf(request.getSession().getAttribute("user_id"));
		System.out.println("user_id" + user_id);
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		List<Client> list = userdao.queryExpList(user_id);
		
		JSONArray jsonArray =JSONArray.fromObject(list);
		out.print(jsonArray);
		System.out.println(jsonArray);
	}
	/*
	 * 点击发送验证码
	 */
	
	private void sendMsg(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String phonenum=request.getParameter("phonenum");
		System.out.println(phonenum);
		code=SendMsgUtil.createRandomVcode();
//		次数有限，必要时取消以下代码即可发送短信
//		String result=SendMsgUtil.sendMsg(phonenum, code);
//		String recode= request.getParameter("verifyCode");
//		if(null!=result){
//		response.getWriter().println(result);
		request.getSession().setAttribute("vcode", code);
		PrintWriter out = response.getWriter();  
		out.print(code);
		System.out.println(code);
//		}
	}
	
	/*
	 * 提交忘记密码表单
	 * 
	 */
	private void findPwd2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		UserDao userDao = new UserDao();
		String phonenum=request.getParameter("phonenum");
		client = userDao.queryUser(phonenum);
		String new_password=request.getParameter("pwd");
		client.setPassword(new_password);
		boolean ret=userDao.userIdentify(client);
		if(ret){
			request.setAttribute("information", "密码修改成功!");
		}
		else{
			request.setAttribute("information", "密码修改失败!请稍后重试!");
		}
			//response.getWriter().print(ret);
		request.getRequestDispatcher("redirectPage.jsp").forward(request, response);
	}
	
	/*
	 * 提交账户信息设置表单
	 * 
	 */
	private void updateInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException{
		int ret=0;
		UserDao userDao = new UserDao();
		HttpSession session=request.getSession();
		String oldName=(String) session.getAttribute("uname");
		String uid=(String)session.getAttribute("user_id");
		String uname=request.getParameter("phonenum");
		String new_password=request.getParameter("new_password");
		String confirm_password=request.getParameter("confirm_password");
		
		if(null!=uname){		
			 ret=userDao.updateUserName(oldName,uname);
		}
		if(null!=new_password&&null!=confirm_password){
			ret=userDao.updatePassword(uid, new_password);
		}
		if(ret>0){			
			information="账户信息修改成功!";
			request.setAttribute("information", information);
			//response.getWriter().print("1");
			//response.sendRedirect(request.getContextPath()+"/login.jsp");
			request.getRequestDispatcher("redirectPage.jsp").forward(request, response);
		}else{
			//response.getWriter().print("0");
			information="账户信息修改失败，请重新操作!";
			request.setAttribute("information", information);
			request.getRequestDispatcher("redirectPage.jsp").forward(request, response);
		}		
	}
	
	/*
	 * 测试方法
	 */
	private void testInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String uname=request.getParameter("username");
		System.out.println(uname);
			PrintWriter out = response.getWriter();  
			request.getSession().setAttribute("ifor", uname);
			 out.print("welcome: "+uname);
			System.out.println("welcome: "+uname);
	}
	
	/*
	 * 验证用户输入验证码
	 */
	private void validateCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("application/json; charset=utf-8");
//		String jsonStr = "{\"valid\":\"true\"}";
		JSONObject json = new JSONObject();
		UserDao userDao = new UserDao();
		String vcode=request.getParameter("verifyCode");
		System.out.println("userinput:" + vcode + " correct:"  + code);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(vcode.equals(code)){
			json.put("valid",true);
		}else{
			response.setContentType("application/json");
			json.put("valid",false);					
		}
		out.print(json);
		System.out.println("验证码校验-用户输入"+vcode+"验证结果："+json);
	}
	
	private void valiUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("application/json; charset=utf-8");
//		String jsonStr = "{\"valid\":\"true\"}";
		JSONObject json = new JSONObject();
		UserDao userDao = new UserDao();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		String phonenum=request.getParameter("phonenum");
		client = userDao.queryUser(phonenum);
		if(null != client){
			json.put("valid",true);
		}
			else{
			response.setContentType("application/json");
			json.put("valid",false);					
		}
		out.print(json);
		System.out.println("用户名校验-请求用户"+phonenum+"验证结果："+json);
//		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

}
