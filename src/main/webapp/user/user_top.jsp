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
		<div class="menu_name"> 会員管理</div>
	</div>

	<div class="button_container">
		<form action="/team_dev_library_system/user/user_add.jsp" method="post">
			<button class="top_button">新規会員登録</button>
		</form>
		<form action="/team_dev_library_system/user/user_search.jsp" method="post">
			<button class="top_button">会員検索</button>
		</form>
	</div>

</body>

</html>