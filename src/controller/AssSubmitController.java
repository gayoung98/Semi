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

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.AssSubmitDAO;
import dao.MemberDAO;
import dto.AssSubmitDTO;


@WebServlet("*.assSubmit")
public class AssSubmitController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		String requestURI = request.getRequestURI();
		String ctxPath = request.getContextPath();
		String url = requestURI.substring(ctxPath.length());
		AssSubmitDAO dao = AssSubmitDAO.getInstance();
		MemberDAO daoM = MemberDAO.getInstance();

		try {
			if(url.contentEquals("/write.assSubmit")) {
				
				System.out.println("write.assSubmit");
				
				String email = (String)request.getSession().getAttribute("login");
				String filesPath = request.getServletContext().getRealPath("assSubmit/"+email);
				File filesFolder = new File(filesPath); //java.io.file
				System.out.println("프로젝트가 저장된 진짜 경로: " + filesPath);
				int maxSize = 1024*1024 *10; //10메가

				if(!filesFolder.exists()) {
					filesFolder.mkdir(); //make directory
				}

				MultipartRequest multi = new MultipartRequest(request, filesPath, maxSize, "utf-8", new DefaultFileRenamePolicy());
				//파라미터: 멀티파트로 업그레이드할 인자, 저장 경로, 최대 사이즈 , 인코딩, 파일명명규칙(겹치는 이름있으면 뒤에 숫자붙임. 인터넷 검색 필요)

				
				String writer = email;
				String id = daoM.getAllInfo(email).getId();

				String oriName = multi.getOriginalFileName("assSubmit"); //오리지널 이름
				String sysName = multi.getFilesystemName("assSubmit"); //서버에 저장된 이름
				System.out.println(oriName);
				System.out.println(sysName);
				int parent = Integer.parseInt(multi.getParameter("parent"));

				AssSubmitDTO dto = new AssSubmitDTO(0, writer, id, oriName, sysName, null, parent);
				int result = dao.insert(dto);
				if(result>0) {
					System.out.println("과제 제출 완료");
				}else {
					System.out.println("과제 제출 안 됨.");
				}
				
				
				response.sendRedirect("view.ass?ass_seq="+parent);
				
			}else if(url.contentEquals("/delete.assSubmit")) {

				System.out.println("delete.assSubmit");
				int parent = Integer.parseInt(request.getParameter("assSubmitParent"));
				int seq = Integer.parseInt(request.getParameter("assSubmitSeq"));
				
				int result = dao.delete(seq);
				System.out.println("parent: " +parent);
				if(result>0) {
					System.out.println("삭제 완료");
				}else {
					System.out.println("삭제 안 됨.");
				}
				
				response.sendRedirect("view.ass?ass_seq="+parent);
				
			}else if(url.contentEquals("/download.assSubmit")) {

				int seq = Integer.parseInt(request.getParameter("seq"));
				String email =request.getParameter("writer");
				String oriName = request.getParameter("oriName");
				String sysName = request.getParameter("sysName");
				String filesPath = request.getServletContext().getRealPath("assSubmit/"+email);

				File targetFile = new File(filesPath+"/"+sysName); //import io.File


				try(
						FileInputStream fis = new FileInputStream(targetFile);
						DataInputStream dis = new DataInputStream(fis);
						DataOutputStream dos = new DataOutputStream(response.getOutputStream());
						){

					byte[] fileContents = new byte[(int)targetFile.length()];
					dis.readFully(fileContents);
					oriName = new String(oriName.getBytes("utf-8"), "iso-8859-1");
						//파일 이름의 인코딩 방식을 크롬이 인식할 수 있는 iso-8859-1로 수정
					
					//1. 하드디스크에 있는 파일의 내용을 램으로 복사

					response.reset(); //Response 객체의 기본 동작을 모두 제거
					response.setContentType("application/octet-stream"); 
					//8진수 stream(binary 내용)을 전송하는 것이라는 것을 명시
					response.setHeader("content-disposition", "attachment; filename=\""+oriName+"\"");
					//attachment: 첨부파일을 전송하는 것을 명시, 다운로드 하라는 지침
					//client에 전송해야 하는 파일명은 클라이언트가 업로드한 이름이어야 하므로 oriName

					dos.write(fileContents);
					dos.flush();
				}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
