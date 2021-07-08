<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<style>
* {
	box-sizing: border-box;
	text-align: center;
}

div {
	border: 1px solid black;
}

#title {
	margin-top: 3%;
	width: 100%;
	height: 100%;
}

#content {
	width: 100%;
	height: 90%;
	margin-top: 4%;
	text-align: left;
}

.buttons {
	padding-bottom: 15px;
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
	<div class="container">

		<div class="row header">
			<div class="col-12">
				<h3>과제</h3>
			</div>
		</div>

		<div class="row title">
			<div class="col-3">${assView.writer}</div>
			<div class="col-5">${assView.title}</div>
			<div class="col-3">${assView.write_date}</div>
			<div class="col-1">${assView.viewCount }</div>
		</div>

		<div class="row files">
			<div class="col-12">
				<a
					href="download.ass?seq=${assFiles.seq}&sysName=${assFiles.sysName}&oriName=${assFiles.oriName}">
					${assFiles.oriName } </a>
			</div>
		</div>

		<div class="row content">
			<div class="col-12" style="padding: 20px;">${assView.contents}
			</div>
		</div>

		<form action="write.assSubmit" method="post"
			enctype="multipart/form-data">
			<div class="row comment" style="padding: 0px;">
				<div class="col-3">과제 제출</div>
				<div class="col-7" style="padding: 0px;">
					<input type="file" name="assSubmit">
				</div>

				<div class="col-2">
					<button type="submit">업로드</button>
				</div>
				<input type="hidden" name="parent" value="${assView.seq}">
			</div>
		</form>

		<c:forEach var="item" items="${assSubmit}">
			<form id="replyFrm"
				action="${pageContext.request.contextPath}/delete.assSubmit"
				method="post">
				<div class="row comments">
					<div class="col-3">${item.writer}</div>
					<div class="col-7">
						<a
							href="download.assSubmit?seq=${item.seq}&sysName=${item.sysName}&oriName=${item.oriName}">${item.oriName}</a>
					</div>
					<div class="col-1">
						<button id="delete" type="submit">삭제</button>
					</div>
				</div>
				<input id="assSubmitseq" name="assSubmitSeq" type="hidden"
					value="${item.seq }"> <input id="assSubmitParent"
					name="assSubmitParent" type="hidden" value="${item.parent }">

			</form>
		</c:forEach>


		<div class="row buttons">
			<div class="col-6" style="text-align: left;">
				<button type="button">back</button>
			</div>
			<div class="col-6" style="text-align: right;">
				<c:choose>
					<c:when test="${assView.id==loginId}">
						<form
							action="${pageContext.request.contextPath}/modiForm.ass?ass_seq=${assView.seq}"
							method=post>
							<button type="submit">Modify</button>
						</form>
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
			</div>
		</div>


	</div>

</body>
</html>