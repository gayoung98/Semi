<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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


</head>
<body>
<div class="wrap">
				<jsp:include page="../menu.jsp"></jsp:include>
				<div>
					<header>
					<div>
						<h1>수정하기</h1>
					</div>
					</header>
	<div class="container">
		<!-- 게시물 제목 -->
		<div class="content">
					<div class="col">
		<form action="${pageContext.request.contextPath}/noticeModifyView.manager" method="post"
			enctype="multipart/form-data">
		<input type="hidden"  name="currentPage" value="${page }">
		<input type="hidden"  name="branch" value="${branch}">
		<input type="hidden"  name="search" value="${search}">
		<input type="hidden"  name="category" value="${category}">
			<div class="contents_box">
				<div class="col-12 title_area">

					<h2>
						<input type="text" name="title" size="50" value="${view.title}">
					</h2>

				</div>
				<!-- 작성자 정보 -->
				<div class="writerInfo">
					<div class="profile_info">
						
						<div class="name_box">
							<h3 class="writer"> ${view.writer} </h3> 
						</div>
					</div>
					<!-- 작성일자,조회수 -->
					<div class="articleInfo">
						<span class="date">${view.write_date}</span> <span class="count">조회
							${view.viewCount}</span> <input type="hidden" name="seq"
							value="${view.seq}">

					</div>
				</div>
				<hr>
				<!-- 게시글  내용 -->
				<div class="col-12 md-5 contents">
					<div class="contents">
						<textarea id="summernote"  name="contents">${view.contents}</textarea>
					</div>
				</div>
				<hr>
				<!--첨부 파일리스트 삭제  -->
				<div id="content">
					<fieldset class="file_box">
						<legend>[첨부 파일 리스트]</legend>
						<c:forEach var="file" items="${filelist}">
							<!--첨부파일 다운로드-->
							<div>${file.oriName}<button type="button"
									class="btn btn-dark delAttach" seq="${file.seq}">삭제</button>
							</div>
						</c:forEach>
					</fieldset>
				</div>
				<hr>
				<!-- 파일 첨부 -->
				<fieldset id="file_box">
					<legend>
						[파일첨부] <input type="file" name="file" multiple="multiple" class="btn btn-dark"> <input
							type="file" name="file" class="btn btn-dark">
					</legend>
				</fieldset>
				<div class="btn_wrap text-right">
					<input type="submit" class="btn btn-primary" value="수정 완료">
					<input type=button class="btn btn-dark" value="취소" id="listBtn">

				</div>
			</div>
		</form>
	</div>
	</div>
	</div>
</div>
</div>
<script>
	$(function() {

		$("#listBtn").on("click", function() {
			location.href = "${pageContext.request.contextPath}/detailView.manager?currentPage=${page}&branch=${branch}&category=${category}&search=${search}&seq=${view.seq}";
		});

		$("#summernote").summernote(
				{
					height : 300, // 에디터 높이
					focus : true, // 에디터 로딩후 포커스를 맞출지 여부
					lang : "ko-KR", // 한글 설정
					placeholder : "내용을 입력하세요.", //placeholder 설정
					toolbar : [
							// [groupName, [list of button]]
							[ 'fontname', [ 'fontname' ] ],
							[ 'fontsize', [ 'fontsize' ] ],
							[
									'style',
									[ 'bold', 'italic', 'underline',
											'strikethrough', 'clear' ] ],
							[ 'color', [ 'forecolor', 'color' ] ],
							[ 'table', [ 'table' ] ],
							[ 'para', [ 'ul', 'ol', 'paragraph' ] ],
							[ 'height', [ 'height' ] ],
							[ 'insert', [ 'picture', 'link', 'video' ] ],
							[ 'view', [ 'fullscreen', 'help' ] ] ],
					fontNames : [ 'Arial', 'Arial Black', 'Comic Sans MS',
							'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋움체',
							'바탕체' ],
					fontSizes : [ '8', '9', '10', '11', '12', '14', '16', '18',
							'20', '22', '24', '28', '30', '36', '50', '72' ],
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

		$(".delAttach").on("click", function() {
			let seq = ($(this).attr("seq"));
			$(this).parent().remove();
			let del = $("<input>");
			del.attr("type", "hidden");
			del.attr("name", "delete");
			del.attr("value", seq);
			$(".contents_box").after(del);

		});

	});
</script>
</body>
</html>