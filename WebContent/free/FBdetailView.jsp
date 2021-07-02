<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${view.title}</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<style>
.container {
	margin-top: 10px;
	width: 100%;

}

div {
	display: block;
}

h2 {
	margin-left: 20px;
	margin-bottom: 0px;
	font-weight: bold;
	display: block;
	font-size: 1.17em;
	margin-inline-end: 0px;
}
.title_area{border-bottom: 1px solid #ddd;
}
a {
	text-decoration: none;
	cursor: pointer;
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
/* 내용 */
.contents {
	height: 200px;
    border: 1px solid #ddd;
	border-radius: 10px;
}
/* 작성자 정보 */
.WriterInfo .profile_info .name_box .name {
	margin-right: 6px;
	font-size: 13px;
	font-weight: 700;
}

.WriterInfo .article_info {
	font-size: 12px;
	line-height: 13px;
}
/* 댓글 */
.com {
	float: right;

}

.com .button_comment {
	display: inline-block;
	margin-right: 20px;
	vertical-align: top;
}


.comment_writer .comment_inbox_text {
	display: block;
	background-attachment: scroll;
	width: 100%;
	min-height: 17px;
	padding-right: 1px;
	padding-bottem: 10px;
	
 	border: 1px solid #ddd;
	font-size: 13px;
	resize: none;
	box-sizing: border-box;
	background: transparent;
	outline: 0;
	border-radius: 1px;
}

textarea {
	width: 100%;
	white-space: pre-wrap;
	text-rendering: auto;
	flex-direction: column;
	cursor: text;
	column-count: initial !important;
	word-spacing: normal;
	overflow: visible;
	overflow-wrap: break-word;
	height: 100px;
}

#replyBtn {
	float: right;
	margin-top: 5px;
}
li{
    display: list-item; }

ul{list-style: none;
    list-style-position: initial;
    list-style-image: initial;
    list-style-type: none;
    margin-top:40px;
    margin-bottom:40px;}

.CommentBox .comment_list .CommentItem {margin-left:46px; padding-left:0;}

.comment_box {
	border: 1px solid black;
}

.CommentBox .comment_list .comment_info_box {
	margin-top:10px;
	font-size: 12px;
}

.articleInfo{margin-top: 5px;margin-bottom: 5px;}

.deleteReply {
	float: right;
	margin-left:5px;
	
}

.modifyReply {
	float: right;
	margin-right:5px;
}
</style>
<script>
	$(function() {
		$(".writeBtn").on("click", function() {
			location.href = "${pageContext.request.contextPath}/write.fboard"
		});
		$("#deleteBtn")
				.on(
						"click",
						function() {
							let check = confirm("정말 게시글을 삭제하겠습니까?");
							if (check) {
								location.href = "${pageContext.request.contextPath}/delete.fboard?seq="
										+ $("#deleteBtn").val(); //게시글 삭제 확인 팝업
							} else {
								return;
							}
						});

		$("#modifyBtn").on("click",function() {
							let check = confirm("정말 게시글을 수정하겠습니까?");
							if (check) {
								location.href = "${pageContext.request.contextPath}/modify.fboard?seq="
										+ $("#modifyBtn").val(); //게시글 수정 확인 팝업
							} else {
								return;
							}
						});

		$(".modifyReply").on("click",function() {
							let check = confirm("정말 댓글을 수정하겠습니까?");
							if (check) {
								location.href = "${pageContext.request.contextPath}/modify.freecom?seq="
										+ $(".modify_com").val(); //코멘트 수정 controller로 이동
							} else {
								return;
							}
						});

		$(".deleteReply").on("click", function() { //댓글 삭제
			let check = confirm("정말 댓글을 삭제하겠습니까?");
			if (check) {
				$("#replyForm").attr("action", "delete.freecom");
				$(this).next().attr("name", "seq");
				$(this).next().next().attr("name", "parent");
				$("#replyForm").submit();
			} else {
				return;
			}
		});
	});
