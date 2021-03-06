<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous">
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/manager/css/manager.css">
<style>
.containers {
	text-align: center;
}

.mini>a {
	float: right;
	padding-bottom: 20px;
}

.click2edit {
	margin-top: 30px;
	margin-bottom: 30px;
}
</style>
</head>
<body>
	
			<div class="wrap">
				<jsp:include page="../menu.jsp"></jsp:include>
				<div>
					<div class="container">
					
						<div class="content">
							<div class="col">
								<div class="mini">
									<h4>게시판</h4>
									<a
										href="${pageContext.request.contextPath}/boardList.manager?branch=all&currentPage=1&category=&search=">
										더 보기</a>

									<table class="table">
										<thead class="thead-dark">
											<tr>

												<th scope="col">지점</th>
												<th scope="col">제목</th>
												<th scope="col">작성자</th>
												<th scope="col">작성일</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="i" items="${list}" varStatus="s">
												<tr>

													<td>${i.branch }</td>
													<td><a href="${pageContext.request.contextPath}/freeBoardDetail.manager?branch=all&currentPage=1&category=&search=&seq=${i.seq}">${i.title }</a></td>
													<td>${i.writer}</td>
													<td><fmt:formatDate pattern="yyyy-MM-dd" value="${i.write_date}"/></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							</div>
							<div class="content">
							<div class="col">
								<div class="mini">
									<h4>신고 목록</h4>
									<a
										href="${pageContext.request.contextPath}/boardPolice.manager?currentPage=1&category=&search=">더
										보기</a>
									<table class="table">
										<thead class="thead-dark">
											<tr>

												<th scope="col">신고자</th>
												<th scope="col">신고 내용</th>
												<th scope="col">본 글</th>
												<th scope="col">신고일</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="p" items="${plist}" varStatus="s">
												<tr>

													<td>${p.id }</td>
													<td><a href="${pageContext.request.contextPath}/freeBoardDetail.manager?branch=&currentPage=1&category=&search=&seq=${p.parent}">${p.contents}</a></td>
													<td>${p.parent}</td>
													<td>${p.reg_date}</td>
												</tr>
											</c:forEach>


										</tbody>
									</table>

								</div>
							</div>
						</div>
							<div class="content">
							<div class="col">
								<div class="mini">
									<h4>문의 목록</h4>
									<a
										href="${pageContext.request.contextPath}/inquireList.manager?currentPage=1&category=&search=">더
										보기</a>
									<table class="table">
										<thead class="thead-dark">
											<tr>

												<th scope="col">문의 ID</th>
												<th scope="col">대분류</th>
												<th scope="col">소분류</th>
												<th scope="col">문의날짜</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="m" items="${ilist}" varStatus="s">
												<tr>

													<td><a href="${pageContext.request.contextPath}/inquireDetail.manager?seq=${m.seq}&currentPage=1&category=&search=">${m.id}</a></td>
													<td>${m.major_category}</td>
													<td>${m.sub_category}</td>
													<td>${m.reg_date}</td>
												</tr>
											</c:forEach>


										</tbody>
									</table>

								</div>
							</div>
						</div>
					</div>


				</div>
		</div>

		
	<script>
		$(function() {
			$("#back").on("click", function() {
				location.href = "${pageContext.request.contextPath}/manager/login.jsp";
			})
		})
		
	</script>
</body>
</html>