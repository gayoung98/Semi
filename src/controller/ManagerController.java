package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			AssFilesDAO daoF = AssFilesDAO.getInstance();
			FreeCommentDAO fcdao=FreeCommentDAO.getInstance();
			AssDAO assDao=AssDAO.getInstance();
			AssFilesDAO assFileDao=AssFilesDAO.getInstance();
			AssSubmitDAO assSubmitDao=AssSubmitDAO.getInstance();
			System.out.println(url);    // ?????? url ??????
			
		if(url.contentEquals("/index.manager")) {
				List<FreeBoardDTO> boardList = managerDao.indexBoard();
				List<FreePoliceDTO> policeList = managerDao.indexPolice();
				List<InquireDTO> inquireList = managerDao.indexInquire();
				request.setAttribute("list", boardList);
				request.setAttribute("ilist", inquireList);
				request.setAttribute("plist", policeList);
				request.getRequestDispatcher("manager/manager.member/index.jsp").forward(request,response);
			}
			else if(url.contentEquals("/logout.manager")) {   // ???????????? 
				l.trace(request.getRemoteAddr()+" ????????? ???????????????");
				request.getSession().invalidate();
				response.sendRedirect("manager/login.jsp"); 
			}else if (url.contentEquals("/teacher.manager")) {      // ?????? ??????
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
				
			}else if (url.contentEquals("/student.manager")) {      // ?????? ??????
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
				
			}else if(url.contentEquals("/boardList.manager")) { 		// ??????????????? ??????
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
				
			}else if(url.contentEquals("/noticeList.manager")) {		//???????????? ??????
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
				
			}else if(url.contentEquals("/assList.manager")) {		// ??????????????? ??????
			
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
				
			}else if(url.contentEquals("/boardPolice.manager")) { 		//?????? ??????
			
				
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
				
			}else if(url.contentEquals("/inquireList.manager")) {		// ????????????
			
				
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
				
			}else if(url.contentEquals("/inquireDetail.manager")) {   		//?????? ????????????
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
			}else if ( url.contentEquals("/writeRecomment.manager")) {			// ?????? ??????
				
				l.trace(request.getRemoteAddr()+" ?????? ??????");
				int seq = Integer.parseInt(request.getParameter("seq"));
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				String recomment = request.getParameter("recomment");
				managerDao.insertRecomment(seq, recomment);
				List<InquireDTO> list = managerDao.getInquire(seq);
				boolean hasNotRecomment = managerDao.hasNotRecomment(seq);
				request.setAttribute("list", list);
				request.setAttribute("page", currentPage);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.setAttribute("hasNotRecomment", hasNotRecomment);
				System.out.println(hasNotRecomment);
				request.getRequestDispatcher("manager/manager.inquire/inquireDetail.jsp").forward(request,response);
			}else if(url.contentEquals("/teacherDelete.manager")) {  // ?????? ?????? ??????
				
				l.trace(request.getRemoteAddr()+" ?????? ?????? ??????");
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
			}else if(url.contentEquals("/studentDelete.manager")) {  // ?????? ?????? ??????
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				String branch = request.getParameter("branch");
				String delId = request.getParameter("delId");
				l.trace(request.getRemoteAddr()+" ?????? ?????? ??????");
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
			}else if (url.contentEquals("/inquireDelete.manager")) {		// ?????? ?????? ??????
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
			}else if(url.contentEquals("/modifyRecomment.manager")) {		// ?????? ??????
				
				l.trace(request.getRemoteAddr()+" ?????? ?????? ??????");
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
			else if(url.contentEquals("/writeView.manager")) { //?????????????????? ???????????? ?????????
	            l.trace(request.getRemoteAddr()+" ???????????? ??????");

	             String root =request.getServletContext().getRealPath("/");
	                  String pathName = root + "uploadDirectory";


	                  File filesFolder = new File(pathName);
	                  System.out.println("??????????????? ????????? ?????? ?????? : " + pathName);

	                  if(!filesFolder.exists()) {filesFolder.mkdir();}


	            MultipartRequest multi = new MultipartRequest(request,pathName,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy());
	            
	            
	            int seq = nbdao.getSeq();
	            String title =multi.getParameter("title");
	            String contents= multi.getParameter("contents");

	            String branch = multi.getParameter("branch");
	            String khClass = multi.getParameter("KhClass");

	            nbdao.write(seq,title,contents,khClass,branch);


	            Set<String>fileNames = multi.getFileNameSet();

	            for(String fileName : fileNames) {
	               if(!fileName.contentEquals("files")) {
	                  String oriName = multi.getOriginalFileName(fileName);
	                  String sysName = multi.getFilesystemName(fileName);

	                  if(oriName!=null) {  
	                     int fileUpload =nfdao.fileUpload(new NoticeFilesDTO(0,oriName,sysName,null,seq));
	                     System.out.println(fileUpload);
	                  }
	               }
	            }
	            request.getSession().removeAttribute("ingFiles");

	            response.sendRedirect(ctxPath+"/noticeList.manager?currentPage=1&branch=all&category=&search=");
	            
	         }else if(url.contentEquals("/detailView.manager")) { //?????????????????? ????????????
				int boardseq = Integer.parseInt(request.getParameter("seq"));
				String branch = request.getParameter("branch");		
				int currentPage =Integer.parseInt(request.getParameter("currentPage"));
				String category = request.getParameter("category");
				String search = request.getParameter("search");
				nbdao.viewCountPlus(boardseq);//?????????

				NoticeBoardDTO nbdto = nbdao.detailView(boardseq); //?????? ??????
				System.out.println("????????? ?????? :"+boardseq);
				request.setAttribute("view", nbdto);
				
				List<NoticeFilesDTO>fileList = nfdao.selectAll(boardseq); 
				System.out.println("????????? ?????? ?????????? "+fileList.isEmpty());//????????? ??????????
				request.setAttribute("filelist", fileList);

				request.setAttribute("count", ncdao); //?????? ??? ?????? 
				List<NoticeCommentsDTO> list =ncdao.CommentsList(boardseq);//???????????????				
				request.setAttribute("reply", list); 
				request.setAttribute("page", currentPage);
				request.setAttribute("branch", branch);
				request.setAttribute("category", category);
				request.setAttribute("search", search);
				request.getRequestDispatcher("manager/manager.board/NBdetailView.jsp").forward(request, response);
			
			
			}else if(url.contentEquals("/noticeModify.manager")) { //?????????????????? ???????????? ????????? ??????
	            
	            int boardseq = Integer.parseInt(request.getParameter("seq"));
	            String branch = request.getParameter("branch");      
	            int currentPage =Integer.parseInt(request.getParameter("currentPage"));
	            String category = request.getParameter("category");
	            String search = request.getParameter("search");
	            System.out.println(boardseq);

	            NoticeBoardDTO bdto = nbdao.detailView(boardseq);
	            request.setAttribute("view", bdto);
	            List<NoticeFilesDTO> fileList = nfdao.selectAll(boardseq);
	            System.out.println("????????? ?????? ?????????? "+fileList.isEmpty());//????????? ??????????
	            System.out.println("?????? ??????: "+ fileList.size());
	            request.setAttribute("filelist", fileList);
	            request.setAttribute("page", currentPage);
	            request.setAttribute("branch", branch);
	            request.setAttribute("category", category);
	            request.setAttribute("search", search);
	            request.getRequestDispatcher("manager/manager.board/NBmodify.jsp").forward(request, response);
	            
	            
	         }else if(url.contentEquals("/noticeModifyView.manager")) { //?????????????????? ???????????? ??? ?????? ?????????
	             l.trace(request.getRemoteAddr()+" ???????????? ??????");
	             String root =request.getServletContext().getRealPath("/");
	             String pathName = root + "uploadDirectory";
	             File filesFolder = new File(pathName);
	             System.out.println("??????????????? ????????? ?????? ?????? : " + pathName);
	             if(!filesFolder.exists()) {filesFolder.mkdir();}

	             MultipartRequest multi = new MultipartRequest(request,pathName,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 


	             int boardSeq = Integer.parseInt(multi.getParameter("seq"));

	             String uptitle = multi.getParameter("title");

	             String upcontents = multi.getParameter("contents");


	             String[]del=multi.getParameterValues("delete");
	             if(del== null) {

	             }else if(del!= null) {
	                for(String deleteFile : del) {
	                   System.out.println("?????? ?????? ?????? "+ deleteFile);

	                   String sysName = nfdao.getSysName(Integer.parseInt(deleteFile));
	                   File targetFile = new File(pathName +"/" + sysName); 
	                   boolean result = targetFile.delete();
	                   System.out.println("?????? ?????? ?????? :" + result);
	                   if(result) {nfdao.fileDelete(Integer.parseInt(deleteFile));}
	                }
	                      System.out.println(del.length);

	             }   
	                int result = nbdao.modify(boardSeq, uptitle, upcontents);
	                System.out.println("?????? ??????"+ result);

	                Set<String>fileNames = multi.getFileNameSet();
	                System.out.println("???????????? "+fileNames.size());
	                for(String fileName : fileNames) {
	                   if(!fileName.contentEquals("files")) {
	                   System.out.println("???????????? ??????: "+ fileName);
	                   String oriName = multi.getOriginalFileName(fileName);
	                   String sysName = multi.getFilesystemName(fileName);


	                   if(oriName!=null) {  
	                      System.out.println("????????????" + oriName + "DB??? ?????????.");

	                      nfdao.fileUpload(new NoticeFilesDTO(0,oriName,sysName,null,boardSeq));         
	                   }
	                }
	             }
	             
	             String branch = multi.getParameter("branch");      
	             int currentPage =Integer.parseInt(multi.getParameter("currentPage"));
	             String category = multi.getParameter("category");
	             String search = multi.getParameter("search");
	             
	             response.sendRedirect(ctxPath+"/detailView.manager?currentPage="+currentPage+"&branch="+branch+"&category="+category+"&search="+search+"&seq="+boardSeq);
	         }else if(url.contentEquals("/noticeDelete.manager")) { //?????????????????? ???????????? ????????? ??????
	             
	             String seq = (request.getParameter("seq"));
	             String branch = request.getParameter("branch");      
	             int currentPage =Integer.parseInt(request.getParameter("currentPage"));
	             String category = request.getParameter("category");
	             String search = request.getParameter("search");
	             
	             List<NoticeFilesDTO>fileList = nfdao.selectAll(Integer.parseInt(seq)); 
	             System.out.println("????????? ?????? ?????????? "+fileList.isEmpty());//????????? ??????????
	             if(fileList.isEmpty()) {//?????? ????????? ?????????? ?????? ???????????? ?????? 
	                
	             }else { //????????????? ?????? ?????? ????????? ?????? 
	             System.out.println("?????? ????????? ????????? ??????:"+seq);//?????? ???????????? ?????? 
	             String filesPath =request.getServletContext().getRealPath("uploadDirectory");
	             System.out.println("??????????????? ????????? ?????? ?????? : " + filesPath);
	             
	                   String sysName = nfdao.getSysNameByparent(Integer.parseInt(seq));
	                 
	                   System.out.println(sysName + "?????? ??????");
	                       File targetFile = new File(filesPath +"/" + sysName); 
	                       boolean result = targetFile.delete();
	                       System.out.println("???????????? ?????? ?????? :" + result);
	                       if(result) {nfdao.fileDeleteByparent(Integer.parseInt(seq));}  
	              }
	             nbdao.delete(Integer.parseInt(seq));//????????? ?????? + ???????????? ??????????????? ?????? 
	             
	             request.setAttribute("page", currentPage);
	             request.setAttribute("branch", branch);
	             request.setAttribute("category", category);
	             request.setAttribute("search", search);
	             request.getRequestDispatcher("manager/manager.board/NBdeleteView.jsp").forward(request, response);
	             
			}else if(url.contentEquals("/deleteCom.manager")) { //?????????????????? ???????????? ????????? ??????
				l.trace(request.getRemoteAddr()+" ???????????? ??????");
				int comment_seq = Integer.parseInt(request.getParameter("seq"));
				System.out.println("????????????: " + comment_seq);
				int result = ncdao.deleteReply(comment_seq);
				System.out.println("????????? ?????? ??? ??????:" +result);
				int parent = Integer.parseInt(request.getParameter("parent"));
				System.out.println("???????????????: " + parent);
				
				if(result>0) {
					response.sendRedirect("/detailView.manager?seq="+parent);
				}		
			}   else if(url.contentEquals("/freeBoardDetail.manager")) {  		// ??????????????? ????????????
				 int boardSeq = Integer.parseInt(request.getParameter("seq"));
				 String branch = request.getParameter("branch");		
					int currentPage =Integer.parseInt(request.getParameter("currentPage"));
					String category = request.getParameter("category");
					String search = request.getParameter("search");
		            fbdao.viewCountPlus(boardSeq);//?????????
		            
	
		            FreeBoardDTO bdto = fbdao.detailView(boardSeq); //?????? ??????
		            System.out.println("????????? ?????? :"+boardSeq);
		            request.setAttribute("view", bdto);

		            List<FreeFilesDTO>fileList = ffdao.selectAll(boardSeq); //???????????? ?????? ??????   
		            System.out.println("????????? ?????? ?????????? "+fileList.isEmpty());//????????? ??????????
		            request.setAttribute("filelist", fileList);//?????????????????? request??? ?????????.
		            request.setAttribute("page", currentPage);
					request.setAttribute("branch", branch);
					request.setAttribute("category", category);
					request.setAttribute("search", search);
		            request.setAttribute("count", fcdao); //?????? ??? ?????? 
		            List<FreeCommentDTO> list =fcdao.CommentsList(boardSeq);//???????????????            
		            request.setAttribute("reply", list); //?????????????????? request??? ?????????.
		            request.getRequestDispatcher("manager/manager.board/FBdetailView.jsp").forward(request, response);

			}else if(url.contentEquals("/freeBoardDelete.manager")) {
				int boardSeq = Integer.parseInt(request.getParameter("seq"));
				
					int currentPage =Integer.parseInt(request.getParameter("currentPage"));
					String category = request.getParameter("category");
					String search = request.getParameter("search");
					managerDao.deleteFreeBoard(boardSeq);
					l.trace(request.getRemoteAddr()+" ??????????????? ??????");
			           response.sendRedirect(ctxPath+"/boardList.manager?currentPage="+currentPage+"&category="+category+"&search="+search+"&branch=all");

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

	           request.getRequestDispatcher("manager/manager.board/assDetail.jsp").forward(request,response);


	         }else if(url.contentEquals("/assDelete.manager")) {
	        	 	int delSeq = Integer.parseInt(request.getParameter("seq"));
	        	 	String email = assDao.select(delSeq).getWriter();
		            String branch = request.getParameter("branch");		
					int currentPage =Integer.parseInt(request.getParameter("currentPage"));
					String category = request.getParameter("category");
					String search = request.getParameter("search");
					
					
					String filesPath = request.getServletContext().getRealPath("assFiles/"+email);
					String sysName = daoF.getSysName(delSeq);
					
					if(sysName!=null) {
					File targetFile = new File(filesPath +"/" + sysName); 
					boolean result = targetFile.delete();
					System.out.println("?????? ?????? ??????: " + result);
					daoF.deleteAll(delSeq);
					}
					managerDao.deleteAss(delSeq);
					  
					l.trace(request.getRemoteAddr()+" ??????????????? ??????");
					
					
					 response.sendRedirect(ctxPath+"/assList.manager?currentPage="+currentPage+"&category="+category+"&search="+search+"&branch="+branch);
					
	         }
		}catch(Exception e) {
			l.trace(request.getRemoteAddr()+" ????????? ????????? ??????");
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
}
		
				
			
			
		
	




				
		
