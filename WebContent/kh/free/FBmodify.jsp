<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>freeBoard modify</title>
           
       <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
         
         <style>
            body {background-color: #D8E3E7;}
            /*navibar*/
 		 .navbar>.container-fluid {
            padding: 0px;
        }
		ul{
        padding:0px;}
        .navbar-nav {
            flex-grow: 1;
            justify-content: space-around;
        }

        .slide {
            position: absolute;
            width: 100%;
            height: 50px;
            top: 100%;
            background-color: #55555550;
        }
                .container {
                    max-width: 900px;
                    margin-left: 10px;
                    margin: 30px auto;
                    margin-top:100px;
                    margin-bottom:20px;
                    
                }

                div {
                    display: block;
                }
                
			

                h2 {
                    margin-left: 20px;
                    margin-bottom: 0px;
                    font-weight: bold;   
                    display: block;
                    margin-inline-end: 0px;
                }

                .title_area {
                    border-bottom: 1px solid #ddd;
                    padding-top:30px;
                }

                a {
                    text-decoration: none;
                    cursor: pointer;
                    font-weight: bold;
                    
                }

                li a:hover {
                    color: cadetblue;
                }

                a {
                    color: black;
                    font-weight: bold;
                }

                .title_are {
                    margin-top: 10px;
                }
			#title_input{
				width:800px;}
                .title_area h2 input {
                    border: none;
                }

                .title_area h2 input:focus {
                    outline: none;
                }
				
                /* 작성자 정보 */
                .profile_info{
				margin-left:20px;
				}
                .WriterInfo {
                    margin-top: 10px;
                }
			.writer{padding-left: 10px;
				padding-top:10px;
				}
         

                .WriterInfo .profile_info .name_box .name {
                    padding: 10px;
                    margin-right: 6px;
                    font-size: 13px;
                    font-weight: 700;
                }
                
			
                .profile_info img {
                    margin-top: 10px;
                }
			span{
				margin-left:20px;}
				
				.count{
				font-weight:800;}
				
                .WriterInfo .article_info {
                    font-size: 12px;
					line-height: 13px;
					width: 95%;
                }
			.articleInfo {
					margin-top: 10px;
					margin-bottom: 10px;
				}
				
                /* 내용 */
                .contents {
                    padding: 10px;
                    min-height: 250px;
                }

                .contents textarea {
                    width: 100%;
                    height: 90%;
                }
                p{margin-left:20px;}
                
                
				/* 첨부파일 */
				.delfiles{margin-left:20px;}
				.delAttach{margin-left:20px;}
                legend {
                    border: 0;
                    margin-left:20px;
                }
                #file_box{margin-left:20px;}
                .footer{
				padding-top:10px;
				padding-bottom:20px;}
				
                hr{padding:0;}
            </style>
            <script>
                $(function () {

                    $("#listBtn").on("click", function () {
                        location.href = "${pageContext.request.contextPath}/list.fboard?cpage=1";
                    });
                    $('#summernote').val('${view.contents}');
                    $("#summernote").summernote(
                        {
                            height: 250, // 에디터 높이
                            focus: true, // 에디터 로딩후 포커스를 맞출지 여부
                            lang: "ko-KR", // 한글 설정
                            toolbar: [
                                // [groupName, [list of button]]
                                ['fontname', ['fontname']],
                                ['fontsize', ['fontsize']],
                                [
                                    'style',
                                    ['bold', 'italic', 'underline',
                                        'strikethrough', 'clear']],
                                ['color', ['forecolor', 'color']],
                                ['table', ['table']],
                                ['para', ['ul', 'ol', 'paragraph']],
                                ['height', ['height']],
                                ['insert', ['picture', 'link', 'video']],
                                ['view', ['fullscreen', 'help']]],
                            fontNames: ['Arial', 'Arial Black', 'Comic Sans MS',
                                'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋움체',
                                '바탕체'],
                            fontSizes: ['8', '9', '10', '11', '12', '14', '16', '18',
                                '20', '22', '24', '28', '30', '36', '50', '72'],
                                callbacks: {
             					    onImageUpload: function(imagefiles) {
             					    	let editor =this; //summernote 인스턴스의 주소를 editor 변수에 저장
             					    	let file = imagefiles[0];//업로드 해야 하는 파일 인스턴스
             					    	
             					    	let form = new FormData();
             					    	form.append("file",file);//input type=file name 속성
             					    	
             					     $.ajax({
             					    	data:form,
             					    	type:"post",
             					    	url:"${pageContext.request.contextPath}/upload.file",
             					    	contentType:false,
             					    	processData:false
             					     }).done(function(resp){
             					    	 $(editor).summernote('insertImage',"${pageContext.request.contextPath}" +resp);
             					    	 //editor 인스턴스의 Image 기능으로 이미지를 화면에 출력
             					     });
             					   }
                   			  } 
                        });

                    $(".delAttach").on("click", function () {
                    let seq = ($(this).attr("seq"));
                    $(this).parent().remove();
                    let del = $("<input>");
                    del.attr("type", "hidden");
                    del.attr("name", "delete");
                    del.attr("value", seq);
                    $(".contents_box").after(del);

                });
                    
                    $("#submit").on("click",function(){
            		    let titleCheck = $("#title_input");
            		    let contentsCheck=$("#summernote");

            		    if (titleCheck.val()=="") {
            		        alert("제목을 입력해주세요.");
            		        titleCheck.focus();
            		        return false;
            		    }
            		    else if (contentsCheck.val()=="") {
            		        alert("내용을 입력해주세요.");
            		       contentsCheck.focus();
        					return false;
            		    }
                   		$("#form").submit();

            		});
                });



            </script>
        </head>

        <body>
        <jsp:include page= "/header.jsp" />
        <jsp:include page="/navibar.jsp"></jsp:include>
        
            <div class="container shadow bg-white rounded">
                <!-- 게시물 제목 -->
                <form action="${pageContext.request.contextPath}/modifyedit.fboard" method="post" enctype="multipart/form-data" id="form">

                    <div class="contents_box">
                        <div class="col-12 title_area">

                            <h2>
                                <input id="title_input" type="text" name="title" size="50" value="${view.title}" required>
                            </h2>

                        </div>
                        <!-- 작성자 정보 -->
                        <div class="writerInfo">
                            <div class="profile_info">
                                <a href=""> <img src="title.jpg" alt="프로필 사진" width="30" height="30">
                                </a>
                                <div class="name_box">
                                    <a href="#" role="button"> ${view.name} </a> <em class="position">${view.branch}지점
                                    </em>
                                </div>
                            </div>
                            <!-- 작성일자,조회수 -->
                            <div class="articleInfo">
                                <span class="date">${view.write_date}</span> <span class="count">조회
                                    ${view.viewCount}</span> <input type="hidden" name="seq" value="${view.seq}">

                            </div>
                        </div>
                        <hr>
                        <!-- 게시글  내용 -->
                        <div class="col-12 md-5 contents">
                            <div class="contents">
                                <textarea id="summernote" name="contents"></textarea>
                            </div>
                        </div>
                        <hr>
                        <!--첨부 파일리스트 삭제  -->
                        <div id="content">
                            <fieldset class="file_box">
                                <legend>[첨부 파일 목록]</legend>
                                <c:forEach var="file" items="${filelist}">
                                    <!--첨부파일 다운로드-->
                                    <div class = "delfiles">${file.oriName}<button type="button" class="btn btn-dark delAttach"
                                            seq="${file.seq}">삭제</button>
                                    </div>
                                </c:forEach>
                            </fieldset>
                        </div>
                        <hr>
                        <!-- 파일 첨부 -->
                            	<fieldset id="file_box">
                            <legend> [파일첨부]  </legend>
                                <input type="file" name="file" multiple="multiple"> <br>
                                <input type="file"name="file">
                        </fieldset>
                        <div class="btn_wrap text-right footer">
                            <input type="submit" class="btn btn-primary" value="수정 완료" id="submit">
                            <input type= "button" class="btn btn-dark" value="목록으로" id="listBtn">

                        </div>
                    </div>
                </form>
            </div>

        </body>

        </html>