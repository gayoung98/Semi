<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>reportResult</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js"></script>
 
<style>
*{
	margin:20px;
}

.fs-2{
font-weight: 800;}

</style>
<script>
$(function(){
	$("#back").on("click", function(){
		
		location.href ="${pageContext.request.contextPath}/kh/free/reportPop.jsp";
	})

</script>
</head>
<body>
<center>
<p class="fs-2">해당 게시글에 대한 신고가 접수 되었습니다.</p>
<div class ="btn_container">
	 			<button type="button" class="btn btn-dark" id="cancel" onclick="self.close();">닫기</button>
     </div>
</center>

</body>
</html>