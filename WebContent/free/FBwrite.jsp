<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>freeBoard Write</title>
   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
            <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
  			<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
            <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
            <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
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
			margin-top:30px;

		}

		.title {
			overflow: hidden;
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
			padding: 20px;
			min-height: 350px;
		}

		.contents textarea {
			padding: 5px;
			width: 100%;
			height: 90%;
			border-color: #fff;
		
		}
		.btn_wrap {
			padding-top: 10px;
		}
		
		legend {
                    border: 0;
                }
      .footer{padding-bottom:10px;}          
      
	</style>

   <script>
    $(function() {
    	
    	$("#listBtn").on("click",function(){
    		location.href = "${pageContext.request.contextPath}/list.fboard?cpage=1";
    	});
    	
    	 $("#summernote").summernote(
                 {
                     height: 300, // 에디터 높이
                     focus: true, // 에디터 로딩후 포커스를 맞출지 여부
                     lang: "ko-KR", // 한글 설정
                     placeholder: "내용을 입력하세요.",
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
                         '20', '22', '24', '28', '30', '36', '50', '72']
                 });

        
        
        
        
    });
  </script>
</head>

<body>
<jsp:include page="/navibar.jsp"></jsp:include>

<div class="container shadow bg-white rounded">
<!-- 		<h2 class="text-center mb-3">자유게시판</h2>
 -->	<form action="${pageContext.request.contextPath}/write.fboard" method="post" enctype="multipart/form-data" >
			<div class="contents_box">	
					<div class="title">		
					<h3>
						<input type="text" name="title" id="title_input" placeholder="제목을 입력하세요">
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
					<input type="submit" class="btn btn-primary" value="등록하기">
					<input type=button class="btn btn-dark" value="목록으로" id="listBtn">
				</div>
			</div>
		</form>

	</div>
 
</body>
</html>