package com.bacaling.Servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
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
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import net.sf.json.JSONObject;

public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int method;
	 private ServletConfig config;  
	 final public void init(ServletConfig config) throws ServletException {  
	  this.config = config;  
	 }  

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
			} catch (SmartUploadException e) {
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
		if(method==7){
			try {
				this.deactiveAccount(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(method==8){
			this.loadUserSettings(request, response);
		}
	}
	/*
	 * 加载用户设置
	 */
	public void loadUserSettings(HttpServletRequest request, HttpServletResponse response) throws IOException{
		UserDao userdao = new UserDao();
		JSONObject json = new JSONObject();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		String userId = String.valueOf(request.getSession().getAttribute("user_id")); 
		Client ret = userdao.getUserSettings(userId);
		
		json.put("id",ret.getUserId());
		json.put("name", ret.getUserName());
		json.put("password", ret.getPassword());
		json.put("email", ret.getUserEmail());
		json.put("goal", ret.getDailyGoal());
		json.put("img", ret.getProfileImg());
		json.put("autoplay", ret.getAutoplay());
		json.put("effect", ret.getEffect());
		json.put("notice", ret.getMailNotice());
		
		out.print(json);
		System.out.println(json);
	}
//	信息更新
	public void updateAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, FileUploadException, SmartUploadException{
	   SmartUpload su = new com.jspsmart.upload.SmartUpload();
	   PrintWriter out = response.getWriter(); 
	   su.initialize(config, request, response);
	   su.setTotalMaxFileSize(100000000);
	   su.setAllowedFilesList("jpg,bmp,gif,jpeg,png");
	   su.setDeniedFilesList("exe,bat,jsp,htm,html,,");
	   su.upload();
	   String fileName = null;
	   String url = null;
	   int count = 0;  
       for (int i = 0; i < su.getFiles().getCount(); i++) {  
           com.jspsmart.upload.File myfile = su.getFiles().getFile(i);  
           fileName = myfile.getFileName();  
           count = su.save("E:/javahh/BACALINGWEB/WebContent/images/");  
          }  
         out.println(count + " file uploaded.");  

		String userName=su.getRequest().getParameter("user_name");
		String email=su.getRequest().getParameter("email");
		String autoplay=su.getRequest().getParameter("autoplay");
		String effect=su.getRequest().getParameter("effect");
		System.out.println("111- userName-"+userName+" email-"+email+" img-"+fileName);
		System.out.println("autoplay-"+autoplay+" effect-"+effect);
		
		String userId = String.valueOf(request.getSession().getAttribute("user_id"));
		String userName0 = String.valueOf(request.getSession().getAttribute("user_name"));
		String email0 = String.valueOf(request.getSession().getAttribute("user_email"));
		String profileImg0 = String.valueOf(request.getSession().getAttribute("profile_img"));
		System.out.println("000-userId-"+userId+" userName-"+userName0+" email-"+email0+" img-"+profileImg0);
		UserDao userdao = new UserDao();
		if(userName != userName0){
			int ret = userdao.updateUserName(userId, userName);
			System.out.println("user-name-"+ret);
		}
		if(email != email0){
			int ret = userdao.updateEmail(userId, email);
			System.out.println("email-"+ret);
		}
		if(autoplay != null){
			int ret = userdao.updateAutoplay(userId, concertToInt(autoplay));
			System.out.println("autoplay-"+ret);
		}
		if(effect != null){
			int ret = userdao.updateEffect(userId, concertToInt(effect));
			System.out.println("effect-"+ret);
		}
		if(fileName != null){
			url = "../images/" + fileName;
			int ret = userdao.updateImg(userId, url);
			System.out.println("img-"+ret);
		}
		HttpSession session = request.getSession();
		session.setAttribute("user_name", userName);
		session.setAttribute("user_email", email);
		session.setAttribute("profile_img", url);
		response.sendRedirect(request.getContextPath()+"/normalPages/Settings.jsp");
	}
	public String concertToInt(String str){
		String ret = "0";
		if(str == "on"){
			ret = "1";
		}else if(str == "off")
		{
			ret = "0";
		}
		return ret;
	}
	/*
	 * 更改密码
	 */
	public void updateProfile(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		UserDao userdao = new UserDao();
		JSONObject json = new JSONObject();
		String userId = String.valueOf(request.getSession().getAttribute("user_id")); 
		String password=request.getParameter("modifyPassword");
		int ret = userdao.updatePassword(userId, password);
		if(ret > 0){
			response.sendRedirect(request.getContextPath()+"/normalPages/Settings.jsp");
		}
	}
	/*
	 * 更改邮件通知
	 */
	public void updateNotice(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		UserDao userdao = new UserDao();
		JSONObject json = new JSONObject();
		int sum = 0;
		String userId = String.valueOf(request.getSession().getAttribute("user_id")); 
		String[] result=request.getParameterValues("notice");
		for(String i : result){
			sum += Integer.parseInt(i);
			System.out.println("rrr-"+i+"  sum-"+sum);
		}
		int ret = userdao.updateNotice(userId, String.valueOf(sum));
		if(ret > 0){
			response.sendRedirect(request.getContextPath()+"/normalPages/Settings.jsp");
		}
//		response.setContentType("application/json; charset=utf-8");
//		PrintWriter out = response.getWriter();
//		if( ret > 0){
//			json.put("valid",true);
//		}else{
//			json.put("valid",false);					
//		}
//		out.print(json);
//		System.out.println(json);
	}
	/*
	 * 更改每日目标
	 */
	public void updateGoal(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		UserDao userdao = new UserDao();
		JSONObject json = new JSONObject();
		String userId = String.valueOf(request.getSession().getAttribute("user_id")); 
		String result=request.getParameter("goal");
		int ret = userdao.updateGoal(userId, result);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		if( ret > 0){
			response.sendRedirect(request.getContextPath()+"/normalPages/Settings.jsp");
		}else{
			json.put("valid",false);	
			out.print(json);
			System.out.println(json);
		}

		
	}
	/*
	 * 注销账号
	 */
	public void deactiveAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		UserDao userdao = new UserDao();		
		String userId = String.valueOf(request.getSession().getAttribute("user_id")); 
		int ret = userdao.deactiveAccount(userId);
		JSONObject json = new JSONObject();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		session.removeAttribute("user_id");
		session.removeAttribute("user_name");
		session.removeAttribute("user_tel");
		session.removeAttribute("current_language");
		session.removeAttribute("user_state");
		session.removeAttribute("profile_img");
		
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
