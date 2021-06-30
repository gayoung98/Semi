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
		
			
			if(url.contentEquals("/writechat.main")) {
				String chat = request.getParameter("writechat");
				int result = dao.writechat(chat);
				
				response.sendRedirect("main/main.jsp");
				
			}else if(url.contentEquals("/listchat.main")) {
				Gson g = new Gson();
				List<MainDTO> list = dao.getAllList();
				String result = g.toJson(list);
				response.getWriter().append(result);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
