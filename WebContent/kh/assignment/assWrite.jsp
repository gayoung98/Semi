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
	height: 100%;
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


</head>
<body>
	<jsp:include page="/header.jsp" />
	<jsp:include page="/navibar.jsp" />
	<div class="container p-4 shadow bg-white rounded">
		<form action="${pageContext.request.contextPath}/write.ass"
			method="post" enctype="multipart/form-data" id="writeForm">

			<div class="row header">
				<div class="col-12">
					<h3>과제</h3>
				</div>

			</div>
			<div class="row title">
				<div class="col-12 col-md-8">
					<input type="text" name="title" id="title" placeholder="제목을 입력하세요.">
				</div>
				<div class="col-12 col-md-4" style="margin-top: 3%;">
					<input type="file" name="file">
				</div>
			</div>

			<div class="row content" style="padding: 0px;">
				<div class="col-12">
					<div id="editor"></div>
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
					<button type="submit" id="submit" class="btn btn-primary">제출</button>
				</div>
			</div>

			<input type=hidden id=seq>
		</form>
	</div>
	<script>
		$("#back")
				.on(
						"click",
						function() {
							location.href = "${pageContext.request.contextPath}/list.ass?currentPage=1";
						})
		$("#submit").on("click", function() {
			if ($("#title").val() == "") {
				alert("제목을 입력하세요.");
				$("#title").focus();
				return false;
			} else if ($("#contents").val() == "") {
				alert("내용을 입력하세요.");
				$(".ql-editor").focus();
				return false;
			} else {
				let check = confirm("과제를 정말 제출하시겠습니까?");
				if (check) {
					$("#writeForm").submit();
				}else{
					return false;
				}
			}
		})
	</script>
</body>
</html>