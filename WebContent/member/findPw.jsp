<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호찾기</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
<style>
	body {background-color: #D8E3E7;}
	.container {
		max-width: 600px;
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
	}
    input:focus {outline:none;}
    #hidden{display:none;}
    input{
        width: 60%;
        margin-left: 10px; 
        margin-bottom: 5%;
        border:0 solid black;}
    h2{padding:15px}
    i{padding:20px}
</style>
<script>
$(function(){
	$("#find").on("click",function(){
		if($("#email").val()===""){
			alert("이메일을 입력하세요")
		}else{
			alert("인증번호를 발송했습니다!");
			$.ajax({
				url : "${pageContext.request.contextPath}/send.email",
				type : "get",
				data : {"email":$("#email").val()},
				dataType:"text"
			}).done(function(resp){
				$("#hidden").val(resp);
			})	
		}
	})
	
	$("#check").on("click",function(){
		let msg = $("#msg").val();
		let msg1 = $("#hidden").val();
		console.log(msg);
		console.log(msg1)

		if(msg==msg1){
			alert("인증완료되었습니다.")
			location.href="updatePw.member?email="+$("#email").val();
		}else{
			alert("인증번호를 다시 확인해주세요")
		}
	})
})
</script>

</head>
<body>
	<div class="container shadow bg-white rounded">
		<h2 class="text-center">비밀번호찾기</h2>
    	<div>
       		<i class="far fa-envelope"></i>
        	<input  type="email" name="email" id="email" placeholder="이메일" value="${email }" readonly>
        	<input type="password" id="hidden">
        	<button class="btn btn-outline-info" id="find" type = button>인증번호발송</button>
    	</div>
    <div>
        <i class="far fa-paper-plane"></i>
        <input  type=text name="msg" id="msg" placeholder="인증번호입력">
        <input type="password" id="hidden">
        <button class="btn btn-outline-info" id="check" type="button">인증번호확인</button>
    </div>   	
	</div>
</body>
</html>