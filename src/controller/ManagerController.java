package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ManagerDAO;


@WebServlet("*.manager")
public class ManagerController extends HttpServlet {
	private HttpSession session;
	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset =utf-8");
		
		try {
			String requestURI = request.getRequestURI();
			String ctxPath = request.getContextPath();
			String url = requestURI.substring(ctxPath.length());
			ManagerDAO managerDao = ManagerDAO.getInstance();    
			
			System.out.println(url);    // 접속 url 출력
			
			if(url.contentEquals("/login.manager")) {   // 로그인 요청
				String id = request.getParameter("id");
				String pw = request.getParameter("pw");
				
				boolean login = managerDao.login(id, pw);
				if(login) {
					request.getSession().setAttribute("login", id);
					response.sendRedirect("manager/manager.member/index.jsp");
				}else {
					response.sendRedirect("manager/login.jsp");
				}
			}else if(url.contentEquals("/logout.manager")) {
				request.getSession().invalidate();
				response.sendRedirect("manager/login.jsp");
			}
			
	}catch(Exception e) {
		e.printStackTrace();
	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

}
