package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FreeBoardDAO;
import dao.FreeCommentDAO;
import dao.FreeFilesDAO;
import dao.FreePoliceDAO;
import dao.MemberDAO;
import dto.FreeBoardDTO;
import dto.MemberDTO;


@WebServlet("/.police")
public class PoliceController extends HttpServlet {
	


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset =utf-8");

		String requestURI = request.getRequestURI();
		String ctxPath = request.getContextPath();

		String url = requestURI.substring(ctxPath.length());
		System.out.println("요청 명령 :" + url);

		try {
			FreePoliceDAO fpdao = FreePoliceDAO.getInstance();
			FreeBoardDAO fbdao = FreeBoardDAO.getInstance();
			MemberDAO mdao = MemberDAO.getInstance();



			if(url.contentEquals("/report.police")) { //게시글 신고
				String email = (String) request.getSession().getAttribute("login");
				MemberDTO dto = mdao.getMainInfo(email);
				
				
//				List<FreeBoardDTO> boardlist =fbdao.boardList();// 목록 받아오기

				String id = dto.getId();
				String parent = request.getParameter("fboard_seq");
				String reason = request.getParameter("contents");
				int result = fpdao.report(id,parent, reason);
				System.out.println("신고글 등록 여부 : "+ result);
				
				
				request.setAttribute("name", dto.getName());


				RequestDispatcher rd = request.getRequestDispatcher("free/reportResult.jsp"); //게시물 등록 성공 화면
				rd.forward(request, response);
			}
			
		}catch(Exception e) {
				e.printStackTrace();
				response.sendRedirect("error.jsp");
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
