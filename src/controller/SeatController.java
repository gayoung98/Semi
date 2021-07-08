package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.MemberDAO;
import dao.SeatDAO;
import dto.MemberDTO;
import dto.SeatDTO;

@WebServlet("*.seat")
public class SeatController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset= utf-8");
		try {
			String requestURI = request.getRequestURI();
			String ctxPath = request.getContextPath();
			String url = requestURI.substring(ctxPath.length());
			SeatDAO dao = SeatDAO.getInstance();
			MemberDAO memdao = MemberDAO.getInstance();
			System.out.println(url);
			if(url.contentEquals("/reserve2.seat")){
				System.out.println((String)request.getParameter("seatNumber"));
				System.out.println((String)request.getParameter("cancelSeat"));
				String email = (String) request.getSession().getAttribute("login");
				MemberDTO dto = memdao.getMainInfo(email);
				String name =  dto.getName();

				int count = dao.rownum();
				boolean already = dao.isReserved(email);
				if(request.getParameter("seatNumber")!=null) {
					String seat_number = (String)request.getParameter("seatNumber");
					boolean mySeat = dao.mySeat(email, seat_number);
					if(count < 14) {
						if(already == false) {
							dao.insert(email, name, (String)request.getParameter("seatNumber"));
							response.getWriter().append(request.getParameter("seatNumber"));
						}else {
							//이미 선택했는데 다른 좌석 누를때
							response.getWriter().append("already");
						}
					}else {
						//14명 신청했을때
						response.getWriter().append("corona");
					}
				}
				if(request.getParameter("cancelSeat")!=null) {
					String seat_number = (String)request.getParameter("cancelSeat");
					boolean mySeat = dao.mySeat(email, seat_number);
					if(mySeat == true) {
						dao.delete(new SeatDTO(request.getParameter("cancelSeat")));
						response.getWriter().append(request.getParameter("cancelSeat"));
					}else {
						//다른 사람이 신청한 좌석 누를때
						response.getWriter().append("notmyseat");
					}
				}

			} else if(url.contentEquals("/complete.seat")) {
				Gson gs =new Gson(); 

				String email = (String)request.getSession().getAttribute("login");
				MemberDTO dto = memdao.getMainInfo(email);
				String khclass = dto.getKhClass();
				String branch = dto.getBranch();

				response.getWriter().append(gs.toJson(dao.classList(khclass, branch)));
			
			}else if(url.contentEquals("/confirmCancel.seat")) {
				String email = (String) request.getSession().getAttribute("login");
				MemberDTO dto = memdao.getMainInfo(email);
				String name =  dto.getName();
				dao.insert(email, name, (String)request.getParameter("seat_number"));
				response.sendRedirect("seat/seat.jsp");
			}else if(url.contentEquals("/confirmReserve.seat")) {
				dao.delete(new SeatDTO((String)request.getParameter("seat_number")));
				response.sendRedirect("seat/seat.jsp");
			}


		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
