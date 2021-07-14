<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
	integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
	crossorigin="anonymous" />
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

    <style>
    	@import url(https://fonts.googleapis.com/css?family=Varela+Round);
* {
	margin: 0;
	padding: 0;
}

.container{
	margin-top :5%;
	background-color:white;
}

body {
	min-height: 500px;
	background-color: #D8E3E7;
}


@import url(https://fonts.googleapis.com/css?family=Varela+Round);
.navbar>.container-fluid {
            padding: 0px;
        }

        .navbar-nav {
            flex-grow: 1;
            justify-content: space-around;
        }

        .slide {
        	z-index:1;
            position: absolute;
            width: 100%;
            height: 50px;
            top: 100%;
            background-color: #00ff0000;
        }
a{text-decoration:none;color: black;font-weight: bold;}

        
        body{
        	text-align:center;
        }
		
		.day{
			margin-top : 7%;	
			margin-bottom : 3%;
			padding-top : 5%;
		}
        .day>div{
            display: inline-block;
            width: 10%;
            font-size : 35px;
            margin-left : 2%;
            margin-right : 2%;
        }
        .fas {
            font-size: 150%;
            text-align: center;
        }
        .seat {
            background-color: white;
            font : 'Nanum Gothic', 'Malgun Gothic', sans-serif;
        }

        div {
            width: 700px;
            font-size: 50px bold;
        }
        table{
        	margin-top : 2%;
        }
        .noselect{
        	background-color:rgb(47, 47, 47);
        	color: #fff;
  			font-family: 'Roboto', Gmarket Sans, sans-serif;
  			font-size: 2em;
  			font-weight: bold;
  			text-align: center;
 			width: 40px;
 			height: 40px;
        }
        
        .week>a{
        	list-style-type: none;
        	font-family: 'Nanum Gothic', 'Malgun Gothic', sans-serif;
        	letter-spacing: 0.1px;
        	font-weight: 800;
        }
        .week:hover a{
        	cursor: pointer;
        	color:#8b8686;
        }
        .seat:hover {
        	cursor : pointer;
        }
        .tbcenter{
        	font-weight: 700;
            letter-spacing: 0.03em;
            font-family: 'Roboto', Gmarket Sans, sans-serif;
            font-size:18px;
        }
        .letter{
        	font-size: 25px;
            font-weight: 700;
            letter-spacing: 0.03em;
            font-family: "Varela Round", sans-serif;
        }
        #choose_seat{
        	height : 60px;
        }
    </style>

		<script>
        $(function () {
     
        	$.ajax({
        		url: "${pageContext.request.contextPath}/complete.seat",
        		dataType: "JSON"
        	}).done(function(result){
        		if(result == "mo"){
        			$("#"+result).css("font-size","40px")
        		}else if(result == "tu"){
        			$("#"+result).css("font-size","40px")
        		}else if(result == "we"){
        			$("#"+result).css("font-size","40px")
        		}else if(result == "th"){
        			$("#"+result).css("font-size","40px")
        		}else if(result == "fr"){
        			$("#"+result).css("font-size","40px")
        		}else{
        		for(var i=0; i<result.length; i++){
        			if(result[i].email == $("#writer").val()){
        				$("#"+result[i].seat_number).css("background-color","#86c4dae7")
        			}else{
        			$("#"+result[i].seat_number).css("background-color","#707070")
        			}
        			$("#"+result[i].seat_number).attr("data-Ischoose","true")
        			$("#"+result[i].seat_number).html(result[i].name)
        			$("#"+result[i].seat_day).css("font-size","40px")
        			$("#"+result[i].seat_day).css("background-color","#D8E3E7")
        			$("#"+result[i].seat_day).css("border-radius","49%")
        			$("#"+result[i].seat_day).css("border-radius","49%")
        			
        		}
        	}
        	})
        	
        	
            let sub = 0;
            let before_seat = null;
            for (let index = 1; index <= 18; index += 3) {
                let tr = $("<tr>");
                for (let index2 = index; index2 < index + 3; index2++) {
                    let number = index2 - sub;
                    if(number % 2 == 1){
                    let td = $("<td class=seat data-seat=A" + number + " data-Ischoose= false id=A"+number+" align=center>")
                    td.append("<i class=\"fas fa-desktop\">" + "<br>" + "A" + number)
                        td.css("width", "100");
                    
                    tr.append(td);
                    }else {
                    	let td = $("<td class=noselect data-seat=A" + number + " data-Ischoose= false align=center>")
                        //td.append("<i class=\"fas fa-desktop\">" + "<br>" + "A" + number)
                        td.append("X")
                            td.css("width", "100");
                        
                        tr.append(td);
                    }
                }
                $(".left").append(tr);
            }
            let sub2 = 0;
            for (let index3 = 1; index3 <= 12; index3 += 2) {
                let tr = $("<tr>");
                for (let index4 = index3; index4 < index3 + 2; index4++) {
                    let number = index4 - sub;
                    if(number % 4 == 0 || number % 4 == 1){
                    	 let td = $("<td class=seat data-seat=B" + number + " data-Ischoose= false id=B"+number+" align=center>")
                         td.append("<i class=\"fas fa-desktop\">" + "<br>" + "B" + number)
                             td.css("width", "100");
                         
                         tr.append(td);
                    }else{
                    	 let td = $("<td class=noselect data-seat=B" + number + " data-Ischoose= false align=center>")
                         //td.append("<i class=\"fas fa-desktop\">" + "<br>" + "B" + number)
                         td.append("X")    
                         td.css("width", "100");
                         
                         tr.append(td);
                    }
                    
                }
                $(".right").append(tr);
            } 
			
            $(".noselect").on("click",function(){
            	alert("선택할 수 없는 좌석입니다.")
            })
            
            
       
            $(document).on("click",".seat",function(){
            	if($(this).attr("data-Ischoose")==="false"){
            		if(confirm("해당 좌석을 예약하시겠습니까?")){
	                $.ajax({
	                	url: "${pageContext.request.contextPath}/reserve2.seat",
	                	data: {"seatNumber":$(this).attr("id")}
	                }).done(function(result){
	                	console.log(result)
	                	if(result == "already"){
	                		alert("이미 다른 좌석을 예약하셨습니다.")
	                	}else if(result == "corona"){
	                		alert("해당 요일은 이미 14명이 신청하였습니다.")
	                	}else{
	                		$("#"+result).css("background-color","#86c4dae7");
		                	$("#"+result).attr("data-Ischoose","true");
		                	$("#"+result).html("예약완료")
		                	alert("예약완료")
	                		
	                	}
	                	
	                })
            		}
                }else{
                	if(confirm("예약을 취소하시겠습니까?")){
                	$.ajax({
	                	url: "${pageContext.request.contextPath}/reserve2.seat",
	                	data: {"cancelSeat":$(this).attr("id")}
	                }).done(function(result){
	                	if(result == "notmyseat"){
	                		alert("다른 사람이 먼저 예약한 자리입니다.")
	                	}else{
	                			$("#"+result).css("background-color","white");
			                	$("#"+result).attr("data-Ischoose","false");
			                	$("#"+result).html("<i class=\"fas fa-desktop\">" + "<br>" + result)
			                	alert("예약이 취소되었습니다.")
	                		
	                	}
	                })
                }
                }
            	
            })
            
            })
            
        
    </script>
</head>
<body>
<jsp:include page= "/navibar.jsp" />
<input type=hidden id=length value="${list}">
<input type = "hidden" id="writer" value = "${login }">
	<div class="container shadow bg-white rounded">
    <center>
        <div class="day">
            <div class="week shadow bg-#D8E3E7 rounded" id="mo"><a href = "${pageContext.request.contextPath}/date.seat?date=mo">월</a></div>
            <div class="week shadow bg-#D8E3E7 rounded" id="tu"><a href ="${pageContext.request.contextPath}/date.seat?date=tu">화</a></div>
            <div class="week shadow bg-#D8E3E7 rounded" id="we"><a href ="${pageContext.request.contextPath}/date.seat?date=we">수</a></div>
            <div class="week shadow bg-#D8E3E7 rounded" id="th"><a href ="${pageContext.request.contextPath}/date.seat?date=th">목</a></div>
            <div class="week shadow bg-#D8E3E7 rounded" id="fr"><a href ="${pageContext.request.contextPath}/date.seat?date=fr">금</a></div>
        </div>
        <div class="letter">
        [우리반 좌석 예약]
        </div>
        <input type = hidden id =temp>
        <table border="1" width=700 id=table2>
            <tr>
                <td colspan="3" align="left">
                    <i class="fas fa-door-open" style="font-size: 200%;"></i>출구
                </td>
                <td></td>
                <td colspan="2" align="">
                    <i class="fas fa-chalkboard-teacher">강사님 자리</i>
                </td>
            </tr>
            <tr class="tbcenter">
                <td colspan="3" align="center" class="left">
                </td>
                <td></td>
                <td colspan="2" align="center" class="right">
                </td>
            </tr>
        </table>
        <div id=choose_seat>
        </div>
    </center>
    </div>
    
</body>
</html>