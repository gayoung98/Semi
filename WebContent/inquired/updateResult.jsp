<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:choose>
	<c:when test="${update_result !=null || delete_result !=null }">
		<script>
			if(!alert("업데이트 되었습니다!")) parent.window.close();
		</script>
	</c:when>
	<c:otherwise>
		<script>
			if(!alert("오류가 발생되었습니다.")) location.href = "${pageContext.request.contextPath}/updateRequired.mp?seq="+${seq};
		</script>
	</c:otherwise>
</c:choose>

</body>
</html>