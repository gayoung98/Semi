package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Manager;
import org.apache.commons.lang3.StringUtils;

import config.ManagerConfig;
import dao.ManagerDAO;
import dto.AssDTO;
import dto.FreeBoardDTO;
import dto.FreePoliceDTO;
import dto.InquireDTO;
import dto.MemberDTO;
import dto.NoticeBoardDTO;



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
					response.sendRedirect(ctxPath+"/index.manager");
					
				
				}else {
					response.sendRedirect("manager/login.jsp");
				}
			}else if(url.contentEquals("/index.manager")) {
				List<FreeBoardDTO> boardList = managerDao.indexBoard();
				List<FreePoliceDTO> policeList = managerDao.indexPolice();
			
				request.setAttribute("list", boardList);
				request.setAttribute("plist", policeList);
				request.getRequestDispatcher("manager/manager.member/index.jsp").forward(request,response);
			}
			else if(url.contentEquals("/logout.manager")) {   // 로그아웃 
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
		
				
				List<MemberDTO> teacherList=managerDao.getMemberPageList(position,startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getMemberPageNavi(currentPage,category,search,position,branch);
				request.setAttribute("list", teacherList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.member/teacher.jsp").forward(request,response);
				
			}else if (url.contentEquals("/student.manager")) {      // 학생 목록
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
				String position =ManagerConfig.student;
		
				
				List<MemberDTO> teacherList=managerDao.getMemberPageList(position,startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getMemberPageNavi(currentPage,category,search,position,branch);
				request.setAttribute("list", teacherList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.member/student.jsp").forward(request,response);
				
			}else if(url.contentEquals("/boardList.manager")) { 		// 자유게시판 목록
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
			
		
				
				List<FreeBoardDTO> boardList=managerDao.getBoardPageList(startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getBoardPageNavi(currentPage,category,search,branch);
				request.setAttribute("list", boardList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.board/boardList.jsp").forward(request,response);
				
			}else if(url.contentEquals("/noticeList.manager")) {		//공지사항 목록
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
			
		
				
				List<NoticeBoardDTO> boardList=managerDao.getNoticePageList(startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getNoticeBoardPageNavi(currentPage,category,search,branch);
				request.setAttribute("list", boardList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.board/noticeList.jsp").forward(request,response);
				
			}else if(url.contentEquals("/assList.manager")) {		// 과제게시판 목록
			
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
			
				
				List<AssDTO> boardList=managerDao.getAssPageList(startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getAssPageNavi(currentPage,category,search,branch);
				request.setAttribute("list", boardList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.board/assList.jsp").forward(request,response);
				
			}else if(url.contentEquals("/boardPolice.manager")) { 		//신고 목록
			
				
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
			
				
				List<FreePoliceDTO> boardList=managerDao.getFreePolicePageList(startNum, endNum,category,search);
				List<String> pageNavi = managerDao.getFreePolicePageNavi(currentPage,category,search);
				request.setAttribute("list", boardList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.police/boardPolice.jsp").forward(request,response);
				
			}else if(url.contentEquals("/inquireList.manager")) {		// 문의목록
			
				
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
			
				
				List<InquireDTO> boardList=managerDao.getInquirePageList(startNum, endNum,category,search);
				List<String> pageNavi = managerDao.getInquirePageNavi(currentPage,category,search);
				request.setAttribute("list", boardList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.inquire/inquireList.jsp").forward(request,response);
				
			}else if(url.contentEquals("/inquireDetail.manager")) {   		//문의 상세보기
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				int seq = Integer.parseInt(request.getParameter("seq"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				List<InquireDTO> list = managerDao.getInquire(seq);
				boolean hasNotRecomment = managerDao.hasNotRecomment(seq);
				request.setAttribute("list", list);
				request.setAttribute("page", currentPage);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.setAttribute("hasNotRecomment", hasNotRecomment);
				System.out.println(hasNotRecomment);
				request.getRequestDispatcher("manager/manager.inquire/inquireDetail.jsp").forward(request,response);
			}else if ( url.contentEquals("/writeRecomment.manager")) {			// 답변 작성
				int seq = Integer.parseInt(request.getParameter("seq"));
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				String recomment = request.getParameter("recomment");
				int result=managerDao.insertRecomment(seq, recomment);
				List<InquireDTO> list = managerDao.getInquire(seq);
				boolean hasNotRecomment = managerDao.hasNotRecomment(seq);
				request.setAttribute("list", list);
				request.setAttribute("page", currentPage);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.setAttribute("hasNotRecomment", hasNotRecomment);
				System.out.println(hasNotRecomment);
				request.getRequestDispatcher("manager/manager.inquire/inquireDetail.jsp").forward(request,response);
			}else if(url.contentEquals("/teacherDelete.manager")) {  // 강사 정보 삭제
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				String branch = request.getParameter("branch");
				String delId = request.getParameter("delId");
				
				managerDao.memberDelete(delId);
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
				String position =ManagerConfig.teacher;
				List<MemberDTO> teacherList=managerDao.getMemberPageList(position,startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getMemberPageNavi(currentPage,category,search,position,branch);
				request.setAttribute("list", teacherList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.member/teacher.jsp").forward(request,response);
			}else if(url.contentEquals("/studentDelete.manager")) {  // 학생 정보 삭제
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				String branch = request.getParameter("branch");
				String delId = request.getParameter("delId");
				
				managerDao.memberDelete(delId);
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
				String position =ManagerConfig.student;
				List<MemberDTO> studentList=managerDao.getMemberPageList(position,startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getMemberPageNavi(currentPage,category,search,position,branch);
				request.setAttribute("list", studentList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.member/student.jsp").forward(request,response);
			}else if (url.contentEquals("/inquireDelete.manager")) {		// 문의 답변 삭제
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int delSeq =Integer.parseInt(request.getParameter("delSeq"));
				
				managerDao.inquireDelete(delSeq);
				List<InquireDTO> list = managerDao.getInquire(delSeq);
				boolean hasNotRecomment = managerDao.hasNotRecomment(delSeq);
				request.setAttribute("list", list);
				request.setAttribute("page", currentPage);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.setAttribute("hasNotRecomment", hasNotRecomment);
				System.out.println(hasNotRecomment);
				request.getRequestDispatcher("manager/manager.inquire/inquireDetail.jsp").forward(request,response);
			}else if(url.contentEquals("/modifyRecomment.manager")) {		// 답변 수정
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int seq =Integer.parseInt(request.getParameter("seq"));
				String recomment = request.getParameter("recomment");
				managerDao.inquireModify(seq,recomment);
				List<InquireDTO> list = managerDao.getInquire(seq);
				boolean hasNotRecomment = managerDao.hasNotRecomment(seq);
				request.setAttribute("list", list);
				request.setAttribute("page", currentPage);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.setAttribute("hasNotRecomment", hasNotRecomment);
				System.out.println(hasNotRecomment);
				request.getRequestDispatcher("manager/manager.inquire/inquireDetail.jsp").forward(request,response);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
}
		
				
			
			
		
	




				
		
