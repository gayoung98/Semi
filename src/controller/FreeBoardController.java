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
				
			
				String category =request.getParameter("category");
				String keyWord =request.getParameter("keyword"); 
				int cpage =Integer.parseInt(request.getParameter("cpage"));
				System.out.println("현재페이지: "+ cpage);
				System.out.println("카테고리: "+ category);
				System.out.println("검색어: "+ keyWord);


				int endNum= cpage* BoardConfig.Recode_Count_Per_Page;
				int startNum =endNum-(BoardConfig.Navi_Count_Per_Page-1);

				List<FreeBoardDTO> boardlist;
				List<String>pageNavi;//페이지 네비게이션 리스트

				if(keyWord==null || keyWord.contentEquals("")) 
				{
					boardlist =fbdao.getPageList(startNum,endNum);

				}else {
					boardlist =fbdao.getPageList(startNum,endNum,category,keyWord);

				}

				pageNavi =fbdao.getPageNavi(cpage,category,keyWord);
				request.setAttribute("boardlist", boardlist);
				request.setAttribute("navi", pageNavi);

				
				request.setAttribute("category", category);
				request.setAttribute("keyword", keyWord);


				request.setAttribute("count", fcdao);
				RequestDispatcher rd = request.getRequestDispatcher("free/FBlist.jsp");
				rd.forward(request, response);
			
			}else if(url.contentEquals("/towrite.fboard")){
				response.sendRedirect("free/FBwrite.jsp");

			}else if(url.contentEquals("/write.fboard")) { 
				System.out.println("작성 중");
				String filesPath =request.getServletContext().getRealPath("files");

				File filesFolder = new File(filesPath);
				System.out.println("프로젝트가 저장된 진짜 경로" + filesPath);

				
				if(!filesFolder.exists()) filesFolder.mkdir();
				
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

			
				Set<String>fileNames = multi.getFileNameSet();
				System.out.println("파일갯수 "+fileNames.size());
				for(String fileName : fileNames) {
					System.out.println("파라미터 이름: "+ fileName);
					String oriName = multi.getOriginalFileName(fileName);
					String sysName = multi.getFilesystemName(fileName);

					
					if(oriName!=null) {  
						
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
				
				List<FreeFilesDTO>fileList = ffdao.selectAll(boardseq); 
				System.out.println("파일이 비어 있나요? "+fileList.isEmpty());//파일이 있나요?
				request.setAttribute("filelist", fileList);

				request.setAttribute("count", fcdao); 
				List<FreeCommentDTO> list =fcdao.CommentsList(boardseq);			
				request.setAttribute("reply", list); 
				
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
				
			
				File filesFolder = new File(filesPath);
				System.out.println("프로젝트가 저장된 진짜 경로" + filesPath);

				
				if(!filesFolder.exists()) {filesFolder.mkdir();}
				
				MultipartRequest multi = new MultipartRequest(request,filesPath,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 


				int board_seq = Integer.parseInt(multi.getParameter("seq"));
				System.out.println(board_seq);
				String uptitle = multi.getParameter("title");
				System.out.println("수정한 제목:" +uptitle);
				String upcontents = multi.getParameter("contents");
				System.out.println("수정한 내용: "+ upcontents);
				
				String[]delTargets=multi.getParameterValues("delete");
				
				if(delTargets!= null) {  
					for(String target : delTargets) {
						System.out.println("지울 파일 번호 "+ target);

						String sysName = ffdao.getSysName(Integer.parseInt(target));
						File targetFile = new File(filesPath +"/" + sysName); 
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

				
					if(oriName!=null) {  
						System.out.println("파일이름" + oriName + "DB에 저장됨.");
					
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
