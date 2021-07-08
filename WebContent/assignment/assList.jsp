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
}

div {
	border: 1px solid black;
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
	$(function(){
		 $("#write").on("click",function(){
			 location.href="assignment/assWrite.jsp";
		 })
		 $(".del").on("click", function(){
			 let delSeq = $(this).parent().siblings(".seq").text();
			 location.href="delete.ass?delSeq="+delSeq;
		 })
	})
</script>
</head>
<body>


	<jsp:include page="/navibar.jsp" />
	<div class="container">
		<div class="row header" style="text-align: center;">
			<h2>과제</h2>
		</div>

		<div class="row search" style="text-align: center;">

			<form action="list.ass">
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
			<div class="col-5">title</div>
			<div class="col-2">writer</div>
			<div class="col-3">write_date</div>
			<div clss="col-1"></div>
		</div>

		<c:forEach var="item" items="${assList}">
			<div class="row list" style="overflow: hidden">
				<div class="col-1 seq">${item.seq }</div>
				<div class="col-5">
					<a href="view.ass?ass_seq=${item.seq}">${item.title }</a>
				</div>
				<div class="col-2">${item.writer }</div>
				<div class="col-3">${item.write_date }</div>
				<div class="col-1">
					<c:choose>
						<c:when test="${item.id==loginId}">
							<button class=del seq="${item.seq }">x</button>
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

			<div class="col-3">
				<c:choose>
					<c:when test="${loginPosition=='teacher'}">
						<button id="write">글쓰기</button>
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>


</body>
</html>