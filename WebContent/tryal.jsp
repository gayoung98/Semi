<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<script>
 $(function(){
	 
	 $("#write").on("click",function(){
		 location.href="assignment/assWrite.jsp";
	 })
	 $("#list").on("click",function(){
		 location.href="list.ass?currentPage=1";
	 })
	 
 })
 </script>
</head>
<body>
	<button id="list">List</button><br>
	<button id="write">Write</button><br>

<!-- 	
	<button>Assignment List</button><br>
	<button>Assignment List</button><br>
	<button>Assignment List</button><br>  -->
	
</body>
</html>