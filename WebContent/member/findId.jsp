<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디찾기</title>
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
    input{
        width: 60%;
        margin-left: 10px; 
        margin-bottom: 5%;
        border:0 solid black;}
    h2{padding:15px}
    i{padding:20px}
    button{width:15%}
</style>
<script>
	$(function(){
		$("#find").on("click",function(){
			if($("#name").val() === ""){
				alert("이름을 입력해주세요")
				}else if($("#phone").val()===""){
					alert("핸드폰을 입력해주세요")
				}else{
					$.ajax({
						url : "${pageContext.request.contextPath}/find_id.member",
						type : "get",
						data : {"name":$("#name").val(),"phone":$("#phone").val()},
						dataType:"text"
					}).done(function(resp){
						$("#email").val(resp);
					})
				}
			})
	})
</script>
</head>
<body>
	<div class="container shadow bg-white rounded">
		<h2 class="text-center">아이디찾기</h2>
    	<div>
        	<i class="fas fa-user-edit"></i>
        	<input type=text id=name name="name" placeholder="이름">
    	</div>
    	<div>
        	<i class="fas fa-mobile-alt"></i>
        	<input type=text name="phone" id="phone" placeholder="핸드폰">
        	<button class="btn btn-outline-info" id="find" type="button">찾기</button>
    	</div>
   		<div>
        	<i class="far fa-envelope"></i>
        	<input  type="text" id="email" readonly>
        	<a href="${pageContext.request.contextPath}/login.jsp"><button class="btn btn-outline-info" type="button">로그인</button></a>
    	</div>    	
	</div>               
</body>
</html>