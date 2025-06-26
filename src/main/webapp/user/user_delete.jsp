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

	<div class="menu">
		<jsp:include page="../top/menu.jsp" />
	</div>
	
	<div class="holder">
		<div class="menu_image">
			<img src="/team_dev_library_system/image/user_icon.png">
		</div>
		<div class="menu_name">会員情報削除</div>
	</div>

	<div class="container">
		<div class="delete_message">
			<p>　　　以下の会員情報を削除しますか？</p>
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
		<div>
			<form action="/team_dev_library_system/UserServlet" method="post">
				<input type="hidden" name="action" value="delete"> <input
					type="hidden" name="user_id" value="${user.userId}">
				<button class="general_button edit_button">削除</button>
			</form>
			<form action="/team_dev_library_system/UserServlet" method="post">
				<button class="return_button">戻る</button>
				<input type="hidden" name="action" value="return_search">
			</form>
		</div>
	</div>

</body>

</html>