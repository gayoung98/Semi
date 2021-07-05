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
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
	integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
	crossorigin="anonymous" />


<style>
* {
	margin: 0;
	padding: 0;
}
div[class*=col]{
padding: 0px;
margin:0px;
}
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
            background-color: #55555550;
        }


.chat_wrap {
	width: 100%;
	left: 20%;
}

.card {
	float: left;
	top: 55px;
	left: 8%;
	width: 70%;
	text-align: center;
}

.writebox {
	float: left;
	width: 100%;
	text-align: center;
}

/* sns */
.chat_wrap .chat ul {
	list-style: none;
}

#message>div {
	display: block;
	word-break: break-all;
	margin: 5px 20px;
	padding: 10px;
	border-radius: 5px;
	font-size: 18px;
}

#message>div>.msg1 {
	display: inline-block;
	word-break: break-all;
	margin: 5px 20px;
	max-width: 75%;
	border: 1px solid #888;
	padding: 10px;
	border-radius: 5px;
	background-color: #FFE194;
	color: #555;
	text-align: right;
	font-size: 18px;
	word-break: break-all;
}

#message>div>.msg2 {
	display: inline-block;
	word-break: break-all;
	margin: 5px 20px;
	max-width: 75%;
	border: 1px solid #888;
	padding: 10px;
	border-radius: 5px;
	background-color: #FCFCFC;
	color: #555;
	text-align: left;
	font-size: 18px;
}

.input-div {
	top: 55px;
	width: 60%;
	margin:auto;
	background-color: #FFF;
	text-align: center;
	border-bottom: 1px solid #F18C7E;
}



.input-div>input {
	width: 100%;
	height: 80px;
	border: none;
	padding: 10px;
}

.wrapper {
	margin-top: 85px;
}

.format {
	display: none;
}

#inquire {
	position: fixed;
	bottom: 10%;
	right: 15%;
	width: 50px;
}

img {
	width: 100%;
}

body {
	min-height: 500px;
}

.fas {
	display: none
}

.msg2:hover .fas {
	display: block;
}
/* 로딩바 */
.footer {
	display: block;
	width: 100%;
	position: fixed;
	text-align: center;
	bottom: 0px;
}

.sk-circle {
	display: inline-block;
	margin: auto;
	width: 40px;
	height: 40px;
}

.sk-circle .sk-child {
	width: 40px;
	height: 40px;
	position: absolute;
}

.sk-circle .sk-child:before {
	content: '';
	display: block;
	margin: 0 auto;
	width: 6px;
	height: 6px;
	background-color: rgb(179, 179, 179);
	border-radius: 100%;
	-webkit-animation: sk-circleBounceDelay 0.5s infinite ease-in-out both;
	animation: sk-circleBounceDelay 1.2s infinite ease-in-out both;
}

.sk-circle .sk-circle2 {
	-webkit-transform: rotate(30deg);
	-ms-transform: rotate(30deg);
	transform: rotate(30deg);
}

.sk-circle .sk-circle3 {
	-webkit-transform: rotate(60deg);
	-ms-transform: rotate(60deg);
	transform: rotate(60deg);
}

.sk-circle .sk-circle4 {
	-webkit-transform: rotate(90deg);
	-ms-transform: rotate(90deg);
	transform: rotate(90deg);
}

.sk-circle .sk-circle5 {
	-webkit-transform: rotate(120deg);
	-ms-transform: rotate(120deg);
	transform: rotate(120deg);
}

.sk-circle .sk-circle6 {
	-webkit-transform: rotate(150deg);
	-ms-transform: rotate(150deg);
	transform: rotate(150deg);
}

.sk-circle .sk-circle7 {
	-webkit-transform: rotate(180deg);
	-ms-transform: rotate(180deg);
	transform: rotate(180deg);
}

.sk-circle .sk-circle8 {
	-webkit-transform: rotate(210deg);
	-ms-transform: rotate(210deg);
	transform: rotate(210deg);
}

.sk-circle .sk-circle9 {
	-webkit-transform: rotate(240deg);
	-ms-transform: rotate(240deg);
	transform: rotate(240deg);
}

.sk-circle .sk-circle10 {
	-webkit-transform: rotate(270deg);
	-ms-transform: rotate(270deg);
	transform: rotate(270deg);
}

.sk-circle .sk-circle11 {
	-webkit-transform: rotate(300deg);
	-ms-transform: rotate(300deg);
	transform: rotate(300deg);
}

.sk-circle .sk-circle12 {
	-webkit-transform: rotate(330deg);
	-ms-transform: rotate(330deg);
	transform: rotate(330deg);
}

.sk-circle .sk-circle2:before {
	-webkit-animation-delay: -1.1s;
	animation-delay: -1.1s;
}

.sk-circle .sk-circle3:before {
	-webkit-animation-delay: -1s;
	animation-delay: -1s;
}

.sk-circle .sk-circle4:before {
	-webkit-animation-delay: -0.9s;
	animation-delay: -0.9s;
}

.sk-circle .sk-circle5:before {
	-webkit-animation-delay: -0.8s;
	animation-delay: -0.8s;
}

