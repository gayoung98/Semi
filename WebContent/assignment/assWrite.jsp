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
	<jsp:include page="/navibar.jsp" />
	<form action="${pageContext.request.contextPath}/write.ass"
		method="post" enctype="multipart/form-data">
		<div class="container">
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
					<div id="editor">

						<p>Hello World!</p>

						<p>
							Some initial <strong>bold</strong> text
						</p>

						<p>
							<br>
						</p>

					</div>
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
					<button type="button">이전</button>
				</div>
				<div class="col-6" style="text-align: right;">
					<button type="submit">제출</button>
				</div>
			</div>
		</div>
		<input type=hidden id=seq>
	</form>

</body>
</html>