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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/seat/seat.css">
    

		<script>
        $(function () {
         /* $(document).on("click",".week",function(){
            	console.log($(this).attr("id"))
            	date = $(this).attr("id");
            	/* location.href = "${pageContext.request.contextPath}/complete.seat?date="+date */
           /* $.ajax({
        		url: "${pageContext.request.contextPath}/complete.seat",
        		dataType: "JSON"
        	}).done(function(result){
        		for(var i=0; i<result.length; i++){
        			$("#"+result[i].seat_number).css("background-color","rgb(252, 255, 53)")
        			$("#"+result[i].seat_number).attr("data-Ischoose","true")
        			$("#"+result[i].seat_number).html(result[i].name)
        	}
        	})
            }) */
         	
        	$.ajax({
        		url: "${pageContext.request.contextPath}/complete.seat",
        		dataType: "JSON"
        	}).done(function(result){
        		for(var i=0; i<result.length; i++){
        			$("#"+result[i].seat_number).css("background-color","rgb(255, 255, 166)")
        			$("#"+result[i].seat_number).attr("data-Ischoose","true")
        			$("#"+result[i].seat_number).html(result[i].name)
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
			
            /* $(document).on("click", ".seat", function () {
            	//confirm("해당 좌석을 예약하시겠습니까?")
                location.href="${pageContext.request.contextPath}/reserve.seat?seat_number=" + "11";
            }) */
            $(".noselect").on("click",function(){
            	alert("선택할 수 없는 좌석입니다.")
            })
            
            
       
            $(document).on("click",".seat",function(){
    //        	console.log($(this).attr("id"));
      //      	console.log($(this).attr("data-Ischoose"));
            	if($(this).attr("data-Ischoose")==="false"){
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
	                		if(confirm("해당 좌석을 예약하시겠습니까?")){
	                		$("#"+result).css("background-color","rgb(255, 255, 166)");
		                	$("#"+result).attr("data-Ischoose","true");
		                	$("#"+result).html("예약완료")
		                	alert("예약완료")
	                		}else{
	                			location.href = "${pageContext.request.contextPath}/confirmReserve.seat?seat_number="+result;
	                		}
	                	}
	                	
	                })	
                }else{
                	$.ajax({
	                	url: "${pageContext.request.contextPath}/reserve2.seat",
	                	data: {"cancelSeat":$(this).attr("id")}
	                }).done(function(result){
	                	if(result == "notmyseat"){
	                		alert("다른 사람이 먼저 예약한 자리입니다.")
	                	}else{
	                		if(confirm("예약을 취소하시겠습니까?")){
	                			$("#"+result).css("background-color","white");
			                	$("#"+result).attr("data-Ischoose","false");
			                	$("#"+result).html("<i class=\"fas fa-desktop\">" + "<br>" + result)
			                	alert("예약이 취소되었습니다.")
	                		}else{
	                			location.href = "${pageContext.request.contextPath}/confirmCancel.seat?seat_number="+result;
	                		}
	                	}
	                })
                }
            	
            	
            })
            
        //console.log(date);
            	
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
            <div class="week" id="mo"><a href = "${pageContext.request.contextPath}/date.seat?date=mo">월</a></div>
            <div class="week" id="tu"><a href ="${pageContext.request.contextPath}/date.seat?date=tu">화</a></div>
            <div class="week" id="we"><a href ="${pageContext.request.contextPath}/date.seat?date=we">수</a></div>
            <div class="week" id="th"><a href ="${pageContext.request.contextPath}/date.seat?date=th">목</a></div>
            <div class="week" id="fr"><a href ="${pageContext.request.contextPath}/date.seat?date=fr">금</a></div>
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