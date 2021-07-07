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
			if(url.contentEquals("/reserve.seat")) {
				/*
				 * //String seat_day = request.getParameter("seat_day");
				 * if(dao.reservedList().size()!=0) request.setAttribute("reservedList",
				 * dao.reservedList()); String seat_number =
				 * request.getParameter("seat_number"); boolean already =
				 * dao.isReserved(seat_number); if(already == false) { int result =
				 * dao.insert(new SeatDTO(seat_number)); }else { int result = dao.delete(new
				 * SeatDTO(seat_number)); }
				 * request.getRequestDispatcher("seat/seat.jsp").forward(request, response);
				 * //response.sendRedirect("seat/seat.seat");
				 */				
			}else if(url.contentEquals("/seat.seat")) {
				//String seat_day = request.getParameter("seat_day");
				//request.getRequestDispatcher("seat/seat.jsp").forward(request, response);
				
			}else if(url.contentEquals("/reserve2.seat")){
				System.out.println((String)request.getParameter("seatNumber"));
				System.out.println((String)request.getParameter("cancelSeat"));
				String email = (String) request.getSession().getAttribute("login");
				MemberDTO dto = memdao.getMainInfo(email);
				String name =  dto.getName();
				
				int count = dao.rownum();
				boolean already = dao.isReserved(email);
				if(request.getParameter("seatNumber")!=null) {
					if(count < 14) {
					if(already == false) {
					dao.insert(email, name, (String)request.getParameter("seatNumber"));
					response.getWriter().append(request.getParameter("seatNumber"));
					}
					}
				}
				if(request.getParameter("cancelSeat")!=null) {
					String seat_number = (String)request.getParameter("cancelSeat");
					boolean mySeat = dao.mySeat(email, seat_number);
					if(mySeat == true) {
					dao.delete(new SeatDTO(request.getParameter("cancelSeat")));
					response.getWriter().append(request.getParameter("cancelSeat"));
					}
				}

			} else if(url.contentEquals("/complete.seat")) {
				Gson gs =new Gson(); 
				response.getWriter().append(gs.toJson(dao.reservedList()));
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
