package controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.CalanderDAO;
import dao.MemberDAO;
import dao.MyPageDAO;
import dao.NoticeBoardDAO;
import dao.NoticeCommentDAO;
import dao.NoticeFileDAO;
import dao.ProfileFileDAO;
import dao.inquiredDAO;
import dto.InquireDTO;
import dto.MemberDTO;
import dto.NoticeBoardDTO;
import dto.NoticeCommentsDTO;
import dto.NoticeFilesDTO;
import dto.ProfileFileDTO;
import utils.*;

@WebServlet("*.mp")
public class MypageController extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("html/text; charset=utf-8");
		
		String session = (String) request.getSession().getAttribute("login");
		int maxSize = 1024*1024*5; //�룯占� 占쎈씜嚥≪뮆諭� 占쎈솁占쎌뵬 占쎄쾿疫뀐옙 5mb
 		String uri = request.getRequestURI();
		String ctxPath = request.getContextPath();
		String url = uri.substring(ctxPath.length());
		MyPageDAO mpd = MyPageDAO.getInstance();
		ProfileFileDAO pfd = ProfileFileDAO.getInstance(); 
		CalanderDAO cd = CalanderDAO.getInstance();
		Calendar cal = Calendar.getInstance();
		Calendar caltoGre = new GregorianCalendar(); 
		inquiredDAO idao = inquiredDAO.getInstance();
		NoticeBoardDAO nbd = NoticeBoardDAO.getInstance();
		MemberDAO md = MemberDAO.getInstance();
		System.out.println(url);
		switch (url) {
		case "/mypage.mp": 
			try {
				System.out.println(cal.get(caltoGre.MONTH)+1);
				System.out.println(mpd.getMember(session).getBranch());
				System.out.println(mpd.getMember(session).getKhClass());
				
				if(pfd.selectBySeq(mpd.getID(session)).size()==0){
					String filesPath = request.getServletContext().getRealPath("profile/"+session);      
				    File filesFolder = new File(filesPath);
				    if(!filesFolder.exists()) filesFolder.mkdirs();
				    request.setAttribute("defalut_profile_img","profile.png");
				} else { 
					request.setAttribute("profile_img",pfd.getFile(mpd.getID(session)));
					 }
					 
				request.setAttribute("calander", cd.getMonth(cal.get(caltoGre.MONTH)+1, mpd.getMember(session).getBranch(), mpd.getMember(session).getKhClass()));
				request.setAttribute("rest_calander", cd.rest_getMonth(mpd.getMember(session).getBranch(), mpd.getMember(session).getKhClass()));
				request.setAttribute("member",mpd.getMember(session));
			    request.setAttribute("d_day",Util.dDay_to_Today());
			    request.setAttribute("d_day_percent",Util.dDay_to_Total());
				request.setAttribute("FreeBoard", mpd.getWrittenFreeBoard(session));
				request.setAttribute("Inquired", mpd.getWrittenInquired(session));
				request.setAttribute("Notice", nbd.getMypageNotice(md.getAllInfo(session).getBranch()));
				request.setAttribute("studentNumber", mpd.getStudentList(md.getAllInfo((String)request.getSession().getAttribute("login"))).size());
				request.getRequestDispatcher("Mypage/mypage.jsp").forward(request, response);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/modify.mp":
			try {
				request.setAttribute("member",mpd.getMember(session));
				request.setAttribute("profile_img",pfd.getFile(mpd.getID(session)));
				request.getRequestDispatcher("Mypage/modify.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/update.mp": 
			String filesPath = request.getServletContext().getRealPath("profile/"+session);      
		    File filesFolder = new File(filesPath);
			MultipartRequest multi  = new MultipartRequest(request,filesPath,maxSize,"utf8",new DefaultFileRenamePolicy());
			String id = multi.getParameter("id");
			String update_pw = multi.getParameter("pw"); 
			String update_phone=multi.getParameter("phone");
			System.out.println(id);
			System.out.println(update_pw);
			System.out.println(update_phone);
			try {
				MemberDTO temp_member = new MemberDTO();
				temp_member.setPw(Util.getSHA512(update_pw));
				temp_member.setId(id);
				temp_member.setPhone(update_phone);
				int result=mpd.update(temp_member);
				if(multi.getOriginalFileName("profile") != null) {
				if(pfd.getFile(id) != null) pfd.deletefile(id);
				pfd.insert(new ProfileFileDTO(multi.getOriginalFileName("profile"), multi.getFilesystemName("profile"),id));
				}
				request.setAttribute("update_result", result);
				request.getRequestDispatcher("/mypage.mp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;	
			
		case "/picture_change.mp":
			String img_path  = request.getParameter("origin");
			System.out.println(request.getParameter("change_result"));
			request.setAttribute("origin", img_path);
			if(request.getParameter("change_result")!=null) {
				if(request.getParameter("change_result").equalsIgnoreCase("true")) request.setAttribute("change_result", true);
				else if (request.getParameter("change_result").equalsIgnoreCase("false")) request.setAttribute("change_result", false);
			}
			request.getRequestDispatcher("Mypage/picture_change.jsp").forward(request, response);
			break;
			
		case "/profileUpdate.mp": 
			String profile_filesPath = request.getServletContext().getRealPath("profile/"+session);      
		    File profile_filesFolder = new File(profile_filesPath);
			MultipartRequest profile_multi  = new MultipartRequest(request,profile_filesPath,maxSize,"utf8",new DefaultFileRenamePolicy());
			String update_profile = profile_multi.getOriginalFileName("after_profile");
			System.out.println(update_profile);
		
			try {
				if(update_profile != null) {
					String temp = URLEncoder.encode(update_profile,StandardCharsets.UTF_8.toString());
					if(pfd.getFile(mpd.getID(session)) != null) pfd.deletefile(mpd.getID(session));
					pfd.insert(new ProfileFileDTO(profile_multi.getOriginalFileName("after_profile"), profile_multi.getFilesystemName("after_profile"),mpd.getID(session)));
					response.sendRedirect("picture_change.mp?origin="+ctxPath+"/profile/"+session+"/"+temp+"&change_result=true");	
				} else {
					String temp = URLEncoder.encode(pfd.getFile(mpd.getID(session)).getSysName(),StandardCharsets.UTF_8.toString());
					response.sendRedirect("picture_change.mp?origin="+ctxPath+"/profile/"+session+"/"+temp+"&change_result=false");
				} 
			}catch (Exception e) {
				e.printStackTrace();
			}
			 break;
			 
		case "/inquired.mp": 
			try {
				request.setAttribute("inquired",  idao.getDTO(Integer.parseInt(request.getParameter("seq"))));
				request.getRequestDispatcher("inquired/inquiredPopup.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			break;
			
		case "/inquiredList.mp": 
			try {
				request.setAttribute("inquiredList", idao.getList((String)request.getSession().getAttribute("login")));
				request.getRequestDispatcher("inquired/inquiredList.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			break;
			
		case "/studentinfo.mp":
			try {
				request.setAttribute("studentList", mpd.getStudentList(md.getAllInfo((String)request.getSession().getAttribute("login"))));
				request.setAttribute("calander",cd.getCoursePeriod(md.getAllInfo((String)request.getSession().getAttribute("login"))));
				request.getRequestDispatcher("inquired/studentList.jsp").forward(request, response);
			} catch (Exception e) {
				e.getStackTrace();
				System.out.println(e.getMessage());
			}
			break;
			
			
		case "/updateRequired.mp":
			int seq = Integer.parseInt(request.getParameter("seq"));
			try {
				request.setAttribute("inquired", idao.getDTO(seq));
				request.getRequestDispatcher("inquired/updateInquired.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/insertUpdateInquired.mp": 
			try {
			InquireDTO temp_id = new InquireDTO();
			temp_id.setSeq(Integer.parseInt(request.getParameter("seq")));
			temp_id.setId(request.getParameter("id"));
			temp_id.setMajor_category(request.getParameter("major_category"));
			temp_id.setSub_category(request.getParameter("sub_category"));
			temp_id.setContents(request.getParameter("inquire_contents"));
			int result = idao.update(temp_id);
			if(result>0) {
				request.setAttribute("update_result",result);
			}
			request.setAttribute("seq",temp_id.getSeq());
			request.getRequestDispatcher("inquired/updateResult.jsp").forward(request, response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;	
		case "/deleteInquired.mp":
			try {
				int result = idao.delete(Integer.parseInt(request.getParameter("seq")));
				if(result>0) {
					request.setAttribute("delete_result",result);
				}
				request.setAttribute("seq",Integer.parseInt(request.getParameter("seq")));
				request.getRequestDispatcher("inquired/updateResult.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			}	
	    }
	}
	



