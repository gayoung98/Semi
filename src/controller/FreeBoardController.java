package controller;

import java.io.File;
import org.apache.log4j.Logger;



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
	Logger l = Logger.getLogger(LoginController.class);

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



         if(url.contentEquals("/list.fboard")) { //由ъ뒪�듃 蹂닿린

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
            List<String>pageNavi;

            if(keyWord==null || keyWord.contentEquals("")){ //keyword媛� �뾾嫄곕굹, keyword�쓽 input 諛뺤뒪媛� 鍮꾩썙 �엳�쓣 寃쎌슦
               if(branch==null || branch.contentEquals("")&  category.contentEquals("") & keyWord.contentEquals("")) {
                  System.out.println("�쟾泥�");
                  boardlist =fbdao.getPageList(startNum,endNum);
                  pageNavi =fbdao.getPageNavi(cpage,category,keyWord);//�럹�씠吏� �꽕鍮꾧쾶�씠�뀡�뿉 capge,category, keyword �씤�옄 媛믪쓣 諛쏆쓬

               }else {
                  System.out.println("媛� 吏��젏");
                  boardlist =fbdao.getEachBranch(startNum,endNum,branch);// �럹�씠吏� 由ъ뒪�듃 �떆�옉 踰덊샇, �걹踰덊샇 parameter濡� 諛쏆쓬
                  pageNavi =fbdao.getPageNavi(cpage,category,keyWord,branch);


               }
            }else {
               if(branch==null){
                  System.out.println("keyword + �쟾泥�");
                  boardlist =fbdao.searchAll(startNum,endNum,category,keyWord);
                  pageNavi =fbdao.getPageNavi(cpage,category,keyWord);


               }else {
                  System.out.println("keyword + 媛� 吏��젏");
                  boardlist =fbdao.searchBranch(branch,category,keyWord,startNum,endNum); 
                  pageNavi =fbdao.getPageNavi(cpage,category,keyWord,branch);               
               }
            }

            System.out.println(boardlist);
            if(branch==null){
               pageNavi =fbdao.getPageNavi(cpage,category,keyWord);
            }else {
               pageNavi =fbdao.getPageNavi(cpage,category,keyWord,branch);
            }


            request.setAttribute("boardlist", boardlist);
            request.setAttribute("navi", pageNavi);
            request.setAttribute("category", category);
            request.setAttribute("keyword", keyWord);
            request.setAttribute("branch", branch);

            request.setAttribute("count", fcdao); 
            RequestDispatcher rd = request.getRequestDispatcher("kh/free/FBlist.jsp");
            rd.forward(request, response);

         }else if(url.contentEquals("/towrite.fboard")){
            response.sendRedirect("kh/free/FBwrite.jsp");

         }else if(url.contentEquals("/write.fboard")) { 
        	 l.trace(request.getRemoteAddr()+" �옄�쑀寃뚯떆�뙋 �옉�꽦");
            System.out.println("�옉�꽦 以�");
            String root =request.getServletContext().getRealPath("/");
            String pathName = root + "uploadDirectory";


            File filesFolder = new File(pathName);
            System.out.println("�봽濡쒖젥�듃媛� ���옣�맂 吏꾩쭨 寃쎈줈 : " + pathName);

            if(!filesFolder.exists()) filesFolder.mkdir();

            MultipartRequest multi = new MultipartRequest(request,pathName,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 

            String title =multi.getParameter("title");
            String contents= multi.getParameter("contents");
            System.out.println("�젣紐� :" + title);
            System.out.println("�궡�슜 :" + contents);

            MemberDTO dto = mdao.getMainInfo(session);

            int seq = fbdao.getSeq();            
            String branch =dto.getBranch();
            System.out.println("吏��젏: "+ branch);
            String writer = session;
            System.out.println("�옉�꽦�옄: "+ writer);

            String id = dto.getId();
            System.out.println("�븰踰�: "+ id);


            int result = fbdao.write(seq,branch,writer,title,contents,id);
            System.out.println("寃뚯떆湲� �엯�젰 �뿬遺� : "+ result);


            Set<String>fileNames = multi.getFileNameSet();
            if(fileNames == null) {

            }else {
               System.out.println("�뙆�씪媛��닔 "+fileNames.size());
               for(String fileName : fileNames) {
                  if(!fileName.contentEquals("files")) {//泥⑤��뙆�씪留�
                     System.out.println("�뙆�씪誘명꽣 �씠由�: "+ fileName);
                     String oriName = multi.getOriginalFileName(fileName);
                     String sysName = multi.getFilesystemName(fileName);


                     if(oriName!=null) {  

                        int fileUpload =ffdao.fileUpload(new FreeFilesDTO(0,oriName,sysName,null,seq));
                        System.out.println(fileUpload);
                     }   
               	}
               }
               request.getSession().removeAttribute("ingFiles");
            }
            List<FreeBoardDTO> boardlist =fbdao.boardList();//紐⑸줉 異쒕젰
            request.setAttribute("boardlist", boardlist);
            RequestDispatcher rd = request.getRequestDispatcher("kh/free/FBwriteView.jsp"); //湲��벐湲� �럹�씠吏�濡� �씠�룞
            rd.forward(request, response);

         }else if(url.contentEquals("/detailView.fboard")) {  //�긽�꽭蹂닿린

            MemberDTO dto = mdao.getMainInfo(session);
            request.setAttribute("dto", dto);

            int boardseq = Integer.parseInt(request.getParameter("seq"));

            fbdao.viewCountPlus(boardseq);//議고쉶�닔 +1

            //�봽濡쒗븘 �궗吏� 異쒕젰
            if(pfdao.selectBySeq(mpdao.getID(session)).size()==0){
               String filesPath = request.getServletContext().getRealPath("profile/"+session);      
               File filesFolder = new File(filesPath);
               if(!filesFolder.exists()) filesFolder.mkdirs();
               request.setAttribute("defalut_profile_img","profile.png");
            } else { 
               request.setAttribute("mm", mpdao);
               request.setAttribute("profile_img",pfdao);
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

            request.getRequestDispatcher("kh/free/FBdetailView.jsp").forward(request, response);
            
         }else if(url.contentEquals("/modify.fboard")){ //�닔�젙�븯湲�
        	 l.trace(request.getRemoteAddr()+" �옄�쑀寃뚯떆�뙋 �닔�젙");     
            int boardseq = Integer.parseInt(request.getParameter("seq"));
            System.out.println(boardseq);
            FreeBoardDTO bdto = fbdao.detailView(boardseq);
            request.setAttribute("view", bdto);

            List<FreeFilesDTO> fileList = ffdao.selectAll(boardseq);
            System.out.println("�뙆�씪�씠 鍮꾩뼱 �엳�굹�슂? "+fileList.isEmpty());//�뙆�씪�씠 �엳�굹�슂?
            System.out.println("�뙆�씪 媛��닔: "+ fileList.size());
            request.setAttribute("filelist", fileList);

            request.getRequestDispatcher("kh/free/FBmodify.jsp").forward(request, response);

         }else if(url.contentEquals("/modifyedit.fboard")) {   //湲� �닔�젙�븯湲�
            
            String root =request.getServletContext().getRealPath("/");
            String pathName = root + "uploadDirectory";


            File filesFolder = new File(pathName);
            System.out.println("�봽濡쒖젥�듃媛� ���옣�맂 吏꾩쭨 寃쎈줈 : " + pathName);


            if(!filesFolder.exists()) {filesFolder.mkdir();}

            MultipartRequest multi = new MultipartRequest(request,pathName,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 


            int board_seq = Integer.parseInt(multi.getParameter("seq"));
            System.out.println(board_seq);
            String uptitle = multi.getParameter("title");
            System.out.println("�닔�젙�븳 �젣紐�:" +uptitle);
            String upcontents = multi.getParameter("contents");
            System.out.println("�닔�젙�븳 �궡�슜: "+ upcontents);

            String[]del=multi.getParameterValues("delete");

            if(del== null) {
            }else if(del!= null) {
               for(String deleteFile : del) {
                  System.out.println("吏��슱 �뙆�씪 踰덊샇 "+ deleteFile);

                  String sysName = ffdao.getSysName(Integer.parseInt(deleteFile));
                  File targetFile = new File(pathName +"/" + sysName); 
                  boolean result = targetFile.delete();
                  System.out.println("�뙆�씪 �궘�젣 �뿬遺� :" + result);
                  if(result) {ffdao.fileDelete(Integer.parseInt(deleteFile));}
               }

               System.out.println(del.length);
            }
               int result = fbdao.modify(board_seq, uptitle, upcontents);
               System.out.println("�닔�젙 寃곌낵"+ result);

               Set<String>fileNames = multi.getFileNameSet();
               System.out.println("�뙆�씪媛��닔 "+fileNames.size());
               for(String fileName : fileNames) {
                  if(!fileName.contentEquals("files")) {

                  System.out.println("�뙆�씪誘명꽣 �씠由�: "+ fileName);
                  String oriName = multi.getOriginalFileName(fileName);
                  String sysName = multi.getFilesystemName(fileName);

                  if(oriName!=null) {  
                     System.out.println("�뙆�씪�씠由�" + oriName + "DB�뿉 ���옣�맖.");

                  }
               }
            }

            FreeBoardDTO dto = fbdao.detailView(board_seq);
            List<FreeFilesDTO> flist = ffdao.selectAll(board_seq);
            request.setAttribute("view", dto);
            request.setAttribute("filelist", flist);
            request.getRequestDispatcher("kh/free/FBmodifyView.jsp").forward(request, response);
               
         }else if(url.contentEquals("/delete.fboard")){ //�궘�젣�븯湲�
        	 l.trace(request.getRemoteAddr()+" �옄�쑀寃뚯떆�뙋 �궘�젣");
        	 System.out.println("寃뚯떆湲� �궘�젣以�");
            
            String delseq = request.getParameter("seq");
            
            List<FreeFilesDTO>fileList = ffdao.selectAll(Integer.parseInt(delseq)); 
			System.out.println("�뙆�씪�씠 鍮꾩뼱 �엳�굹�슂? "+fileList.isEmpty());//�뙆�씪�씠 �엳�굹�슂?
			if(fileList.isEmpty()) {//�뙆�씪 鍮꾩썙�졇 �엳�떎硫�? 諛붾줈 寃뚯떆湲�留� �궘�젣 
				
			}else {	//�븘�땲�씪硫�? �떎�젣 泥⑤� �뙆�씪�룄 �궘�젣 
            System.out.println("吏��슱 �뙆�씪�쓽 寃뚯떆臾� 踰덊샇:"+delseq);//�떎泥� 泥⑤��뙆�씪 �궘�젣
			String filesPath =request.getServletContext().getRealPath("uploadDirectory");
			System.out.println("�봽濡쒖젥�듃媛� ���옣�맂 吏꾩쭨 寃쎈줈 : " + filesPath);
			
        	    String sysName = ffdao.getSysNameByparent(Integer.parseInt(delseq));
        	    System.out.println(sysName + "�뙆�씪 �씠由�");
                File targetFile = new File(filesPath +"/" + sysName); 
                boolean result = targetFile.delete();
                System.out.println("泥⑤��뙆�씪 �궘�젣 �뿬遺� :" + result);
                if(result) {ffdao.fileDeleteByparent(Integer.parseInt(delseq));}  
            
			}
            int delfree = fbdao.delete(Integer.parseInt(delseq)); ;//寃뚯떆湲� �궘�젣 + �쇅�옒�궎濡� 泥⑤��뙆�씪�룄 �궘�젣 
            response.sendRedirect("kh/free/FBdeleteView.jsp");

                
         }else if(url.contentEquals("/reportForm.fboard")) { //�떊怨좏븯湲� �뤌
            System.out.println("seq 諛쏆븘�샂!!");
            int board_seq = Integer.parseInt(request.getParameter("seq"));
            FreeBoardDTO dto = fbdao.detailView(board_seq);
            request.setAttribute("view", dto);
            request.getRequestDispatcher("kh/free/reportPop.jsp").forward(request, response);

         }else if(url.contentEquals("/report.fboard")) { //�떊怨좎쿂由�
        	 l.trace(request.getRemoteAddr()+" �옄�쑀寃뚯떆�뙋 �떊怨�");
        	 System.out.println("�떊怨� 泥섎━以�!!");
            MemberDTO dto = mdao.getMainInfo(session);
            String id = dto.getId();
            String contents = request.getParameter("contents");
            int parent = Integer.parseInt(request.getParameter("seq"));
            int result = fpdao.report(id,contents,parent);
            System.out.println("�떊怨� 泥섎━ �뿬遺� : "+ result);

            RequestDispatcher rd = request.getRequestDispatcher("kh/free/reportResult.jsp"); //�떊怨� 寃곌낵�솕硫�
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