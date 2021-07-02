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
<link rel="stylesheet" href="${pageContext.request.contextPath}/manager/css/manager.css">
<style>
.select {
	width: 10%;
}
</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="../menu.jsp"></jsp:include>
		<div>
			<div class="container">
				<header>
					<div>
						<h1>문의하기</h1>
					</div>
					
				</header>
				<div class="content">
					<div class="col">
						<table class="table">
							<thead class="thead-dark">
								<tr>
									<th scope="col">번호</th>
									<th scope="col">작성자</th>
									<th scope="col">대분류</th>
									<th scope="col">소분류</th>
									<th scope="col">신고일</th>
									<th scope="col">답변완료</th>
								
								</tr>
							</thead>
							<tbody>
								<c:forEach var="i" items="${list}" varStatus="s">
								<tr>
									<th scope="row">${i.seq}</th>
									<td>${i.id}</td>
									<td>${i.major_category}</td>
									<td>${i.sub_category}</td>
									<td>${i.reg_date}</td>
									<td id="isAnswer"></td>
									
								</tr>
									</c:forEach>
							</tbody>
							<tfoot>
							<tr>
							<td colspan ="6">
							<c:forEach var="i" items="${navi}" varStatus="s">
					<c:choose>
						<c:when test="${i=='>'}">
							<a
								href="${pageContext.request.contextPath}/inquireList.manager?currentPage=${navi[s.index-1]+1}&category=${category}&search=${search}">${i}</a>
						</c:when>
						<c:when test="${i=='<'}">
							<a
								href="${pageContext.request.contextPath}/inquireList.manager?currentPage=${navi[s.index+1]-1}&category=${category}&search=${search}">${i}</a>
						</c:when>
						<c:otherwise>
							<a
								href="${pageContext.request.contextPath}/inquireList.manager?currentPage=${i}&category=${category}&search=${search}">${i}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
							</td>
							</tr>
							</tfoot>
						</table>
						<div class="search">
							<form action="${pageContext.request.contextPath}/inquireList.manager" method="get" >
								<div class="input-group">
									<div class="select">
										<select class="custom-select" id="inputGroupSelect04" name="category"
											aria-label="Example select with button addon">
											<option selected value="">전체</option>
											
											<option value="id">id</option>
											<option value="major_category">대분류</option>
											<option value="sub_category">소분류</option>
												<option value="contents">내용</option>
										</select>
									</div>
									<input type="hidden" name = "currentPage" value="1">
									
									<input type="text" class="form-control" name="search" 
										aria-label="Text input with segmented dropdown button" value="${search}">
									<div class="input-group-append">

										<button class="btn btn-outline-secondary" >검색</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>