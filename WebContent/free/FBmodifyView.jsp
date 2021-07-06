<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modify Result</title>
</head>
<body>
	
	<script>
	<c:choose>
	<c:when test="${view!=null}">
		alert("게시글 수정에 성공하셨습니다.");
		</c:when>
		<c:otherwise>
		alert("다시 확인해주세요.");
		</c:otherwise>
		</c:choose>
	location.href = "/list.fboard?cpage=1";
	</script>
</body>
</html>