package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import config.ManagerConfig;
import dao.ManagerDAO;
import dto.MemberDTO;



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
			}else if(url.contentEquals("/logout.manager")) {   // 로그아웃 
				request.getSession().invalidate();
				response.sendRedirect("manager/login.jsp"); 
			}else if (url.contentEquals("/teacher.manager")) {      // 강사 목록
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
				String position =ManagerConfig.teacher;
		
				
				List<MemberDTO> teacherList=managerDao.getPageList(position,startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getPageNavi(currentPage,category,search,position,branch);
				request.setAttribute("list", teacherList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.member/teacher.jsp").forward(request,response);
				
			}else if (url.contentEquals("/student.manager")) {      // 강사 목록
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
				String position =ManagerConfig.student;
		
				
				List<MemberDTO> teacherList=managerDao.getPageList(position,startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getPageNavi(currentPage,category,search,position,branch);
				request.setAttribute("list", teacherList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.member/student.jsp").forward(request,response);
				
			}
			
		
	
	}catch(Exception e) {
		e.printStackTrace();
	}
}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
}


}




				
		
