<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>구글 reCaptcha</title>
	

</head>
<body>
<h1>구글 reCaptcha</h1>
<script src="https://www.google.com/recaptcha/api.js"></script>
	<form  method="POST" id="frm">
   	<div>아이디 : <input type="text" id="userid" name="userid"></div>
   	<div>비밀번호 : <input type="password" id="passwd" name="passwd"></div>
   	<div class="g-recaptcha" data-sitekey="6LfaIwgbAAAAAEVrujfQ72ArOe5Ru4YCjVW1GBSl"></div>
	<input type="hidden" id="g-recaptcha" name="g-recaptcha" />
   	<div><button type="button" id="capbtn">입력완료</button></div>
	</form>
	
<script>
	 var capbtn = document.getElementById('capbtn');
	 capbtn.addEventListener('click',checkCap);
	 
	function checkCap() {
		// captcha 선택을 하지 않았으면 넘어가지 않게 하는 코드
		if (grecaptcha.getResponse() == '') 
			alert('captcha를 확인하세요!');
		else
			//alert(grecaptcha.getResponse());
		
		// recaptcha 코드 유효성 검사를 위한 변수에 값 설정
        // 클라이언트에서 생성한 코드를 서버에서도 확인하기 위한 목적

		document.getElementById('g-recaptcha').value=grecaptcha.getResponse();
		document.getElementById('frm').submit();
		
	}
</script>

</body>
</html>