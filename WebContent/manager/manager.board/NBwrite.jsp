<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>NoticeBoard</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
  <link rel="stylesheet"
	href="${pageContext.request.contextPath}/manager/css/manager.css">
  <style>
		.title {
			overflow: hidden;
			padding-bottom: 10px;
			border-bottom: 1px solid #ddd;
		}

		.title .seq {
			
			display: block;
			padding: 8px 10px;
			font-size: 15px;
		}

		

		.title h3 input {
			border: none;
			
		}

		.title h3 input:focus {
			outline: none;
			
		}


		.contents {
			padding: 20px;
			min-height: 300px;
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
	</style>

   <script>
    $(function() {
    	
    	$("#main").on("click",function(){
    		location.href ="${pageContext.request.contextPath}/noticeList.manager?currentPage=${page}&branch=${branch}&category=${category}&search=${search}";
    	});
    	
        $("#summernote").summernote({     
        height: 300,                 // 에디터 높이
		  focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
		  lang: "ko-KR",					// 한글 설정
		  placeholder: "내용을 입력하세요.",	//placeholder 설정
			  toolbar: [
				    // [groupName, [list of button]]
				    ['fontname', ['fontname']],
				    ['fontsize', ['fontsize']],
				    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
				    ['color', ['forecolor','color']],
				    ['table', ['table']],
				    ['para', ['ul', 'ol', 'paragraph']],
				    ['height', ['height']],
				    ['insert',['picture','link','video']],
				    ['view', ['fullscreen', 'help']]
				  ],
				fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
				fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']
        });

    });
  </script>
</head>

<body>
<div class="wrap">
				<jsp:include page="../menu.jsp"></jsp:include>
				<div>
<div class="container">
		<h2>공지게시판 글쓰기</h2>
	
	<div class="content">
							<div class="col">
			<div class="contents_box">	
	<form action="${pageContext.request.contextPath}/writeView.manager" method="post" enctype="multipart/form-data" >
					<div class="title">	
					<select name="branch"> 
					<option value="all">전체</option>
					<option value="J">종로</option>
					<option value="K">강남</option>
					<option value="D">당산</option>
					</select>
					<select name="KhClass">
					<option value="전체">전체</option> 
					<option value="A">A</option>
					<option value="B">B</option>
					<option value="C">C</option>
					</select>			
					<h3>
						<input type="text" name="title" id="" placeholder="제목을 입력하세요">
					</h3>
					
				</div>
				<div class="contents">
					  <textarea id="summernote" name="contents"></textarea>
				</div>
				<hr>
				<!-- 파일 첨부 -->
			<fieldset id="file_box">
					<legend>파일첨부
					<input type ="file" name="file" multiple= "multiple" class="btn btn-dark">
					<input type ="file" name="file" class="btn btn-dark">
					</legend> 
					</fieldset>
					
					<div class="btn_wrap text-right">

					<input type="submit" class="btn btn-primary" value="등록하기">
		
					<input type=button class="btn btn-dark" value="목록으로" id="main">
				</div>
		</form>
			</div>
		</div>
		</div>
</div>
	</div>
 </div>
</body>
</html>