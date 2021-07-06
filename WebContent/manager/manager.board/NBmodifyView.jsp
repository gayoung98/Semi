<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Join Result</title>
</head>
<body>
	
	<script>
	<c:choose>
	<c:when test="${view!=null}">
		alert("게시글 수정에 성공하셨습니다.");
		location.href = "${pageContext.request.contextPath}/noticeList.manager?currentPage=${page}&branch=${branch}&category=${category}&search=${search}";
		</c:when>
		<c:otherwise>
		alert("다시 확인해주세요.");
		</c:otherwise>
		</c:choose>
		
	</script>
</body>
</html>