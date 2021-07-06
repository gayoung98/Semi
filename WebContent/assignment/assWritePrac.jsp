<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<style>
* {
	box-sizing: border-box;
	text-align: center;
}

#title {
	margin-top: 3%;
	width: 100%;
	height: 100%;
}

#content {
	width: 100%;
	margin-top: 4%;
	margin-bottom: 2%;
	text-align: left;
}

.buttons {
	padding-bottom: 15px;
}
</style>

    <script>
    $(function(){	

    	var array = new Array();
    	var count =0;
    	
          $('#summernote').summernote({
            placeholder: 'Hello Bootstrap 4',
            tabsize: 2,
            height: 100,
            callbacks:{
            	onImageUpload:function(files){
            		console.log(files);
            		array[count++] = $(".note-image-input").val();
            		
            	/* 	let editor = this; //SummerNote 인스턴스의 주소를 editor 변수에 저장
            		let file = files[0]; //업로드할 파일 인스턴스
            		let form = new FormData(); //html form을 getElement해서 가져오면 formData임.
            		form.append("file", file); //"file"은  name 속성명 */
            		        		
            	/* 	$.ajax({
            			data:form,
            			type:"post",
            			url:"${pageContext.request.contextPath}/upload.ass",
            			contentType: false, //멀티파트폼데이터로 설정
            			processData: false //무슨 작업을 안 하고 데이터 변경 없이 전송

           			
            		}).done(function(resp){
            			console.log(resp);
            			$(editor).summernote('insertImage',"${pageContext.request.contextPath}"+resp)
            			//editor 인스턴스의 insertImage 기능으로 이미지를 화면에 출력
            			//let seq = $("<input>");
            			//seq.attr("type", "hidden");
            			//seq.attr("name", "ass_seq");
            			//seq.attr("ass_seq", resp.parent);
            			//$(".title").append(seq);
            		}); */
            	}//이미지가 업로드 되는 순간 ajax를 실행
            }
          });   
         
          
    	   
        $(document).ready(function () {
            $('#summernote').summernote({
                height: 600,                 // set editor height
                minHeight: null,             // set minimum height of editor
                maxHeight: null,             // set maximum height of editor
                focus: true                  // set focus to editable area after initializing summernote
            });
        });
        
        
        
})
    </script>
    
</head>
<body>
	<form action="${pageContext.request.contextPath}/write.ass"
		method="post" enctype="multipart/form-data">
		<div class="container">
			<div class="row header">
				<div class="col-12">
					<h3>과제</h3>
				</div>

			</div>
			<div class="row title">
				<div class="col-12 col-md-8">
					<input type="text" name="title" id="title" placeholder="제목을 입력하세요.">
				</div>
				<div class="col-12 col-md-4" style="margin-top: 3%;">
					<input type="file" name="file">
				</div>
			</div>


			<div class="row content" style="padding: 0px;">
				<div class="col-12">
					<div id="summernote" name="contents"></div>
					
				</div>
			</div>
			
			<div class="row buttons">
				<div class="col-6" style="text-align: left;">
					<button type="button">이전</button>
				</div>
				<div class="col-6" style="text-align: right;">
					<button type="submit">제출</button>
				</div>
			</div>
		</div>
		<input type=hidden id= seq>
	</form>
<!-- <script>

	let array = new Array();
	let count =0;
	
      $('#summernote').summernote({
        placeholder: 'Hello Bootstrap 4',
        tabsize: 2,
        height: 100,
        callbacks:{
        	onImageUpload:function(files){
        		console.log(files);
        		console.log($(".note-image-input").val());
        		array[count++] = $(".note-image-input").val();
        		
        		let editor = this; //SummerNote 인스턴스의 주소를 editor 변수에 저장
        		let file = files[0]; //업로드할 파일 인스턴스
        		let form = new FormData(); //html form을 getElement해서 가져오면 formData임.
        		form.append("file", file); //"file"은  name 속성명
        		        		
        	/* 	$.ajax({
        			data:form,
        			type:"post",
        			url:"${pageContext.request.contextPath}/upload.ass",
        			contentType: false, //멀티파트폼데이터로 설정
        			processData: false //무슨 작업을 안 하고 데이터 변경 없이 전송

       			
        		}).done(function(resp){
        			console.log(resp);
        			$(editor).summernote('insertImage',"${pageContext.request.contextPath}"+resp)
        			//editor 인스턴스의 insertImage 기능으로 이미지를 화면에 출력
        			//let seq = $("<input>");
        			//seq.attr("type", "hidden");
        			//seq.attr("name", "ass_seq");
        			//seq.attr("ass_seq", resp.parent);
        			//$(".title").append(seq);
        		}); */
        	}//이미지가 업로드 되는 순간 ajax를 실행
        }
      
      
      });   
      
      
      for(var i = 0; i<array.length; i++){
    	  console.log(array[i]);
    	  }
      
</script> -->
</body>
</html>