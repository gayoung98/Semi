package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import dao.NoticeCommentDAO;
import dto.FreeCommentDTO;
import dto.MemberDTO;
import dto.NoticeCommentsDTO;


@WebServlet("*.noticom")
public class NoticeCommentController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset =utf-8");

		String requestURI = request.getRequestURI();
		String ctxPath = request.getContextPath();

		String url = requestURI.substring(ctxPath.length());
		System.out.println("요청 명령 :" + url);

		try {	NoticeCommentDAO ncdao = NoticeCommentDAO.getInstance();
				MemberDAO mdao = MemberDAO.getInstance();

		
		if(url.contentEquals("/write.noticom")) { //댓글 작성하기
			System.out.println("댓글 작성중");
			String email = (String) request.getSession().getAttribute("login");
			MemberDTO dto = mdao.getMainInfo(email);
			
			String id = dto.getId();
			System.out.println("학번" + id);
			String writer = dto.getName();
			System.out.println(writer);
			String comments = request.getParameter("comments");
			System.out.println(comments);
			int parent = Integer.parseInt(request.getParameter("parent"));//댓글이 달린 게시글				
			System.out.println(parent +" 번 글의 댓글 작성 " +comments);

			int result = ncdao.writeReply(new NoticeCommentsDTO(0,id,writer,comments,null,parent));				
			response.sendRedirect(ctxPath+"/detailView.nboard?seq="+parent);

		}else if(url.contentEquals("/delete.noticom")) {	//댓글 삭제하기

			System.out.println("정보 넘어옴");
			int comment_seq = Integer.parseInt(request.getParameter("seq"));
			System.out.println("댓글번호: " + comment_seq);
			int result = ncdao.deleteReply(comment_seq);
			System.out.println("게시글 삭제 행 갯수:" +result);
			int parent = Integer.parseInt(request.getParameter("parent"));
			System.out.println("게시글번호: " + parent);
			
			if(result>0) {
				response.sendRedirect(ctxPath+"/detailView.nboard?seq="+parent);
				System.out.println("/detailView.nboard?seq="+parent);
			}		
				
		}else if(url.contentEquals("/modify.noticom")) { //수정할 댓글 작성하기

			int comment_seq = Integer.parseInt(request.getParameter("seq"));
			int parent = Integer.parseInt(request.getParameter("parent"));

			System.out.println("댓글 번호: "+comment_seq);
			String comments = request.getParameter("reply");
			System.out.println("수정된 댓글 내용 :" +comments);
			int result = ncdao.modifyReply(comment_seq, comments); //댓글 수정 dao
			System.out.println("댓글 수정 여부: " + result);
			
			request.getRequestDispatcher(ctxPath+"/detailView.nboard?seq="+parent).forward(request, response);;
			System.out.println("/detailView.nboard?seq="+parent);
			
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
