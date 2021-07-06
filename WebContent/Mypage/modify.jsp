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
      let phoneRegex = /^010-?\d{3,4}-?\d{4}$/;
      
     /*  document.getElementById("ch").oninput = function(){
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
      }) */
   }
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
<form action="${pageContext.request.contextPath}/update.mp" method="post" enctype="multipart/form-data">
<div class="container shadow bg-white rounded">
   <h2 class="text-center">회원 정보</h2>
    <hr> 
    <div>
    <i class="far fa-images"></i>
    <input  type="file" name="profile" id="profile">
    </div>
    <div>
        <i class="far fa-envelope"></i>
        <input  type="email" name="email" id="email" value="${member.email }" readonly>
    </div>
    <div>
        <i class="far fa-address-card"></i>
        <input  type="text" name="id" id="id" value="${member.id }" readonly>
    </div>
        <div>
        <i class="fas fa-school"></i>
          <c:choose>
	                            <c:when test="${member.branch=='J' }">
	                            	<c:set var="branch" value="종로점"/>
	                            </c:when>
	                             <c:when test="${member.branch=='D' }">
	                            	<c:set var="branch" value="당산점"/>
	                            </c:when>
	                            <c:otherwise>
	                            	<c:set var="branch" value="강남점"/>
	                            </c:otherwise>
                            </c:choose>
        <input  type="text" name="branch_class" id="branch_class" value="${branch} ${member.khClass } 클래스" readonly>
    </div>
    <div id="modify_form">
       <div>
           <i class="fas fa-unlock"></i>
           <input type=password id="pw" name="pw" placeholder="비밀번호 특수문자 / 문자 / 숫자 포함 8~15자리">
       </div>
       <div id="c">
           <i class="fas fa-check"></i>
           <input type=password id="ch" placeholder="비밀번호확인" style="width:25%;">
           <div id="val"></div>
       </div>
       <div>
           <i class="fas fa-user-edit"></i>
           <input type=text name="name" placeholder="이름" value = "${member.name }">
       </div>
       <div>
           <i class="fas fa-mobile-alt"></i>
           <input type=text name="phone" id="phone" placeholder="핸드폰" value = "${member.phone}">
       </div>
      <div><center>
           <input type="submit" class="btn btn-outline-info w-25" value="수정하기">
           <a href="${pageContext.request.contextPath}/mypage.mp"><input type="button" class="btn btn-outline-info w-25" value="뒤로가기"></a>
       </center></div>
		
</div>
</form>   
</body>
</html>   
</body>
</html>