<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

<style>
	.container{
	    width: 500px;
	    padding-top: 10px;
	}
	h2{
	    padding-bottom: 20px;
	}
	
	.btn_container{
	    float: right;
	}
	textarea{
	    resize: none;
	}
	#content{
	    font-size: 12px;
	    color: darkgray;
	}
</style>

<script>
	$(function(){
		$("#submit_btn").on("click", function(){
	/* 	    $.ajax({
		        url:"insert.inquired",
		        data:{"writer": $("#writer").val(), 
		        	  "major_category":$("#major_category").val(), 
		        	  "sub_category": $("#sub_category").val(), 
		        	  "inquire_contents":$("#inquire_contents").val()},
		        dataType:"TEXT"
		    }).done(function(result){
		    	parent.window.close();
		    }) */
		    
		    
		})
	
	    let limit = 200;
	    $("#inquire_contents").on("keyup", function(){
	        let text = $(this).val();
	        $("#content_length").text($(this).val().length);
	        if($(this).val().length >= 200){
	            $("#inquire_contents").val(text.substr(0,200));
	        }
	    })
	
	    $("#close_popup").on("click", function(){
	        parent.window.close();
	    })
	
	})
</script>
</head>

<body>
	 <div class = "container">
        <h2> 문의하기 </h2>
        <form action= "${pageContext.request.contextPath}/inquired.main" method="post">
            <div class="form-group">
            <label for="exampleFormControlInput1"><strong>작성자</strong></label>
            <input type="email" class="form-control" id="writer" name="writer" value = "${login }" readonly>
            </div>
            <div class="form-group">
                <label for="exampleFormControlSelect1"><strong>주제 분류</strong></label>
                <select class="form-control" id="major_category" name="major_category">
                    <option value = "site">사이트이용 관련</option>
                    <option value = "board">게시판 관련</option>
                    <option value = "institute">학원 관련</option>
                    <option value = "etc">기타</option>
                </select>
                </div>
            <div class="form-group">
            <label for="exampleFormControlSelect1"><strong>문의 분류</strong></label>
            <select class="form-control" id="sub_category" name = "sub_category">
                <option value = "error">오류/버그 관련</option>
                <option value = "complain">불만 관련</option>
                <option value = "suggest">건의 관련</option>
                <option value = "etc">기타 문의</option>
            </select>
            </div>
            <div class="form-group">
                <label for="exampleFormControlTextarea1"><strong>내용</strong></label> <label id ="content">( <label id ="content_length">0</label>/ 200) </label>
                <textarea class="form-control" id="inquire_contents" name ="inquire_contents"  rows="6" required></textarea>
                </div>
                <div class ="btn_container">
                <button type="submit" class="btn btn-light" id="submit_btn" >Submit</button>
                <button type="button" class="btn btn-danger" id="close_popup">Cancel</button>
            </div>
        </form>
    </div>
</body>
</html>