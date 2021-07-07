<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신고하기</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>

<style>
html[Attributes Style] {
    -webkit-locale: "ko";
}
html {
    display: block;
}
header{border-bottom:1px solid #ddd;}
body {
    -webkit-text-size-adjust: none;
}
body, button, input, select, td, textarea, th {
    font-size: 14px;
    line-height: 1.5;
    color: #000;}
    
    .report_type .tit_popup {
    font-size: 18px;
    line-height: 55px;
    text-align: center;
    }
    h2 {
    display: block;
    font-size: 1.5em;
    margin-block-start: 0.83em;
    margin-block-end: 0.83em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    font-weight: bold;
}
    div {display: block;
}
.report_type .tit_report {
    display: block;
    padding: 27px 22px 10px;
    font-weight: 600;
    font-size: 16px;
    line-height: 19px;
    background-color: #fff;
}
legend {
    display: block;
    padding-inline-start: 2px;
        
    padding-inline-end: 2px;
    border-width: initial;
    border-style: none;
    border-color: initial;
    border-image: initial;}
    
    dl, li, menu, ol, ul {
    list-style: none;
}

blockquote, body, button, code, dd, div, dl, dt, fieldset, form, h1, h2, h3, h4, h5, h6, input, legend, li, ol, p, pre, select, td, textarea, th, ul {
    margin: 0;
    padding: 0;
}
.ir_caption, .screen_out {
    overflow: hidden;
    line-height: 0;
    text-indent: -9999px;
}

ul{display: block;
    list-style-type: disc;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    padding-inline-start: 40px;}
    
    
  .popup_body{    padding-inline-start: 40px;}
  }

a, a:active, a:hover {
    text-decoration: none;
}
.report_type .btn_group {
    overflow: hidden;
    padding: 15px;}

button {
    -webkit-writing-mode: horizontal-tb !important;
    text-rendering: auto;
    letter-spacing: normal;
    word-spacing: normal;
    text-transform: none;
    width:80px;
}

.report_type .txt_guide {
    display: block;
    padding-left: 14px;
    margin: 2px 20px 18px;
    text-indent: -14px;
    font-size: 13px;
    line-height: 17px;
    color: #999;
    letter-spacing: -.03em;
}

.report_type .btn_confirm .inner_btn {
    border-color: #353a59;
    background-color: #353a59;
    color: #fff;
    text-align:center;
}
  .btn_cancel.inner_btn {
    display: block;
    height: 38px;
    margin: 0 5px;
    border: 1px solid #cdcdcd;
    border-radius: 4px;
 	text-align:center;	
    font-size: 15px;
    line-height: 38px;
   
    text-align: center;
}
.report_type .btn_comm .inner_btn {
    display: block;
    height: 38px;
    margin: 0 5px;
    border: 1px solid #cdcdcd;
    border-radius: 4px;
    font-size: 15px;
    line-height: 38px;
    text-align: center;}
    
    .btn_group{padding-inline-start: 40px;}
    .btn_group .cancel_btn{ border:1px solid #ddd;}
    .btn_group .btn_confirm{border:1px solid #ddd;}
    
</style>

</head>
<body>
<div id="app">
    <div>
        <div class="report_type">
           
            <section class="section_popup">
                <header class="popup_head">
                    <h2 class="tit_popup">신고하기</h2>
                </header>
                <div class="popup_body">
                
                    <legend class="screen_out"></legend>
                    <strong class="tit_report tit_report"><span>신고사유 선택</span></strong>
                    
              <form action="${pageContext.request.contextPath}/report.fboard" method="post">
                    <input type="hidden" name="fboard_seq" value="${view.seq}">
                    <ul class="list_report">
                        <li id="r1" class="reason">
                            <div class="choice_wrap">
                                <input type="radio" name="contents" class="inp_radio" value ="욕설, 비방, 차별, 혐오" checked>
                                <label for="r1" class="lab_radio"><span class="ico_report ico_check"></span><span>욕설, 비방, 차별, 혐오</span><span class="ico_report ico_arr"></span></label>
                            </div>
                        </li>
                       
                        <li id="r2" class="reason" >
                            <div class="choice_wrap">
                                <input type="radio" name="contents" class="inp_radio" value ="불법 정보">
                                <label for="r2" class="lab_radio"><span class="ico_report ico_check"></span><span>불법 정보</span><span class="ico_report ico_arr"></span></label>
                            </div>
                            
                        </li>
                        <li id="r3" class="reason" >
                            <div class="choice_wrap">
                                <input type="radio" name="contents" class="inp_radio" value ="음란, 청소년 유해">
                                <label for="inpChoice1" class="lab_radio"><span class="ico_report ico_check"></span><span>음란, 청소년 유해</span><span class="ico_report ico_arr"></span></label>
                            </div>
                           
                        </li>
                        <li id="r4" class="reason" >
                            <div class="choice_wrap">
                                <input type="radio" name="contents" class="inp_radio" value ="개인 정보 노출, 유포, 거래">
                                <label for="r4" class="lab_radio"><span class="ico_report ico_check"></span><span>개인 정보 노출, 유포, 거래</span><span class="ico_report ico_arr"></span></label>
                            </div>
                          
                        </li>
                        <li id="r5" class="reason" >
                            <div class="choice_wrap">
                                <input type="radio" name="contents" class="inp_radio" value ="도배, 스팸">
                                <label for="r5" class="lab_radio"><span class="ico_report ico_check"></span><span>도배, 스팸</span><span class="ico_report ico_arr"></span></label>
                            </div>                        
                        </li>                             
                    </ul>


                    <div class="btn_group">
                        <button type="button" class="btn btn-light cancel_btn"><span class="inner_btn" onclick="self.close();">취소</span></button>
                        <button type="submit" class="btn btn-dark btn_confirm"><span class="inner_btn" id="submitBtn">신청</span></button>
                    </div>
                    
                    </form>
                    <span class="txt_guide">※ 허위신고일 경우, 신고자의 서비스 활동이 제한될 수 있습니다.</span>
                </div>
            </section>

                </div>
            </div>
        </div>
</body>
</html>