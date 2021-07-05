<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        
        $(function () {
            $(".slide").hide();
            $(".navbar").on("mouseenter", function () {
                $(".slide").stop().slideDown(1000);
            })
            $(".navbar").on("mouseleave", function () {
                $(".slide").stop().slideUp(1000);
            })
        })

    </script>
    <style>
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
        .navbar{
        	position:fixed;
        	top:0px;
        	width:100%
        }
        
    </style>
</head>

<body>

    <nav class="navbar navbar-expand navbar-light bg-light">
        <div class="container-fluid">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">게시판</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">과제</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">타자</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">자리 신청</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">스케줄</a>
                </li>

            </ul>
            <div class="row slide">
                <div class="col-2 sub">
                </div>
                <div class="col-2 sub" style="text-align: center;">
                    공지<br>자유
                </div>
                <div class="col-2 sub">
                </div>
                <div class="col-2 sub" style="text-align: center;">
                    검정<br>기록
                </div>
                <div class="col-2 sub">
                </div>
                <div class="col-2 sub">
                </div>
            </div>
        </div>
    </nav>


</body>

</html>