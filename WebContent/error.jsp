<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>This is error page</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous">

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
	crossorigin="anonymous"></script>
	<style>
	.container{width=100%;text-align:center;}
	header{margin-top:10vh;}
	.errorMessage{margin-top:3vh; margin-bottom:5vh;}
	</style>
</head>
<body>

<div class="container">
<header>
<h1>Error</h1>
</header>
<div class="errorMessage">
<h3>문제 해결을 위해 노력하겠습니다.</h3>
<h3>We are working hard to resolve it</h3>
</div>
<div class="buttons">
<button type="button" class="btn btn-primary btn-lg" id="main">메인으로</button>
<button type="button" class="btn btn-secondary btn-lg" id="ask">문의하기</button>
</div>
</div>
<script>
$(function(){
	$("#main").on("click",function(){
		
		location.href="${pageContext.request.contextPath}/main.main";
	})
	 $("#ask").on("click", function(){
	                let popup = window.open('${pageContext.request.contextPath}/kh/inquired/popup.jsp', 'inquire', 'width=550px,height=600px,left=500,top=300,scrollbars=no,resizable=no');
	            })
})
</script>
</body>
</html>