package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SeatDAO;
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
			
			if(url.contentEquals("/reserve.seat")) {
				//String seat_day = request.getParameter("seat_day");
				String seat_number = request.getParameter("seat_number");
				boolean already = dao.isReserved(seat_number);
				if(already == false) {
					int result = dao.insert(new SeatDTO(seat_number));
				}else {
					int result = dao.delete(new SeatDTO(seat_number));
				}
				response.sendRedirect("seat/seat.seat");
			}else if(url.contentEquals("/seat.seat")) {
				//String seat_day = request.getParameter("seat_day");
				request.getRequestDispatcher("seat/seat.jsp").forward(request, response);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
