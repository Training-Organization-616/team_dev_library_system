<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="../style.css" rel="stylesheet">
</head>

<body>

	<jsp:include page="../top/header.jsp" />

	<div class="holder">
		<div class="menu">
			<jsp:include page="../top/menu.jsp" />
			<%-- メニューはいったんぜんぶ表示してます --%>
		</div>
	<div class="holder">
		<div class="menu_image">
			<img src="/team_dev_library_system/image/menu_icon.png">
			
		</div>
		<div class="menu_name">新規会員登録</div>
	</div>
	
	<div class="container">
		<div>
			<p>新規登録が完了しました</p>
		</div>
		
		<div>
			<ul>
				<li>会員ID：${user.userId}</li>
				<li>氏名：${user.userName}</li>
				<li>住所：${user.userAdress}</li>
				<li>電話番号：${user.userTel}</li>
				<li>E-Mail：${user.userEmail}</li>
				<li>生年月日：${user.userBirthday}</li>
				<li>入会年月日：${user.userAdmissionDate}</li>
			</ul>
		</div>
	

	<jsp:include page="../top/footer.jsp" />

</body>

</html>