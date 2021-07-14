<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>freeBoard Write</title>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
    
  <style>
  * {
	margin: 0;
	padding: 0;
}
div[class*=col]{
padding: 0px;
margin:0px;
}

  body {background-color: #D8E3E7;}
  	/*navibar*/
nav{padding:0;margin: 0;}
			
  .navbar>.container-fluid {
            padding: 0px;
        }

        .navbar-nav {
            flex-grow: 1;
            justify-content: space-around;
        }
        .slide {
            position: absolute;
            width: 100%;
            height: 40px;
            top: 100%;
            background-color: #55555550;
        }
  
  	.container {
			max-width: 900px;
			margin: 30px auto;
			margin-top:100px;

		}

		.title {
			overflow: hidden;
			padding-top: 10px;
			padding-bottom: 10px;
			border-bottom: 1px solid #ddd;
		}
	#title_input{
		width:800px;}
		
		.title .seq {
			float: left;
			display: block;
			padding: 8px 10px;
			font-size: 15px;
		}

		 .titel h3 {
			float: left;
			font-size: 2rem;
		}

		.title h3 input {
			border: none;
			
		}

		.title h3 input:focus {
			outline: none;
			
		}


		.contents {
			padding-top:20px;
			padding-left:20px;
			margin-right:20px;
			min-height: 350px;
		}

		.contents textarea {
			padding: 5px;
			width: 90%;
			height: 80%;
			border-color: #fff;
		
		}
		  #file_box{margin-left:20px;}
		
		.btn_wrap {
			padding-top: 10px;
		}
		
		legend { border: 0;
                }
      .footer{padding-bottom:20px;}          
      
	</style>

   <script>
    $(function() {
    	
    	$("#listBtn").on("click",function(){
    		location.href = "${pageContext.request.contextPath}/list.fboard?cpage=1";
    	});

    	 $("#summernote").summernote({
                 
                     height: 300, // 에디터 높이
                     focus: true, // 에디터 로딩후 포커스를 맞출지 여부
                     lang: "ko-KR", // 한글 설정
                     placeholder: "내용을 입력하세요.",
                     toolbar: [
                         // [groupName, [list of button]]
                         ['fontname', ['fontname']],
                         ['fontsize', ['fontsize']],
                         ['style', ['bold', 'italic', 'underline',
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
                     fontSizes: ['8','9', '10', '11', '12', '14', '16', '18',
                         '20', '22', '24', '28', '30', '36', '50', '72'],
                         
                        callbacks: {
     					    onImageUpload: function(imagefiles) {
     					    	let editor =this; //summernote 인스턴스의 주소를 editor 변수에 저장
     					    	let file = imagefiles[0];//업로드 해야 하는 파일 인스턴스
     					    	
     					    	let form = new FormData(); //html<form action="">
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

    	 $("#submit").on("click",function(){ //글 작성 전 제목 내용 입력여부 확인
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
<!-- 		<h2 class="text-center mb-3">자유게시판</h2>
 -->	<form action="${pageContext.request.contextPath}/write.fboard" method="post" enctype="multipart/form-data" id="form" >
			<div class="contents_box">	
					<div class="title">		
					<h3>
						<input type="text" name="title" id="title_input" placeholder="제목을 입력하세요" required>
					</h3>
					
				</div>
				<div class="contents">
					  <textarea id="summernote" name="contents"></textarea>
				</div>
				<hr>
				<!-- 파일 첨부 -->
			<fieldset id="file_box">
                            <legend> [파일첨부]  </legend>
                                <input type="file" name="file" multiple="multiple"> <br>
                                <input type="file"name="file">
                          
                        </fieldset>
				<div class="btn_wrap text-right footer">
					<input type="submit" class="btn btn-primary" id="submit" value="등록하기">
					<input type="button" class="btn btn-dark" value="목록으로" id="listBtn">
				</div>
			</div>
		</form>

	</div>
 
</body>
</html>