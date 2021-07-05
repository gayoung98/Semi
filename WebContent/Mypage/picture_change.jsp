<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
   <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>

<style>
.profilebox{
    width: 150px;
    height: 150px; 
    border-radius: 30%;
    overflow: hidden;
    margin : auto;
}
#profile{
    width: 100%;
    height: 100%;
    object-fit: cover;
}
</style>

<c:choose>

<c:when test="${change_result=='true'}">
	<script>
		alert("사진 변경이 완료되었습니다.");
	</script>
</c:when>

<c:when test="${change_result=='false'}">
	<script>
		alert("사진을 선택해주세요!");
	</script>
</c:when>

</c:choose>

<script>
$(function(){
	$("#fileUpload").on('change', function () {
        if (typeof (FileReader) != "undefined") {
            var image_holder = $(".profilebox");
            image_holder.empty();
            var reader = new FileReader();
            reader.onload = function (e) {
                $("<img />", {
                    "src": e.target.result,
                    "class": "thumb-image",
                    "id" : "profile",
                    "name" : "profile"
                }).appendTo(image_holder);
            }
            image_holder.show();
            reader.readAsDataURL($(this)[0].files[0]);
            console.log($(this)[0].files[0]);
        } else {
            alert("This browser does not support FileReader.");
        }
	});
	
	   $("#close_popup").on("click", function(){
		   opener.location.reload();
	       parent.window.close();
	    })
	    
	 /*    $("#submit_btn").on("click", function(event){
	    	event.preventDefault();
	    	$.ajax({
	    		url: "profileUpdate.mp",
	    		type : "POST",
	    		enctype : "multipart/form-data"
	    	})
	    }) */
})
</script>
</head>
<body>
<form action="${pageContext.request.contextPath}/profileUpdate.mp" method="post" enctype="multipart/form-data">
	<div class = container>
	<div class="card border-success mb-3" style="max-width: 18rem;">
	  <div class="card-header bg-transparent border-success"> <h3>프로필 사진 변경 </h3></div>
	  <div class="card-body text-success">
	    <h5 class="card-title">
	    <div id = before>
		 <div class=profilebox>
		     <img src="${origin}" class="card-img-top" alt="profile_picture" id = profile name = profile>
		</div>
		</div>
	    </h5>
	    <p class="card-text"><input type="file" id="fileUpload" name = after_profile><br/></p>
	  </div>
	  <div class="card-footer bg-transparent border-success">
	  	  <button type="submit" class="btn btn-light" id="submit_btn">Accept</button>
	      <button type="button" class="btn btn-danger" id="close_popup">Cancel</button>
	  </div>
	</div>
	</div>
</form>
</body>
</html>