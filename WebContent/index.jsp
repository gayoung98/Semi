<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
<style>
	body {background-color: #D8E3E7;}
	.container {
			max-width: 400px;
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
		}
		#login{width: 350px;}
		img{display: inline-block;
    		width: 100%;
    		height: 39.3px;
    		overflow: hidden;
    		object-fit: cover;
    		border-radius: 5px;}
</style>
<script>
	$(function(){
		$("#login").on("click",function(){
			location.href="login.jsp";
		})
	})
</script>
</head>
<body>
	<div class="container p-4 shadow bg-white rounded">
		<h2 class="mb-5 text-center">KH Story로그인</h2>
			<div class="form-row align-items-center mt-4">
				<div class="col-6 text-center">
					<button type="button" class="btn btn-outline-info" id="login">로그인</button>
				</div>
			</div>   
			<div class="form-row align-items-center mt-4">
				<div class="col-12 align-items-center">
					<a href="javascript:kakaoLogin();"><img src="kakao_.png"></a>
                    <script>
						window.Kakao.init('5355d050e8c209a5745ea2394d2d4db0');
		
						function kakaoLogin(){
							window.Kakao.Auth.login({
								scope:'account_email',
								success: function(authObj){
									console.log(authObj);
									window.Kakao.API.request({
										url:'/v2/user/me',
											success: res => {
												location.href ="${pageContext.request.contextPath}/sns.member?email="+res.kakao_account.email;
											}	
									});
								}
							});
						}
					</script>					
				</div>
            </div>
			<div class="form-row align-items-center mt-4">
				<div class="col-12 align-items-center">
					<div id="naverIdLogin"></div>	
					<script type="text/javascript">
						var naverLogin = new naver.LoginWithNaverId(
							{
								clientId: "kOdPDaEhTlerLjUpxZcV",
								callbackUrl: "http://localhost:8887/Semi/view/naverCallback.jsp",
								isPopup: false, /* 팝업을 통한 연동처리 여부 */
								loginButton: {color: "green", type: 3, height: 60} /* 로그인 버튼의 타입을 지정 */
							}
						);
	
						/* 설정정보를 초기화하고 연동을 준비 */
						naverLogin.init();
	
					</script>				
				</div>
            </div>            
			<div class="form-row align-items-center mt-4">
				<div class="col-12">
					<center><a href="joinForm.member">회원가입</a></center>
				</div>
            </div>
	</div>               
</body>
</html>