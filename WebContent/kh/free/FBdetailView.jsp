<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<title>자유게시판 - ${view.title}</title>
			<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
		    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
		    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
		
			
			<style>
		body {background-color: #D8E3E7;}
			
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
        		ul{
        			padding:0px;}
        			
        		/* 상단: 제목 */
				.container {
					max-width: 900px;
					margin-top: 50px;
					width: 100%;
					margin-bottom:20px;
				}

				div {
					display: block;
				}
				h3{margin-left: 20px;
				}
				h2 {
					margin-left: 20px;
					margin-bottom: 0px;
					font-weight: bold;
					display: block;
					font-size: 1.17em;
					margin-inline-end: 0px;
				}

				.title_area {
					border-bottom: 1px solid #ddd;
					padding-top:20px;
				}

				li a:hover {
					color: cadetblue;
				}

				a {
					text-decoration: none;
					cursor: pointer;
					color: black;
					font-weight: bold;
				}

				.title {
					text-align: center;
				}
				
					/*프로필 사진*/
				.card-img-top{
				width:100%;
				}

				.profilebox{
  			  width: 90px;
   			 height: 50px; 
    		overflow: hidden;
    
			}
				.profile_info{
				margin-left:10px;
				}
					/* 작성자 정보 */
					.name_box{
					margin-top:10px;
					}
					
					
				.WriterInfo .profile_info .name_box .name {
					padding-top:10px;
					margin-right: 6px;
					font-size: 13px;
					font-weight: 700;
				}

				.WriterInfo .article_info {
					font-size: 12px;
					line-height: 13px;
					width: 70%;
					margin-left:30px;
				}
				.date,.date{
				margin-left:20px;}
				.count{
				font-weight:800;}
				
				.writerInfo{margin-bottom:15px;
				margin-top:10px;
				padding-left:20px;
				width:98%;
				}

				/* 내용 */
				.contents {
					padding:0;
					margin-left:20px;
					height: 700px;
					width: 95%;
					border: 1px solid #ddd;
					border-radius: 10px;
				}
				p {margin-left:20px; margin-top:10px;}
	
				legend{
				margin-left:20px;
				padding:0;
				}
				
				/* 첨부파일 */
				.files{margin-left:30px;}
				
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
					padding-bottom: 10px;
					border: 1px solid #ddd;
					font-size: 15px;
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

				li {
					display: list-item;
				
				}

				.comment_list {
					list-style: none;
					list-style-position: initial;
					list-style-image: initial;
					list-style-type: none;
					margin-top: 40px;
					margin-bottom: 40px;
					border:none;
				}

				.CommentBox .comment_list .CommentItem {
					margin-left: 46px;
					padding-left: 0;
				}

				.comment_box {
				border: 1px solid #ddd;
				margin-left:20px;
				width:800px;
					padding-right: 20;
				}

				.CommentBox .comment_list .comment_info_box {
					margin-top: 10px;
					font-size: 12px;
				}

				.articleInfo {
					margin-top: 5px;
					margin-bottom: 5px;
				}

				.deleteReply {
					float: right;
					margin-right: 5px;
					margin-top:5px;
					
				}

				.modifyReply {
					float: right;
					margin-left: 5px;
					margin-top:5px;
				}
				.complete {
					margin-left: 5px;
				}
				.listBtn{margin-right:10px;}
				
				.footer{
				padding-top:10px;
				padding-bottom:20px;}
				
				/* 신고하기  */
				#report{font-size:12px;}
				
			
			</style>
			<script>
				$(function () {
					$("#deleteBtn").on("click",function () { //게시글 삭제
								let check = confirm("정말 게시글을 삭제하겠습니까?");
								if (check) {
									location.href = "${pageContext.request.contextPath}/delete.fboard?seq="
										+ $("#deleteBtn").val(); //게시글 삭제 확인 팝업
								} else {
									return;
								}
							});

					$("#modifyBtn").on("click",function () { //게시글 수정
								let check = confirm("정말 게시글을 수정하겠습니까?");
								if (check) {
									location.href = "${pageContext.request.contextPath}/modify.fboard?seq="
										+ $("#modifyBtn").val(); //게시글 수정 확인 팝업
								} else {
									return;
								}
							});
					$(".modifyReply").on("click", function () { //댓글 수정 버튼
						
						
						$(this).parent().siblings($(".comment_text")).children($(".text_view")).children($("#com")).attr("contenteditable", "true");
						$(this).parent().siblings($(".comment_text")).children($(".text_view")).children($("#com")).focus();
							let complete = $("<button type =button>");
							complete.addClass("btn btn-dark complete")
							complete.text("수정완료");
							let cancel = $("<button type = button>");
							cancel.addClass("btn btn-dark cancel")
							cancel.text("취소");
							
							$(".deleteReply").remove();
							$(this).before(cancel);
							$(this).before(complete);
							$(this).remove();
					});
					
			
					$(document).on("click",".cancel" ,function(){ //댓글 수정 폼의 취소
						location.href = "${pageContext.request.contextPath}/detailView.fboard?seq=${view.seq}";
						
					});
					$(document).on("click",".complete",function(){//댓글 수정 폼의 수정완료
						let inputcom = $("<input>");
						inputcom.attr("type", "hidden");
						inputcom.attr("name", "reply");
						inputcom.val($(this).parent().siblings($(".comment_text")).children($(".text_view")).children($("#com")).text());
						let sub = $(this).parents($("#modifyForm"));
						$(sub).append(inputcom);
						$(sub).submit();
						
					});
					
					
					$(".deleteReply").on("click",function () { //댓글 삭제
						let check = confirm("정말 댓글을 삭제하겠습니까?");
						if (check) {
							$("#delReplyForm").submit();
						} else {
							return;
						}
						
					});
					
				  //게시글 신고
					  $("#report").on("click",function() {							
					let parent ="${view.seq}";
						  window.open("${pageContext.request.contextPath}/reportForm.fboard?seq="+parent,"게시글 신고","width=450,height=450");           
					    });   
					
						});
			</script>
		</head>

		<body>
		<jsp:include page="/navibar.jsp"></jsp:include>
		
			<div class="container shadow bg-white rounded">
				<!-- 게시물 제목 -->
				<div class="col-12 title_area">

					<h3>${view.title}</h3>

				</div>
				<!-- 작성자 정보 -->
				<div class="writerInfo">
					<div class="profile_info">
					
					<div class ="profilebox shadow bg-white" >
			                	<c:choose>
				                	<c:when test="${profile_img != null}">
			                  			<img src="${pageContext.request.contextPath}/profile/${view.writer}/${profile_img.sysName}" class="card-img-top" alt="profile_picture" id = profile>
			                   		</c:when>
			                   		<c:otherwise>
			                   			<img src="${defalut_profile_img}" class="card-img-top" alt="profile_picture" id = profile>
			                   		</c:otherwise>
		                   		</c:choose>
		                   		
		                   	</div>
						<div class="name_box">
							<a href="#" role="button"> ${view.name} </a> <em
								class="position">${view.branch}지점  </em>
						</div>
					</div>
					<!-- 작성일자,조회수 -->
					<div class="articleInfo">
						<span class="date"><fmt:formatDate pattern="yyyy-MM-dd:hh:mm" value="${view.write_date}"/></span> <span class="count">조회
							${view.viewCount}</span> <input type="hidden" name="seq" value="${view.seq}">

						<!-- 댓글 수 및 신고버튼-->
						<div class="com">
							<a href="#" role="button" class="button_comment"> <strong class="num"> 댓글
									${count.replyCount(view.seq)}</strong>
							</a>
							<button type="button" class="btn btn-danger" id="report"><i class="fas fa-exclamation-triangle"></i> 신고</button>
						</div>
					</div>

				</div>
				<!-- 게시글  내용 -->
				<div class="col-12 md-5 contents">
					<p class="target">${view.contents}</p>
				</div>
				<hr>
				<!--첨부 파일리스트 출력  -->
				<div id="content">
					<fieldset class="file_box">
						<legend>[첨부 파일 목록]</legend>
						<c:forEach var="file" items="${filelist}">
							<!--첨부파일 다운로드-->
							<a download href="download.file?seq=${file.seq}&sysname=${file.sysName}&oriname=${file.oriName}"class="files">${file.oriName}</a>
							<br>
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
										<a id="" href="#" role="button" aria-haspopup="true" aria-expanded="false"
											class="comment_nickname"> ${i.name}
										</a>
									</div>
									<!--댓글 수정-->
									<form action="${pageContext.request.contextPath}/modify.freecom" method="post" id="modifyForm">
										<div class="comment_text">
											<p class="text_view">
												<span class="modify_option" id="com">${i.comments}</span>
											</p>
										</div>

										<div class="commentInfo">
											<span><fmt:formatDate pattern="yyyy-MM-dd:hh:mm" value="${i.write_date}"/></span>
										</div>

										<!-- 댓글 수정 controller -->
										<div class="btn_wrap text-right" id="buttons">
											<c:if test="${i.writer == login}">
												<!-- 게시글 번호 -->
												<input type="hidden" name="seq" value="${i.seq}">
												<!--댓글 번호 -->
												<input type="hidden" name="comments" value="${i.comments}">
												<!--댓글 내용 -->
												<input type="hidden" name="parent" value="${i.parent}">
												<button type="submit" name="update_com" value="${i.seq}" class="btn btn-dark modifyReply">수정</button>
											
									</form>
									<!-- 댓글 삭제 -->
									<form action="${pageContext.request.contextPath}/delete.freecom" method="post" id="delReplyForm">
										<button type="button" value="${i.seq}"
											class="btn btn-dark deleteReply">삭제</button>
										<input type="hidden" name="seq" value="${i.seq}"> 
										<input type="hidden" name="parent" value="${i.parent}">
										
									</form>
									</c:if>
								</div>
							</li>
						</ul>
					</c:forEach>
					<hr>
					<div class="col-12 mb-5 comment_writer">
						<form action="${pageContext.request.contextPath}/write.freecom" method="post">
							<strong>${dto.name}</strong>
							<textarea placeholder="댓글을 남겨보세요" name="comments" class="comment_inbox_text"></textarea>

							<input type="submit" class="btn btn-dark" id="replyBtn" value="등록">
							<input type="hidden" name="parent" value="${view.seq}">
						</form>
					</div>
				</div>
				<hr>
				<!-- 로그인 유저와 글쓴이가 같다면? 수정/삭제 -->
				<div class="btn_wrap text-right footer">
					<c:choose>
						<c:when test="${login== view.writer}">
							<button type="button" value="${view.seq}" class="btn btn-primary"
								id="modifyBtn">수정하기</button>
							<button type="button" value="${view.seq}" id="deleteBtn" name="delete"
								class="btn btn-dark">삭제</button>
						</c:when>
					</c:choose>
					<a href="${pageContext.request.contextPath}/list.fboard?cpage=1" class="btn btn-secondary">목록으로</a>

				</div>
			</div>
		</body>