package controller;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FreeBoardDAO;
import dao.FreeFilesDAO;


@WebServlet("*.file")
public class FileController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset =utf-8");

		String requestURI = request.getRequestURI();
		String ctxPath = request.getContextPath();

		String url = requestURI.substring(ctxPath.length());
		System.out.println("요청 명령 :" + url);

		try {
			

			if(url.contentEquals("/download.file")) {
				System.out.println("다운로드 요청 확인!");
				
				int seq = Integer.parseInt(request.getParameter("seq"));
				String sysName = request.getParameter("sysname");
				String oriName = request.getParameter("oriname");
				String filesPath = request.getServletContext().getRealPath("files"); 

				File targetFile = new File(filesPath +"/"+sysName);
				
					
				try(
						FileInputStream fis = new FileInputStream(targetFile); 
						DataInputStream dis = new DataInputStream(fis);
						DataOutputStream dos = new DataOutputStream(response.getOutputStream());){
					
					
					byte[]fileContents = new byte[(int)targetFile.length()]; 

					oriName = new String(oriName.getBytes("utf-8"),"iso-8859-1"); 
					
					response.reset();
					response.setHeader("content-disposition","attachment; filename=\""+oriName+"\"");
					
					dos.write(fileContents);
					dos.flush();
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
