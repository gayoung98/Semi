<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<style>
	body {background-color: #D8E3E7;}

	.container {
			max-width: 400px;
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -70%);
		}
		#login{width : 350px}
</style>
<script>
	$(function(){
		$("#signup").on("click",function(){
			location.href="joinForm.member";
		})
	})
</script>
</head>
<body>
	<div class="container p-4 shadow bg-white rounded">
		<h2 class="mb-5 text-center">KH Story로그인</h2>
		<form action="login.member" method="post">
			<div class="form-row mb-3">
				<div class="col-12 mb-2">
					<input type="text" class="form-control" name="id" id="id" placeholder="아이디를 입력해주세요.">
				</div>
				<div class="col-12">
					<input type="password" class="form-control" name="pw" id="pw" placeholder="비밀번호를 입력해주세요.">
				</div>
			</div>
			
			<div class="form-row align-items-center mt-4">
				<div class="col-6 text-center">
					<input type="submit" class="btn btn-outline-info" id="login" value="로그인">
				</div>
			</div>
			<div></div>
			<div class="form-row align-items-center mt-4">
				<div class ="col-2"></div>
				<div class="col-8 text-center">
					<a href="findId.member">아이디찾기&nbsp;</a>
					<a href="member/checkInfo.jsp">비밀번호찾기</a>
				</div>
				<div class ="col-2"></div>
			</div>			
		</form>
	</div>                    
</body>
</html>