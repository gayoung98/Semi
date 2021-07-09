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
import dao.FreePoliceDAO;
import dao.MemberDAO;
import dao.MyPageDAO;
import dao.ProfileFileDAO;
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
		System.out.println("�슂泥� 紐낅졊 :" + url);

		try {
			FreeCommentDAO fcdao = FreeCommentDAO.getInstance();	
			FreeBoardDAO fbdao = FreeBoardDAO.getInstance();
			MemberDAO mdao = MemberDAO.getInstance();
			FreeFilesDAO ffdao =FreeFilesDAO.getInstance();
			MyPageDAO mpdao = MyPageDAO.getInstance();
			ProfileFileDAO pfdao = ProfileFileDAO.getInstance();
			FreePoliceDAO fpdao = FreePoliceDAO.getInstance();
			String session = (String) request.getSession().getAttribute("login");



			if(url.contentEquals("/list.fboard")) { //寃뚯떆湲� 紐⑸줉 

				//寃��깋湲곕뒫 異붽�
				String category =request.getParameter("category"); //移댄뀒怨좊━ 
				String keyWord =request.getParameter("keyword"); //寃��깋�뼱 �엯�젰
				String branch = request.getParameter("branch");//branch �엯�젰

				int cpage =Integer.parseInt(request.getParameter("cpage"));
				System.out.println("�쁽�옱�럹�씠吏�: "+ cpage);
				System.out.println("移댄뀒怨좊━: "+ category);
				System.out.println("寃��깋�뼱: "+ keyWord);
				System.out.println("吏��젏: "+ branch);

				int endNum= cpage* BoardConfig.Recode_Count_Per_Page;
				int startNum =endNum-(BoardConfig.Navi_Count_Per_Page-1);

				List<FreeBoardDTO> boardlist;
				List<String>pageNavi;//�럹�씠吏� �꽕鍮꾧쾶�씠�뀡 由ъ뒪�듃

				if(keyWord==null || keyWord.contentEquals("")){ //keyword媛� �뾾嫄곕굹, keyword�쓽 input 諛뺤뒪媛� 鍮꾩썙 �엳�쓣 寃쎌슦
					if(branch==null & category==null & keyWord== null) {
						System.out.println("�쟾泥�");
						boardlist =fbdao.getPageList(startNum,endNum);
						
					}else {
						System.out.println("吏��젏");
						boardlist =fbdao.getEachBranch(startNum,endNum,branch);// �럹�씠吏� 由ъ뒪�듃 �떆�옉 踰덊샇, �걹踰덊샇 parameter濡� 諛쏆쓬
					}
				}else { //keyword瑜� 爾ㅼ쓣 �븣, branch null�씠硫� 
					if(branch==null){
						System.out.println("key �쟾泥�");
						boardlist =fbdao.searchAll(startNum,endNum,category,keyWord);

					}else {
						boardlist =fbdao.searchBranch(branch,category,keyWord,startNum,endNum); //�럹�씠吏� �꽕鍮꾧쾶�씠�뀡 �떆�옉/�걹 踰덊샇 + category �궎�썙�뱶瑜� parameter濡� 諛쏆쓬
						System.out.println("key 吏��젏");
					}
				}
				
				System.out.println(boardlist);
				if(branch==null){
				 pageNavi =fbdao.getPageNavi(cpage,category,keyWord);//�럹�씠吏� �꽕鍮꾧쾶�씠�뀡�뿉 capge,category, keyword �씤�옄 媛믪쓣 諛쏆쓬
				}else {
					pageNavi =fbdao.getPageNavi(cpage,category,keyWord,branch);
				}
	
				request.setAttribute("boardlist", boardlist);
				request.setAttribute("navi", pageNavi);
				request.setAttribute("category", category);
				request.setAttribute("keyword", keyWord);
				request.setAttribute("branch", branch);

				request.setAttribute("count", fcdao); //�뙎湲� �닔 異쒕젰 
				RequestDispatcher rd = request.getRequestDispatcher("free/FBlist.jsp");
				rd.forward(request, response);

			}else if(url.contentEquals("/towrite.fboard")){
				response.sendRedirect("free/FBwrite.jsp");

			}else if(url.contentEquals("/write.fboard")) { //寃뚯떆�뙋�뿉 湲��벐湲�
				System.out.println("�옉�꽦 以�");
				String filesPath =request.getServletContext().getRealPath("files");

				File filesFolder = new File(filesPath);
				System.out.println("�봽濡쒖젥�듃媛� ���옣�맂 吏꾩쭨 寃쎈줈" + filesPath);

				//files folder媛� �뾾�떎硫� 留뚮뱾�뼱以섎씪!!
				if(!filesFolder.exists()) filesFolder.mkdir();
				//�뙆�씪 �뾽濡쒕뱶 湲곕뒫!
				MultipartRequest multi = new MultipartRequest(request,filesPath,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 

				String title =multi.getParameter("title");
				String contents= multi.getParameter("contents");
				System.out.println("�젣紐� :" + title);
				System.out.println("�궡�슜 :" + contents);

				MemberDTO dto = mdao.getMainInfo(session);

				//�뙆�씪 �뾽濡쒕뱶 �옉�뾽
				int seq = fbdao.getSeq();				
				String branch =dto.getBranch();
				System.out.println("吏��젏: "+ branch);
				String writer = session;
				System.out.println("�옉�꽦�옄: "+ writer);

				String id = dto.getId();
				System.out.println("�븰踰�: "+ id);


				int result = fbdao.write(seq,branch,writer,title,contents,id);
				System.out.println("寃뚯떆湲� �엯�젰 �뿬遺� : "+ result);

				//�뿬�윭媛쒖쓽 �뙆�씪�씠 �뾽濡쒕뱶 �맆�븣瑜� for臾�
				Set<String>fileNames = multi.getFileNameSet();
				System.out.println("�뙆�씪媛��닔 "+fileNames.size());
				for(String fileName : fileNames) {
					System.out.println("�뙆�씪誘명꽣 �씠由�: "+ fileName);
					String oriName = multi.getOriginalFileName(fileName);
					String sysName = multi.getFilesystemName(fileName);


					if(oriName!=null) {  

						int fileUpload =ffdao.fileUpload(new FreeFilesDTO(0,oriName,sysName,null,seq));
						System.out.println(fileUpload);
					}	
				}
				List<FreeBoardDTO> boardlist =fbdao.boardList();// 紐⑸줉 諛쏆븘�삤湲�
				request.setAttribute("boardlist", boardlist);
				RequestDispatcher rd = request.getRequestDispatcher("free/FBwriteView.jsp"); //寃뚯떆臾� �벑濡� �꽦怨� �솕硫�
				rd.forward(request, response);

			}else if(url.contentEquals("/detailView.fboard")) {  //寃뚯떆湲� �긽�꽭蹂닿린
				
				MemberDTO dto = mdao.getMainInfo(session);
				request.setAttribute("dto", dto);
				
				int boardseq = Integer.parseInt(request.getParameter("seq"));

				fbdao.viewCountPlus(boardseq);//議고쉶�닔
				
				//�봽濡쒗븘 �궗吏� 異쒕젰
				if(pfdao.selectBySeq(mpdao.getID(session)).size()==0){
					String filesPath = request.getServletContext().getRealPath("profile/"+session);      
				    File filesFolder = new File(filesPath);
				    if(!filesFolder.exists()) filesFolder.mkdirs();
				    request.setAttribute("defalut_profile_img","profile.png");
				} else { 
					request.setAttribute("profile_img",pfdao.getFile(mpdao.getID(session)));
					 }
				
				request.setAttribute("member",mpdao.getMember(session));
				
				FreeBoardDTO bdto = fbdao.detailView(boardseq); //�긽�꽭 蹂닿린
				System.out.println("寃뚯떆湲� 踰덊샇 :"+boardseq);
				request.setAttribute("view", bdto);

				List<FreeFilesDTO>fileList = ffdao.selectAll(boardseq); //泥⑤��뙆�씪 紐⑸줉 異쒕젰	
				System.out.println("�뙆�씪�씠 鍮꾩뼱 �엳�굹�슂? "+fileList.isEmpty());//�뙆�씪�씠 �엳�굹�슂?
				request.setAttribute("filelist", fileList);//�뙆�씪由ъ뒪�듃瑜� request�뿉 �떞�뒗�떎.

				request.setAttribute("count", fcdao); //�뙎湲� �닔 異쒕젰 
				List<FreeCommentDTO> list =fcdao.CommentsList(boardseq);//�뙎湲�由ъ뒪�듃				
				request.setAttribute("reply", list); //�뙎湲�由ъ뒪�듃瑜� request瑜� �떞�뒗�떎.

				request.getRequestDispatcher("free/FBdetailView.jsp").forward(request, response);

			}else if(url.contentEquals("/modify.fboard")){ //�닔�젙�븯湲�
				
				
				int boardseq = Integer.parseInt(request.getParameter("seq"));
				System.out.println(boardseq);
				FreeBoardDTO bdto = fbdao.detailView(boardseq);
				request.setAttribute("view", bdto);

				List<FreeFilesDTO> fileList = ffdao.selectAll(boardseq);
				System.out.println("�뙆�씪�씠 鍮꾩뼱 �엳�굹�슂? "+fileList.isEmpty());//�뙆�씪�씠 �엳�굹�슂?
				System.out.println("�뙆�씪 媛��닔: "+ fileList.size());
				request.setAttribute("filelist", fileList);

				request.getRequestDispatcher("free/FBmodify.jsp").forward(request, response);

			}else if(url.contentEquals("/modifyedit.fboard")) {	//湲� �닔�젙�븯湲�
				System.out.println("�닔�젙以�");
				String filesPath =request.getServletContext().getRealPath("files"); //�뙆�씪 ���옣�맂 寃쎈줈
				System.out.println(filesPath);
				//�닔�젙 �럹�씠吏��뿉�꽌 �뙆�씪泥⑤� 異붽�
				File filesFolder = new File(filesPath);
				System.out.println("�봽濡쒖젥�듃媛� ���옣 寃쎈줈" + filesPath);

				if(!filesFolder.exists()) {filesFolder.mkdir();}

				MultipartRequest multi = new MultipartRequest(request,filesPath,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 


				int board_seq = Integer.parseInt(multi.getParameter("seq"));
				System.out.println(board_seq);
				String uptitle = multi.getParameter("title");
				System.out.println("�닔�젙�븳 �젣紐�:" +uptitle);
				String upcontents = multi.getParameter("contents");
				System.out.println("�닔�젙�븳 �궡�슜: "+ upcontents);
				//String�삎 諛곗뿴 return
				String[]del=multi.getParameterValues("delete");

				if(del!= null) {
					for(String deleteFile : del) {
						System.out.println("吏��슱 �뙆�씪 踰덊샇 "+ deleteFile);

						String sysName = ffdao.getSysName(Integer.parseInt(deleteFile));
						File targetFile = new File(filesPath +"/" + sysName); 
						boolean result = targetFile.delete();
						System.out.println("�뙆�씪 �궘�젣 �뿬遺� :" + result);
						if(result) {ffdao.fileDelete(Integer.parseInt(deleteFile));}
					}
				}
				System.out.println(del.length);

				int result = fbdao.modify(board_seq, uptitle, upcontents);
				System.out.println("�닔�젙 寃곌낵"+ result);

				Set<String>fileNames = multi.getFileNameSet();
				System.out.println("�뙆�씪媛��닔 "+fileNames.size());
				for(String fileName : fileNames) {
					System.out.println("�뙆�씪誘명꽣 �씠由�: "+ fileName);
					String oriName = multi.getOriginalFileName(fileName);
					String sysName = multi.getFilesystemName(fileName);


					if(oriName!=null) {  
						System.out.println("�뙆�씪�씠由�" + oriName + "DB�뿉 ���옣�맖.");

						int fileUpload =ffdao.fileUpload(new FreeFilesDTO(0,oriName,sysName,null,board_seq));			
					}
				}
				FreeBoardDTO dto = fbdao.detailView(board_seq);
				List<FreeFilesDTO> flist = ffdao.selectAll(board_seq);
				request.setAttribute("view", dto);
				request.setAttribute("filelist", flist);
				request.getRequestDispatcher("free/FBmodifyView.jsp").forward(request, response);

			}else if(url.contentEquals("/delete.fboard")){ //�궘�젣�븯湲�
				System.out.println("�궘�젣以�");
				int seq = Integer.parseInt(request.getParameter("seq"));
				int result = fbdao.delete(seq);
				response.sendRedirect("free/FBdeleteView.jsp");
				
			}else if(url.contentEquals("/reportForm.fboard")) { //寃뚯떆臾� �떊怨좏뤌�쑝濡�
				System.out.println("seq �꽆湲�!!");
				int board_seq = Integer.parseInt(request.getParameter("seq"));
				FreeBoardDTO dto = fbdao.detailView(board_seq);
				request.setAttribute("view", dto);
				request.getRequestDispatcher("free/reportPop.jsp").forward(request, response);

			}else if(url.contentEquals("/report.fboard")) { //寃뚯떆湲� �떊怨�
				System.out.println("�떊怨좎젒�닔!!");
					
				MemberDTO dto = mdao.getMainInfo(session);
				String id = dto.getId();
				String contents = request.getParameter("contents");
				int parent = Integer.parseInt(request.getParameter("seq"));
				int result = fpdao.report(id,contents,parent);
				System.out.println("�떊怨좉� �벑濡� �뿬遺� : "+ result);

				RequestDispatcher rd = request.getRequestDispatcher("free/reportResult.jsp"); //寃뚯떆臾� �벑濡� �꽦怨� �솕硫�
				rd.forward(request, response);
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
