package com.bacaling.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bacaling.dao.UserDao;
import com.bacaling.entity.Client;

/**
 * Servlet implementation class SignupServlet
 */
//@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
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
		UserDao userDao=new UserDao();
		
		String phonenum=request.getParameter("phonenum");
		String password=request.getParameter("pwd");
		System.out.println(phonenum+",pwd-"+password);
		try {
			userDao.newUserReg2(phonenum,password);
			System.out.println("successed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Client client = null;
//		try {
//			client = userDao.newUserReg(phonenum,password);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}			
//		HttpSession session = request.getSession();
//		session.setAttribute("user_id", client.getUserId());
//		session.setAttribute("user_name", client.getPhoneNum());
//		session.setAttribute("user_tel", client.getPhoneNum());
//		session.setAttribute("current_language", client.getCurrentLanguage());
//		response.sendRedirect(request.getContextPath()+"/normalPages/index.jsp");
	}
}
