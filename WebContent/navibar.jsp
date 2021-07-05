<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
    <nav class="navbar navbar-expand navbar-light bg-light">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="main.main">Home</a>
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
                    <a class="nav-link" href="calander.main">스케줄</a>
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
    </nav>
    </div>
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