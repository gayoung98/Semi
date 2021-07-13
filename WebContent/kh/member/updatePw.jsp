<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
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
    #val{float: right;}
    i{padding:20px}
</style>
<script>
window.onload = function(){
    let val = document.getElementById("val");

    document.getElementById("ch").oninput = function(){
        let pw = document.getElementById("pw").value;
        let ch = document.getElementById("ch").value;

        if(pw != ch){
            val.innerHTML = "패스워드가 일치하지 않습니다.."
        }else{
            val.innerHTML = "패스워드가 일치합니다."
        }
    }	
}

</script>
</head>
<body>
	<div class="container shadow bg-white rounded">
		<form action="${pageContext.request.contextPath}/modify.member" method="post">
			<h2 class="text-center">비밀번호 변경</h2>
    		<div>
        		<i class="fas fa-unlock"></i>
        		<input type=password id="pw" name="pw" placeholder="비밀번호 특수문자 / 문자 / 숫자 포함 8~15자리">
    		</div>
    		<div id="c">
        		<i class="fas fa-check"></i>
        		<input type=password id="ch" placeholder="비밀번호확인" style="width:25%;">
        		<div id="val"></div>
    		</div>
    		<input type="hidden" name="email" value="${email }">
    		<center><div>
    			<input type="submit" class="btn btn-outline-info w-50" value="변경">
    		</div></center>
		</form>
	</div>
</body>
</html>