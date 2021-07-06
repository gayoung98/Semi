package controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import dto.MemberDTO;


@WebServlet("*.member")
public class LoginController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String requestURI = request.getRequestURI();
		String ctxPath = request.getContextPath();
		String url = requestURI.substring(ctxPath.length());
		System.out.println("요청URL : " + url);
		
		try {
			MemberDAO dao = MemberDAO.getInstance();
			
			if(url.contentEquals("/joinForm.member")) {
				
				request.getRequestDispatcher("/member/join.jsp").forward(request, response);
				
			}else if(url.contentEquals("/sns.member")){
				String email = request.getParameter("email");
				boolean result = dao.sns(email);
				
				if(result == true) {
					String snsresult = dao.snslogin(email);
					if(snsresult != null || request.getSession().isNew()) {
						request.getSession().setAttribute("login",snsresult);
						response.sendRedirect("main.main");
					}
				}else {
					request.setAttribute("email", email);
					request.getRequestDispatcher("/member/join.jsp").forward(request, response);
				}
				
			}else if(url.contentEquals("/join.member")) {
				String email = request.getParameter("email");
				boolean emailregex = Pattern.matches("\\S+@\\w+\\.\\w+(\\.\\w+)?", email);
	            System.out.println("이메일 : " +emailregex);
				
				String pw = request.getParameter("pw");
				boolean pwregex = Pattern.matches("^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", pw);
	            System.out.println("비밀번호 : "+ pwregex);
	            if(pwregex == false) {
	            	response.getWriter().append(pw);
	            }	
				
				String name = request.getParameter("name");
				
				String phone = request.getParameter("phone").trim();
				boolean regex = Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", phone);
	            System.out.println("핸드폰 : " + regex);
	            
				String position = request.getParameter("position");
				
				MemberDTO dto = dao.getInfo(name, phone);
				if(dto == null) {
					response.sendRedirect(ctxPath+"/view/checkMember.jsp");
				}else {
					String id = dto.getId();
					String khClass = dto.getKhClass();
					String branch = dto.getBranch();
					boolean resultId = dao.checkId(id);
					if(resultId == true) {
						response.sendRedirect(ctxPath+"/view/checkId.jsp");
					}else {
						int result = dao.Join(new MemberDTO(email,pw,name,phone,id,khClass,branch,position,null));
						
						request.setAttribute("result", result);
						request.getRequestDispatcher(ctxPath+"/view/joinView.jsp").forward(request, response);
					}
				}
								
			}else if(url.contentEquals("/login.member")) {
				String email = request.getParameter("id");
				String pw = request.getParameter("pw");
				String result = dao.login(email, pw);
				
				if(result != null) {
					request.getSession().setAttribute("login",result);
					response.sendRedirect("main.main");
				}else {
					response.sendRedirect("index.jsp");
				}	
								
			}else if(url.contentEquals("/signout.member")) {
				 request.getSession().invalidate();
				 response.sendRedirect("login.jsp");
				 
			}else if(url.contentEquals("/findId.member")) {
				response.sendRedirect("member/findId.jsp");
				
			}else if(url.contentEquals("/find_id.member")) {
				String name = request.getParameter("name");
				String phone = request.getParameter("phone");
				String email = dao.findId(name, phone);
				String firstEmail = email.split("@")[0];
				String secondEmail = email.split("@")[1];
				response.getWriter().append("*".repeat(firstEmail.length()/2)+firstEmail.substring(firstEmail.length()/2)+"@"+secondEmail);
				
			}else if(url.contentEquals("/findPw.member")){
				response.sendRedirect("member/findPw.jsp");
								
			}else if(url.contentEquals("/member/modify.member")) {
				String email = request.getParameter("email");
				System.out.println(email);				
				String pw = request.getParameter("pw");
				
				int result = dao.updatePw(email, pw);
				request.setAttribute("result", result);
				request.getRequestDispatcher("/view/modifyView.jsp").forward(request, response);
				
			}else if(url.contentEquals("/member/updatePw.member")) {
				request.setAttribute("email", request.getParameter("email"));
				request.getRequestDispatcher("updatePw.jsp").forward(request, response);
				
			}else if(url.contentEquals("/member/checkInfo.member")) {
				String phone = request.getParameter("phone");
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				boolean result = dao.checkInfo(phone, name, email);
				if(result == true) {
					request.setAttribute("email", email);
					request.getRequestDispatcher("/member/findPw.jsp").forward(request, response);
				}else {
					response.sendRedirect(ctxPath+"/view/checkView.jsp");
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
