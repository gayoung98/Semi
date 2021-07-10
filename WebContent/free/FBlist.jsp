<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FreeBoard List</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<style>
html{}
	body {background-color: #D8E3E7;min-height:900px;}

/*navibar*/
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
/*전체*/
.container {
	margin-top: 50px;
	width: 900px;

}


.first {
	border-bottom: 3px solid black;
	margin-left:10px;
	margin-right:10px;
	text-align:center;
}

.second {
	border-bottom: 1px solid rgba(0, 0, 0, 0.192);
	margin-left:10px;
	margin-right:10px;
	text-align:center;
}

h2 {	
	margin-left: 20px;
	font-weight: bold;
	padding-top:10px;
	padding-bottom: 10px;
}

.branch_list {
	padding-top: 10px;
	padding-bottom: 40px;
}

li a:hover {
	color: cadetblue;
}

a {
	color: black;
	font-weight: bold;
}

.title {
	text-align: center;
}

.writeBtn {
	padding-left:10px;	
}

.search {
	margin-left:20px;
	float:left;
}

.footer {
	padding-top: 10px;
	padding-bottom: 30px;
}
</style>

<script>
$(function(){
$(".writeBtn").on("click",function(){
location.href="${pageContext.request.contextPath}/towrite.fboard"
});

$("#back").on("click",function(){
	location.href="${pageContext.request.contextPath}/index.jsp"
	});


});
</script>
</head>
<body>
	<jsp:include page="/navibar.jsp"></jsp:include>

	<div class="container shadow bg-white rounded">
		<h2 class="text-center">자유게시판</h2>
		<ul class="nav justify-content-center branch_list">
			<li class="nav-item"><a class="nav-link active"
				aria-current="page"
				href="${pageContext.request.contextPath}/list.fboard?cpage=1&category=&keyword=">전체</a>
			</li>
			<li class="nav-item"><a class="nav-link"
				href="${pageContext.request.contextPath}/list.fboard?branch=J&cpage=1&category=&keyword=">종로</a>
			</li>
			<li class="nav-item"><a class="nav-link"
				href="${pageContext.request.contextPath}/list.fboard?branch=K&cpage=1&category=&keyword=">강남</a>
			</li>
			<li class="nav-item"><a class="nav-link "
				href="${pageContext.request.contextPath}/list.fboard?branch=D&cpage=1&category=&keyword=">당산</a>
			</li>
		</ul>


		<div class="row first">
			<div class="col-12 col-md-1 d-none d-md-block">No</div>
			<div class="col-12 col-md-1" >지점</div>
			<div class="col-12 col-md-4 title">제목</div>
			<div class="col-12 col-md-2 d-none d-md-block">작성자</div>
			<div class="col-12 col-md-2 d-none d-md-block">작성일자</div>
			<div class="col-12 col-md-2 d-none d-md-block">조회수</div>
		</div>

		<c:forEach var="i" items="${boardlist}">
			<div class="row second">

				<div class="col-1 col-md-1 d-none d-md-block">${i.seq}</div>
				<div class="col-12 col-md-1">${i.branch}</div>
				<div class="col-12 col-md-4 title">
					<a href="detailView.fboard?seq=${i.seq}">${i.title}</a>
					[${count.replyCount(i.seq)}]
				</div>
				<div class="col-3 col-md-2  d-md-block">${i.name}</div>
				<div class="col-2 col-md-2  d-md-block">${i.write_date}</div>
				<div class="col-1 col-md-2  d-md-block">${i.viewCount}</div>
			</div>
		</c:forEach>


		<!--페이징 네비바-->
		<div class="nav justify-content-center">	
				<c:forEach var="i" items="${navi}" varStatus="s">
					<c:choose>
						<c:when test="${i=='>'}">
							<a href="${pageContext.request.contextPath}/list.fboard?cpage=${navi[s.index-1]+1}&category=${category}&keyword=${keyword}&branch=${branch}">${i}</a>
							<!--s.index 10번인테 -1한 (배열 9번)요소: index 10+1=11번째!(배열로 10번요소) -->
						</c:when>
						<c:when test="${i=='<'}">
							<a href="${pageContext.request.contextPath}/list.fboard?cpage=${navi[s.index+1]-1}&category=${category}&keyword=${keyword}&branch=${branch}">${i}</a>
							<!--s.index 10번인테 +1한 (배열 10번)요소;index 11-1= 10번째(배열의 9번요소)! -->
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath}/list.fboard?cpage=${i}&category=${category}&keyword=${keyword}&branch=${branch}">${i}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
		</div>
		<div class="text-right footer">
			<button type="button" name="write" class="btn btn-primary writeBtn">글쓰기</button>
			<input type=button class="btn btn-secondary" value="메인으로" id="back">
		
		<div class="search">
			<form action="${pageContext.request.contextPath}/list.fboard"
				method="get">

				<c:choose>
					<c:when test="${not empty branch}">
						<select name="category">
							<option value="title">제목</option>
							<option value="contents">내용</option>
						</select>
						<input type="hidden" name="cpage" value="1">
						<input type="hidden" name="branch" value="${branch}">
						<input type="text" placeholder="검색어를 입력하세요" name="keyword"
							required value="${keyword}">
						<input type="submit" value="찾기" class="btn btn-dark"
							id="searchBtn">

					</c:when>
					<c:otherwise>
						<select name="category">
							<option value="title">제목</option>
							<option value="contents">내용</option>
						</select>
						<input type="hidden" name="cpage" value="1">
						<input type="text" placeholder="검색어를 입력하세요" name="keyword"
							required value="${keyword}">
						<input type="submit" value="찾기" class="btn btn-dark "
							id="searchBtn">
							
					</c:otherwise>
				</c:choose>
			</form>
		</div>
		</div>
	</div>
</body>
</html>