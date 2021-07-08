<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
    <nav class="navbar navbar-expand navbar-light bg-light">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/main.main">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link">게시판</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="list.ass?currentPage=1">과제</a>
                </li>
                <li class="nav-item">
                      <a class="nav-link">타자</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/seat/seat.jsp">자리 신청</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="calander.main">스케줄</a>
                </li>

            </ul>
            <div class="row slide">
                <div class="col-2 sub">
                </div>
                <div class="col-2 sub" style="text-align: center;">
                       <a href="${pageContext.request.contextPath}/list.nboard?cpage=1">공지</a><br> <a href="${pageContext.request.contextPath}/list.fboard?cpage=1">자유</a>
                </div>
                <div class="col-2 sub">
                </div>
                <div class="col-2 sub" style="text-align: center;">
                       <a href="https://typing.malangmalang.com/typing-practice/exercising-sentence-typing">검정</a>
                       <br>
                       <a href="${pageContext.request.contextPath}/view.typ">기록</a>
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