.sk-circle .sk-circle6:before {
	-webkit-animation-delay: -0.7s;
	animation-delay: -0.7s;
}

.sk-circle .sk-circle7:before {
	-webkit-animation-delay: -0.6s;
	animation-delay: -0.6s;
}

.sk-circle .sk-circle8:before {
	-webkit-animation-delay: -0.5s;
	animation-delay: -0.5s;
}

.sk-circle .sk-circle9:before {
	-webkit-animation-delay: -0.4s;
	animation-delay: -0.4s;
}

.sk-circle .sk-circle10:before {
	-webkit-animation-delay: -0.3s;
	animation-delay: -0.3s;
}

.sk-circle .sk-circle11:before {
	-webkit-animation-delay: -0.2s;
	animation-delay: -0.2s;
}

.sk-circle .sk-circle12:before {
	-webkit-animation-delay: -0.1s;
	animation-delay: -0.1s;
}

@
-webkit-keyframes sk-circleBounceDelay { 0%, 80%, 100% {
	-webkit-transform: scale(0);
	transform: scale(0);
}

40


%
{
-webkit-transform


:


scale
(


1


)
;


transform


:


scale
(


1


)
;


}
}
@
keyframes sk-circleBounceDelay { 0%, 80%, 100% {
	-webkit-transform: scale(0);
	transform: scale(0);
}
40


%
{
-webkit-transform


:


scale
(


1


)
;


transform


:


scale
(


1


)
;


}
}
</style>
<script>
	/* sns */
	let writechat = document.getElementById("wirtechat");

	const Chat = (function() {
		const myName = "blue";

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
							if (((window.innerHeight + window.scrollY) >= $("#wrapper").height())&& isScroll) {
								$.ajax({
													url : "${pageContext.request.contextPath}/listchat.main",
													type : "get",
													data : {"count" : count},
													dataType : "json"
												}).done(
												function(resp) {
													if (count > Number($("#length").val()) / 8)
														isScroll = false;
													console.log("count= "+ count)
													for (var i = 0; i < resp.length; i++) {

														if (resp[i].writer == "blue") {
															let msgBox = $("<div class=\"msgBox\">");
															let msg = $("<div class=\"msg1\">");
															msgBox.attr(
																			"style",
																			"text-align:right");
															msg.text(resp[i].chat);
															$(msgBox).append(msg);
															$("#message").append(msgBox);
														} else {
															let msgBox = $("<div class=\"msgBox\">");
															let msg = $("<div class=\"msg2\">");
															msg.text(resp[i].chat);
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
	                let popup = window.open('${pageContext.request.contextPath}/inquired/popup.jsp', 'inquire', 'width=550px,height=600px,scrollbars=no,resizable=no');
	            })
	        })
		
	});
</script>

</head>

<body>
<jsp:include page= "/navibar.jsp" />
	<div class = container>
		<input type=hidden id=length value="${list}">
		<div class =row>
		<div class="col-3"> 
		<div class="card">
			<img src="profile.png" class="card-img-top" alt="...">
			<p class="card-text">닉네임 님</p>
			<div class="card-body">
				<a href="${pageContext.request.contextPath}/mypage.mp" id="mypage" class="card-link">마이페이지</a> <a href="#"
					id="logout" class="card-link">로그아웃</a>
			</div>
		</div>
		</div>
		<div class = "col-9">
		<div class="chat_wrap">
			<div class="writebox">
				<form action="${pageContext.request.contextPath}/writechat.main"
					method="post" id="submit">
					<div class="input-div">
						<input type=text id="writechat" name="writechat"
							placeholder="Press Enter for send message.">
					</div>
				</form>
			</div>
			<div class="wrapper" id="wrapper">
				<div class="big-box">
					<div class="chat">
						<ul id="message">
							<!-- 동적 생성 -->
							<c:forEach var="item" items="${firstlist }">
								<c:if test="${item.writer == blue}">
									<div class="msgBox" style="text-align: right">
										<div class="msg1">${item.chat}</div>
									</div>
								</c:if>
								<c:if test="${item.writer != blue}">
									<div class="msgBox">
										<div class="msg2">${item.chat}</div>
									</div>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
</div>
		</div>
		</div>
		</div>
		<div id="inquire">
			<img src="https://image.flaticon.com/icons/png/512/1370/1370958.png" />
		</div>
		<div class="footer">
			<div class="sk-circle" id="hiddenDivLoading">
				<div class="sk-circle1 sk-child"></div>
				<div class="sk-circle2 sk-child"></div>
				<div class="sk-circle3 sk-child"></div>
				<div class="sk-circle4 sk-child"></div>
				<div class="sk-circle5 sk-child"></div>
				<div class="sk-circle6 sk-child"></div>
				<div class="sk-circle7 sk-child"></div>
				<div class="sk-circle8 sk-child"></div>
				<div class="sk-circle9 sk-child"></div>
				<div class="sk-circle10 sk-child"></div>
				<div class="sk-circle11 sk-child"></div>
				<div class="sk-circle12 sk-child"></div>
			</div>
		</div>
</body>

</html>