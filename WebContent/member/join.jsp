<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
<script>
	window.onload = function(){
		let email = $("#email")
		let ch1 = $("#ch")
		let phone1 = $("#phone")
		
		let id = document.getElementById("email");  
        let ch2 = document.getElementById("ch");
		let phone = document.getElementById("phone");
		let val = document.getElementById("val");
		
		let idRegex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		let pwRegex = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
		let phoneRegex = /^010\d{3,4}\d{4}$/;
		
		email.blur(function(){
			let resultid = idRegex.test(id.value);
            if(resultid){
                return;
            }else{
                alert("EMAIL 형식을 확인하세요.")
                document.getElementById("email").value="";
            }
		})
		
		document.getElementById("ch").oninput = function(){
			let pw = document.getElementById("pw").value;
	        let ch = document.getElementById("ch").value;
            if(pw != ch){
                val.innerHTML = "패스워드가 일치하지 않습니다.."
            }else{
             	val.innerHTML = "패스워드가 일치합니다." 
            }
        }	
				
		ch1.blur(function(){
	        let resultpw = pwRegex.test(ch2.value);
            if(resultpw){
                return;
            }else{
                alert("비밀번호 형식을 확인하세요.")
    			document.getElementById("pw").value = "";
    	        document.getElementById("ch").value = "";
            }
		})
		
		phone1.blur(function(){
			let resultphone = phoneRegex.test(phone.value);
			if(resultphone){
				return;
			}else{
				alert("핸드폰 형식을 확인하세요.")
				document.getElementById("phone").value="";
			}
		})
	}
	$(function(){
		$("#confirm").on("click",function(){
			if($("#email").val() === ""){
				alert("이메일을 입력해주세요");
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
		
		$("#msg").on("click",function(){
			if($("#check").val()===""){
				alert("인증번호를 입력하세요")
			}else{
			let msg = $("#check").val();
			let msg1 = $("#hidden").val();
			console.log(msg);
			console.log(msg1)

			if(msg==msg1){
				alert("인증완료되었습니다.")
				$("#hidden_form").show();
				$("#email").attr("readonly","true");
			}else{
				alert("인증번호를 다시 확인해주세요")
				$("#check").val("");
			}
			}
		})
		
		
		if($("#email").val() !== ""){
			$("#hidden_form").css("display","block");
			$("#email").attr("readonly","true");
		} else{
			$("#hidden_form").css("display","none");
		}
	})
</script>
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
    #val{float: right;}
    h2{padding:15px}
    i{padding:20px}
</style>
</head>
<body>
<form action="join.member" method="post">
<div class="container shadow bg-white rounded">
	<h2 class="text-center">회원가입</h2>
    <center><div>
        <input type="radio" style="width: 5%;" name="position" value="student" checked>학생
        <input type="radio" style="width: 5%;" name="position" value="teacher">강사
    </div></center>
    <hr>    
    <div>
        <i class="far fa-envelope"></i>
        <input  type="email" name="email" id="email" value="${email }" placeholder="이메일">
        <c:if test="${email==null }">
        <input type="password" id="hidden">
        <button class="btn btn-outline-info" id="confirm" type = button>인증번호발송</button>
    </div>
    <div>
        <i class="far fa-paper-plane"></i>
        <input  type=text name="check" id="check" placeholder="인증번호입력"><input type="password" id="hidden">
        <button class="btn btn-outline-info" id="msg" type="button">인증번호확인</button>
    </div>
    	</c:if>
    <div id="hidden_form">
    	<div>
        	<i class="fas fa-unlock"></i>
        	<input type=password id="pw" name="pw" placeholder="비밀번호 특수문자 / 문자 / 숫자 포함 8~15자리" required>
    	</div>
    	<div id="c">
        	<i class="fas fa-check"></i>
        	<input type=password id="ch" placeholder="비밀번호확인" style="width:25%;" required>
        	<div id="val"></div>
    	</div>
    	<div>
        	<i class="fas fa-user-edit"></i>
        	<input type=text name="name" placeholder="이름" required>
    	</div>
    	<div>
        	<i class="fas fa-mobile-alt"></i>
        	<input type=text name="phone" id="phone" placeholder="핸드폰 ex)01011111111" required>
    	</div>
    	<center><div>
    	</div>
        	<input type="submit" class="btn btn-outline-info w-25" value="가입하기">
        	<input type="reset" class="btn btn-outline-info w-25" value="취소하기">
        	<a href="${pageContext.request.contextPath}/index.jsp"><input type="button" class="btn btn-outline-info w-25" value="뒤로가기"></a>
    	</div></center>

</div>
</form>	
</body>
</html>	
</body>
</html>