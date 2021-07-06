package controller;

import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Config;

@WebServlet("*.email")
public class EmailController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String requestURI = request.getRequestURI();
		String ctxPath = request.getContextPath();
		String url = requestURI.substring(ctxPath.length());
		System.out.println("요청URL : " + url);
		
		try {
			
		      String host = "smtp.naver.com";
		      String user = Config.user;
		      String password  = Config.password;
		      String to = request.getParameter("email");
		      String sub = Config.sub;
		      String text = null;
			
			if(url.contentEquals("/send.email")) {

			      Properties props = new Properties();

			      props.put("mail.smtp.host", host);
			      props.put("mail.smtp.port", 587);
			      props.put("mail.smtp.auth", "true");

			      Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			         protected PasswordAuthentication getPasswordAuthentication() {
			            return new PasswordAuthentication(user, password);}
			      });

			      try {

			         MimeMessage message = new MimeMessage(session);
			         message.setFrom(new InternetAddress(user));

			         //수신자
			         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

			         //제목
			         message.setSubject(sub);

			         //내용
			         int msg2 = (int)(Math.random()*(1000000-100000))+100000;
			         String msg = String.valueOf(msg2);
			         message.setText(msg);

			         Transport.send(message);
			         message.setText(msg);
			         response.getWriter().append(msg);
			         
			      }catch(Exception e) {
			    	  e.printStackTrace();
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
