<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
   <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
<style>
.container{height: 1200px; margin-top: 3%;}
.card-img-top{width: 150px; text-align: center; margin: auto;}
.btn-primary{width: 49%; font-size: 16px;}
.div[class *=col]{margin: 5px; padding: 0px}
.alert{width:50%; text-align: left;}
legend {
    width: 50%;
    background-color: #000;
    color: #fff;
    text-align: center;
}
#graph{color: black; border: 1px solid black}
.graph{width: 100%; height: 180px; position:relative; top: 15%; overflow: hidden;}
.figure{position: inherit; color: white; background-color: red;height: 25px; text-align: center; line-height: 50px;}
.figure_BG{position:relative;width: 99%; height: 25px; background-color: rgba(255,0,0,0.1); top:35%;}
fieldset{border: 1px solid black;}
.row{margin-bottom: 5%;}
.profilebox{
    width: 150px;
    height: 150px; 
    border-radius: 30%;
    overflow: hidden;
    margin-top: 5%;
    margin-left: 5%;
}
.updatebox{
width:100%;

}
.btn{
position:absolute;
top:65%;
}
#profile{
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.card-img-top{
width:100%;
}
.picture_modify{
float:left;
position:absolute;
right:0;
}
body {background-color: #D8E3E7;}
   .navbar>.container-fluid {
            padding: 0px;
        }

        .navbar-nav {
            flex-grow: 1;
            justify-content: space-around;
        }

        .slide {
        	z-index:2;
            position: absolute;
            width: 100%;
            height: 50px;
            top: 100%;
            background-color: #55555550;
        }
        
        h2{display:inline;}
.card-header> a{
float: right;
margin-top: 2%;
}        
</style>

<script>
let callPopUp = function(){
	$(".card-title").on("click", function(){
		
		switch ($(this).attr("name")) {
		case "inquired":
			window.open("${pageContext.request.contextPath}/inquired.mp?seq="+$(this).attr("id"), "inquired", 'width=550px,height=600px,scrollbars=no,resizable=no');
			break;
		case "notice":
			location.href="${pageContext.request.contextPath}/detailView.nboard?seq="+$(this).attr("id");
			break;
		case "freeboard":
			location.href="${pageContext.request.contextPath}/detailView.fboard?seq="+$(this).attr("id");
			break;
		case "subject":
			location.href="${pageContext.request.contextPath}/view.ass?ass_seq="+$(this).attr("id");
			break;
		}
		
	})	
}

let studentinfo = function(){
	window.open("${pageContext.request.contextPath}/studentinfo.mp","studentinfo","width=720px,height=860px,scrollbars=no,resizable=no")
	
}

	let InquiredList = function(){
		window.open("${pageContext.request.contextPath}/inquiredList.mp", "inquiredList", 'width=1280px,height=480px,scrollbars=no,resizable=no');
	}
	
	$(function(){
	
	$("#picture_modify").on("click", function(){
		let origin_picture = $("#profile").attr("src");
		let popup = window.open('picture_change.mp?origin='+origin_picture, 'change','width=550px,height=480px,scrollbars=no,resizable=no');
	})
	
	})
</script>

<c:if test="${update_result!=null }">
	<script>
		alert("정보 수정이 완료되었습니다!");
	</script>
</c:if>
</head>
<body>
<jsp:include page= "/header.jsp" />
<jsp:include page="/navibar.jsp"></jsp:include>
    <div class = "container shadow bg-white rounded">
        <div class = "row header">
            <div class="alert alert-light" role="alert">
                <h1>My Page<h1>
              </div>
        </div>
        <hr>
        <div class = row>
            <div class = "col-4">
                <div class="card">
                	<div class = row>
	                	<div class = "col-6">
			                <div class ="profilebox shadow bg-white" id= picture_modify>
			                	<c:choose>
				                	<c:when test="${profile_img != null}">
			                  			<img src="${pageContext.request.contextPath}/profile/${member.email }/${profile_img.sysName}" class="card-img-top" alt="profile_picture" id = profile>
			                   		</c:when>
			                   		<c:otherwise>
			                   			<img src="${defalut_profile_img}" class="card-img-top" alt="profile_picture" id = profile>
			                   		</c:otherwise>
		                   		</c:choose>
		                   	</div>
	                   	</div>
	                   	<div class = "col-6">
	                   	<br>
	                   	<div class = info>
		                   	  <c:choose>
		                    	<c:when test="${member.position=='teacher' }">
		                    		 <h5 class="card-title">${member.name} 강사님 </h5>
		                    	</c:when>
		                    	<c:otherwise>
		                    		<h5 class="card-title">${member.name} 님 </h5>
		                    	</c:otherwise>
	                    	  </c:choose>
			                  <i class="far fa-address-card"></i><a href="${pageContext.request.contextPath}/modify.mp" style="text-decoration:none; color:black"> 내 정보</a><br/>
			                  <a href="${pageContext.request.contextPath}/signout.member"><button class ="btn btn-secondary rounded">LogOut</button></a>
	                   	</div>
	                   	</div>
                   	</div>
                   <%--  <div class="card-body">
                
                      <p class="card-text">
                            <p>KH 
                            <c:choose>
	                            <c:when test="${member.branch=='J' }">
	                            	종로점
	                            </c:when>
	                             <c:when test="${member.branch=='D' }">
	                            	당산점
	                            </c:when>
	                            <c:otherwise>
	                            	강남점
	                            </c:otherwise>
                            </c:choose>
                            ${member.khClass } 클래스
                            </p>
                            <p>디지털 컨버전스 자바 양성과정</p>
                      </p>
                    </div> --%>
                  </div>
                  <br/>
                  <div class = card>
                  	<div class = card-body style="font-size: 12px">
                  		<p>[NCS]디지털컨버전스 융합 응용SW 개발자 양성과정</p>
                  		  	<c:choose>
               		<c:when test="${member.position=='teacher' }">
               		<p> 총 수강인원: ${studentNumber} 명</p>
               		</c:when>
               		
               		<c:otherwise>
               		 <p> ${teacher.name } 강사님 | 최미교 취업 담당 </p> 
               		</c:otherwise>
               	</c:choose>
                  	</div>
                  </div>
            </div>
            <div class = "col-5" id = graph>
            <div class =row>
            	<div class ="col-8" style ="text-align: left;"><h6>${period[0]}일 ~ ${period[1] }일</h6> </div>
            	<div class = "col-4" style ="text-align: right;"><h6> -${d_day}일</h6></div>
            </div>
            <div class = row>
               	<div class ="col-8"  style ="text-align: left">
               	<c:choose>
               		<c:when test="${member.position=='teacher' }">
               		<h5> 전문 프로그래머로 성장 중! </h5>
               		</c:when>
               		
               		<c:otherwise>
               		<h5> 전문 프로그래머로 교육 중! </h5>
               		</c:otherwise>
               	</c:choose>
               	</div>
            	<div class = "col-4" style ="text-align: right"><h5>${d_day_percent}%</h5></div>
            </div>
                <div class = "graph">
                    <div class = "figure_BG">
                        <div class="figure" style ="width:${d_day_percent}%">
                        	<div class="figure_icon" style = "text-align: right; display:inline-block; position: relative; bottom: 110%; left:10%">
                        		<img src ="figure_icon.png" style = "width:15%">
                        	</div>
                        </div>
                    </div>
                </div>
            </div>

            <div class = "col-3">
                <fieldset>
                    <legend>일정</legend>
                    <ul>
                    	<c:forEach var="item" items="${calander }">
                    			<li>${item.event_date} ${item.contents}</li>
                    	</c:forEach>
                    </ul>
                </fieldset>
                <hr>
                 <fieldset>
                    <legend>휴강일</legend>
                    <ul>
                    	<c:forEach var="item" items="${rest_calander }">
                    			<li>${item.event_date} ${item.contents}</li>
                    	</c:forEach>
                    </ul>
                </fieldset>
            </div>
            
            </div>
            
            <c:choose>
				<c:when test="${member.position=='teacher' }">
					<div class = "row">
						<div class = "col-6">
							 <div class="card">
				                  <div class="card-header">
				                      <h2>Process In Progress</h2>
				                   </div>
				                   <div class="card-body">
				                  		<table class="table">
											  <thead class="thead-dark">
											    <tr>
											      <th scope="col">회차</th>
											      <th scope="col" colspan=2 style ="text-align: center">수강</th>
											      <th scope="col">수강인원</th>
											    </tr>
											  </thead>
											  <tbody>
											    <tr>
											      <th scope="row">1</th> 
											      <td scope="col" colspan=2 style ="text-align: center"><a href="javascript:void(0)" onclick ="javascript:studentinfo();" return false;>디지털 컨버전스 자바 양성 과정</a></td>
											      <td>${studentNumber} 명</td>
											    </tr>
											    <tr>
											      <th scope="row">2</th>
											      <td scope="col" colspan=2 style ="text-align: center">미정</td>
											      <td>-명</td>
											    </tr>
											    <tr>
											   <th scope="row">3</th>
											      <td scope="col" colspan=2 style ="text-align: center">미정</td>
											      <td>-명</td>
											    </tr>
											  </tbody>
											</table>
				                   </div>
				                 </div>
						</div>
						
						<div class = "col-6">
							<div class ="row">
								<div class = "col-12">
									 <div class="card">
				                        <div class="card-header">
				                          <h2>Board Written</h2><a href ="${pageContext.request.contextPath}/list.fboard?cpage=1">더보기(More)</a>
				                        </div>
				                        <div class="card-body">
				                        <c:choose>
					                       	<c:when test="${FreeBoard.size() != 0}">
						                        <c:forEach var="item" items="${FreeBoard}">
						                          <h5 class="card-title" id =${item.seq }><a href="javascript:void(0)" onclick ="javascript:callPopUp();" return false;>${item.title }</a></h5>
						                        </c:forEach>
					                        </c:when>
					                        <c:otherwise>
					                     		<h5 class="card-title">등록한 글이 없습니다!</h5>
					                        </c:otherwise>
				                        </c:choose>
				                        </div>
				                      </div>
								</div>
							</div>
							
								<div class = "row">
								<div class = "col-12">
									  <div class="card">
                        <div class="card-header">
                          <h2>Q&A</h2><a href ="javascript:InquiredList();">더보기(More)</a>
                        </div>
                        <div class="card-body">
                         <c:choose>
                        	<c:when test="${Inquired.size() != 0  }">
		                         <c:forEach var="item" items="${Inquired }">
		                          	<h5 class="card-title" name= inquired id="${item.seq }"><a href="javascript:void(0)" onclick ="javascript:callPopUp();" return false;>${item.contents}</a></h5>
		                         </c:forEach>
                         	</c:when>
                         	<c:otherwise>
                         		등록된 문의가 없습니다!
                         	</c:otherwise>
                         </c:choose>
                        </div>
                      </div>
								</div>
							</div>
							
							
						</div>
					
					</div>
				</c:when>
				<c:otherwise>
				   <div class="row">
                <div class="col-6">
                <div class="card">
                    <div class="card-header">
                      <h2>Noctice</h2><a href ="${pageContext.request.contextPath}/list.nboard?cpage=1">더보기(More)</a>
                    </div>
                    <div class="card-body">
                    <c:choose>
                    	<c:when test="${Notice.size() !=0 }">
		                    <c:forEach var="item" items="${Notice }">
		                      <h5 class="card-title" name ="notice" id="${item.seq}" >
		                      <c:choose>
		                      	<c:when test="${item.branch =='all'}">
		                      		<span class="badge badge-danger">전체</span>
		                      	</c:when>
		                      	<c:when test="${item.branch =='J'}">
		                      		<span class="badge badge-info">종로</span>
		                      	</c:when>
		                      		<c:when test="${item.branch =='G'}">
		                      		<span class="badge badge-info">강남</span>
		                      	</c:when>
		                      		<c:when test="${item.branch =='D'}">
		                      		<span class="badge badge-info">당산</span>
		                      	</c:when>
		                      </c:choose>
		                      <a href="javascript:void(0)" onclick ="javascript:callPopUp();" return false;>${item.title }</a>
		                      </h5>
		                    </c:forEach>
                    	</c:when>
                    	<c:otherwise>
                    		등록된 공지사항이 없습니다!
                    	</c:otherwise>
                    </c:choose>
                    </div>
                  </div>
                </div>
                <div class="col-6">
                    <div class="card">
                        <div class="card-header">
                          <h2>Board Written</h2><a href ="${pageContext.request.contextPath}/list.fboard?cpage=1">더보기(More)</a>
                        </div>
                        <div class="card-body">
                        <c:choose>
                        <c:when test="${FreeBoard.size() != 0}">
	                        <c:forEach var="item" items="${FreeBoard}">
	                          <h5 class="card-title" name = freeboard id =${item.seq }><a href="javascript:void(0)" onclick ="javascript:callPopUp();" return false;>${item.title }</a></h5>
	                        </c:forEach>
                        </c:when>
                        <c:otherwise>
                        	<h5 class="card-title" name = freeboard>등록한 글이 없습니다!</h5>
                        </c:otherwise>
                        </c:choose>
                        </div>
                      </div>
                    </div>
            </div>

            <div class="row">
                <div class="col-6">
                <div class="card">
                    <div class="card-header">
                      <h2>Subject</h2> <a href ="${pageContext.request.contextPath}/list.ass?currentPage=1">더보기(More)</a>
                    </div>
                    <div class="card-body">
                      <c:choose>
                        	<c:when test="${subject.size() != 0  }">
			                      <c:forEach var="item" items="${subject }">  	 
			                       	 <h5 class="card-title" name = subject id =${item.seq }>
			                       	 	<c:choose>
			                       	 		<c:when test="${submitted.indexOf(item.seq) != -1}">
			                       	 			<span class="badge badge-success">제출완료 </span>
			                       	 		</c:when>
			                       	 		<c:otherwise>
			                       	 			<span class="badge badge-warning">미제출 </span>
			                       	 		</c:otherwise>
			                       	 	</c:choose>
			                       	 		<a href="javascript:void(0)" onclick ="javascript:callPopUp();" return false;>${item.title }</a>
			                       	 </h5>
			                      </c:forEach>
                     		 </c:when>
		                     <c:otherwise>
		                     	새로 등록된 과제는 없습니다!
		                     </c:otherwise>
                      </c:choose>
                    </div>
                  </div>
                </div>
                <div class="col-6">
                    <div class="card">
                        <div class="card-header">
                          <h2>Q&A</h2><a href ="javascript:InquiredList();">더보기(More)</a>
                        </div>
                        <div class="card-body">
                        <c:choose>
                        	<c:when test="${Inquired.size() != 0  }">
		                         <c:forEach var="item" items="${Inquired }">
		                          	<h5 class="card-title" name= inquired id="${item.seq }"><a href="javascript:void(0)" onclick ="javascript:callPopUp();" return false;>${item.contents}</a></h5>
		                         </c:forEach>
                         	</c:when>
                         	<c:otherwise>
                         		등록된 문의가 없습니다!
                         	</c:otherwise>
                         </c:choose>
                        </div>
                      </div>
                    </div>
            </div>
				
				</c:otherwise>

			</c:choose>
        </div>
</body>
</html>
</html>