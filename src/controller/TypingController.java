package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import dao.TypingDAO;
import dto.TypingDTO;

/**
 * Servlet implementation class TypingController
 */
@WebServlet("*.typ")
public class TypingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		String requestURI = request.getRequestURI();
		String ctxPath = request.getContextPath();
		String url = requestURI.substring(ctxPath.length());

		TypingDAO daoT = TypingDAO.getInstance();
		MemberDAO daoM = MemberDAO.getInstance();

		try {

			if(url.contentEquals("/write.typ")) {
				
				System.out.println("write.typ");
				int record = Integer.parseInt(request.getParameter("record"));
				int accuracy = Integer.parseInt(request.getParameter("accuracy"));

				String writer = (String)request.getSession().getAttribute("login");
				String id = daoM.getAllInfo(writer).getId();


				TypingDTO dto = new TypingDTO(0, writer, id, record, accuracy, null);
				int result = daoT.insert(dto); 
				if(result>0) {
					System.out.println("타이핑 저장 완료");
				}else {
					System.out.println("타이핑 저장 안 됨.");
				}

				response.sendRedirect("view.typ");

			}else if(url.contentEquals("/view.typ")) {
				System.out.println("view.typ");

				String email = (String)request.getSession().getAttribute("login");
				String id = daoM.getAllInfo(email).getId();

				List<TypingDTO> recentList = daoT.getRecentList(id);

				if(recentList != null) {
					request.setAttribute("recentList", recentList);

					RequestDispatcher rd = request.getRequestDispatcher("kh/typing/typing.jsp");
					rd.forward(request,response);
				}else {
					response.sendRedirect("kh/typing/typing.jsp");
				}

			}




		}catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
