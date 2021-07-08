<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
   <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
    
    <script>
    let callPopUp = function(e){
		window.open("${pageContext.request.contextPath}/inquired.mp?seq="+e, "inquired", 'width=550px,height=600px,scrollbars=no,resizable=no');
	}	
    $(function(){
    
    	$("#inquired_btn").on("click",function(){
    		let popup = window.open('${pageContext.request.contextPath}/inquired/popup.jsp', 'inquire', 'width=550px,height=600px,scrollbars=no,resizable=no');
    	})
    	
    	 $("#close_popup").on("click", function(){
	        parent.window.close();
	    })
    })
    
    
    
    </script>
    
    <style type="text/css">
    .btn_container{
	    float: right;
	    margin-right:3%;
	}
    
    </style>
    
</head>
<body>
<div class = "container-fluid">
  <table class="table">
  <thead class="thead-light">
    <tr>
      <th scope="col">접수 번호</th>
      <th scope="col">카테 고리</th>
      <th scope="col" colspan =2>문의 내용</th>
      <th scope="col">접수 날짜</th>
      <th scope="col">답변 상태</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach var="item" items="${inquiredList }">
    <tr>
      <th scope="row">${item.seq }</th>
      <td>
      <c:choose>
				<c:when test="${inquired.major_category == site}">
					 사이트 관련
				</c:when>
					<c:when test="${inquired.major_category == board}">
					 게시판 관련
					</c:when>
					<c:when test="${inquired.major_category == institute}">
					 학원 관련
					</c:when>
					<c:otherwise>
						기타
					</c:otherwise>
				</c:choose>
      </td>
      <td colspan =2><a href ="javascript:callPopUp(${item.seq})">${item.contents }</a></td>
      <td>${item.reg_date }</td>
      <td>
      <c:choose>
      <c:when test="${item.recomment==null }">
     	<span class="badge badge-secondary">답변 대기</span>
      </c:when>
      <c:otherwise>
      	<span class="badge badge-danger">답변 완료</span>
      </c:otherwise>
      </c:choose>
      </td>
    </tr>
    </c:forEach>
  </tbody>
</table>
	<div class ="btn_container">
                <button type="button" class="btn btn-light" id="inquired_btn" >문의하기</button>
                <button type="button" class="btn btn-danger" id="close_popup">닫기</button>
            </div>
            
            </div>
</body>
</html>