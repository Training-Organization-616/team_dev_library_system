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
			<img src="/team_dev_library_system/image/menu_icon.png">
		</div>
		<div class="menu_name">その他</div>
	</div>

	<div class="button_container">
		<form action="/team_dev_library_system/OverdueServlet" method="post">
			<input type="hidden" name="action" value="list">
			<button class="top_button">延滞者対応</button>
		</form>

		<form action="/team_dev_library_system/InquiriesServlet" method="post">
			<input type="hidden" name="action" value="list">
			<button class="top_button">お問い合わせ</button>
		</form>
	</div>

</body>

</html>