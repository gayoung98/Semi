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

body {
	background-color: #D8E3E7;
}

.container {
	margin-top: 80px;
}

.header {
	margin: 3%;
}

.search {
	margin: 3%;
}

.columns {
	margin: 2%;
}

.list {
	margin: 1%;
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
		
		
		$(document).on('click', '#navbarDropdownMenuLink', function() {
			   if($(this).siblings($(".dropdown-menu")).css("display") == "none"){
			 	  $(this).siblings($(".dropdown-menu")).css("display",'block')
			 	  for(let i =0; i<$(".dropdown-menu").length; i++){
			 		 if(($($(".dropdown-menu")[i]).text() !== $(this).siblings($(".dropdown-menu")).text())){
			 			$($(".dropdown-menu")[i]).css("display","none");
			 		}
			 	  }
			   }else{
			 	  $(this).siblings($(".dropdown-menu")).css("display",'none')
			   }
			})				
		
})
		

	
	
</script>
</head>
<body>


	<jsp:include page="/navibar.jsp" />
	<div class="container p-4 shadow bg-white rounded">
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
			<div class="col-5">제목</div>
			<div class="col-1">작성자</div>
			<div class="col-3">작성일</div>
			<div class="col-1">조회</div>
			<div class="col-1"></div>
		</div>

		<c:forEach var="item" items="${assList}">
			<div class="row list" style="overflow: hidden">
				<div class="col-1 seq">${item.seq }</div>
				<div class="col-5">
					<a href="view.ass?ass_seq=${item.seq}"><b>${item.title }</b></a>
				</div>
				<div class="col-1">${item.name }</div>
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

			<div class="col-3" style="" text-align:right">
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