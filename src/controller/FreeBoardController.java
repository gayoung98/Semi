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

            //검색기능 추가
            String category =request.getParameter("category"); //카테고리 
            String keyWord =request.getParameter("keyword"); //검색어 입력
            String branch = request.getParameter("branch");//branch 입력

            int cpage =Integer.parseInt(request.getParameter("cpage"));
            System.out.println("현재페이지: "+ cpage);
            System.out.println("카테고리: "+ category);
            System.out.println("검색어: "+ keyWord);
            System.out.println("지점: "+ branch);

            int endNum= cpage* BoardConfig.Recode_Count_Per_Page;
            int startNum =endNum-(BoardConfig.Navi_Count_Per_Page-1);

            List<FreeBoardDTO> boardlist;
            List<String>pageNavi;

            if(keyWord==null || keyWord.contentEquals("")){ //keyword가 없거나, keyword의 input 박스가 비워 있을 경우
               if(branch==null || branch.contentEquals("")&  category.contentEquals("") & keyWord.contentEquals("")) {
                  System.out.println("전체");
                  boardlist =fbdao.getPageList(startNum,endNum);
                  pageNavi =fbdao.getPageNavi(cpage,category,keyWord);//페이지 네비게이션에 capge,category, keyword 인자 값을 받음

               }else {
                  System.out.println("각 지점");
                  boardlist =fbdao.getEachBranch(startNum,endNum,branch);// 페이지 리스트 시작 번호, 끝번호 parameter로 받음
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
            System.out.println("작성 중");
            String root =request.getServletContext().getRealPath("/");
            String pathName = root + "uploadDirectory";


            File filesFolder = new File(pathName);
            System.out.println("프로젝트가 저장된 진짜 경로 : " + pathName);

            if(!filesFolder.exists()) filesFolder.mkdir();

            MultipartRequest multi = new MultipartRequest(request,pathName,FileConfig.uploadMaxSize,"utf8",new DefaultFileRenamePolicy()); 

            String title =multi.getParameter("title");
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
            System.out.println("학번: "+ id);


            int result = fbdao.write(seq,branch,writer,title,contents,id);
            System.out.println("게시글 입력 여부 : "+ result);


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
            RequestDispatcher rd = request.getRequestDispatcher("kh/free/FBwriteView.jsp"); //글쓰기 페이지로 이동
            rd.forward(request, response);

         }else if(url.contentEquals("/detailView.fboard")) {  //상세보기

            MemberDTO dto = mdao.getMainInfo(session);
            request.setAttribute("dto", dto);

            int boardseq = Integer.parseInt(request.getParameter("seq"));

            fbdao.viewCountPlus(boardseq);//조회수 +1

            //프로필 사진 출력
            if(pfdao.selectBySeq(mpdao.getID(session)).size()==0){
               String filesPath = request.getServletContext().getRealPath("profile/"+session);      
               File filesFolder = new File(filesPath);
               if(!filesFolder.exists()) filesFolder.mkdirs();
               request.setAttribute("defalut_profile_img","profile.png");
            } else { 
               request.setAttribute("profile_img",pfdao.getFile(mpdao.getID(session)));
            }

            request.setAttribute("member",mpdao.getMember(session));

            FreeBoardDTO bdto = fbdao.detailView(boardseq); //상세 보기
            System.out.println("게시글 번호 :"+boardseq);
            request.setAttribute("view", bdto);

            List<FreeFilesDTO>fileList = ffdao.selectAll(boardseq); //첨부파일 목록 출력   
            System.out.println("파일이 비어 있나요? "+fileList.isEmpty());//파일이 있나요?
            request.setAttribute("filelist", fileList);//파일리스트를 request에 담는다.

            request.setAttribute("count", fcdao); //댓글 수 출력 
            List<FreeCommentDTO> list =fcdao.CommentsList(boardseq);//댓글리스트            
            request.setAttribute("reply", list); //댓글리스트를 request를 담는다.

            request.getRequestDispatcher("kh/free/FBdetailView.jsp").forward(request, response);
            
         }else if(url.contentEquals("/modify.fboard")){ //수정하기


            int boardseq = Integer.parseInt(request.getParameter("seq"));
            System.out.println(boardseq);
            FreeBoardDTO bdto = fbdao.detailView(boardseq);
            request.setAttribute("view", bdto);

            List<FreeFilesDTO> fileList = ffdao.selectAll(boardseq);
            System.out.println("파일이 비어 있나요? "+fileList.isEmpty());//파일이 있나요?
            System.out.println("파일 갯수: "+ fileList.size());
            request.setAttribute("filelist", fileList);

            request.getRequestDispatcher("kh/free/FBmodify.jsp").forward(request, response);

         }else if(url.contentEquals("/modifyedit.fboard")) {   //글 수정하기
            
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
            }else if(del!= null) {
               for(String deleteFile : del) {
                  System.out.println("지울 파일 번호 "+ deleteFile);

                  String sysName = ffdao.getSysName(Integer.parseInt(deleteFile));
                  File targetFile = new File(pathName +"/" + sysName); 
                  boolean result = targetFile.delete();
                  System.out.println("파일 삭제 여부 :" + result);
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

                  System.out.println("파라미터 이름: "+ fileName);
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
               
         }else if(url.contentEquals("/delete.fboard")){ //삭제하기
            System.out.println("삭제중");            
            int seq = Integer.parseInt(request.getParameter("seq"));
            int result = fbdao.delete(seq);
            response.sendRedirect("kh/free/FBdeleteView.jsp");
         

         }else if(url.contentEquals("/reportForm.fboard")) { //신고하기 폼
            System.out.println("seq 받아옴!!");
            int board_seq = Integer.parseInt(request.getParameter("seq"));
            FreeBoardDTO dto = fbdao.detailView(board_seq);
            request.setAttribute("view", dto);
            request.getRequestDispatcher("kh/free/reportPop.jsp").forward(request, response);

         }else if(url.contentEquals("/report.fboard")) { //신고처리
            System.out.println("신고 처리중!!");

            MemberDTO dto = mdao.getMainInfo(session);
            String id = dto.getId();
            String contents = request.getParameter("contents");
            int parent = Integer.parseInt(request.getParameter("seq"));
            int result = fpdao.report(id,contents,parent);
            System.out.println("신고 처리 여부 : "+ result);

            RequestDispatcher rd = request.getRequestDispatcher("kh/free/reportResult.jsp"); //신고 결과화면
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