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
.select {
	width: 10%;
}

.contents {
	width: 30vh;
	height: 30vh;
	margin-top: 50px;
	margin-left: 30px;
}

.row {
	margin-bottom: 20px;
}
.recomment{margin-top:30px}
textarea {
	width: 100%;
	height: 100px;
}

.answerbox {
	display: none;
}
#back{float: right; margin-right:30px;}
.submitbox{margin-top:30px;}
#modiform{display:none;}
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
						<div class="row">
							<div class="col-1">
								<b>번호</b>
							</div>
							<div class="col-2">
								<b>작성자</b>
							</div>
							<div class="col-3">
								<b>대분류</b>
							</div>
							<div class="col-3">
								<b>소분류</b>
							</div>
							<div class="col-3">
								<b>등록일</b>
							</div>
						</div>
						<c:forEach var="i" items="${list}" varStatus="s">
							<div class="row contentsinfo">
								<div class="col-1">${i.seq}</div>
								<div class="col-2">${i.id}</div>
								<div class="col-3">${i.major_category}</div>
								<div class="col-3">${i.sub_category}</div>
								<div class="col-3">${i.reg_date}</div>
							</div>
							<div class="row contents">

								<div class="col-12">${i.contents }</div>
							</div>
							<c:choose>
								<c:when test="${hasNotRecomment}">
									<div class="btn" id="btn">
										<button id="writebtn" type="button"
											class="btn btn-secondary btn-lg">답변 등록하기</button>
									</div>
										<form action="${pageContext.request.contextPath}/writeRecomment.manager" method="post">
									<div class="row answerbox" id="answerbox">
											<div class="col-12">
												<textarea placeholder="답변등록해주세요!" name="recomment" autofocus></textarea>
											</div>
											<input type="hidden" name="seq" value="${i.seq }">
											<input type="hidden" name="currentPage" value="${page}">
											<input type="hidden" name="category" value="${category}">
											<input type="hidden" name="search" value="${search}">
											<div class="col-12 submitbox">
												<div class="btn-group" role="group"
													aria-label="Basic example">
													<button  class="btn btn-secondary">답변등록</button>
													<button type="button" id="cancel" class="btn btn-secondary">취소하기</button>
													
												</div>
											</div>
									</div>
										</form>
								</c:when>
								<c:otherwise>
									<div class="row answer">
										<div class="col-12">
											<h3>답변</h3>
										</div>
										<div class="col-12 recomment">${i.recomment}</div>
									<div class="col-12 submitbox">
												<div class="btn-group" role="group"
													aria-label="Basic example">
													<button  type="button" id="modify" class="btn btn-secondary">수정하기</button>
													<button type="button" id="deletebtn" class="btn btn-secondary">삭제하기</button>
													
												</div>
											</div>
									</div>
									<form action="${pageContext.request.contextPath}/modifyRecomment.manager" method="post" id="modiform">
									<div class="row modibox" id="modibox">
											<div class="col-12">
												<textarea id="moditext" name="recomment" autofocus></textarea>
											</div>
											<input type="hidden" name="seq" value="${i.seq }">
											<input type="hidden" name="currentPage" value="${page}">
											<input type="hidden" name="category" value="${category}">
											<input type="hidden" name="search" value="${search}">
											<div class="col-12 submitbox">
												<div class="btn-group" role="group"
													aria-label="Basic example">
													<button  class="btn btn-secondary">수정하기</button>
													<button type="button" id="modicancel" class="btn btn-secondary">취소하기</button>
													
												</div>
											</div>
									</div>
										</form>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<div><button id="back" type="button" class="btn btn-warning">목록으로</button></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(function() {
			$("#writebtn").on("click", function() {
				$("#btn").css("display", "none");
				$("#answerbox").css("display", "block");
			})
			$("#cancel").on("click",function(){
				$("#btn").css("display","inline-block");
				$("#answerbox").css("display","none");
			})
			$("#back").on("click",function(){
				location.href="${pageContext.request.contextPath}/inquireList.manager?currentPage=${page}&category=${category}&search=${search}"
			})
			$("#deletebtn").on("click",function(){
				let delSeq = $(".contentsinfo .col-1").text();
				let check=confirm("삭제하시겠습니까?")
				if(check){
				let delLink ="${pageContext.request.contextPath}/inquireDelete.manager?currentPage=${page}&category=${category}&search=${search}&delSeq="+delSeq;
				location.href=delLink;
				}
			})
			$("#modify").on("click",function(){
				let prev = $(".recomment").text();
				$("#moditext").text(prev);
				$(".answer").css("display","none");
				$("#modiform").css("display","block");
			})
			$("#modicancel").on("click",function(){
				$("#modiform").css("display","none");
				$(".answer").css("display","inline-block");
			})	
		})
	</script>
</body>
</html>