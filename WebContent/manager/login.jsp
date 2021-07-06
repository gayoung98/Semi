<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
 <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
    integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
 
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
    crossorigin="anonymous"></script>
      <link rel="stylesheet" href="css/manager.css">
    <style>
		.containers {
			width: 500px;
			margin: 60px auto;
		}

		input {
			width: 100%;
		}

		#title {
			text-align: center;
			
		}
	</style>
    
</head>
<body>

<div class="containers">
<div class="col-12 mb-5" id="title"><h2>관리자 페이지</h2></div>
    <form action="${pageContext.request.contextPath}/login.manager" method="post">
     <div class="row mb-5">
				<div class="col-3 mb-3">아이디</div>
				
				<div class="col-9 mb-3">
					<input type="text" name="id" id="id" required autofocus>
				</div>
				<div class="col-3 mb-5">비밀번호</div>
				<div class="col-9 mb-5">
					<input type="password" name="pw" id="pw" required>
				</div>
				<div class="col-12">
					<input type="submit" class="btn btn-secondary btn-lg"  value="로그인">
				</div>
				
      </div>
    </form>
  </div>
 
</body>
</html>