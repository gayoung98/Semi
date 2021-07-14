<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavDropdown">
    <ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="${pageContext.request.contextPath}/main.main">Home <span class="sr-only"></span></a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          게시판
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" href="${pageContext.request.contextPath}/list.nboard?cpage=1">공지게시판</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/list.fboard?cpage=1">자유게시판</a>
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/list.ass?currentPage=1">과제</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          타자
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" target="_blank" href="https://typing.malangmalang.com/typing-practice/exercising-sentence-typing">검증</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/view.typ">기록</a>
        </div>     
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/seat.seat">자리신청</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/calander.main">스케쥴</a>
      </li>      
      </li>
    </ul>
  </div>
</nav>

 
   <!--   <nav class="navbar navbar-expand navbar-light bg-light">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/main.main">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link">게시판</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/list.ass?currentPage=1">과제</a>
                </li>
                <li class="nav-item">
                      <a class="nav-link">타자</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/seat/seat.jsp">자리 신청</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/calander.main">스케줄</a>
                </li>

            </ul>
            <div class="row slide">
                <div class="col-2 sub">
                </div>
                <div class="col-2 sub" style="text-align: center;">
                       <a href="${pageContext.request.contextPath}/list.nboard?cpage=1">공지</a><br>
                       <a href="${pageContext.request.contextPath}/list.fboard?cpage=1">자유</a>
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
    </script>-->