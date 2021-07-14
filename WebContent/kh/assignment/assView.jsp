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
	max-width: 1000px;
}

.row {
	margin: 1%;
}

.col {
	text-align: center;
}

.header {
	margin: 3%;
}

.title {
	padding-top: 1%;
	border-top: 1px solid black;
}

.titleContent {
	padding-bottom: 0.5%;
	border-bottom: 1px solid grey;
}

.files {
	padding: 1%;
}

.content {
	padding: 3%;
	border-top: 1px solid grey;
	border-bottom: 1px solid grey;
}

.comment {
	padding: 1%;

	margin: 1%;
}

.comments {
	padding: 0.5%;
}

.buttons {
	border-top: 1px solid grey;
	border-bottom: 1px solid grey;
	padding: 1%;
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
</style>

</head>
<body>
	<jsp:include page="/navibar.jsp" />
	<div class="container p-4 shadow bg-white rounded">

		<div class="row header">
			<div class="col-12" style="text-align: center;">
				<h2>과제</h2>
			</div>
		</div>
		<div class="row title" style="padding-left: 5%; padding-right: 5%;">

			<div class="col-3">
				<b>작성자</b>
			</div>
			<div class="col-5">
				<b>제목</b>
			</div>
			<div class="col-3">
				<b>등록일</b>
			</div>
			<div class="col-1">
				<b>조회</b>
			</div>
		</div>
		<div class="row titleContent"
			style="padding-left: 5%; padding-right: 5%;">
			<div class="col-3">${assView.name}</div>
			<div class="col-5">${assView.title}</div>
			<div class="col-3">${assView.write_date}</div>
			<div class="col-1">${assView.viewCount }</div>
		</div>

		<div class="row files">
			<div class="col-12">
				<a
					href="download.ass?writer=${assView.writer}&sysName=${assFiles.sysName}&oriName=${assFiles.oriName}">
					${assFiles.oriName } </a>
			</div>
		</div>

		<div class="row content">
			<div class="col-12" style="padding: 20px; text-align: center;">${assView.contents}
			</div>
		</div>

		<form action="write.assSubmit" method="post"
			enctype="multipart/form-data">
			<div class="row comment">
				<div class="col-3">과제 제출</div>
				<div class="col-7" style="padding: 0px;">
					<input type="file" name="assSubmit">
				</div>

				<div class="col-2">
					<button type="submit" class="btn btn-info">업로드</button>
				</div>
				<input type="hidden" name="parent" value="${assView.seq}">
			</div>
		</form>

		<c:forEach var="item" items="${assSubmit}">

			<div class="row comments">
				<div class="col-3">${item.name}</div>
				<div class="col-7">
					<a
						href="download.assSubmit?writer=${item.writer}&sysName=${item.sysName}&oriName=${item.oriName}">${item.oriName}</a>
				</div>
				<div class="col-1">
					<c:choose>
						<c:when test="${item.id==loginId}">
							<form id="replyFrm"
								action="${pageContext.request.contextPath}/delete.assSubmit"
								method="post" id="delForm">

								<button id="delete" type="submit" class="btn btn-dark">X</button>
								<input id="assSubmitseq" name="assSubmitSeq" type="hidden"
									value="${item.seq }"> <input id="assSubmitParent"
									name="assSubmitParent" type="hidden" value="${item.parent }">
							</form>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</div>
			</div>



		</c:forEach>


		<div class="row buttons">
			<div class="col-6" style="text-align: left;">
				<button type="button" id="back" class="btn btn-secondary">back</button>
			</div>
			<div class="col-6" style="text-align: right;">
				<c:choose>
					<c:when test="${assView.id==loginId}">
						<form
							action="${pageContext.request.contextPath}/modiForm.ass?ass_seq=${assView.seq}"
							method=post style="text-align: right;">
							<button type="submit" class="btn btn-primary">Modify</button>
						</form>
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	
	<script>
	$("#back").on("click", function(){
		location.href="${pageContext.request.contextPath}/list.ass?currentPage=1";
	})
	$("#delete").on("click", function(){
		let check = confirm("정말 삭제하시겠습니까?");
		if (check) {
			$("#delForm").submit();
		}
	})
	</script>

</body>
</html>