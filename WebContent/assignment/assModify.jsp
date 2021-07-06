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
        $(document).ready(function () {
            $('#summernote').summernote({
                height: 300,                 // set editor height
                minHeight: null,             // set minimum height of editor
                maxHeight: null,             // set maximum height of editor
                focus: true                  // set focus to editable area after initializing summernote
            });
        })
        $(function(){
        	
        	$("#fileDel").on("click", function() {
    			let seq = ($(this).attr("seq"));
    			
    			let inputFile = $("<input>");
    			inputFile.attr("type","file");
    			inputFile.attr("name","file");
    			
    			let inputBox = $("<div>");
    			inputBox.attr("class","col-12");
    			inputBox.attr("style","margin-top: 3%;");
    			
    			inputBox.append(inputFile);
    			$("#files").append(inputBox);
    			    			
    			$(this).parent().remove();
    			let del = $("<input>");
    			del.attr("type", "hidden");
    			del.attr("name", "delete");
    			del.attr("value", seq);
    			$(".title").append(del);
    			
    		});
        	
        })
        
	</script>
    
</head>
<body>
	<form action="${pageContext.request.contextPath}/modiProc.ass?ass_seq=${assView.seq}"
		method="post" enctype="multipart/form-data">
		<div class="container">
			<div class="row header">
				<div class="col-12">
					<h3>과제</h3>
				</div>

			</div>
			<div class="row title">
				<div class="col-12">
					<input type="text" name="title" value="${assView.title}" id="title">
				</div>

			</div>
			<div class="row files">
				<div class="col-12" style="margin-top: 3%;">
					${assFiles.oriName}
				
					<button type="button" id="fileDel" seq="${assFiles.seq}">삭제</button>
				</div>
			</div>


			<div class="row content" style="padding: 0px;">
				<div class="col-12">
					<!-- <textarea name="contents" id="summernote"></textarea>  -->
					<textarea rows=10 cols=20 id="contents" name="contents" value="${assView.contents}"></textarea>
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
	</form>
	
</body>
</html>