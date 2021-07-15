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
         FreeCommentDAO fcdao = FreeCommentDAO.getInstance();   
         FreeBoardDAO fbdao = FreeBoardDAO.getInstance();
         MemberDAO mdao = MemberDAO.getInstance();
         FreeFilesDAO ffdao =FreeFilesDAO.getInstance();
         MyPageDAO mpdao = MyPageDAO.getInstance();
         ProfileFileDAO pfdao = ProfileFileDAO.getInstance();
         FreePoliceDAO fpdao = FreePoliceDAO.getInstance();
         String session = (String) request.getSession().getAttribute("login");



         if(url.contentEquals("/list.fboard")) { //리스트 보기

            
            String category =request.getParameter("category"); 
            String keyWord =request.getParameter("keyword"); 
            keyWord= XSSFilter(keyWord);

            String branch = request.getParameter("branch");

            int cpage =Integer.parseInt(request.getParameter("cpage"));
            System.out.println("현재페이지: "+ cpage);
            System.out.println("카테고리: "+ category);
            System.out.println("검색어: "+ keyWord);
            System.out.println("지점: "+ branch);

            int endNum= cpage* BoardConfig.Recode_Count_Per_Page;
            int startNum =endNum-(BoardConfig.Navi_Count_Per_Page-1);

            List<FreeBoardDTO> boardlist;
            List<String>pageNavi;

            if(keyWord==null || keyWord.contentEquals("")){ 
               if(branch==null || branch.contentEquals("")&  category.contentEquals("") & keyWord.contentEquals("")) {
                  System.out.println("전체");
                  boardlist =fbdao.getPageList(startNum,endNum);
                  pageNavi =fbdao.getPageNavi(cpage,category,keyWord);

               }else {
                  System.out.println("각 지점");
                  boardlist =fbdao.getEachBranch(startNum,endNum,branch);
                  pageNavi =fbdao.getPageNavi(cpage,category,keyWord,branch);


               }
            }else {
               if(branch==null){
                  System.out.println("keyword + 전체");
                  boardlist =fbdao.searchAll(startNum,endNum,category,keyWord);
                  pageNavi =fbdao.getPageNavi(cpage,category,keyWord);


               }else {
                  System.out.println("keyword + 각 지점");
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
            System.out.println("자유게시판 작성중");
            String root =request.getServletContext().getRealPath("/");
            String pathName = root + "uploadDirectory";


            File filesFolder = new File(pathName);
            System.out.println("파일경로 : " + pathName);

            if(!filesFolder.exists()) filesFolder.mkdir();

            MultipartRequest multi = new MultipartRequest(request,pathName,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 


            String title =multi.getParameter("title");
            title= XSSFilter(title);
            String contents= multi.getParameter("contents");

            System.out.println("제목 :" + title);
            System.out.println("내용 :" + contents);

            MemberDTO dto = mdao.getMainInfo(session);

            int seq = fbdao.getSeq();            
            String branch =dto.getBranch();
            System.out.println("지점: "+ branch);
            String writer = session;
            System.out.println("작성자: "+ writer);

            String id = dto.getId();
            System.out.println("학원 학번: "+ id);


            int result = fbdao.write(seq,branch,writer,title,contents,id);
            System.out.println("글 작성 결과 : "+ result);


            Set<String>fileNames = multi.getFileNameSet();
            if(fileNames == null) {

            }else {
               System.out.println("파일갯수 "+fileNames.size());
               for(String fileName : fileNames) {
                  if(!fileName.contentEquals("files")) {
                     System.out.println("파라미터 이름: "+ fileName);
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
            List<FreeBoardDTO> boardlist =fbdao.boardList();//목록 출력
            request.setAttribute("boardlist", boardlist);
            RequestDispatcher rd = request.getRequestDispatcher("kh/free/FBwriteView.jsp"); 
            rd.forward(request, response);

         }else if(url.contentEquals("/detailView.fboard")) {  //글 상세보기

            MemberDTO dto = mdao.getMainInfo(session);
            request.setAttribute("dto", dto);

            int boardseq = Integer.parseInt(request.getParameter("seq"));

            fbdao.viewCountPlus(boardseq);//조회수 +1
            FreeBoardDTO bdto = fbdao.detailView(boardseq); 
            //프로필 사진
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

            List<FreeFilesDTO>fileList = ffdao.selectAll(boardseq); //첨부파일 목록 출력   
            System.out.println("파일이 비어 있나요?? "+fileList.isEmpty());//파일이 있나요?
            request.setAttribute("filelist", fileList);//파일리스트를 request애 담는다.

            request.setAttribute("count", fcdao); //파일리스트를 request애 담는다. 
            List<FreeCommentDTO> list =fcdao.CommentsList(boardseq);//댓글리스트            
            request.setAttribute("reply", list);//댓글리스트를 request를 담는다.

            request.getRequestDispatcher("kh/free/FBdetailView.jsp").forward(request, response);
            
         }else if(url.contentEquals("/modify.fboard")){ //수정하기
        	 l.trace(request.getRemoteAddr()+" 자유게시판 수정");     
            int boardseq = Integer.parseInt(request.getParameter("seq"));
            System.out.println(boardseq);
            FreeBoardDTO bdto = fbdao.detailView(boardseq);
            request.setAttribute("view", bdto);

            List<FreeFilesDTO> fileList = ffdao.selectAll(boardseq);
            System.out.println("프로젝트가 저장된 진짜 경로? "+fileList.isEmpty());//파일이 비워져 있나요?
            
            System.out.println("파일 갯수?: "+ fileList.size());
            request.setAttribute("filelist", fileList);

            request.getRequestDispatcher("kh/free/FBmodify.jsp").forward(request, response);

         }else if(url.contentEquals("/modifyedit.fboard")) {   //수정하기
            
            String root =request.getServletContext().getRealPath("/");
            String pathName = root + "uploadDirectory";


            File filesFolder = new File(pathName);
            System.out.println("프로젝트가 저장된 진짜 경로 : " + pathName);


            if(!filesFolder.exists()) {filesFolder.mkdir();}

            MultipartRequest multi = new MultipartRequest(request,pathName,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 


            int board_seq = Integer.parseInt(multi.getParameter("seq"));
            System.out.println(board_seq);
            String uptitle = multi.getParameter("title");
            System.out.println("수정한 제목:" +uptitle);
            String upcontents = multi.getParameter("contents");
            System.out.println("수정한 내용: "+ upcontents);

            String[]del=multi.getParameterValues("delete");

            if(del== null) {
            }else if(del!= null) {//삭제할 항목이 null 아닐때, 
               for(String deleteFile : del) {
                  System.out.println("지울 파일 번호"+ deleteFile);

                  String sysName = ffdao.getSysName(Integer.parseInt(deleteFile));
                  File targetFile = new File(pathName +"/" + sysName); 
                  boolean result = targetFile.delete();
                  System.out.println("파일 삭제 여부:" + result);
                  if(result) {ffdao.fileDelete(Integer.parseInt(deleteFile));}
               }

               System.out.println(del.length);
            }
               int result = fbdao.modify(board_seq, uptitle, upcontents);
               System.out.println("수정 결과"+ result);

               Set<String>fileNames = multi.getFileNameSet();
               System.out.println("파일갯수 "+fileNames.size());
               for(String fileName : fileNames) {
                  if(!fileName.contentEquals("files")) {

                  System.out.println("파라미터 이름 "+ fileName);
                  String oriName = multi.getOriginalFileName(fileName);
                  String sysName = multi.getFilesystemName(fileName);

                  if(oriName!=null) {  
                     System.out.println("파일이름" + oriName + "DB에 저장됨.");

                  }
               }
            }

            FreeBoardDTO dto = fbdao.detailView(board_seq);
            List<FreeFilesDTO> flist = ffdao.selectAll(board_seq);
            request.setAttribute("view", dto);
            request.setAttribute("filelist", flist);
            request.getRequestDispatcher("kh/free/FBmodifyView.jsp").forward(request, response);
               
         }else if(url.contentEquals("/delete.fboard")){ //삭제
        	 l.trace(request.getRemoteAddr()+" 자유게시판 삭제");
        	 System.out.println("삭제중");
            
            String delseq = request.getParameter("seq");
            
            List<FreeFilesDTO>fileList = ffdao.selectAll(Integer.parseInt(delseq)); 
			System.out.println("파일이 비워져 있나요? "+fileList.isEmpty());;//파일이 있나요?
			if(fileList.isEmpty()) {//파일 비워져 있다면? 바로 게시글만 삭제
				
			}else {	//아니라면? 실제 첨부 파일도 삭제 
            System.out.println("지울 파일의 게시물 번호:"+delseq);//실체 첨부파일 삭제
			String filesPath =request.getServletContext().getRealPath("uploadDirectory");
			System.out.println("프로젝트가 저장된 진짜 경로 : " + filesPath);
			
        	    String sysName = ffdao.getSysNameByparent(Integer.parseInt(delseq));
        	    System.out.println(sysName + "파일 이름");
                File targetFile = new File(filesPath +"/" + sysName); 
                boolean result = targetFile.delete();
                System.out.println("첨부파일 삭제 여부" + result);
                if(result) {ffdao.fileDeleteByparent(Integer.parseInt(delseq));}  
            
			}
            int delfree = fbdao.delete(Integer.parseInt(delseq)); ;//삭제할 게시글 
            response.sendRedirect("kh/free/FBdeleteView.jsp");

                
         }else if(url.contentEquals("/reportForm.fboard")) { //신고하기 폼
            System.out.println("seq 신고 접수!!");
            int board_seq = Integer.parseInt(request.getParameter("seq"));
            FreeBoardDTO dto = fbdao.detailView(board_seq);
            request.setAttribute("view", dto);
            request.getRequestDispatcher("kh/free/reportPop.jsp").forward(request, response);

         }else if(url.contentEquals("/report.fboard")) { //신고하기 
        	 l.trace(request.getRemoteAddr()+" 자유게시판 신고");
        	 System.out.println("신고 처리중 !!");
            MemberDTO dto = mdao.getMainInfo(session);
            String id = dto.getId();
            String contents = request.getParameter("contents");
            int parent = Integer.parseInt(request.getParameter("seq"));
            int result = fpdao.report(id,contents,parent);
            System.out.println("신고 결과 화면 : "+ result);

            RequestDispatcher rd = request.getRequestDispatcher("kh/free/reportResult.jsp"); //신고 결과화면
            rd.forward(request, response);
         }
      }catch(Exception e) {
         e.printStackTrace();
         response.sendRedirect("${pageContext.request.contextPath}/error.jsp");
      }
   }


   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      doGet(request, response);
   }

}