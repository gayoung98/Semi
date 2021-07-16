package controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

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
	private final ReentrantLock re = new ReentrantLock();

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
				re.lock();
				String date  = request.getParameter("date");
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
					boolean conflict = dao.conflict(seat_number);
					if(count < 14) {
						if(already == false && conflict == false) {
							int deleteSame = dao.deleteSame(dao.min(name, seat_number), name, seat_number);
							dao.insert(date, email, name, (String)request.getParameter("seatNumber"));
							deleteSame = dao.deleteSame(dao.min(name, seat_number), name, seat_number);
							response.getWriter().append(request.getParameter("seatNumber"));
						
					}else {
						//�씠誘� �꽑�깮�뻽�뒗�뜲 �떎瑜� 醫뚯꽍 �늻瑜쇰븣
						response.getWriter().append("already");
					}
					}
				}else {
					//14紐� �떊泥��뻽�쓣�븣
					response.getWriter().append("corona");
				}

				re.unlock();
			} else if(url.contentEquals("/complete.seat")) {
				Gson gs =new Gson(); 
				String date  = request.getParameter("date");
				//				date = request.getParameter("date");
				//				System.out.println("date: " + date);

				String email = (String)request.getSession().getAttribute("login");
				MemberDTO dto = memdao.getMainInfo(email);
				String khclass = dto.getKhClass();
				String branch = dto.getBranch();
				String name = dto.getName();

				String mySeatNumber = dao.mySeatNumber(date, name);
				if(mySeatNumber != null ) dao.deleteSame(dao.min(name, mySeatNumber), name, mySeatNumber);
				for(SeatDTO sd:dao.classList(date, khclass, branch) ) {
					System.out.println(sd.getEmail());
				}
				if(dao.classList(date, khclass, branch).size() == 0) {
					response.getWriter().append(gs.toJson(date));
				}else {
					response.getWriter().append(gs.toJson(dao.classList(date, khclass, branch)));
					/* request.getRequestDispatcher("seat/seat.jsp").forward(request, response); */
				}
			}else if(url.contentEquals("/date.seat")) {
				String date  = request.getParameter("date");
				request.setAttribute("date", date);
				request.getRequestDispatcher("kh/seat/seat.jsp").forward(request, response);

			}else if(url.contentEquals("/seat.seat")) {
				String date  = request.getParameter("date");
				request.getRequestDispatcher("kh/seat/seat.jsp").forward(request, response);
			}else if(url.contentEquals("/cancel.seat")) {
				String date  = request.getParameter("date");
				System.out.println((String)request.getParameter("seatNumber"));
				System.out.println((String)request.getParameter("cancelSeat"));
				String email = (String) request.getSession().getAttribute("login");
				MemberDTO dto = memdao.getMainInfo(email);
				String name =  dto.getName();

				System.out.println("date: " + date);

				if(request.getParameter("cancelSeat")!=null) {

					String seat_number = (String)request.getParameter("cancelSeat");
					boolean mySeat = dao.mySeat(email, seat_number, date);
					if(mySeat == true) {
						dao.delete(email, request.getParameter("cancelSeat"));
						response.getWriter().append(request.getParameter("cancelSeat"));
					}else {
						//�떎瑜� �궗�엺�씠 �떊泥��븳 醫뚯꽍 �늻瑜쇰븣
						response.getWriter().append("notmyseat");
					}
				}
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