</script>
</head>
<body>
	<div class="container">
		<!-- 게시물 제목 -->
		<div class="col-12 title_area">

			<h3>${view.title}</h3>
			
		</div>
		<!-- 작성자 정보 -->
		<div class="writerInfo">
			<div class="profile_info">
				<a href=""> <img src="title.jpg" alt="프로필 사진" width="30"
					height="30">
				</a>
				<div class="name_box">
					<a href="#" role="button" class="name"> ${view.writer} </a> <em
						class="position">${view.branch}지점 ${login.khClass}반  ${login.position} </em>
				</div>


			</div>
			<!-- 작성일자,조회수 -->
			<div class="articleInfo">
				<span class="date">${view.writeDate}</span> <span class="count">조회
					${view.viewCount}</span>
						<input type="hidden" name="seq" value="${view.seq}">
		<!-- 댓글 수 -->
		<div class="com">
			<a href="#" role="button" class="button_comment"> <strong
				class="num"> 댓글 ${count.replyCount(view.seq)}</strong>
			</a>
			</div>
		</div>
				<!-- 게시글  내용 -->
		<div class="col-12 md-5 contents">
			<p>${view.contents}</p>
		</div>
		<hr>
		<!--첨부 파일리스트 출력  -->
				<div id="content">
					<fieldset class="file_box">  
						<legend>[첨부 파일 리스트]</legend>
								<c:forEach var="file" items="${filelist}">
								<!--첨부파일 다운로드-->
									<a href="download.file?seq=${file.seq}&sysname=${file.sysName}&oriname=${file.oriName}">${file.oriName}</a><br>
								</c:forEach>
					</fieldset>
			</div>
		<hr>
		<!-- 댓글 작성 -->
		<h2 class="comment_title">댓글</h2>

		<div class="col-12 md-5 commentBox">

			<c:forEach items="${reply}" var="i">
				<ul class="comment_list">
					<li class="commentItem">
						<!-- 댓글 출력 -->
						<div class="col-12 d-md-block comment_box">

							<div class="comment_writerInfo">
								<a id="" href="#" role="button" aria-haspopup="true"
									aria-expanded="false" class="comment_nickname"> ${i.writer}
								</a>
							</div>

							<div class="comment_text">
								<p class="text_view">
									<span clas="text_comment">${i.comments}</span>
								</p>
							</div>
							<div class="commentInfo">
								<span>${i.writeDate}</span>
							</div>

							<!-- 댓글 수정 controller -->
							<div class="btn_wrap text-right">
								<c:if test="${i.writer == login.name}">
							<form id=replyForm>
								<button type="button" value="${i.seq}"
									class="btn btn-dark deleteReply">삭제</button>
								<input type="hidden" value="${i.seq}"> <input
									type="hidden" value="${i.parent}">
							</form>
							<form action="/modify.freecom" method="post">
									<!-- 게시글 번호 -->
									<input type="hidden" name="seq" value="${i.seq}">
									<!--댓글 번호 -->
									<input type="hidden" name="comments" value="${i.comments}">
									<!--댓글 내용 -->
									<input type="hidden" name="parent" value="${i.parent}">
									<button type="submit" name="update_com" value="${i.seq}"
										class="btn btn-dark modifyReply ">수정</button>
							</form>
							
							</c:if>
							</div>
						</div>
					</li>
			  </ul>
		</c:forEach>
		<hr>
				<div class="col-12 mb-5 comment_writer">
				<form action="/write.freecom" method="post">
					<strong>${login.name}</strong>
					<textarea placeholder="댓글을 남겨보세요" name="comments"
						class="comment_inbox_text"></textarea>

					<input type="submit" class="btn btn-dark" id="replyBtn" value="등록">
					<input type="hidden" name="parent" value="${view.seq}">			
		</form>
</div>
	</div>

	<!-- 로그인 유저와 글쓴이가 같다면? 수정/삭제 -->
	<div class="btn_wrap text-right">
		<c:choose>
			<c:when test="${login.name == view.writer}">
				<a
					href="${pageContext.request.contextPath}/modify.fboard?seq=${view.seq}"
					class="btn btn-primary">수정하기</a>
				<a
					href="${pageContext.request.contextPath}/delete.fboard?seq=${view.seq}"
					class="btn btn-danger">삭제하기</a>
			</c:when>
		</c:choose>
		<a href="${pageContext.request.contextPath}/list.fboard?cpage=1"
			class="btn btn-secondary">목록으로</a>

	</div>
	</div>
</body>
</html>