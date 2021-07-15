<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
	<style>
	.admin>a{color:#DEDEDE;display: block;width: 100%;height: 100%; padding-left:8px;font-size:17px;}
	.menu>li>a{color:#DEDEDE;display: block;width: 100%;height: 100%; font-size:25px}
	.submenu a{color:#DEDEDE;font-size:18px;}
	</style>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/manager/css/manager.css">
<div class="navi">
	<div class="navbar-content scroll-div">
		<div class="admin">
			<a href="${pageContext.request.contextPath}/logout.manager" ><i class="fas fa-sign-out-alt"></i>로그아웃</a>
		</div>
		<div class="logo">
			<a
				href="${pageContext.request.contextPath}/index.manager"><img
				src="${pageContext.request.contextPath}/manager/css/kh2.jpg" alt=""></a>
		</div>
		<ul class="menu">
			<li><a href="${pageContext.request.contextPath}/teacher.manager?branch=all&currentPage=1&category=&search=">회원 관리 <i class="fas fa-caret-down"></i></a>
				<ul class="submenu">
					<li><a
						href="${pageContext.request.contextPath}/teacher.manager?branch=all&currentPage=1&category=&search=">강사
							정보</a></li>
					<li><a
						href="${pageContext.request.contextPath}/student.manager?branch=all&currentPage=1&category=&search=">학생
							정보</a></li>
				
				</ul></li>
			<li><a href="${pageContext.request.contextPath}/noticeList.manager?branch=all&currentPage=1&category=&search=">게시물 관리<i class="fas fa-caret-down"></i></a>
				<ul class="submenu">
					<li><a
						href="${pageContext.request.contextPath}/noticeList.manager?branch=all&currentPage=1&category=&search=">공지사항
							</a></li>
					<li><a
						href="${pageContext.request.contextPath}/boardList.manager?branch=all&currentPage=1&category=&search=">자유게시판
							</a></li>
					<li><a
						href="${pageContext.request.contextPath}/assList.manager?branch=all&currentPage=1&category=&search=">과제게시판
							</a></li>
							<li>
				</ul></li>
			<li><a href="${pageContext.request.contextPath}/boardPolice.manager?currentPage=1&category=&search=">신고 관리<i class="fas fa-caret-down"></i></a>
				<ul class="submenu">
					<li><a
						href="${pageContext.request.contextPath}/boardPolice.manager?currentPage=1&category=&search=">게시글
							신고</a></li>
					
				</ul></li>
			<li><a href="${pageContext.request.contextPath}/inquireList.manager?currentPage=1&category=&search=">문의 관리<i class=" fas fa-caret-down"></i></a>
				<ul class="submenu">
					<li><a href="${pageContext.request.contextPath}/inquireList.manager?currentPage=1&category=&search=">문의 내용 관리</a></li>

				</ul>
			
		</ul>
	</div>
</div>
