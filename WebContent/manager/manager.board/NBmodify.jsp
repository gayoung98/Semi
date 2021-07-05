<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>freeBoard modify</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<style>
.container {
	max-width: 900px;
	margin-left: 10px;
	margin: 30px auto;
}

div {
	display: block;
}

h2 {
	margin-left: 20px;
	margin-bottom: 0px;
	display: block;
	margin-inline-end: 0px;
}

.title_area {
	border-bottom: 1px solid #ddd;
}

a {
	text-decoration: none;
	cursor: pointer;
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

.title_area h2 input {
	border: none;
}

.title_area h2 input:focus {
	outline: none;
}

/* 내용 */
.contents {
	padding: 10px;
	min-height: 350px;
}

.contents textarea {
	width: 100%;
	height: 90%;
}
/* 작성자 정보 */
	.writer{padding-left: 10px;
				padding-top:10px;
				padding-bottom:10px;
				}
.WriterInfo {
	margin-top: 10px;
}

.articleInfo {
	margin-top: 5px;
	margin-bottom: 5px;
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

.WriterInfo .article_info {
	font-size: 12px;
	line-height: 13px;
}

legend {
	border: 0;
}
</style>

<script>
	$(function() {

		$("#listBtn").on("click", function() {
			location.href = '/list.fboard?cpage=1';
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
							'20', '22', '24', '28', '30', '36', '50', '72' ]
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
</head>
<body>
	<div class="container">
		<!-- 게시물 제목 -->
		<form action="/modifyedit.manager" method="post"
			enctype="multipart/form-data">

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
						<span class="date">${view.writeDate}</span> <span class="count">조회
							${view.viewCount}</span> <input type="hidden" name="seq"
							value="${view.seq}">

					</div>
				</div>
				<hr>
				<!-- 게시글  내용 -->
				<div class="col-12 md-5 contents">
					<div class="contents">
						<textarea id="summernote" value="${view.contents}" name="contents"></textarea>
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
						[파일첨부] <input type="file" name="file" multiple="multiple"> <input
							type="file" name="file">
					</legend>
				</fieldset>
				<div class="btn_wrap text-right">
					<input type="submit" class="btn btn-primary" value="수정 완료">
					<input type=button class="btn btn-dark" value="목록으로" id="listBtn">

				</div>
			</div>
		</form>
	</div>

</body>
</html>