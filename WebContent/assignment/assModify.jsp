<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<!-- include libraries(jQuery, bootstrap) -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="https://cdn.quilljs.com/1.3.6/quill.snow.css"
	rel="stylesheet">


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

		$("#fileDel").on("click", function() {
			let seq = ($(this).attr("seq"));

			let inputFile = $("<input>");
			inputFile.attr("type", "file");
			inputFile.attr("name", "file");

			let inputBox = $("<div>");
			inputBox.attr("class", "col-12");
			inputBox.attr("style", "margin-top: 3%;");

			inputBox.append(inputFile);
			$("#files").append(inputBox);

			$(this).parent().remove();
			let del = $("<input>");
			del.attr("type", "hidden");
			del.attr("name", "delete");
			del.attr("value", seq);
			$(".title").append(del);

		});

	})
</script>

</head>
<body>
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
				<div class="col-1" style="line-height: 40px;"><b>제목</b></div>
				<div class="col-7">
					<input type="text" name="title" id="title" value="${assView.title}">
				</div>
				<div class="col-2"></div>
			</div>

			<div class="row files">
				<div class="col-12">
					${assFiles.oriName}

					<button type="button" id="fileDel" seq="${assFiles.seq}">삭제</button>
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
					<button type="button" class="btn btn-secondary">이전</button>
				</div>
				<div class="col-6" style="text-align: right;">
					<button type="submit" class="btn btn-primary">제출</button>
				</div>
			</div>
		
		
	</form>
	</div>

</body>
</html>