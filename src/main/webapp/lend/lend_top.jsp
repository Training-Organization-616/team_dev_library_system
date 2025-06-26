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
			<img src="/team_dev_library_system/image/library_icon.png">
		</div>
		<div class="menu_name">貸出管理</div>
	</div>

	<div class="button_container_lend">
		<form action="/team_dev_library_system/lend/lend_add.jsp"
			method="post">
			<button class="top_button">貸出</button>
		</form>

		<form action="/team_dev_library_system/lend/return_add.jsp"
			method="post">
			<button class="top_button">返却</button>
		</form>

		<form action="/team_dev_library_system/lend/reservation_top.jsp"
			method="post">
			<button class="top_button">予約</button>
		</form>
	</div>

</body>

</html>