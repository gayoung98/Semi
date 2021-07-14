<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    

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
  <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
<script>
	$(function(){
		var date = new Date();
		var todaytarget = $(".card-title");
		for(var i=0; i<todaytarget.length; i++){
			if(($(todaytarget[i]).text() == date.getDate()) && ((date.getMonth()+1)==$("#month").text().substr(0,1)) ){
				$(todaytarget[i]).parents("#day").css("background-color","gray")
								 .css("color","white");
			}
		}
		
		let event_schedual = $(".card-body");
		for(var i =0; i< event_schedual.length; i++){
			switch ($(event_schedual[i]).attr("id")) {
				case "exam":$(event_schedual[i]).parents("#day").css({"background-color":"red","color":"white"});
				  break;
				case "rest":$(event_schedual[i]).parents("#day").css({"background-color":"black","color":"white"});
				  break;
				case "begin":$(event_schedual[i]).parents("#day").css({"background-color":"green","color":"white"});
				  break;
				case "end":$(event_schedual[i]).parents("#day").css({"background-color":"yellow","color":"black"});
				  break;
				case "enquete":$(event_schedual[i]).parents("#day").css({"background-color":"dodgerblue","color":"white"});
				  break;
			}
		}
	})
</script>


<style>
#t_body{
width:100px;
height:50px;}
  .container{
  	margin-top:6%;
  }
  .card-body{
        height: 50px;
        padding :0px;
        font-size: 13px;
    }
    .table-responsive{
        width:60%;
        table-layout : fixed;
    }
    div[class*=col]{
    margin:0px; 
    padding:0px;
    text-align:center;
    line-height: 50px;
    }
     .navbar>.container-fluid {
            padding: 0px;
        }

        .navbar-nav {
            flex-grow: 1;
            justify-content: space-around;
        }
        
        .navbar{
        height:56px;
        }
        
        .dropdown-menu{
        width:158px;
        height:72px;
          line-height: 22px;
        }

        .slide {
            position: absolute;
            width: 100%;
            height: 50px;
            top: 100%;
            background-color: #55555550;
        }
        body {background-color: #D8E3E7;}
</style>
</head>
<body>
<jsp:include page="/navibar.jsp"></jsp:include>
  <div class="container table-responsive shadow bg-white rounded">
  		<div class =row>
	  		<div class = col-3><a href= "${pageContext.request.contextPath}/calander.main?change_month=${month-2}"><i class="fas fa-chevron-left"></i></a></div>
	  		<div class = col-6><h1 id = "month" value = ${month }>${month }월</h1></center></div>
	  		<div class = col-3><a href= "${pageContext.request.contextPath}/calander.main?change_month=${month}"><i class="fas fa-chevron-right"></i></a></div>
  		</div>
        <table class="table align-middle">
          <thead>
            <tr>
                <th width ="14.28%">Sun</th>
                <th width ="14.28%">Mon</th>
                <th width ="14.28%">Thu</th>
                <th width ="14.28%">Wen</th>
                <th width ="14.28%">Thur</th>
                <th width ="14.28%">Fri</th>
                <th width ="14.28%">Sat</th>
            </tr>
          </thead>

          <tbody id=t_body>
			<tr>
			<c:set var="record" value ="0"/>          	
          	<c:forEach begin="1" end="7" varStatus="i" >
    			      <c:choose>
    			      	<c:when test="${i.current>=day.get(0)}">
    			      	<c:set var="record" value ="${ date.get(i.current-day.get(0))}"/>
    			    		<td id=day height=100 style="table-layout:fixed">
                        	 <h5 class="card-title">${record}</h5>
                        	 <c:if test="${list.size() !=0}">
    						<c:forEach begin="0" end="${list.size()-1}" varStatus="z" step="1">
          						<c:if test="${list.get(z.current).get(date.get(record-1))!=null}">
          							<div class ="card-body" id ="${list.get(z.current).get(date.get(record-1))[1]}">
                        	 		<i class="far fa-check-circle"></i>${list.get(z.current).get(date.get(record-1))[0]}
                        	 		</div>
          						</c:if>       						
          					</c:forEach>
          					</c:if>
    			    		</td>
    			      	</c:when>
    			      	<c:otherwise>
    			      		<td></td>
    			      	</c:otherwise>
    			      </c:choose>
          	</c:forEach>
          	</tr>
         <c:forEach begin="${record}" end="${date.size()}" step="7" varStatus="i">
          	<tr>
          		<c:forEach begin="${i.current}" end ="${record+i.current+day.get(0)-2}" step="1" varStatus="j">
          			<td id=day height=100 style="table-layout:fixed"> 
          				<c:if test="${j.current < date.size()}">
          				<h5 class="card-title">${date.get(j.current)}</h5>
          				<c:if test="${list.size() !=0}">
          					<c:forEach begin="0" end="${list.size()-1}" varStatus="z" step="1">
          						<c:if test="${list.get(z.current).get(date.get(j.current))!=null}">
          							<div class ="card-body" id ="${list.get(z.current).get(date.get(j.current))[1]}">
                        	 		<i class="far fa-check-circle"></i> ${list.get(z.current).get(date.get(j.current))[0]}
                        	 		</div>
          						</c:if>         						
          					</c:forEach>
          				</c:if>
          				</c:if>
          			</td>
          		</c:forEach>
          	</tr>
          	</c:forEach> 
         
          </tbody>
        </table>
      </div>
</body>
</html>