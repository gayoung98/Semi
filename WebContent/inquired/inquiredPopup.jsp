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

<script>
$(function(){
	
	   $("#close_popup").on("click", function(){
	        parent.window.close();
	    })
	
})

</script>

</head>

<body>

	<table class="table">
	<tbody>
		<tr>
			<th scope="row">접수번호 </th>
			<td>No.${inquired.seq }</td>
		</tr>
		<tr>
			<th scope="row">분류 </th>
			<td>
				<c:choose>
					<c:when test="${inquired.major_category == 'site'}">
					 사이트 관련
					</c:when>
					<c:when test="${inquired.major_category == 'board'}">
					 게시판 관련
					</c:when>
					<c:when test="${inquired.major_category == 'institute'}">
					 학원 관련
					</c:when>
					<c:otherwise>
						기타
					</c:otherwise>
				</c:choose> >
				<c:choose>
					<c:when test="${inquired.sub_category == 'error'}">
					 오류/버그
					</c:when>
					<c:when test="${inquired.major_category == 'complain'}">
					 불만 관련
					</c:when>
					<c:when test="${inquired.major_category == 'suggest'}">
					 건의 관련
					</c:when>
					<c:otherwise>
						기타
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<th scope="row" colspan=2>문의 내용 
			 <div class="form-group">
                <div class="form-control" style ="height:200px">${inquired.contents }</textarea>
                </div>
			</th>
		</tr>
		<c:if test=""></c:if>
		<tr>
			<th scope="row" colspan=2>답변
			 <div class="form-group">
                <div class="form-control"  style ="height:100px">${inquired.recomment }</div>
                </div>
			</th>
		</tr>
		</tbody>
	</table>
			<div class ="btn_container">
                <button type="submit" class="btn btn-light" id="submit_btn" >Submit</button>
                <button type="button" class="btn btn-danger" id="close_popup">Cancel</button>
            </div>
</body>
</html>