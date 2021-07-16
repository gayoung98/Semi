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
import dao.MemberDAO;
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
	private String XSSFilter(String target) { 
	if(target!=null) {
		target =target.replaceAll("<", "&lt;"); 
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
			NoticeBoardDAO nbdao = NoticeBoardDAO.getInstance();
			NoticeFileDAO  nfdao = NoticeFileDAO.getInstance();
			NoticeCommentDAO ncdao = NoticeCommentDAO.getInstance();
			MemberDAO mdao = MemberDAO.getInstance();
			String email = (String)request.getSession().getAttribute("login");
			MemberDTO dto = mdao.getMainInfo(email);
			

			if(url.contentEquals("/list.nboard")) { //리스트 출력
			
				String category =request.getParameter("category");
				String keyWord =request.getParameter("keyword"); 
	            keyWord= XSSFilter(keyWord);

				int cpage =Integer.parseInt(request.getParameter("cpage"));
				System.out.println("현재페이지: "+ cpage);
	            System.out.println("카테고리: "+ category);
	            System.out.println("검색어: "+ keyWord);

				int endNum= cpage* BoardConfig.Recode_Count_Per_Page;
				int startNum =endNum-(BoardConfig.Navi_Count_Per_Page-1);

				
				String mykhclass = dto.getKhClass();
				String myBranch = dto.getBranch();
				
				List<NoticeBoardDTO> boardlist;
				List<String>pageNavi;
				
					if(((category==null)&(keyWord==null))||(category.contentEquals("") & keyWord.contentEquals(""))) {
						System.out.println("my class/my branch");
					boardlist =nbdao.getPageList(mykhclass,myBranch,startNum,endNum);

					
				}else { 
					
						System.out.println("keyword + my class/my branch");
						boardlist =nbdao.searchAll(mykhclass, myBranch,startNum,endNum,category,keyWord);
						 
				}
					
				System.out.println(boardlist);
				request.setAttribute("boardlist", boardlist);
				
				pageNavi =nbdao.getPageNavi(mykhclass,myBranch,cpage,category,keyWord);//�럹�씠吏� �꽕鍮꾧쾶�씠�뀡�뿉 capge,category, keyword �씤�옄 媛믪쓣 諛쏆쓬
				request.setAttribute("navi", pageNavi);

				request.setAttribute("category", category);
				request.setAttribute("keyword", keyWord);
				request.setAttribute("cpage", cpage);
				request.setAttribute("count", ncdao); 
				RequestDispatcher rd = request.getRequestDispatcher("kh/notice/NBlist.jsp");
				rd.forward(request, response);
				
			}else if(url.contentEquals("/detailView.nboard")) { //상세보기
			
				request.setAttribute("dto", dto);
				
				int boardseq = Integer.parseInt(request.getParameter("seq"));			
				nbdao.viewCountPlus(boardseq);//조회수

				NoticeBoardDTO nbdto = nbdao.detailView(boardseq);
				System.out.println("게시글 번호 :"+boardseq);
				request.setAttribute("view", nbdto);
				 

				List<NoticeFilesDTO>fileList = nfdao.selectAll(boardseq); 	
				System.out.println("파일이 비워있나요? "+fileList.isEmpty());
				request.setAttribute("filelist", fileList);

				request.setAttribute("count", ncdao); //댓글 dao
				List<NoticeCommentsDTO> list =ncdao.CommentsList(boardseq);//댓글 리스트			
				request.setAttribute("reply", list); 
				
				request.getRequestDispatcher("kh/notice/NBdetailView.jsp").forward(request, response);			


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
