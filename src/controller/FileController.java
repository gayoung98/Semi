package controller;

import java.io.BufferedInputStream;


import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import config.FileConfig;
import dao.FreeBoardDAO;
import dao.FreeFilesDAO;
import dto.FreeFilesDTO;



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
			FreeBoardDAO fbdao = FreeBoardDAO.getInstance();
			FreeFilesDAO ffdao = FreeFilesDAO.getInstance();

			if(url.contentEquals("/download.file")) {
				System.out.println("다운로드 요청 확인!");
				
				
				int seq = Integer.parseInt(request.getParameter("seq"));
				String sysName = request.getParameter("sysname");
				String oriName = request.getParameter("oriname");
				
				
				String filesPath =request.getServletContext().getRealPath("/uploadDirectory");
		
				System.out.println("다운로드 위치: "+ filesPath);
				
				File targetFile = new File(filesPath+"/"+sysName); //import io.File
			
			try(
						FileInputStream fis = new FileInputStream(targetFile); 
						DataInputStream dis = new DataInputStream(fis);
					DataOutputStream dos = new DataOutputStream(response.getOutputStream());){
					
					byte[]fileContents = new byte[(int)targetFile.length()]; 
					dis.readFully(fileContents);
					oriName = new String(oriName.getBytes("utf-8"),"iso-8859-1"); 
					
					response.reset();
					response.setHeader("content-disposition","attachment; filename=\""+oriName+"\"");
					
						dos.write(fileContents);					
						dos.flush();
					
				}
			}else if(url.contentEquals("/upload.file")) {
				response.setCharacterEncoding("utf8");
				response.setContentType("text/html;charset=utf8");
				String filesPath =request.getServletContext().getRealPath("imagefiles");

				File filesFolder = new File(filesPath);
				System.out.println("프로젝트가 저장된 진짜 경로 : " + filesPath);

				//files folder가 없다면 만들어줘라!!
				if(!filesFolder.exists()) filesFolder.mkdir();
				//파일 업로드 기능!
				MultipartRequest multi = new MultipartRequest(request,filesPath,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 

					String oriName = multi.getOriginalFileName("file");
					String sysName = multi.getFilesystemName("file");					

					if(oriName!=null) { 
						System.out.println("파일 이름: " +oriName +" DB에 저장됨.");
					//submit하는 순간 parent 
//						int fileUpload =ffdao.fileUpload(new FreeFilesDTO(0,oriName,sysName,null,0));// summernote애 이미지 붙여놓는것이라 parent 값 필요없음!!

						request.getSession().setAttribute("ingFiles", sysName);//파일이 한 개라는 전제,파일 업로드 하는 순간 파일 이름 저장
						
					}
				String returnPath =  "/imagefiles/"+ sysName;
					response.getWriter().append(returnPath);
					
				}else if(url.contentEquals("/resolveFiles.file")) {
			System.out.println("정리 요청!!!");
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
