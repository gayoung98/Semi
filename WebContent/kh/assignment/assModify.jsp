<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous">

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
	crossorigin="anonymous"></script>

<link href="https://cdn.quilljs.com/1.3.6/quill.snow.css"
	rel="stylesheet">
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
<style>
* {
	box-sizing: border-box;
	text-align: center;
}

body {
	background-color: #D8E3E7;
}

.container {
	margin-top: 80px;
}

.row {
	margin: 4%;
}

#title {
	width: 100%;
	height: 40px;
}

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
	height: 50px;
	top: 100%;
	background-color: #55555550;
}

.ql-editor {
	min-height: 40vh;
	min-width: 100%;
	overflow-y: auto;
}
</style>
<script>
	$(function() {
		
		//quill editor에 마우스를 올렸다가 다른 부분을 클릭했을 때 작동하는
		//on blur를 사용한 코드는 html 내용을 가져가는 방식은
		//modify 영역에서는 문제가 있음.
		
		//제목만 바꾸거나, 파일만 바꾸고 싶다든지 할 때에는 애초에 editor에 마우스를 클릭하지 않았을 수 있음.
		//그렇게 되면 기존 내용이 담겨오는 게 아니라 그냥 담긴 내용이 없이 날라감.
		
		//이를 보완하기 위해 페이지 로딩 직후 기존에 넣어놨던 내용을 textarea에 넣어주는 코드를 넣어둠.
		//이걸 넣어주면, 마우스를 에디터에 넣었다가 나오지 않아도 기존 내용이 넘어가게끔 해결!
		
		var myEditor = document.querySelector('#editor');
		var html = myEditor.children[0].innerHTML;
		$("#contents").html(html);

		$("#fileDel").on("click", function() {
			let seq = ($(this).attr("seq"));

			$(this).parent().remove();
			let del = $("<input>");
			del.attr("type", "hidden");
			del.attr("name", "delete");
			del.attr("value", seq);
			$(".title").append(del);

			let newFile = $("<input>");
			newFile.attr("type", "file");
			newFile.attr("name", "file");
			let newFileBox = $("<div>");
			newFileBox.attr("class", "col-12");
			newFileBox.attr("style", "text-align: center");
			newFileBox.append(newFile);
			$(".files").append(newFileBox);

		});

	})
</script>

</head>
<body>
	<jsp:include page="/header.jsp" />
	<jsp:include page="/navibar.jsp" />
	<div class="container p-4 shadow bg-white rounded">
		<form
			action="${pageContext.request.contextPath}/modiProc.ass?ass_seq=${assView.seq}"
			method="post" enctype="multipart/form-data">

			<div class="row header">
				<div class="col-12">
					<h2>과제</h2>
				</div>

			</div>
			<div class="row title">
				<div class="col-2"></div>
				<div class="col-1" style="line-height: 40px;">
					<b>제목</b>
				</div>
				<div class="col-7">
					<input type="text" name="title" id="title" value="${assView.title}">
				</div>
				<div class="col-2"></div>
			</div>

			<div class="row files">
				<div class="col-12" id="fileBox">
					<c:choose>
						<c:when test="${assFiles.oriName!=null}">
							${assFiles.oriName}
							<button type="button" id="fileDel" seq="${assFiles.seq}">삭제</button>
						</c:when>
						<c:otherwise>
							<input type="file" name="file"></input>
						</c:otherwise>
					</c:choose>
				</div>
			</div>


			<div class="row content" style="padding: 0px;">
				<div class="col-12">
					<div id="editor">${assView.contents}</div>
					<textarea id="contents" name="contents" style="display: none"></textarea>

					<!-- Include the Quill library -->
					<script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>

					<!-- Initialize Quill editor -->
					<script>
						var quill = new Quill('#editor', {
							theme : 'snow'
						});

						// 폼(input)에 자동저장

						$(".ql-editor").on('blur', function() {

							var myEditor = document.querySelector('#editor');
							var html = myEditor.children[0].innerHTML;
							$("#contents").html(html);

						});
					</script>
				</div>

			</div>


			<div class="row buttons" style="margin-top: 58px;">
				<div class="col-6" style="text-align: left;">
					<button type="button" id="back" class="btn btn-secondary">back</button>
				</div>
				<div class="col-6" style="text-align: right;">
					<button id="submit" type="submit" class="btn btn-primary">제출</button>
				</div>
			</div>


		</form>
	</div>
	<script>
		$("#back").on("click", function() {
			location.href = "view.ass?ass_seq=" + ${assView.seq};
		})
	</script>
</body>
</html>