<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <script
  src="https://code.jquery.com/jquery-3.6.0.js"
  integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
  crossorigin="anonymous"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>

<style>
.container{max-width:720px}
 .btn_container{
	    float: right;
	    position: relative;
	    right:5%;
	}
</style>

<script>
$(function(){
	
	$(document).on("click","#detailST",function(){
		window.open("${pageContext.request.contextPath}/detailStudent.mp?email="+$(this).val(),"detail", 'width=1024px,height=568px,scrollbars=no,resizable=no');
	})
	
	$("#close_popup").on("click", function(){
	    parent.window.close();
	})

})


</script>

</head>
<body>
<div class = container>
	<table class="table table-bordered">
  <thead>
  	<tr>
      <td scope="row">회차</td>
      <td colspan=4>1회차</td>
    </tr>
    <tr>
      <td scope="row">과정명</td>
      <td colspan=4>디지털 컨버전스 자바 양성 과정</td>
    </tr>
    <tr>
      <td scope="row">수강 기간</td>
      <td colspan=4>${calander[0] }~${calander[1] }</td>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th scope="row" colspan =5>학생 명단</th>
    </tr>
    <tr>
      <th scope="row">학생번호</th>
      <th scope="row">이름</th>
      <th scope="row">휴대폰번호</th>
      <th scope="row">이메일</th>
      <th scope="row">상세보기</th>
    </tr>
    <c:forEach var="item" items="${studentList}">
    <tr>
      <td>${item.id}</td>
      <td>${item.name}</td>
      <td>${item.phone}</td>
      <td>${item.email}</td>
      <td><button class ="btn btn-link" id= detailST value =${item.email }>상세보기</button></td>
    </tr>
	</c:forEach>
  </tbody>
</table>
</div>
<div class ="btn_container">
       <button type="button" class="btn btn-danger" id="close_popup">Cancel</button>
</div>
</body>
</html>