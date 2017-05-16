package com.bacaling.Servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.bacaling.dao.UserDao;
import com.bacaling.entity.Client;
import com.bacaling.util.UploadImgUtil;

import net.sf.json.JSONObject;

public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int method;

	public SettingsServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		method=Integer.parseInt(request.getParameter("method"));
		if(method==1){
			try {
				this.updateAccount(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(method==2){
			try {
				this.updateProfile(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(method==3){
			try {
				this.updateNotice(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(method==4){
			try {
				this.updateGoal(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(method==5){
			try {
				this.updateProfile(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(method==6){
				this.changeLanguage(request, response);
		}
	}
//	信息更新
	public void updateAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, FileUploadException{
		String userName=request.getParameter("user_name");
		String email=request.getParameter("email");
		String autoplay=request.getParameter("autoplay");
		String effect=request.getParameter("effect");
//		String profilePic=request.getParameter("profile_pic");
//		System.out.println(profilePic);
		
		String userId = (String) request.getSession().getAttribute("user_id"); 
		UserDao userdao = new UserDao();
		if(userName != null){
			userdao.updateUserName(userId, userName);
		}
		if(email != null){
			userdao.updateEmail(userId, email);
		}
		if(autoplay != null){
			userdao.updateAutoplay(userId, autoplay);
		}
		if(effect != null){
			userdao.updateEffect(userId, effect);
		}
		if(request.getParameter("profile_pic") != null){
			this.uploadImg(request, response);
		}
	}
	/*
	 * 更改密码
	 */
	public void updateProfile(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		UserDao userdao = new UserDao();
		JSONObject json = new JSONObject();
		String userId = (String) request.getSession().getAttribute("user_id"); 
		String password=request.getParameter("modifyPassword");
		int ret = userdao.updatePassword(userId, password);
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
	/*
	 * 更改邮件通知
	 */
	public void updateNotice(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		UserDao userdao = new UserDao();
		JSONObject json = new JSONObject();
		String userId = (String) request.getSession().getAttribute("user_id"); 
		String result=request.getParameter("notice");
		int ret = userdao.updateNotice(userId, result);
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
	/*
	 * 更改邮件通知
	 */
	public void updateGoal(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		UserDao userdao = new UserDao();
		JSONObject json = new JSONObject();
		String userId = (String) request.getSession().getAttribute("user_id"); 
		String result=request.getParameter("goal");
		int ret = userdao.updateGoal(userId, result);
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
	/*
	 * 注销账号
	 */
	public void deactiveAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		UserDao userdao = new UserDao();		
		String userId = (String) request.getSession().getAttribute("user_id"); 
		int ret = userdao.deactiveAccount(userId);
		JSONObject json = new JSONObject();
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
	/*
	 * 上传头像
	 */
//	@SuppressWarnings("deprecation")
	public void uploadImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileUploadException{
		request.setCharacterEncoding("utf-8"); 
	    String path = "d:/university";
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(path));
        factory.setSizeThreshold(1024*1024) ;
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
        UploadImgUtil.uplaod(path,list);
//	        request.getRequestDispatcher("filedemo.jsp").forward(request, response);
	}
	public void changeLanguage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JSONObject json = new JSONObject();
		String language=request.getParameter("language");
		HttpSession session = request.getSession();
		session.setAttribute("current_language", language);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		json.put("language",(String) request.getSession().getAttribute("current_language"));
		out.print(json);
		System.out.println(json);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
}
