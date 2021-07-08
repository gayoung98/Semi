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
import javax.servlet.http.HttpSession;

import org.apache.catalina.Manager;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import config.FileConfig;
import config.ManagerConfig;
import dao.AssDAO;
import dao.AssFilesDAO;
import dao.AssSubmitDAO;
import dao.FreeBoardDAO;
import dao.FreeCommentDAO;
import dao.FreeFilesDAO;
import dao.ManagerDAO;
import dao.NoticeBoardDAO;
import dao.NoticeCommentDAO;
import dao.NoticeFileDAO;
import dto.AssDTO;
import dto.AssFilesDTO;
import dto.AssSubmitDTO;
import dto.FreeBoardDTO;
import dto.FreeCommentDTO;
import dto.FreeFilesDTO;
import dto.FreePoliceDTO;
import dto.InquireDTO;
import dto.MemberDTO;
import dto.NoticeBoardDTO;
import dto.NoticeCommentsDTO;
import dto.NoticeFilesDTO;




@WebServlet("*.manager")
public class ManagerController extends HttpServlet {
	Logger l = Logger.getLogger(ManagerController.class);
	
	
	private HttpSession session;
	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset =utf-8");
		request.getRemoteAddr();
		try {
			
			String requestURI = request.getRequestURI();
			String ctxPath = request.getContextPath();
			String url = requestURI.substring(ctxPath.length());
			ManagerDAO managerDao = ManagerDAO.getInstance();    
			NoticeBoardDAO nbdao = NoticeBoardDAO.getInstance();
			NoticeCommentDAO ncdao = NoticeCommentDAO.getInstance();
			NoticeFileDAO nfdao=NoticeFileDAO.getInstance();
			FreeFilesDAO ffdao=FreeFilesDAO.getInstance();
			FreeBoardDAO fbdao=FreeBoardDAO.getInstance();
			FreeCommentDAO fcdao=FreeCommentDAO.getInstance();
			AssDAO assDao=AssDAO.getInstance();
			AssFilesDAO assFileDao=AssFilesDAO.getInstance();
			AssSubmitDAO assSubmitDao=AssSubmitDAO.getInstance();
			System.out.println(url);    // 접속 url 출력
			
			if(url.contentEquals("/login.manager")) {   // 로그인 요청
				l.trace(request.getRemoteAddr()+ " 로그인을 시도함");
				String id = request.getParameter("id");
				String pw = request.getParameter("pw");
				
				boolean login = managerDao.login(id, pw);
				if(login) {
					l.trace(request.getRemoteAddr()+ " 로그인 완료");
					request.getSession().setAttribute("login", id);
					response.sendRedirect(ctxPath+"/index.manager");
					
				
				}else {
					response.sendRedirect("manager/login.jsp");
				}
			}else if(url.contentEquals("/index.manager")) {
				List<FreeBoardDTO> boardList = managerDao.indexBoard();
				List<FreePoliceDTO> policeList = managerDao.indexPolice();
				List<InquireDTO> inquireList = managerDao.indexInquire();
				request.setAttribute("list", boardList);
				request.setAttribute("ilist", inquireList);
				request.setAttribute("plist", policeList);
				request.getRequestDispatcher("manager/manager.member/index.jsp").forward(request,response);
			}
			else if(url.contentEquals("/logout.manager")) {   // 로그아웃 
				l.trace(request.getRemoteAddr()+" 로그아웃함");
				request.getSession().invalidate();
				response.sendRedirect("manager/login.jsp"); 
			}else if (url.contentEquals("/teacher.manager")) {      // 강사 목록
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
				String position =ManagerConfig.teacher;
		
				
				List<MemberDTO> teacherList=managerDao.getMemberPageList(position,startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getMemberPageNavi(currentPage,category,search,position,branch);
				request.setAttribute("list", teacherList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.member/teacher.jsp").forward(request,response);
				
			}else if (url.contentEquals("/student.manager")) {      // 학생 목록
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
				String position =ManagerConfig.student;
		
				
				List<MemberDTO> teacherList=managerDao.getMemberPageList(position,startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getMemberPageNavi(currentPage,category,search,position,branch);
				request.setAttribute("list", teacherList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.member/student.jsp").forward(request,response);
				
			}else if(url.contentEquals("/boardList.manager")) { 		// 자유게시판 목록
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
			
		
				
				List<FreeBoardDTO> boardList=managerDao.getBoardPageList(startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getBoardPageNavi(currentPage,category,search,branch);
				request.setAttribute("list", boardList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.board/boardList.jsp").forward(request,response);
				
			}else if(url.contentEquals("/noticeList.manager")) {		//공지사항 목록
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
			
		
				
				List<NoticeBoardDTO> boardList=managerDao.getNoticePageList(startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getNoticeBoardPageNavi(currentPage,category,search,branch);
				request.setAttribute("list", boardList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.board/noticeList.jsp").forward(request,response);
				
			}else if(url.contentEquals("/assList.manager")) {		// 과제게시판 목록
			
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
			
				
				List<AssDTO> boardList=managerDao.getAssPageList(startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getAssPageNavi(currentPage,category,search,branch);
				request.setAttribute("list", boardList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.board/assList.jsp").forward(request,response);
				
			}else if(url.contentEquals("/boardPolice.manager")) { 		//신고 목록
			
				
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
			
				
				List<FreePoliceDTO> boardList=managerDao.getFreePolicePageList(startNum, endNum,category,search);
				List<String> pageNavi = managerDao.getFreePolicePageNavi(currentPage,category,search);
				request.setAttribute("list", boardList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.police/boardPolice.jsp").forward(request,response);
				
			}else if(url.contentEquals("/inquireList.manager")) {		// 문의목록
			
				
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
			
				
				List<InquireDTO> boardList=managerDao.getInquirePageList(startNum, endNum,category,search);
				List<String> pageNavi = managerDao.getInquirePageNavi(currentPage,category,search);
				request.setAttribute("list", boardList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.inquire/inquireList.jsp").forward(request,response);
				
			}else if(url.contentEquals("/inquireDetail.manager")) {   		//문의 상세보기
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				int seq = Integer.parseInt(request.getParameter("seq"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				List<InquireDTO> list = managerDao.getInquire(seq);
				boolean hasNotRecomment = managerDao.hasNotRecomment(seq);
				request.setAttribute("list", list);
				request.setAttribute("page", currentPage);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.setAttribute("hasNotRecomment", hasNotRecomment);
				System.out.println(hasNotRecomment);
				request.getRequestDispatcher("manager/manager.inquire/inquireDetail.jsp").forward(request,response);
			}else if ( url.contentEquals("/writeRecomment.manager")) {			// 답변 작성
				int seq = Integer.parseInt(request.getParameter("seq"));
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				String recomment = request.getParameter("recomment");
				int result=managerDao.insertRecomment(seq, recomment);
				List<InquireDTO> list = managerDao.getInquire(seq);
				boolean hasNotRecomment = managerDao.hasNotRecomment(seq);
				request.setAttribute("list", list);
				request.setAttribute("page", currentPage);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.setAttribute("hasNotRecomment", hasNotRecomment);
				System.out.println(hasNotRecomment);
				request.getRequestDispatcher("manager/manager.inquire/inquireDetail.jsp").forward(request,response);
			}else if(url.contentEquals("/teacherDelete.manager")) {  // 강사 정보 삭제
				
				l.trace(request.getRemoteAddr()+" 강사 정보 삭제");
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				String branch = request.getParameter("branch");
				String delId = request.getParameter("delId");
				
				managerDao.memberDelete(delId);
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
				String position =ManagerConfig.teacher;
				List<MemberDTO> teacherList=managerDao.getMemberPageList(position,startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getMemberPageNavi(currentPage,category,search,position,branch);
				request.setAttribute("list", teacherList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.member/teacher.jsp").forward(request,response);
			}else if(url.contentEquals("/studentDelete.manager")) {  // 학생 정보 삭제
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				String branch = request.getParameter("branch");
				String delId = request.getParameter("delId");
				l.trace(request.getRemoteAddr()+" 학생 정보 삭제");
				managerDao.memberDelete(delId);
				int endNum= currentPage * ManagerConfig.Record_count_Per_Page;
				int startNum = endNum - (ManagerConfig.Record_count_Per_Page-1);
				String position =ManagerConfig.student;
				List<MemberDTO> studentList=managerDao.getMemberPageList(position,startNum, endNum,branch,category,search);
				List<String> pageNavi = managerDao.getMemberPageNavi(currentPage,category,search,position,branch);
				request.setAttribute("list", studentList);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.member/student.jsp").forward(request,response);
			}else if (url.contentEquals("/inquireDelete.manager")) {		// 문의 답변 삭제
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int delSeq =Integer.parseInt(request.getParameter("delSeq"));
				
				managerDao.inquireDelete(delSeq);
				List<InquireDTO> list = managerDao.getInquire(delSeq);
				boolean hasNotRecomment = managerDao.hasNotRecomment(delSeq);
				request.setAttribute("list", list);
				request.setAttribute("page", currentPage);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.setAttribute("hasNotRecomment", hasNotRecomment);
				System.out.println(hasNotRecomment);
				request.getRequestDispatcher("manager/manager.inquire/inquireDetail.jsp").forward(request,response);
			}else if(url.contentEquals("/modifyRecomment.manager")) {		// 답변 수정
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				int seq =Integer.parseInt(request.getParameter("seq"));
				String recomment = request.getParameter("recomment");
				managerDao.inquireModify(seq,recomment);
				List<InquireDTO> list = managerDao.getInquire(seq);
				boolean hasNotRecomment = managerDao.hasNotRecomment(seq);
				request.setAttribute("list", list);
				request.setAttribute("page", currentPage);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.setAttribute("hasNotRecomment", hasNotRecomment);
				System.out.println(hasNotRecomment);
				request.getRequestDispatcher("manager/manager.inquire/inquireDetail.jsp").forward(request,response);
			}else if(url.contentEquals("/write.manager")) {
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.board/NBwrite.jsp").forward(request,response);
				
			}
			else if(url.contentEquals("/writeView.manager")) { //관리자페이지 공지사항 글쓰기
				l.trace(request.getRemoteAddr()+" 공지사항 작성");
			
				String filesPath =request.getServletContext().getRealPath("files");

				File filesFolder = new File(filesPath);
				

				if(!filesFolder.exists()) filesFolder.mkdir();
				MultipartRequest multi = new MultipartRequest(request,filesPath,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy());
				
				int seq = nbdao.getSeq();
				String title =multi.getParameter("title");
				String contents= multi.getParameter("contents");
				
				String branch = multi.getParameter("branch");
				String khClass = multi.getParameter("KhClass");

				int result = nbdao.write(seq,title,contents,khClass,branch);
				

				Set<String>fileNames = multi.getFileNameSet();
			
				for(String fileName : fileNames) {
					
					String oriName = multi.getOriginalFileName(fileName);
					String sysName = multi.getFilesystemName(fileName);

					if(oriName!=null) {  
						int fileUpload =nfdao.fileUpload(new NoticeFilesDTO(0,oriName,sysName,null,seq));
						System.out.println(fileUpload);
					}
				}
				List<NoticeBoardDTO> boardlist =nbdao.boardList();// 목록 받아오기
				request.setAttribute("boardlist", boardlist);
				RequestDispatcher rd = request.getRequestDispatcher("manager/manager.board/NBwriteView.jsp"); //게시물 등록 성공 화면
				rd.forward(request, response);
				
			}else if(url.contentEquals("/detailView.manager")) { //관리자페이지 상세보기
				int boardseq = Integer.parseInt(request.getParameter("seq"));
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				nbdao.viewCountPlus(boardseq);//조회수

				NoticeBoardDTO nbdto = nbdao.detailView(boardseq); //상세 보기
				System.out.println("게시글 번호 :"+boardseq);
				request.setAttribute("view", nbdto);
				
				List<NoticeFilesDTO>fileList = nfdao.selectAll(boardseq); 
				System.out.println("파일이 비어 있나요? "+fileList.isEmpty());//파일이 있나요?
				request.setAttribute("filelist", fileList);

				request.setAttribute("count", ncdao); //댓글 수 출력 
				List<NoticeCommentsDTO> list =ncdao.CommentsList(boardseq);//댓글리스트				
				request.setAttribute("reply", list); 
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.board/NBdetailView.jsp").forward(request, response);
			
			
			}else if(url.contentEquals("/noticeModify.manager")) { //관리자페이지 공지사항 글쓰기 수정
				l.trace(request.getRemoteAddr()+" 공지사항 수정");
				int boardseq = Integer.parseInt(request.getParameter("seq"));
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				System.out.println(boardseq);
				NoticeBoardDTO bdto = nbdao.detailView(boardseq);
				request.setAttribute("view", bdto);

				List<NoticeFilesDTO> fileList = nfdao.selectAll(boardseq);
				System.out.println("파일이 비어 있나요? "+fileList.isEmpty());//파일이 있나요?
				System.out.println("파일 갯수: "+ fileList.size());
				request.setAttribute("filelist", fileList);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.board/NBmodify.jsp").forward(request, response);
				
				
			}else if(url.contentEquals("/noticeModifyView.manager")) { //관리자페이지 공지사항 글 수정 페이지
				l.trace(request.getRemoteAddr()+" 공지사항 수정");
				String filesPath =request.getServletContext().getRealPath("files"); 
				System.out.println(filesPath);
				
				File filesFolder = new File(filesPath);
				System.out.println("프로젝트가 저장 경로" + filesPath);

				if(!filesFolder.exists()) {filesFolder.mkdir();}
				
				MultipartRequest multi = new MultipartRequest(request,filesPath,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 


				int boardSeq = Integer.parseInt(multi.getParameter("seq"));
			
				String uptitle = multi.getParameter("title");
				System.out.println("수정한 제목:" +uptitle);
				String upcontents = multi.getParameter("contents");
				System.out.println("수정한 내용: "+ upcontents);
			
				String[]del=multi.getParameterValues("delete");
				
				if(del!= null) {
					for(String deleteFile : del) {
						System.out.println("지울 파일 번호 "+ deleteFile);

						String sysName = nfdao.getSysName(Integer.parseInt(deleteFile));
						File targetFile = new File(filesPath +"/" + sysName); 
						boolean result = targetFile.delete();
						System.out.println("파일 삭제 여부 :" + result);
						if(result) {nfdao.fileDelete(Integer.parseInt(deleteFile));}
					}
				}else {
				
				int result = nbdao.modify(boardSeq, uptitle, upcontents);
				System.out.println("수정 결과"+ result);
			
				Set<String>fileNames = multi.getFileNameSet();
				System.out.println("파일갯수 "+fileNames.size());
				for(String fileName : fileNames) {
					System.out.println("파라미터 이름: "+ fileName);
					String oriName = multi.getOriginalFileName(fileName);
					String sysName = multi.getFilesystemName(fileName);

					
					if(oriName!=null) {  
						System.out.println("파일이름" + oriName + "DB에 저장됨.");
					
						nfdao.fileUpload(new NoticeFilesDTO(0,oriName,sysName,null,boardSeq));			
					}
				}
				}
				String branch = multi.getParameter("branch");		
				int currentPage =Integer.parseInt(multi.getParameter("currentPage"));
				String category = multi.getParameter("category");
				String search = multi.getParameter("search");
				NoticeBoardDTO dto = nbdao.detailView(boardSeq);
					List<NoticeFilesDTO> flist = nfdao.selectAll(boardSeq);
					request.setAttribute("view", dto);
					request.setAttribute("filelist", flist);
					request.setAttribute("page", currentPage);
					request.setAttribute("branch", branch);
					request.setAttribute("category", category);
					request.setAttribute("search", search);
					request.getRequestDispatcher("manager/manager.board/NBmodifyView.jsp").forward(request, response);

			}else if(url.contentEquals("/noticeDelete.manager")) { //관리자페이지 공지사항 글쓰기 삭제
			
				int seq = Integer.parseInt(request.getParameter("seq"));
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				nbdao.delete(seq);
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.board/NBdeleteView.jsp").forward(request, response);
				//관리자페이지 공지사항 댓글 삭제!!!
			}else if(url.contentEquals("/deleteCom.manager")) { //관리자페이지 공지사항 글쓰기 삭제
				l.trace(request.getRemoteAddr()+" 공지사항 삭제");
				int comment_seq = Integer.parseInt(request.getParameter("seq"));
				System.out.println("댓글번호: " + comment_seq);
				int result = ncdao.deleteReply(comment_seq);
				System.out.println("게시글 삭제 행 갯수:" +result);
				int parent = Integer.parseInt(request.getParameter("parent"));
				System.out.println("게시글번호: " + parent);
				
				if(result>0) {
					response.sendRedirect("/detailView.manager?seq="+parent);
				}		
			}   else if(url.contentEquals("/freeBoardDetail.manager")) {  		// 자유게시판 상세보기
				 int boardSeq = Integer.parseInt(request.getParameter("seq"));
				 String branch = request.getParameter("branch");		
					int currentPage =Integer.parseInt(request.getParameter("currentPage"));
					String category = request.getParameter("category");
					String search = request.getParameter("search");
		            fbdao.viewCountPlus(boardSeq);//조회수

		            FreeBoardDTO bdto = fbdao.detailView(boardSeq); //상세 보기
		            System.out.println("게시글 번호 :"+boardSeq);
		            request.setAttribute("view", bdto);

		            List<FreeFilesDTO>fileList = ffdao.selectAll(boardSeq); //첨부파일 목록 출력   
		            System.out.println("파일이 비어 있나요? "+fileList.isEmpty());//파일이 있나요?
		            request.setAttribute("filelist", fileList);//파일리스트를 request애 담는다.
		            request.setAttribute("page", currentPage);
					request.setAttribute("branch", branch);
					request.setAttribute("category", category);
					request.setAttribute("search", search);
		            request.setAttribute("count", fcdao); //댓글 수 출력 
		            List<FreeCommentDTO> list =fcdao.CommentsList(boardSeq);//댓글리스트            
		            request.setAttribute("reply", list); //댓글리스트를 request를 담는다.
		            request.getRequestDispatcher("manager/manager.board/FBdetailView.jsp").forward(request, response);

			}else if(url.contentEquals("/freeBoardDelete.manager")) {
				int boardSeq = Integer.parseInt(request.getParameter("seq"));
				 String branch = request.getParameter("branch");		
					int currentPage =Integer.parseInt(request.getParameter("currentPage"));
					String category = request.getParameter("category");
					String search = request.getParameter("search");
					managerDao.deleteFreeBoard(boardSeq);
					l.trace(request.getRemoteAddr()+" 자유게시판 삭제");
			           response.sendRedirect(ctxPath+"/boardList.manager?currentPage="+currentPage+"&category="+category+"&search="+search+"&branch="+branch);

			}else if(url.contentEquals("/assDetail.manager")) {

	            int seq = Integer.parseInt(request.getParameter("seq"));
	            String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
	            AssDTO ass  = assDao.select(seq);
	            int viewCount = ass.getViewCount();
	            viewCount = assDao.addViewCount(seq, viewCount);


	            AssFilesDTO assFiles = assFileDao.select(seq);
	          
	            List<AssSubmitDTO> assSubmit = assSubmitDao.selectAll(seq);

	            request.setAttribute("assView", ass);
	            request.setAttribute("assFiles", assFiles);
	            request.setAttribute("assSubmit", assSubmit);
	            request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);

	            RequestDispatcher rd = request.getRequestDispatcher("manager/manager.board/assDetail.jsp");
	            rd.forward(request,response);


	         }else if(url.contentEquals("/assDelete.manager")) {
	        	 	int delSeq = Integer.parseInt(request.getParameter("seq"));
		            String branch = request.getParameter("branch");		
					int currentPage =Integer.parseInt(request.getParameter("currentPage"));
					String category = request.getParameter("category");
					String search = request.getParameter("search");
					managerDao.deleteAss(delSeq);
					l.trace(request.getRemoteAddr()+" 과제게시판 삭제");
					  request.setAttribute("page", currentPage);
						request.setAttribute("branch", branch);
						request.setAttribute("category", category);
						request.setAttribute("search", search);
						 response.sendRedirect(ctxPath+"/assList.manager?currentPage="+currentPage+"&category="+category+"&search="+search+"&branch="+branch);
				        

					
	         }
		}catch(Exception e) {
			l.trace(request.getRemoteAddr()+" 에러남");
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
}
		
				
			
			
		
	




				
		
