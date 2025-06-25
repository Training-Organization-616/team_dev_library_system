<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="/team_dev_library_system/style.css" rel="stylesheet">
	<meta http-equiv="refresh" content="5;url=/team_dev_library_system/user/user_search.jsp">
</head>

<body>

	<jsp:include page="../top/header.jsp" />

	<div class="menu">
		<jsp:include page="../top/menu.jsp" />

	</div>
	<div class="holder">
		<div class="menu_image">
			<img src="/team_dev_library_system/image/menu_icon.png">

		</div>
		<div class="menu_name">会員情報変更</div>
	</div>

	<div class="container">
		<div>
			<p>　　　会員情報を変更しました</p>
			<p>　　　検索画面へ遷移します</p>
		</div>

		<div>
			<ul>
				<li>会員ID：${user.userId}</li>
				<li>氏名：${user.userName}</li>
				<li>住所：${user.address}</li>
				<li>電話番号：${user.tel}</li>
				<li>E-Mail：${user.email}</li>
				<li>生年月日：${user.birthday}</li>
				<li>入会年月日：${user.admissionDate}</li>
			</ul>
		</div>
		<br>
		<div class="transfar_link">
			<form action="/team_dev_library_system/user/user_search.jsp" method="post">
				<button>自動で遷移しない場合はこちらをクリック</button>
				<input type="hidden" name="action" value="">
			</form>
		</div>

</body>

</html>