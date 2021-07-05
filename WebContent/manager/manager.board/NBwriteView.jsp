<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>

</head>
<body>
	<script>
	<c:choose>
	<c:when test="${boardlist!=null}">
		alert("게시글 작성에 성공하셨습니다.");
		</c:when>
		<c:otherwise>
		alert("다시 입력해주세요.");
		</c:otherwise>
		</c:choose>
	location.href = "/list.manager?cpage=1";
	</script>
</body>
</html>