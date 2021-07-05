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
import dao.NoticeBoardDAO;
import dao.NoticeCommentDAO;
import dao.NoticeFileDAO;
import dto.FreeBoardDTO;
import dto.FreeCommentDTO;
import dto.FreeFilesDTO;
import dto.ManagerDTO;
import dto.MemberDTO;
import dto.NoticeBoardDTO;
import dto.NoticeCommentsDTO;
import dto.NoticeFilesDTO;
import config.BoardConfig;
import config.FileConfig;

@WebServlet("*.nboard")
public class NoticeBoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset =utf-8");

		String requestURI = request.getRequestURI();
		String ctxPath = request.getContextPath();

		String url = requestURI.substring(ctxPath.length());
		System.out.println("요청 명령 :" + url);

		try {
			NoticeBoardDAO nbdao = NoticeBoardDAO.getInstance();
			NoticeFileDAO  nfdao = NoticeFileDAO.getInstance();
			NoticeCommentDAO ncdao = NoticeCommentDAO.getInstance();

			

			if(url.contentEquals("/list.nboard")) { //공지게시판 리스트 출력
				//검색기능 추가
				String category =request.getParameter("category"); //카테고리 
				String keyWord =request.getParameter("keyword"); //검색어 입력
				int cpage =Integer.parseInt(request.getParameter("cpage"));
				System.out.println("현재페이지: "+ cpage);
				System.out.println("카테고리: "+ category);
				System.out.println("검색어: "+ keyWord);

				int endNum= cpage* BoardConfig.Recode_Count_Per_Page;
				int startNum =endNum-(BoardConfig.Navi_Count_Per_Page-1);

				List<NoticeBoardDTO> boardlist;
				List<String>pageNavi;//페이지 네비게이션 리스트

				if(keyWord==null || keyWord.contentEquals(""))
				{
					boardlist =nbdao.getPageList(startNum,endNum);

				}else {
					boardlist =nbdao.getPageList(startNum,endNum,category,keyWord); 

				}

				pageNavi =nbdao.getPageNavi(cpage,category,keyWord);//페이지 네비게이션에 capge,category, keyword 인자 값을 받음
				request.setAttribute("boardlist", boardlist);
				request.setAttribute("navi", pageNavi);

				//카테고리, 키워드 request에 담아라!
				request.setAttribute("category", category);
				request.setAttribute("keyword", keyWord);


				request.setAttribute("count", ncdao); //댓글 수 출력 
				RequestDispatcher rd = request.getRequestDispatcher("notice/NBlist.jsp");
				rd.forward(request, response);

			}else if(url.contentEquals("/detailView.nboard")) { //공지게시판 상세보기
				int boardseq = Integer.parseInt(request.getParameter("seq"));

				nbdao.viewCountPlus(boardseq);//조회수

				NoticeBoardDTO nbdto = nbdao.detailView(boardseq); //상세 보기
				System.out.println("게시글 번호 :"+boardseq);
				request.setAttribute("view", nbdto);
				
				List<NoticeFilesDTO>fileList = nfdao.selectAll(boardseq); //첨부파일 목록 출력	
				System.out.println("파일이 비어 있나요? "+fileList.isEmpty());//파일이 있나요?
				request.setAttribute("filelist", fileList);//파일리스트를 request애 담는다.

				request.setAttribute("count", ncdao); //댓글 수 출력 
				List<NoticeCommentsDTO> list =ncdao.CommentsList(boardseq);//댓글리스트				
				request.setAttribute("reply", list); //댓글리스트를 request를 담는다.
				
				request.getRequestDispatcher("notice/NBdetailView.jsp").forward(request, response);			


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
