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

<style>
* {
	margin: 0;
	padding: 0;
}

.chat_wrap {
	width: 70%;
	position: absolute;
	left: 15%;
}

.card {
	float: left;
	position: fixed;
	top: 0;
	width: 15%;
	text-align: center;
	position: fixed;
}
.writebox {
	float: left;
	position: fixed;
	top: 0;
	left: 15%;
	width: 100%;
	text-align: center;
	position: fixed;
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
	position: fixed;
	top: 0;
	width: 70%;
	float: right;
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
	right: 10%;
	width: 50px;
}

img {
	width: 100%;
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
					document.getElementById("submit").submit();
					e.preventDefault();
					const message = $(this).val();

					// 메시지 전송

					// 입력창 clear
					clearInput();

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

	$(function() {
		$(window).scroll(function(){
	     if(window.innerHeight+ window.scrollY>= document.body.offsetHeight){
				let count =2;
				$.ajax({
					url : "${pageContext.request.contextPath}/listchat.main",
					type : "get",
					data : {"count":count++},
					dataType : "json"
				}).done(function(resp) {
					for (var i = 0; i < resp.length; i++) {
						if (resp[i].writer === "blue") {
							let msgBox = $("<div class=\"msgBox\">");
							let msg = $("<div class=\"msg1\">");
							msgBox.attr("style", "text-align:right");
							msg.text(resp[i].chat);
							$(msgBox).append(msg);
							$("#message").prepend(msgBox);
						} else {
							let msgBox = $("<div class=\"msgBox\">");
							let msg = $("<div class=\"msg2\">");
							msg.text(resp[i].chat);
							$(msgBox).append(msg);
		
							$("#message").prepend(msgBox);
		
						}
					}
				})
	          }
		})
		Chat.init();

	});
</script>

</head>

<body>

	<div class="card" style="width: 15%;">
		<img src="profile.png" class="card-img-top" alt="...">
		<p class="card-text">닉네임 님</p>
		<div class="card-body">
			<a href="#" id="mypage" class="card-link">마이페이지</a> <a href="#"
				id="logout" class="card-link">로그아웃</a>
		</div>
	</div>
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
		<div class="wrapper">
			<div class="big-box">
				<div class="chat">
					<ul id="message">
						<!-- 동적 생성 -->
						<c:forEach var="item" items="${firstlist }">
							<c:if test="${item.writer == blue}">
								<div class = "msgBox" style ="text-align:right">
								<div class = "msg1">
										${item.chat}
								</div>
							</div>
							</c:if>
							<div class = "msgBox">
								<div class = "msg2">
										${item.chat}
								</div>
							</div>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>

	</div>
	<div id="inquire">
		<img src="https://image.flaticon.com/icons/png/512/1370/1370958.png" />
	</div>

</body>

</html>