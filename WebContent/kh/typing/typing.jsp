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
	text-align: center;
}

body {
	background-color: #D8E3E7;
}

.container {
	margin-top: 80px;
}

.row {
	text-align: center;
	margin-bottom: 4%;
}

.chart {
	background-color: white;
	border: 2px solid grey;
	box-shadow: 3px 3px 3px grey;
	margin-left: 15%;
	margin-right: 15%;
}

.bar {
	display: inline-block;
	position: relative;
	background-color: cornflowerblue;
}

input {
	height: 100%;
	width: 100%;
}

.record {
	padding: 5px;
	border: 2px solid grey;
	margin-left: 15%;
	margin-right: 15%;
	box-shadow: 3px 3px 3px grey;
}

.navbar>.container-fluid {
	padding: 0px;
}

.navbar-nav {
	flex-grow: 1;
	justify-content: space-around;
}

.slide {
	position: absolute;
	width: 100%;
	height: 50px;
	top: 100%;
	background-color: #55555550;
}
</style>

<script type="text/javascript">
	$(function() {		
						
				$(document).on('click', '#navbarDropdownMenuLink', function() {
							   if($(this).siblings($(".dropdown-menu")).css("display") == "none"){
							 	  $(this).siblings($(".dropdown-menu")).css("display",'block')
							 	  for(let i =0; i<$(".dropdown-menu").length; i++){
							 		 if(($($(".dropdown-menu")[i]).text() !== $(this).siblings($(".dropdown-menu")).text())){
							 			$($(".dropdown-menu")[i]).css("display","none");
							 		}
							 	  }
							   }else{
							 	  $(this).siblings($(".dropdown-menu")).css("display",'none')
							   }
							})					
						

	})
</script>

</head>
<body>
	<jsp:include page="/header.jsp" />
	<jsp:include page="/navibar.jsp" />
	<div class="container p-4 shadow bg-white rounded">
		<div class="row header">
			<h2><b>타자기록</b></h2>
		</div>

		<c:choose>
			<c:when test="${recentList!=null}">
				<div class="row chart">

					<c:forEach var="item" items="${recentList}">
						<div class=col-1
							style="display: table-cell; vertical-align: bottom;">
							<div class=row
								style="height: 300px; display: table-cell; vertical-align: bottom;">
								<div class=bar style="height:${item.record}px;">${item.record}</div>
							</div>
							<div class=row style="text-align: right;">${item.reg_date }</div>
						</div>
					</c:forEach>


				</div>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>

		<form action="write.typ" method="post">
			<div class="row record" style="text-align: center;">

				<div class="col-8 recbox">
					<div class="row rec" style="width: 100%; height: 90%;">

						<div class="col-6">타자</div>
						<div class="col-6">정확도</div>


						<div class="col-6">
							<input type="text" name="record" id="record" pattern="^[1-1000]+$">
						</div>
						<div class="col-6">
							<input type="text" name="accuracy" id="record" pattern="^[1-100]+$">
						</div>

					</div>
				</div>

				<div class="col-4">
					<div class="row btn" style="width: 100%; height: 100%;">
						<button type="submit" class="btn btn-primary"
							style="width: 100%; height: 100%;">제출</button>
					</div>
				</div>

			</div>
		</form>
		<div class="row footer"></div>
	</div>


</body>
</html>