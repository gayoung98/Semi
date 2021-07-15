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
	padding: 5%;
	max-width: 1000px;
}

.row{
	text-align: center;
}

.header {
	margin: 3%;
}

.search {
	margin: 3%;

}

.columns {
	padding: 1%;
	border-bottom: 3px solid black;
	margin-left:10px;
	margin-right:10px;
}

.list {
	margin: 1%;
	border-bottom: 1px solid black;
	padding-bottom: 1%;
}

.footer {
	margin: 2%;
}

a{
	text-decoration: none;
	color: black;
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
<script>
	$(function() {
		$("#write").on("click", function() {
			location.href = "${pageContext.request.contextPath}/writeForm.ass";
		})
		$(".del").on("click", function() {
			let check = confirm("정말 삭제하시겠습니까?");
			if(check){
				let delSeq = $(this).parent().siblings(".seq").text();
				location.href = "delete.ass?delSeq=" + delSeq;
			}else{
				return false;
			}
			
		})
	
		
})
		

	
	
</script>
</head>
<body>

	<jsp:include page="/header.jsp" />
	<jsp:include page="/navibar.jsp" />
	<div class="container p-4 shadow bg-white rounded">
		<div class="row header">
			<h2 style="width: 100%;"><b>과제</b></h2>
		</div>

		<div class="row search" style="text-align: center;">

			<form action="list.ass" style="width:100%; text-align: center;">
				<input type="hidden" name="currentPage" value="${currentPage}">
				<select name="category">
					<option value="title">제목</option>
					<option value="contents">내용</option>
				</select> <input type="text" name="keyword" placeholder="검색어를 입력하세요">
				<button type="submit" id="search">search</button>
			</form>
		</div>

		<div class="row columns">
			<div class="col-1"></div>
			<div class="col-4">제목</div>
			<div class="col-2">작성자</div>
			<div class="col-3">작성일</div>
			<div class="col-1">조회</div>
			<div class="col-1"></div>
		</div>

		<c:forEach var="item" items="${assList}">
			<div class="row list" style="overflow: hidden">
				<div class="col-1 seq">${item.seq }</div>
				<div class="col-4">
					<a href="view.ass?ass_seq=${item.seq}"><b>${item.title }</b></a>
				</div>
				<div class="col-2">${item.name }</div>
				<div class="col-3">${item.write_date }</div>
				<div class="col-1">${item.viewCount }</div>
				<div class="col-1">
					<c:choose>
						<c:when test="${item.id==loginId}">
							<button seq="${item.seq }" class="btn btn-dark del">x</button>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</div>

			</div>
		</c:forEach>

		<div class="row footer">
			<div class="col-3"></div>

			<div class="col-6" style="text-align: center;">
				<nav>
					<c:forEach var="i" items="${pageNavi }" varStatus="s">
						<c:choose>
							<c:when test="${i == '<'}">
								<a
									href="${pageContext.request.contextPath}/list.ass?currentPage=${pageNavi[s.index+1]-1}&category=${category}&keyword=${keyword}">${i}</a>
							</c:when>
							<c:when test="${i == '>'}">
								<a
									href="${pageContext.request.contextPath}/list.ass?currentPage=${pageNavi[s.index-1]+1}&category=${category}&keyword=${keyword}">${i}</a>
							</c:when>
							<c:otherwise>
								<a
									href="${pageContext.request.contextPath}/list.ass?currentPage=${i}&category=${category}&keyword=${keyword}">${i }</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</nav>
			</div>

			<div class="col-3" style="text-align:right">
				<c:choose>
					<c:when test="${loginPosition=='teacher'}">
						<button class="btn btn-primary" id="write">글쓰기</button>
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>


</body>
</html>