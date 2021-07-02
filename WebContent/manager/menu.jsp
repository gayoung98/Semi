<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="navi">
	<div class="navbar-content scroll-div">
		<div class="admin">
			<a href="${pageContext.request.contextPath}/logout.manager">로그아웃</a>
		</div>
		<div class="logo">
			<a
				href="${pageContext.request.contextPath}/manager/manager.member/index.jsp"><img
				src="${pageContext.request.contextPath}/manager/css/kh2.jpg" alt=""></a>
		</div>
		<ul class="menu">
			<li><a href="#">회원 관리 <i class="fas fa-caret-down"></i></a>
				<ul class="submenu">
					<li><a
						href="${pageContext.request.contextPath}/teacher.manager?branch=all&currentPage=1&category=&search=">강사
							정보</a></li>
					<li><a
						href="${pageContext.request.contextPath}/student.manager?branch=all&currentPage=1&category=&search=">학생
							정보</a></li>
					<li><a href="#">로그 기록</a></li>
				</ul></li>
			<li><a href="#">게시물 관리<i class="fas fa-caret-down"></i></a>
				<ul class="submenu">
					<li><a
						href="${pageContext.request.contextPath}/noticeList.manager?branch=all&currentPage=1&category=&search=">공지사항
							작성</a></li>
					<li><a
						href="${pageContext.request.contextPath}/boardList.manager?branch=all&currentPage=1&category=&search=">게시판
							관리</a></li>
					<li><a
						href="${pageContext.request.contextPath}/manager/manager.board/asBoardList.jsp">과제게시판
							관리</a></li>
				</ul></li>
			<li><a href="#">신고 관리<i class="fas fa-caret-down"></i></a>
				<ul class="submenu">
					<li><a
						href="${pageContext.request.contextPath}/manager/manager.police/boardPolice.jsp">게시글
							신고</a></li>
					<li><a
						href="${pageContext.request.contextPath}/manager/manager.police/snsPolice.jsp">SNS
							신고</a></li>
				</ul></li>
			<li><a href="#">문의 관리<i class=" fas fa-caret-down"></i></a>
				<ul class="submenu">
					<li><a href="#">문의 내용 관리</a></li>

				</ul>
			<li><a href="#">자리 신청 관리<i class="fas fa-caret-down"></i></a>
				<ul class="submenu">
					<li><a href="#">종로</a></li>
					<li><a href="#">당산</a></li>
					<li><a href="#">강남</a></li>


				</ul></li>
		</ul>
	</div>
</div>
