<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<style>
* {
	box-sizing: border-box;
}

div {
	border: 1px solid black;
}

.row {
	text-align: center;
}
.rRecord{
	display: inline-block;
	width: 50%;
}

input {
	height: 100%;
	width: 100%;
}

button {
	height: 100%;
	width: 100%;
	color: cornflowerblue;
}
</style>
<script>
$(function(){
	let record = $(".rRecord").attr("record");
	
	$(".rRecord").css("height":record);
})
</script>
</head>
<body>
	<div class="container">
		<div class="row header">
			<h2>타자기록</h2>
		</div>
		<div class="row chart">
			<div class="col-2"></div>
			<div class="col-8">
				<c:forEach var="item" items="${recentList}">
					<div class="row cRecord">
						<div class="col-1">
						<div class="rRecord" record="${item.record}">${item.record}</div>
						</div>
						
					</div>


					<div class="row cDate">
						<div class="col-1">${item.reg_date }</div>

					</div>
				</c:forEach>
			</div>
			<div class="col-2"></div>
		</div>

		<form action="write.typ" method="post">
			<div class="row record" style="text-align: center;">
				<div class="col-2"></div>
				<div class="col-6 recbox">
					<div class="row rec">

						<div class="col-6">타자</div>
						<div class="col-6">정확도</div>


						<div class="col-6">
							<input type="text" name="record" id="record">
						</div>
						<div class="col-6">
							<input type="text" name="accuracy" id="record">
						</div>

					</div>
				</div>
				<div class="col-2">
					<button type="submit">제출</button>
				</div>
				<div class="col-2"></div>
			</div>
		</form>
		<div class="row footer"></div>
	</div>
</body>
</html>