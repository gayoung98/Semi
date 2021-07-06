<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Delete Result</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
</head>
<body>
<script>
	<c:choose>
	<c:when test="${view==null}">
		alert("게시글 삭제되었습니다.");
		location.href = "${pageContext.request.contextPath}/noticeList.manager?currentPage=${page}&branch=${branch}&category=${category}&search=${search}";
		</c:when>
		<c:otherwise>
		alert("다시 확인해주세요.");
		</c:otherwise>
		</c:choose>
	
	</script>
</body>
</html>