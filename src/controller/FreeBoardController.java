package controller;

import java.io.File;

import java.io.IOException;



import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.FreeBoardDAO;
import dao.FreeCommentDAO;
import dao.FreeFilesDAO;
import dao.MemberDAO;
import dto.FreeBoardDTO;
import dto.FreeCommentDTO;
import dto.FreeFilesDTO;
import dto.MemberDTO;
import config.FileConfig;
import config.BoardConfig;




@WebServlet("*.fboard")
public class FreeBoardController extends HttpServlet {


	private String XSSFilter(String target) { //XSS 공격 방어하는 방법
		if(target!=null) {
			target =target.replaceAll("<", "&lt;"); //<를 %lt(less than)으로 바꾸겠다.<script> 기능을 작동을 안한다.=> %ltscript>이렇게 나타냄!!
			target =target.replaceAll(">", "&gt;");
			target =target.replaceAll("&", "&amp;");
		}
		return target;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset =utf-8");

		String requestURI = request.getRequestURI();
		String ctxPath = request.getContextPath();

		String url = requestURI.substring(ctxPath.length());
		System.out.println("요청 명령 :" + url);

		try {
			FreeCommentDAO fcdao = FreeCommentDAO.getInstance();	
			FreeBoardDAO fbdao = FreeBoardDAO.getInstance();
			MemberDAO mdao = MemberDAO.getInstance();
			FreeFilesDAO ffdao =FreeFilesDAO.getInstance();

			if(url.contentEquals("/list.fboard")) { //게시글 목록 
				
				//검색기능 추가
				String category =request.getParameter("category"); //카테고리 
				String keyWord =request.getParameter("keyword"); //검색어 입력
				int cpage =Integer.parseInt(request.getParameter("cpage"));
				System.out.println("현재페이지: "+ cpage);
				System.out.println("카테고리: "+ category);
				System.out.println("검색어: "+ keyWord);

				//게시글 목록 가져오는 코드 --DAO

				int endNum= cpage* BoardConfig.Recode_Count_Per_Page;
				int startNum =endNum-(BoardConfig.Navi_Count_Per_Page-1);

				List<FreeBoardDTO> boardlist;
				List<String>pageNavi;//페이지 네비게이션 리스트

				if(keyWord==null || keyWord.contentEquals("")) //keyword가 없거나, keyword의 input 박스가 비워 있을 경우
				{
					boardlist =fbdao.getPageList(startNum,endNum);// 페이지 리스트 시작 번호, 끝번호 parameter로 받음

				}else {
					boardlist =fbdao.getPageList(startNum,endNum,category,keyWord); //페이징 네비게이션 시작/끝 번호 + category 키워드를 parameter로 받음

				}

				pageNavi =fbdao.getPageNavi(cpage,category,keyWord);//페이지 네비게이션에 capge,category, keyword 인자 값을 받음
				request.setAttribute("boardlist", boardlist);
				request.setAttribute("navi", pageNavi);

				//카테고리, 키워드 request에 담아라!
				request.setAttribute("category", category);
				request.setAttribute("keyword", keyWord);


				request.setAttribute("count", fcdao); //댓글 수 출력 
				RequestDispatcher rd = request.getRequestDispatcher("free/FBlist.jsp");
				rd.forward(request, response);
			
			}else if(url.contentEquals("/towrite.fboard")){
				response.sendRedirect("free/FBwrite.jsp");

			}else if(url.contentEquals("/write.fboard")) { //게시판에 글쓰기
				System.out.println("작성 중");
				String filesPath =request.getServletContext().getRealPath("files");

				File filesFolder = new File(filesPath);
				System.out.println("프로젝트가 저장된 진짜 경로" + filesPath);

				//files folder가 없다면 만들어줘라!!
				if(!filesFolder.exists()) filesFolder.mkdir();
				//파일 업로드 기능!
				MultipartRequest multi = new MultipartRequest(request,filesPath,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 

				String title =multi.getParameter("title");
				String contents= multi.getParameter("contents");
				System.out.println("제목 :" + title);
				System.out.println("내용 :" + contents);
				
				MemberDTO sessionDTO = (MemberDTO)request.getSession().getAttribute("login");
				
				//파일 업로드 작업
				int seq = fbdao.getSeq();				
				String branch =sessionDTO.getBranch();
				System.out.println("지점: "+ branch);
				String writer =sessionDTO.getName();
				System.out.println("작성자: "+ writer);
				
				String id = sessionDTO.getId();
				System.out.println("학번: "+ id);
							
				
				int result = fbdao.write(seq,branch,writer,title,contents,id);
				System.out.println("게시글 입력 여부 : "+ result);

				//여러개의 파일이 업로드 될때를 for문
				Set<String>fileNames = multi.getFileNameSet();
				System.out.println("파일갯수 "+fileNames.size());
				for(String fileName : fileNames) {
					System.out.println("파라미터 이름: "+ fileName);
					String oriName = multi.getOriginalFileName(fileName);
					String sysName = multi.getFilesystemName(fileName);

					//oriname이 null이 아니라면? fileupload
					if(oriName!=null) {  
						//파일 저장 seq,oriName,sysName, sysdate,reg_date,parent
						int fileUpload =ffdao.fileUpload(new FreeFilesDTO(0,oriName,sysName,null,seq));
						System.out.println(fileUpload);
					}	
				}
				List<FreeBoardDTO> boardlist =fbdao.boardList();// 목록 받아오기
				request.setAttribute("boardlist", boardlist);
				RequestDispatcher rd = request.getRequestDispatcher("free/FBwriteView.jsp"); //게시물 등록 성공 화면
				rd.forward(request, response);
					
			}else if(url.contentEquals("/detailView.fboard")) {  //게시글 상세보기
				int boardseq = Integer.parseInt(request.getParameter("seq"));

				fbdao.viewCountPlus(boardseq);//조회수

				FreeBoardDTO bdto = fbdao.detailView(boardseq); //상세 보기
				System.out.println(boardseq);
				request.setAttribute("view", bdto);
				
				List<FreeFilesDTO>fileList = ffdao.selectAll(boardseq); //첨부파일 목록 출력	
				System.out.println("파일이 비어 있나요? "+fileList.isEmpty());//파일이 있나요?
				request.setAttribute("filelist", fileList);//파일리스트를 request애 담는다.

				request.setAttribute("count", fcdao); //댓글 수 출력 
				List<FreeCommentDTO> list =fcdao.CommentsList(boardseq);//댓글리스트				
				request.setAttribute("reply", list); //댓글리스트를 request를 담는다.
				
				request.getRequestDispatcher("free/FBdetailView.jsp").forward(request, response);
				
			}else if(url.contentEquals("/modify.fboard")){ //수정하기
				int boardseq = Integer.parseInt(request.getParameter("seq"));
				FreeBoardDTO dto = fbdao.detailView(boardseq);

				List<FreeFilesDTO> flist = ffdao.selectAll(boardseq);

				request.setAttribute("view", dto);
				request.setAttribute("filelist", flist);
				request.getRequestDispatcher("free/FBmodify.jsp").forward(request, response);
			}else if(url.contentEquals("/modifyedit.board")) {	//글 수정하기

				String filesPath =request.getServletContext().getRealPath("files"); //파일 저장된 경로
				
				//수정 페이지에서 파일첨부 추가
				File filesFolder = new File(filesPath);
				System.out.println("프로젝트가 저장된 진짜 경로" + filesPath);

				//files folder가 없다면 만들어줘라!!
				if(!filesFolder.exists()) {filesFolder.mkdir();}
				
				MultipartRequest multi = new MultipartRequest(request,filesPath,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 


				int board_seq = Integer.parseInt(multi.getParameter("seq"));
				System.out.println(board_seq);
				String uptitle = multi.getParameter("title");
				System.out.println("수정한 제목:" +uptitle);
				String upcontents = multi.getParameter("contents");
				System.out.println("수정한 내용: "+ upcontents);
				//String형 배열 return
				String[]delTargets=multi.getParameterValues("delete");
				
				if(delTargets!= null) { //삭제할 항목이 null 아닐때, 
					for(String target : delTargets) {
						System.out.println("지울 파일 번호 "+ target);

						String sysName = ffdao.getSysName(Integer.parseInt(target));//target: 지우고자 하는 파일의 번호
						File targetFile = new File(filesPath +"/" + sysName); //경로를 얹어 놓음
						boolean result = targetFile.delete();
						System.out.println("파일 삭제 여부" + result);
						if(result) {ffdao.fileDelete(Integer.parseInt(target));}
					}
				}
				System.out.println(delTargets.length);
				
				int result = fbdao.modify(board_seq, uptitle, upcontents);
				System.out.println("수정 결과"+ result);
				//여러개의 파일이 업로드 될때를 for문
				Set<String>fileNames = multi.getFileNameSet();
				System.out.println("파일갯수 "+fileNames.size());
				for(String fileName : fileNames) {
					System.out.println("파라미터 이름: "+ fileName);
					String oriName = multi.getOriginalFileName(fileName);
					String sysName = multi.getFilesystemName(fileName);

					//oriname이 null이 아니라면?
					if(oriName!=null) {  
						System.out.println("파일이름" + oriName + "DB에 저장됨.");
						//파일 저장 seq,oriName,sysName, sysdate,reg_date,parent
						int fileUpload =ffdao.fileUpload(new FreeFilesDTO(0,oriName,sysName,null,board_seq));			
					}
				}
					FreeBoardDTO dto = fbdao.detailView(board_seq);
					List<FreeFilesDTO> flist = ffdao.selectAll(board_seq);
					request.setAttribute("view", dto);
					request.setAttribute("filelist", flist);
					request.getRequestDispatcher("board/modifyView.jsp").forward(request, response);
			
			}else if(url.contentEquals("/delete.fboard")){ //삭제하기
				System.out.println("삭제중");
				int seq = Integer.parseInt(request.getParameter("seq"));
				int result = fbdao.delete(seq);
				response.sendRedirect("free/FBdeleteView.jsp");
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
