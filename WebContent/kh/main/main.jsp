<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<!--  <meta http-equiv="refresh" content="5">  -->
<title>Main</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/kh/main/main.css"  type="text/css" media="all">
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
<script>
	/* sns */
	let writechat = document.getElementById("wirtechat");

	const Chat = (function() {

		// init 함수
		function init() {
			// enter 키 이벤트
			$(document).on('keydown', 'div.input-div input', function(e) {

				if (e.keyCode == 13 && !e.shiftKey) {
					if ($("#writechat").val() != null) {
						document.getElementById("submit").submit();
						e.preventDefault();
						const message = $(this).val();

						// 메시지 전송

						// 입력창 clear
						clearInput();
					} else {
						alert("넣어주세요")
					}

				}

			});
		}

		// 메세지 입력박스 내용 지우기
		function clearInput() {
			$('div.input-div input').val('');
		}

		return {
			'init' : init
		};
	})();
	/*
	 loadBeforeSend: function(){
	 var divLoading = document.all.hiddenDivLoading;
	 divLoading.style.visibility = "visible";
	 $("#hiddenDivLoading").show.css({
	 top:$(document).scrollTop()+($(window).height())/2.6 + 'px',
	 left:($(window).width())/2.6 + 'px'
	 });
	 },
	 loadComplete : function(data){
	 $("#hiddenDivLoading").hide();
	 }
	 $(Window).scroll(function(){
	 top:$(document).scrollTop()+($(window).height())/2.6 + 'px',
	 left:($(window).width())/2.6 + 'px'
	 });
	 });
	 */
	$(function() {

		var count = 2;
		var isScroll = true;

		let loadNewPage = $(window).on("scroll",(function() {
							if (((window.innerHeight + window.scrollY) >= document.body.offsetHeight)&& isScroll) {
								$.ajax({
													url : "${pageContext.request.contextPath}/listchat.main",
													type : "get",
													data : {"count" : count},
													dataType : "json"
												}).done(
												function(resp) {
													if (count > Number($("#length").val()) / 10)
														isScroll = false;
													console.log("count= "+ count)
													for (var i = 0; i < resp.length; i++) {

														if (resp[i].writer === $("#writer").val()) {
															let msgBox = $("<div class=\"msgBox\">");
															let msg = $("<div class=\"msg1\">");
															msgBox.attr(
																			"style",
																			"text-align:right");
															msg.text(resp[i].contents);
															$(msgBox).append(msg);
															$("#message").append(msgBox);
														} else {
															let msgBox = $("<div class=\"msgBox\">");
															let msg = $("<div class=\"msg2\">");
															msg.text(resp[i].contents);
															$(msgBox).append(msg);
															$("#message").append(msgBox);
														}

													}
													count++;

												})

							}
						})

				)

		Chat.init();
		setTimeout(loadNewPage, 1200);
		
		
		  $(function(){
	            $("#inquire").on("click", function(){
	                let popup = window.open('${pageContext.request.contextPath}/kh/inquired/popup.jsp', 'inquire', 'width=550px,height=600px,scrollbars=no,resizable=no');
	            })
	        })
	        $(function(){
	        	$( window ).scroll( function() {
	        		if ( $( this ).scrollTop() > 200 ) {
	        			$( '#top' ).fadeIn();
	        		} else {
	        			$( '#top' ).fadeOut();
	        		}
	        	} );
	        	$( '#top' ).click( function() {
	        		$( 'html, body' ).animate( { scrollTop : 0 }, 400 );
	        		return false;
	        	} );
	        })
		
	});
</script>

</head>

<body>
<jsp:include page= "/navibar.jsp" />
	<div class = "container">
		<input type=hidden id=length value="${list}">
		<div class="talkname">우리반 익명 Talk</div>
		<div class =row>
		<div class="col-2"> 
		<div class="card shadow bg-white rounded" style='width:85%'>
			<c:choose>
				<c:when test="${profile_img != null }">
					<img src="${pageContext.request.contextPath}/profile/${login }/${profile_img.sysName}" class="card-img-top" alt="...">
				</c:when>
				<c:otherwise>
					<img src="${defalut_profile_img }" class="card-img-top" alt="...">
				</c:otherwise>
			</c:choose>
			<p class="card-text">${name } 님</p>
			<div class="card-body">
				<a href="${pageContext.request.contextPath}/mypage.mp" id="mypage" class="card-link" style = "font-weight: bold;">My Page</a>	
				<a href="${pageContext.request.contextPath}/signout.member"id="logout" class="card-link" style ="font-size: 25px" title = "로그아웃"><i class="fas fa-sign-out-alt"></i></a>
			</div>
		</div>
		</div>
		<div class = "col-8" id="col">
			<div class="chat_wrap  shadow bg-white rounded">
				<div class="writebox">
				<form action="${pageContext.request.contextPath}/writechat.main"
					method="post" id="submit">
					<div class="input-div">
						<input type=text id="writechat" name="writechat"
							placeholder="Press Enter for send message."autofocus>
					</div>
					<input type="hidden" name="writer" id = writer value="${login }">
				</form>
			</div>
			<div class="wrapper" id="wrapper">
				<div class="big-box">
					<div class="chat">
						<ul id="message">
							<!-- 동적 생성 -->
							<c:forEach var="item" items="${firstlist }">
								<c:if test="${item.writer == login}">
									<div class="msgBox" style="text-align: right">
										<div class="msg1">${item.contents}</div>
									</div>
								</c:if>
								<c:if test="${item.writer != login}">
									<div class="msgBox">
										<div class="msg2">${item.contents}</div>
									</div>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		</div>
		<div class = col-2>
		
		</div>
		</div>
		</div>
    	<div id="up">
    		<a href="#" id="top">TOP</a>	
    	</div>		
		<div id="inquire">
			<img src="https://image.flaticon.com/icons/png/512/1370/1370958.png"/>
		</div>
		
</body>

</html>