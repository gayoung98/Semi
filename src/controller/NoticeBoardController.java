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
	private String XSSFilter(String target) { //XSS 怨듦꺽 諛⑹뼱�븯�뒗 諛⑸쾿
	if(target!=null) {
		target =target.replaceAll("<", "&lt;"); //<瑜� %lt(less than)�쑝濡� 諛붽씀寃좊떎.<script> 湲곕뒫�쓣 �옉�룞�쓣 �븞�븳�떎.=> %ltscript>�씠�젃寃� �굹���깂!!
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
		System.out.println("�슂泥� 紐낅졊 :" + url);

		try {
			NoticeBoardDAO nbdao = NoticeBoardDAO.getInstance();
			NoticeFileDAO  nfdao = NoticeFileDAO.getInstance();
			NoticeCommentDAO ncdao = NoticeCommentDAO.getInstance();
			MemberDAO mdao = MemberDAO.getInstance();
			String email = (String)request.getSession().getAttribute("login");
			MemberDTO dto = mdao.getMainInfo(email);
			

			if(url.contentEquals("/list.nboard")) { //怨듭�寃뚯떆�뙋 由ъ뒪�듃 異쒕젰
				//寃��깋湲곕뒫 異붽�
				String category =request.getParameter("category"); //移댄뀒怨좊━ 
				String keyWord =request.getParameter("keyword"); //寃��깋�뼱 �엯�젰
	            keyWord= XSSFilter(keyWord);

				int cpage =Integer.parseInt(request.getParameter("cpage"));
				System.out.println("�쁽�옱�럹�씠吏�: "+ cpage);
				System.out.println("移댄뀒怨좊━: "+ category);
				System.out.println("寃��깋�뼱: "+ keyWord);

				int endNum= cpage* BoardConfig.Recode_Count_Per_Page;
				int startNum =endNum-(BoardConfig.Navi_Count_Per_Page-1);

				
				String mykhclass = dto.getKhClass();
				String myBranch = dto.getBranch();
				
				List<NoticeBoardDTO> boardlist;
				List<String>pageNavi;//�럹�씠吏� �꽕鍮꾧쾶�씠�뀡 由ъ뒪�듃
				
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

				//移댄뀒怨좊━, �궎�썙�뱶 request�뿉 �떞�븘�씪!
				request.setAttribute("category", category);
				request.setAttribute("keyword", keyWord);
				request.setAttribute("cpage", cpage);
				request.setAttribute("count", ncdao); //�뙎湲� �닔 異쒕젰 
				RequestDispatcher rd = request.getRequestDispatcher("kh/notice/NBlist.jsp");
				rd.forward(request, response);
				
			}else if(url.contentEquals("/detailView.nboard")) { //怨듭�寃뚯떆�뙋 �긽�꽭蹂닿린
			
				request.setAttribute("dto", dto);
				
				int boardseq = Integer.parseInt(request.getParameter("seq"));			
				nbdao.viewCountPlus(boardseq);//議고쉶�닔

				NoticeBoardDTO nbdto = nbdao.detailView(boardseq); //�긽�꽭 蹂닿린
				System.out.println("寃뚯떆湲� 踰덊샇 :"+boardseq);
				request.setAttribute("view", nbdto);
				 

				List<NoticeFilesDTO>fileList = nfdao.selectAll(boardseq); //泥⑤��뙆�씪 紐⑸줉 異쒕젰	
				System.out.println("�뙆�씪�씠 鍮꾩뼱 �엳�굹�슂? "+fileList.isEmpty());//�뙆�씪�씠 �엳�굹�슂?
				request.setAttribute("filelist", fileList);//�뙆�씪由ъ뒪�듃瑜� request�븷 �떞�뒗�떎.

				request.setAttribute("count", ncdao); //�뙎湲� �닔 異쒕젰 
				List<NoticeCommentsDTO> list =ncdao.CommentsList(boardseq);//�뙎湲�由ъ뒪�듃				
				request.setAttribute("reply", list); //�뙎湲�由ъ뒪�듃瑜� request瑜� �떞�뒗�떎.
				
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
