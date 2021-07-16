<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FreeBoard List</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"/>		    
		   
<style>

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
        	z-index:1;
            position: absolute;
            width: 100%;
            height: 50px;
            top: 100%;
            background-color: #00ff0000;
        }
/*전체*/
.container {
	margin-top: 100px;
	width: 900px;

}


.first {
	border-bottom: 3px solid black;
	margin-left:10px;
	margin-right:10px;
	text-align:center;
	font-weight: bold; 
	font-size: larger;
}

.second {
	border-bottom: 1px solid rgba(0, 0, 0, 0.192);
	margin-left:10px;
	margin-right:10px;
	text-align:center;
	font-size: large;
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
	font-weight: bold; 
	font-size: larger;
}


.pagingBar a{font-weight: bold; font-size: larger; 
			 }
li { 
  transition:all .3s ease; 
  color:#43c4a4;
  
}
 .container a{
	color: black;
	font-weight: bold;
} 
.branch_list li a:hover {
	color:#43c4a4;
}


.branch_list li a:after {
  display:block;
  content: '';
  border-bottom: solid 3px #39d9c8;  
  transform: scaleX(0);  
  transition: transform 250ms ease-in-out;
}
.branch_list li a:hover:after { transform: scaleX(1); }
.branch_list li a.fromRight:after{ transform-origin:100% 50%; }
.branch_list li a.fromLeft:after{  transform-origin:  0% 50%; }


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
	$("#writeBtn").on("click",function(){
	location.href="${pageContext.request.contextPath}/towrite.fboard"
	});
	
	$("#back").on("click",function(){
		location.href="${pageContext.request.contextPath}/main.main"
		});
/* 	$("#navbarDropdownMenuLink").on('click', function() {
		console.log("ek skrkwnj.");
	   if($(this).siblings($(".dropdown-menu")).css("display") === 'none'){
	 	  $(this).siblings($(".dropdown-menu")).css("display",'block')
	   }else{
	 	  $(this).siblings($(".dropdown-menu")).css("display",'none')
	   }
	})     */
	
	console.log($("#cpage").val());
	
	for(let i=0; i< $(".page-link").length; i++){
		if($($(".page-link")[i]).attr("id") === $("#cpage").val()){
			$($(".page-link")[i]).css("background-color","#dee2e6");
		}
	}
});

</script>
</head>
<body>
	<jsp:include page= "/header.jsp" />
	<jsp:include page="/navibar.jsp"/>
	<div class="container shadow bg-white rounded">
		<h2 class="text-center">자유게시판</h2>
		<ul class="nav justify-content-center branch_list">
			<li class="nav-item"><a class="nav-link active"aria-current="page"
				href="${pageContext.request.contextPath}/list.fboard?cpage=1&category=&keyword="><span>전체</span></a>
			</li>
			<li class="nav-item"><a class="nav-link"
				href="${pageContext.request.contextPath}/list.fboard?branch=J&cpage=1&category=&keyword="><span>종로</span></a>
			</li>
			<li class="nav-item"><a class="nav-link"
				href="${pageContext.request.contextPath}/list.fboard?branch=K&cpage=1&category=&keyword="><span>강남</span></a>
			</li>
			<li class="nav-item"><a class="nav-link "
				href="${pageContext.request.contextPath}/list.fboard?branch=D&cpage=1&category=&keyword="><span>당산</span></a>
			</li>
		</ul>


		<div class="row first title_option">
			<div class="col-12 col-md-1 d-none d-md-block">No</div>
			<div class="col-12 col-md-1" >지점</div>
			<div class="col-12 col-md-4 title">제목</div>
			<div class="col-12 col-md-2 d-none d-md-block">작성자</div>
			<div class="col-12 col-md-2 d-none d-md-block">작성일자</div>
			<div class="col-12 col-md-2 d-none d-md-block">조회수</div>
		</div>

		<c:forEach var="i" items="${boardlist}"  varStatus="s">
			<div class="row second list_contents">

				<div class="col-1 col-md-1 d-none d-md-block">${s.count}</div>
				<div class="col-12 col-md-1">${i.branch}</div>
				<div class="col-12 col-md-4 title">
					<a href="detailView.fboard?seq=${i.seq}">${i.title}</a>
					[${count.replyCount(i.seq)}]
				</div>
				<div class="col-3 col-md-2  d-md-block">${i.name}</div>
				<div class="col-2 col-md-2  d-md-block"><fmt:formatDate pattern="yyyy-MM-dd" value="${i.write_date}"/></div>
				<div class="col-1 col-md-2  d-md-block">${i.viewCount}</div>
			</div>
		</c:forEach>


		<!--페이징 네비바-->
		<div class="nav justify-content-center pagingBar">	
				<ul class="pagination">	
			 <li class="page-item"><a href="${pageContext.request.contextPath}/list.fboard?cpage=${navi[s.index-1]+1}&category=${category}&keyword=${keyword}&branch=${branch}" class="page-link"> &laquo; </a>
				<c:forEach var="i" items="${navi}" varStatus="s">
				<li class="page-item"><a href="${pageContext.request.contextPath}/list.fboard?cpage=${i}&category=${category}&keyword=${keyword}&branch=${branch}" class="page-link" id="${i}">${i}</a>
				</c:forEach>
			 <li class="page-item"><a href="${pageContext.request.contextPath}/list.fboard?cpage=${navi[s.index+1]-1}&category=${category}&keyword=${keyword}&branch=${branch}" class="page-link"> &raquo; </a>
			</ul>
			<input type = hidden id = cpage value = ${cpage }>
		</div>
		<div class="text-right footer">
			<button type="button" name="write" class="btn btn-primary writeBtn" id="writeBtn">글쓰기</button>
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