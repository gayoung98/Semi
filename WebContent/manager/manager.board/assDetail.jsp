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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/manager/css/manager.css">
<style>
.btn-group{float:right;}

</style>

</head>
<body>
	<div class="wrap">
		<jsp:include page="../menu.jsp"></jsp:include>
		<div>
			<div class="container">
				<header>
					<div>
						<h1>과제 게시판</h1>
					</div>
				</header>
				<div class="content">
					<div class="col">
					<div class="row">
						
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
								<b>조회수</b>
							</div>
						</div>
						<div class="row">
						
						</div>
						<div class="row">
						
						<div class="col-3">${assView.writer}</div>
			<div class="col-5">${assView.title}</div>
			<div class="col-3">${assView.write_date}</div>
			<div class="col-1">${assView.viewCount }</div>
						</div>
						<div class="col-12">
				<a
					href="download.ass?seq=${assFiles.seq}&sysName=${assFiles.sysName}&oriName=${assFiles.oriName}">${assFiles.oriName }</a>
			</div>
			<div class="row content">
			<div class="col-12" style="padding: 20px;">${assView.contents}
			</div>
		</div>
		<c:forEach var="item" items="${assSubmit}">
		<div class="row comments">
					<div class="col-3">${item.writer}</div>
					<div class="col-7">
						<a
							href="download.assSubmit?seq=${item.seq}&sysName=${item.sysName}&oriName=${item.oriName}">${item.oriName}</a>
					</div>
					<div class="col-1">
						
					</div>
				</div>
				</c:forEach>
				<div class="row footer">
					<div class="col-12 submitbox">
												<div class="btn-group" role="group"
													aria-label="Basic example">
													<button  type="button" id="delete" class="btn btn-secondary">삭제하기</button>
													<button type="button" id="back" class="btn btn-secondary">목록으로</button>
													
												</div>
											</div>
				</div>
					</div>
				</div>

			</div>
		</div>
	</div>
<script>
$(function(){
	$("#delete").on("click",function(){
		var check =confirm("정말 삭세하시겠습니까?");
		if(check){
		location.href="${pageContext.request.contextPath}/assDelete.manager?currentPage=${page}&branch=${branch}&category=${category}&search=${search}&seq=${assView.seq}";
		}
		})
	$("#back").on("click",function(){
		location.href="${pageContext.request.contextPath}/assList.manager?currentPage=${page}&branch=${branch}&category=${category}&search=${search}";
	})
})
</script>
</body>
</html>