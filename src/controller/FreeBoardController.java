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
      System.out.println("占쎌뒄筌ｏ옙 筌뤿굝議� :" + url);

      try {
         FreeCommentDAO fcdao = FreeCommentDAO.getInstance();   
         FreeBoardDAO fbdao = FreeBoardDAO.getInstance();
         MemberDAO mdao = MemberDAO.getInstance();
         FreeFilesDAO ffdao =FreeFilesDAO.getInstance();
         MyPageDAO mpdao = MyPageDAO.getInstance();
         ProfileFileDAO pfdao = ProfileFileDAO.getInstance();
         FreePoliceDAO fpdao = FreePoliceDAO.getInstance();
         String session = (String) request.getSession().getAttribute("login");



         if(url.contentEquals("/list.fboard")) { //�뵳�딅뮞占쎈뱜 癰귣떯由�

            //野껓옙占쎄퉳疫꿸퀡�뮟 �빊遺쏙옙
            String category =request.getParameter("category"); //燁삳똾�믤�⑥쥓�봺 
            String keyWord =request.getParameter("keyword"); //野껓옙占쎄퉳占쎈선 占쎌뿯占쎌젾
            keyWord= XSSFilter(keyWord);

            String branch = request.getParameter("branch");//branch 占쎌뿯占쎌젾

            int cpage =Integer.parseInt(request.getParameter("cpage"));
            System.out.println("占쎌겱占쎌삺占쎈읂占쎌뵠筌욑옙: "+ cpage);
            System.out.println("燁삳똾�믤�⑥쥓�봺: "+ category);
            System.out.println("野껓옙占쎄퉳占쎈선: "+ keyWord);
            System.out.println("筌욑옙占쎌젎: "+ branch);

            int endNum= cpage* BoardConfig.Recode_Count_Per_Page;
            int startNum =endNum-(BoardConfig.Navi_Count_Per_Page-1);

            List<FreeBoardDTO> boardlist;
            List<String>pageNavi;

            if(keyWord==null || keyWord.contentEquals("")){ //keyword揶쏉옙 占쎈씨椰꾧퀡援�, keyword占쎌벥 input 獄쏅벡�뮞揶쏉옙 �뜮袁⑹뜖 占쎌뿳占쎌뱽 野껋럩�뒭
               if(branch==null || branch.contentEquals("")&  category.contentEquals("") & keyWord.contentEquals("")) {
                  System.out.println("占쎌읈筌ｏ옙");
                  boardlist =fbdao.getPageList(startNum,endNum);
                  pageNavi =fbdao.getPageNavi(cpage,category,keyWord);//占쎈읂占쎌뵠筌욑옙 占쎄퐬�뜮袁㏃쓺占쎌뵠占쎈�∽옙肉� capge,category, keyword 占쎌뵥占쎌쁽 揶쏅�れ뱽 獄쏆룇�벉

               }else {
                  System.out.println("揶쏉옙 筌욑옙占쎌젎");
                  boardlist =fbdao.getEachBranch(startNum,endNum,branch);// 占쎈읂占쎌뵠筌욑옙 �뵳�딅뮞占쎈뱜 占쎈뻻占쎌삂 甕곕뜇�깈, 占쎄국甕곕뜇�깈 parameter嚥∽옙 獄쏆룇�벉
                  pageNavi =fbdao.getPageNavi(cpage,category,keyWord,branch);


               }
            }else {
               if(branch==null){
                  System.out.println("keyword + 占쎌읈筌ｏ옙");
                  boardlist =fbdao.searchAll(startNum,endNum,category,keyWord);
                  pageNavi =fbdao.getPageNavi(cpage,category,keyWord);


               }else {
                  System.out.println("keyword + 揶쏉옙 筌욑옙占쎌젎");
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
        	 l.trace(request.getRemoteAddr()+" 자유게시판 작성");
            System.out.println("占쎌삂占쎄쉐 餓ο옙");
            String root =request.getServletContext().getRealPath("/");
            String pathName = root + "uploadDirectory";


            File filesFolder = new File(pathName);
            System.out.println("占쎈늄嚥≪뮇�젰占쎈뱜揶쏉옙 占쏙옙占쎌삢占쎈쭆 筌욊쑴彛� 野껋럥以� : " + pathName);

            if(!filesFolder.exists()) filesFolder.mkdir();

            MultipartRequest multi = new MultipartRequest(request,pathName,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 


            String title =multi.getParameter("title");
            title= XSSFilter(title);
            String contents= multi.getParameter("contents");

            System.out.println("占쎌젫筌륅옙 :" + title);
            System.out.println("占쎄땀占쎌뒠 :" + contents);

            MemberDTO dto = mdao.getMainInfo(session);

            int seq = fbdao.getSeq();            
            String branch =dto.getBranch();
            System.out.println("筌욑옙占쎌젎: "+ branch);
            String writer = session;
            System.out.println("占쎌삂占쎄쉐占쎌쁽: "+ writer);

            String id = dto.getId();
            System.out.println("占쎈린甕곤옙: "+ id);


            int result = fbdao.write(seq,branch,writer,title,contents,id);
            System.out.println("野껊슣�뻻疫뀐옙 占쎌뿯占쎌젾 占쎈연�겫占� : "+ result);


            Set<String>fileNames = multi.getFileNameSet();
            if(fileNames == null) {

            }else {
               System.out.println("占쎈솁占쎌뵬揶쏉옙占쎈땾 "+fileNames.size());
               for(String fileName : fileNames) {
                  if(!fileName.contentEquals("files")) {//筌ｂ뫀占쏙옙�솁占쎌뵬筌랃옙
                     System.out.println("占쎈솁占쎌뵬沃섎챸苑� 占쎌뵠�뵳占�: "+ fileName);
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
            List<FreeBoardDTO> boardlist =fbdao.boardList();//筌뤴뫖以� �빊�뮆�젾
            request.setAttribute("boardlist", boardlist);
            RequestDispatcher rd = request.getRequestDispatcher("kh/free/FBwriteView.jsp"); //疫뀐옙占쎈쾺疫뀐옙 占쎈읂占쎌뵠筌욑옙嚥∽옙 占쎌뵠占쎈짗
            rd.forward(request, response);

         }else if(url.contentEquals("/detailView.fboard")) {  //占쎄맒占쎄쉭癰귣떯由�

            MemberDTO dto = mdao.getMainInfo(session);
            request.setAttribute("dto", dto);

            int boardseq = Integer.parseInt(request.getParameter("seq"));

            fbdao.viewCountPlus(boardseq);//鈺곌퀬�돳占쎈땾 +1
            FreeBoardDTO bdto = fbdao.detailView(boardseq); 
            //占쎈늄嚥≪뮉釉� 占쎄텢筌욑옙 �빊�뮆�젾
            if(pfdao.selectBySeq(bdto.getId()).size()==0){
               String filesPath = request.getServletContext().getRealPath("profile/"+session);      
               File filesFolder = new File(filesPath);
               if(!filesFolder.exists()) filesFolder.mkdirs();
               request.setAttribute("defalut_profile_img","profile.png");
            } else { 
               request.setAttribute("mm", mpdao);
               request.setAttribute("profile_img",pfdao.getSysName(bdto.getId()));
            }

            request.setAttribute("member",mpdao.getMember(session));

            System.out.println("野껊슣�뻻疫뀐옙 甕곕뜇�깈 :"+boardseq);
            request.setAttribute("view", bdto);

            List<FreeFilesDTO>fileList = ffdao.selectAll(boardseq); //筌ｂ뫀占쏙옙�솁占쎌뵬 筌뤴뫖以� �빊�뮆�젾   
            System.out.println("占쎈솁占쎌뵬占쎌뵠 �뜮袁⑸선 占쎌뿳占쎄돌占쎌뒄? "+fileList.isEmpty());//占쎈솁占쎌뵬占쎌뵠 占쎌뿳占쎄돌占쎌뒄?
            request.setAttribute("filelist", fileList);//占쎈솁占쎌뵬�뵳�딅뮞占쎈뱜�몴占� request占쎈퓠 占쎈뼖占쎈뮉占쎈뼄.

            request.setAttribute("count", fcdao); //占쎈솊疫뀐옙 占쎈땾 �빊�뮆�젾 
            List<FreeCommentDTO> list =fcdao.CommentsList(boardseq);//占쎈솊疫뀐옙�뵳�딅뮞占쎈뱜            
            request.setAttribute("reply", list); //占쎈솊疫뀐옙�뵳�딅뮞占쎈뱜�몴占� request�몴占� 占쎈뼖占쎈뮉占쎈뼄.

            request.getRequestDispatcher("kh/free/FBdetailView.jsp").forward(request, response);
            
         }else if(url.contentEquals("/modify.fboard")){ //占쎈땾占쎌젟占쎈릭疫뀐옙
        	 l.trace(request.getRemoteAddr()+" 자유게시판 수정");     
            int boardseq = Integer.parseInt(request.getParameter("seq"));
            System.out.println(boardseq);
            FreeBoardDTO bdto = fbdao.detailView(boardseq);
            request.setAttribute("view", bdto);

            List<FreeFilesDTO> fileList = ffdao.selectAll(boardseq);
            System.out.println("占쎈솁占쎌뵬占쎌뵠 �뜮袁⑸선 占쎌뿳占쎄돌占쎌뒄? "+fileList.isEmpty());//占쎈솁占쎌뵬占쎌뵠 占쎌뿳占쎄돌占쎌뒄?
            System.out.println("占쎈솁占쎌뵬 揶쏉옙占쎈땾: "+ fileList.size());
            request.setAttribute("filelist", fileList);

            request.getRequestDispatcher("kh/free/FBmodify.jsp").forward(request, response);

         }else if(url.contentEquals("/modifyedit.fboard")) {   //疫뀐옙 占쎈땾占쎌젟占쎈릭疫뀐옙
            
            String root =request.getServletContext().getRealPath("/");
            String pathName = root + "uploadDirectory";


            File filesFolder = new File(pathName);
            System.out.println("占쎈늄嚥≪뮇�젰占쎈뱜揶쏉옙 占쏙옙占쎌삢占쎈쭆 筌욊쑴彛� 野껋럥以� : " + pathName);


            if(!filesFolder.exists()) {filesFolder.mkdir();}

            MultipartRequest multi = new MultipartRequest(request,pathName,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 


            int board_seq = Integer.parseInt(multi.getParameter("seq"));
            System.out.println(board_seq);
            String uptitle = multi.getParameter("title");
            System.out.println("占쎈땾占쎌젟占쎈립 占쎌젫筌륅옙:" +uptitle);
            String upcontents = multi.getParameter("contents");
            System.out.println("占쎈땾占쎌젟占쎈립 占쎄땀占쎌뒠: "+ upcontents);

            String[]del=multi.getParameterValues("delete");

            if(del== null) {
            }else if(del!= null) {
               for(String deleteFile : del) {
                  System.out.println("筌욑옙占쎌뒻 占쎈솁占쎌뵬 甕곕뜇�깈 "+ deleteFile);

                  String sysName = ffdao.getSysName(Integer.parseInt(deleteFile));
                  File targetFile = new File(pathName +"/" + sysName); 
                  boolean result = targetFile.delete();
                  System.out.println("占쎈솁占쎌뵬 占쎄텣占쎌젫 占쎈연�겫占� :" + result);
                  if(result) {ffdao.fileDelete(Integer.parseInt(deleteFile));}
               }

               System.out.println(del.length);
            }
               int result = fbdao.modify(board_seq, uptitle, upcontents);
               System.out.println("占쎈땾占쎌젟 野껉퀗�궢"+ result);

               Set<String>fileNames = multi.getFileNameSet();
               System.out.println("占쎈솁占쎌뵬揶쏉옙占쎈땾 "+fileNames.size());
               for(String fileName : fileNames) {
                  if(!fileName.contentEquals("files")) {

                  System.out.println("占쎈솁占쎌뵬沃섎챸苑� 占쎌뵠�뵳占�: "+ fileName);
                  String oriName = multi.getOriginalFileName(fileName);
                  String sysName = multi.getFilesystemName(fileName);

                  if(oriName!=null) {  
                     System.out.println("占쎈솁占쎌뵬占쎌뵠�뵳占�" + oriName + "DB占쎈퓠 占쏙옙占쎌삢占쎈쭡.");

                  }
               }
            }

            FreeBoardDTO dto = fbdao.detailView(board_seq);
            List<FreeFilesDTO> flist = ffdao.selectAll(board_seq);
            request.setAttribute("view", dto);
            request.setAttribute("filelist", flist);
            request.getRequestDispatcher("kh/free/FBmodifyView.jsp").forward(request, response);
               
         }else if(url.contentEquals("/delete.fboard")){ //占쎄텣占쎌젫占쎈릭疫뀐옙
        	 l.trace(request.getRemoteAddr()+" 자유게시판 삭제");
        	 System.out.println("野껊슣�뻻疫뀐옙 占쎄텣占쎌젫餓ο옙");
            
            String delseq = request.getParameter("seq");
            
            List<FreeFilesDTO>fileList = ffdao.selectAll(Integer.parseInt(delseq)); 
			System.out.println("占쎈솁占쎌뵬占쎌뵠 �뜮袁⑸선 占쎌뿳占쎄돌占쎌뒄? "+fileList.isEmpty());//占쎈솁占쎌뵬占쎌뵠 占쎌뿳占쎄돌占쎌뒄?
			if(fileList.isEmpty()) {//占쎈솁占쎌뵬 �뜮袁⑹뜖占쎌죬 占쎌뿳占쎈뼄筌롳옙? 獄쏅뗀以� 野껊슣�뻻疫뀐옙筌랃옙 占쎄텣占쎌젫 
				
			}else {	//占쎈툡占쎈빍占쎌뵬筌롳옙? 占쎈뼄占쎌젫 筌ｂ뫀占� 占쎈솁占쎌뵬占쎈즲 占쎄텣占쎌젫 
            System.out.println("筌욑옙占쎌뒻 占쎈솁占쎌뵬占쎌벥 野껊슣�뻻�눧占� 甕곕뜇�깈:"+delseq);//占쎈뼄筌ｏ옙 筌ｂ뫀占쏙옙�솁占쎌뵬 占쎄텣占쎌젫
			String filesPath =request.getServletContext().getRealPath("uploadDirectory");
			System.out.println("占쎈늄嚥≪뮇�젰占쎈뱜揶쏉옙 占쏙옙占쎌삢占쎈쭆 筌욊쑴彛� 野껋럥以� : " + filesPath);
			
        	    String sysName = ffdao.getSysNameByparent(Integer.parseInt(delseq));
        	    System.out.println(sysName + "占쎈솁占쎌뵬 占쎌뵠�뵳占�");
                File targetFile = new File(filesPath +"/" + sysName); 
                boolean result = targetFile.delete();
                System.out.println("筌ｂ뫀占쏙옙�솁占쎌뵬 占쎄텣占쎌젫 占쎈연�겫占� :" + result);
                if(result) {ffdao.fileDeleteByparent(Integer.parseInt(delseq));}  
            
			}
            int delfree = fbdao.delete(Integer.parseInt(delseq)); ;//野껊슣�뻻疫뀐옙 占쎄텣占쎌젫 + 占쎌뇚占쎌삋占쎄텕嚥∽옙 筌ｂ뫀占쏙옙�솁占쎌뵬占쎈즲 占쎄텣占쎌젫 
            response.sendRedirect("kh/free/FBdeleteView.jsp");

                
         }else if(url.contentEquals("/reportForm.fboard")) { //占쎈뻿�⑥쥚釉�疫뀐옙 占쎈쨲
            System.out.println("seq 獄쏆룇釉섓옙�긾!!");
            int board_seq = Integer.parseInt(request.getParameter("seq"));
            FreeBoardDTO dto = fbdao.detailView(board_seq);
            request.setAttribute("view", dto);
            request.getRequestDispatcher("kh/free/reportPop.jsp").forward(request, response);

         }else if(url.contentEquals("/report.fboard")) { //占쎈뻿�⑥쥙荑귞뵳占�
        	 l.trace(request.getRemoteAddr()+" 자유게시판 신고");
        	 System.out.println("占쎈뻿�⑨옙 筌ｌ꼶�봺餓ο옙!!");
            MemberDTO dto = mdao.getMainInfo(session);
            String id = dto.getId();
            String contents = request.getParameter("contents");
            int parent = Integer.parseInt(request.getParameter("seq"));
            int result = fpdao.report(id,contents,parent);
            System.out.println("占쎈뻿�⑨옙 筌ｌ꼶�봺 占쎈연�겫占� : "+ result);

            RequestDispatcher rd = request.getRequestDispatcher("kh/free/reportResult.jsp"); //占쎈뻿�⑨옙 野껉퀗�궢占쎌넅筌롳옙
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