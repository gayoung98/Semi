package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import dao.ManagerDAO;


@WebServlet("*.managerLogin")
public class ManagerLoginController extends HttpServlet {
	Logger l = Logger.getLogger(ManagerLoginController.class);
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset =utf-8");
		
		try {
		String requestURI = request.getRequestURI();
		String ctxPath = request.getContextPath();
		String url = requestURI.substring(ctxPath.length());
		ManagerDAO managerDao = ManagerDAO.getInstance();  
		if(url.contentEquals("/login.managerLogin")) {   // 로그인 요청
			
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			
			boolean login = managerDao.login(id, pw);
			l.trace(request.getRemoteAddr()+ " 관리자로그인을 시도함");
			if(login) {
				l.trace(request.getRemoteAddr()+ " 관리자 로그인 완료");
				request.getSession().setAttribute("login", id);
				request.getRequestDispatcher("/index.manager").forward(request,response);
				
			
			}else {
				response.sendRedirect("manager/login.jsp");
			}
		}
		}catch(Exception e) {
			l.trace(request.getRemoteAddr()+ " 관리자페이지 에러");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
