package com.bacaling.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bacaling.dao.UserDao;
import com.bacaling.entity.Client;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String information="";

		UserDao userDao=new UserDao();
		
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		System.out.println(userName);
		
		Client client=userDao.queryUser(userName);
		if(null!=client&&password.equals(client.getPassword())){			
			HttpSession session = request.getSession();
			session.setAttribute("user_id", client.getUserId());
			session.setAttribute("user_name", client.getUserName());
			session.setAttribute("user_tel", client.getPhoneNum());
			session.setAttribute("user_password", client.getPassword());
			session.setAttribute("user_email", client.getUserEmail());
			session.setAttribute("current_language", client.getCurrentLanguage());
			session.setAttribute("profile_img", client.getProfileImg());
			session.setAttribute("user_state", client.getState());
//			response.sendRedirect(request.getContextPath()+"/index.jsp");
			response.sendRedirect(request.getContextPath()+"/normalPages/index.jsp");
			//response.getWriter().print("1");
		}else{
//			session.setAttribute("message", "用户名或密码不匹配。");
//		    response.sendRedirect("fail.jsp") ;
			information="Something wrong with username or password.";
			request.setAttribute("information", information);
			request.getRequestDispatcher(request.getContextPath()+"/normalPages/login.jsp").forward(request, response);
			//response.getWriter().println("0");
		}
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
