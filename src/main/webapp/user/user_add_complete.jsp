<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="/team_dev_library_system/style.css" rel="stylesheet">
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
				<li>会員ID：${user[0].userId}</li>
				<li>氏名：${user[0].userName}</li>
				<li>住所：${user[0].address}</li>
				<li>電話番号：${user[0].tel}</li>
				<li>E-Mail：${user[0].email}</li>
				<li>生年月日：${user[0].birthday}</li>
				<li>入会年月日：${user[0].admissionDate}</li>
			</ul>
		</div>
	

	<jsp:include page="../top/footer.jsp" />

</body>

</html>