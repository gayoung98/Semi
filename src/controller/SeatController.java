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
	static String date = "mo";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset= utf-8");
		try {
			System.out.println(date);
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
				
			
				System.out.println("date: " + date);

				int count = dao.rownum(date);
				boolean already = dao.isReserved(email,date);
				if(request.getParameter("seatNumber")!=null) {
					String seat_number = (String)request.getParameter("seatNumber");
					boolean mySeat = dao.mySeat(email, seat_number, date);
					if(count < 14) {
						if(already == false) {
							dao.insert(date, email, name, (String)request.getParameter("seatNumber"));
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
					boolean mySeat = dao.mySeat(email, seat_number, date);
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
				
//				date = request.getParameter("date");
//				System.out.println("date: " + date);
				
				String email = (String)request.getSession().getAttribute("login");
				MemberDTO dto = memdao.getMainInfo(email);
				String khclass = dto.getKhClass();
				String branch = dto.getBranch();
				for(SeatDTO sd:dao.classList(date, khclass, branch) ) {
					System.out.println(sd.getEmail());
				}
				if(dao.classList(date, khclass, branch).size() == 0) {
					System.out.println("아무도 신청 안함");
					response.getWriter().append(gs.toJson(date));
				}else {
				response.getWriter().append(gs.toJson(dao.classList(date, khclass, branch)));
				/* request.getRequestDispatcher("seat/seat.jsp").forward(request, response); */
				}
			}else if(url.contentEquals("/date.seat")) {
				date = request.getParameter("date");
				request.getRequestDispatcher("kh/seat/seat.jsp").forward(request, response);
			
			}else if(url.contentEquals("/seat.seat")) {
				System.out.println("컨트롤러 들렀다 감");
				request.getRequestDispatcher("kh/seat/seat.jsp").forward(request, response);
			}


		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
