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
</head>

<style>
 table{
 width:800px;
 heigth:600px;
 line-height: 50px;
 text-align: center;
 }
 
 img{
 width:200px;
 height:150px;
 }
 .btn_container{
	    float: right;
	    position: relative;
	    right:5%;
	}
</style>

<script>

$(function(){
$("#close_popup").on("click", function(){
    parent.window.close();
})
})
</script>
<body>

	<div class = container>
	<table class="table table-bordered">
	  <tbody>
	  	<tr>
	      <td scope="row" rowspan="4" colspan =2>
	      	<img src ="${profile_img }">
	      </td>
	    </tr>
	    <tr>
	      <th>이름</th>
	      <td>${member.name }</td>
	      </tr>
	    <tr>
	      <th>학생번호</th>
	      <td>${member.id }</td>
	    </tr>
	    <tr>
	      <th>연락처</th>
	      <td>${member.phone }</td>
	    </tr>
	   	<tr>
	      <th>과정명</th>
	      <td colspan="3">[NCS]디지털컨버전스 융합 응용SW 개발자 양성과정</td>
	    </tr>
	    <tr>
	      <th>지점 및 클래스명</th>
	      <td colspan="3">
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
	       ${branch} ${member.khClass } 클래스
	      </td>
	    </tr>
	      <tr>
	      <th>담당 강사님 및 취업 담당자</th>
	      <td colspan="3">${teacher.name } | 최미교 취업 담당</td>
	    </tr>
  </tbody>
</table>
</div>
 	<div class ="btn_container">
      <button type="button" class="btn btn-danger" id="close_popup">Close</button>
     </div>
</body>
</html>