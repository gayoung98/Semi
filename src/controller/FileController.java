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

		String requestURI = request.getRequestURI();
		String ctxPath = request.getContextPath();

		String url = requestURI.substring(ctxPath.length());
		System.out.println("요청 명령 :" + url);

		try {
			
			FreeBoardDAO fbdao = FreeBoardDAO.getInstance();
			FreeFilesDAO ffdao = FreeFilesDAO.getInstance();

			if(url.contentEquals("/download.file")) {
				System.out.println("다운로드 요청 확인!");
				//filedownload는 비동기통신으로 동작
				
				//1번 하드디스크에 있는 파일 내용을 RAM으로 복사
				int seq = Integer.parseInt(request.getParameter("seq"));
				String sysName = request.getParameter("sysname");
				String oriName = request.getParameter("oriname");
				String filesPath = request.getServletContext().getRealPath("files"); //파일 위치 경로

				File targetFile = new File(filesPath +"/"+sysName);//파일 경로 뒤에 서버파일이름 불러오기!
				
					//try-with-resource 
				try(
						FileInputStream fis = new FileInputStream(targetFile); //서버의 RAM기준으로 stream 개방
						DataInputStream dis = new DataInputStream(fis);
						DataOutputStream dos = new DataOutputStream(response.getOutputStream());){
					//내부적으로 성능 업그레이드..(기본 스트림에서 업그레이드 =>필터드스트림!) (서버에게 넘김)
					
					byte[]fileContents = new byte[(int)targetFile.length()]; // long형-> int형으로 배열 사이즈는 int형만이라 [casting] (저장공간 확보)
					dis.readFully(fileContents); //전부 읽어와라!(파일 내용 복사)

					oriName = new String(oriName.getBytes("utf-8"),"iso-8859-1"); 
					//byte배열로 꺼내서 utf-8으로 바꿔! (파일이름의 인코딩 방식을 크롬이 인식할 수 있는 iso-8859-1로 수정)
					
					//2번 RAM에 로딩된 내용을 client에게 전송
					response.reset();//Response 객체의 기본적인 동작을 모두 제거!
					response.setContentType("application/octet-stream");//text가 아닌 (binary)파일을 보낸다고 명시!(text로 인코딩 처리 금지)
					response.setHeader("content-disposition","attachment; filename=\""+oriName+"\"");
					//header영역: attachment는 다운로드를 받으라는 지침! 그 파일의 이름은 이거야! client가 볼 이름은 oriName(자신이 업로드한 파일명)

					dos.write(fileContents);
					dos.flush();//파일 보내고 나서 비워주기!
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
