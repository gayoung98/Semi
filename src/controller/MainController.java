package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.MainDAO;
import dao.inquiredDAO;
import dto.InquireDTO;
import dto.MainDTO;


@WebServlet("*.main")
public class MainController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset= utf-8");
		
		try {
			String requestURI = request.getRequestURI();
			String ctxPath = request.getContextPath();
			String url = requestURI.substring(ctxPath.length());
			MainDAO dao = MainDAO.getInstance();
		
			System.out.println(url);
			if(url.contentEquals("/writechat.main")) {
				String chat = request.getParameter("writechat");
				int result = dao.writechat(chat);
				response.sendRedirect(ctxPath+"/main.main");
			} else if(url.contentEquals("/main.main")) {
	            request.getSession().setAttribute("id",(String)"blue");
	            request.setAttribute("firstlist", dao.likeFacebook(10, 1));
	            request.setAttribute("list", dao.getAllList().size());
	            request.getRequestDispatcher("main/main.jsp").forward(request, response);
			}else if(url.contentEquals("/listchat.main")) {
				Gson g = new Gson();
				List<MainDTO> list = dao.likeFacebook(10, Integer.parseInt(request.getParameter("count")));
				String result = g.toJson(list);
				response.getWriter().append(result);
			}else if(url.contentEquals("/inquired.main")) {
				
				inquiredDAO iqD = inquiredDAO.getInstance();
				String writer = request.getParameter("writer");
				String major = request.getParameter("major_category");
				String sub = request.getParameter("sub_category");
				String contents = request.getParameter("inquire_contents");
				try {
					int result=iqD.insert(new InquireDTO(writer, major, sub, contents));
					boolean insertResult = result >0 ? true : false;
					System.out.println(String.valueOf(insertResult));
					request.setAttribute("result", insertResult);
					request.getRequestDispatcher("inquired/PopResult.jsp").forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
