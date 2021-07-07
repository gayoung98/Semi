<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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
	<c:choose>
		<c:when test="${login != null }">
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
													<td>${i.title }</td>
													<td>${i.writer }</td>
													<td>${i.write_date}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
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
													<td>${p.contents}</td>
													<td>${p.parent}</td>
													<td>${p.reg_date}</td>
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

		</c:when>
		<c:otherwise>
			<div class="containers">
				<h2>세션이 만료되었습니다. 다시 로그인 해주시길 바랍니다.</h2>
				<button class="btn btn-secondary btn-lg" id="back">로그인 하러가기</button>
			</div>
		</c:otherwise>
	</c:choose>
	<script>
		$(function() {
			$("#back").on("click", function() {
				location.href = "../login.jsp";
			})
		})
		
	</script>
</body>
</html>