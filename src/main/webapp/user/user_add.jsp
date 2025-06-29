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
		<div class="menu_name">新規会員登録</div>
	</div>

	<div class="container">
		<div>
			<p>　　　登録する会員情報を入力してください</p>
		</div>

		<div class="message">　　　　${message}</div>

		<div class="input_table">
			<form action="/team_dev_library_system/UserServlet" method="post">
				<input type="hidden" name="action" value="add">
				<table>
					<tr>
						<th>氏名</th>
						<td><input class="input_form" type="text" size="40" name="user_name"
							value="${userName}"></td>
					</tr>
					<tr>
						<th>住所</th>
						<td><input class="input_form" type="text" size="40" name="user_address" value="${address}"></td>
					</tr>
					<tr>
						<th>電話番号</th>
						<td><input class="input_form" type="text" size="40" name="user_tel" value="${tel}"></td>
					</tr>
					<tr>
						<th>E-Mail</th>
						<td><input class="input_form" type="email" size="40" name="user_email" value="${email}"></td>
					</tr>
					<tr>
						<th>生年月日</th>
						<td><input class="input_form" type="date" name="user_birthday" value="${birthday}"></td>
					</tr>
				</table>
				<br>
				<button class="general_button">新規登録</button>
			</form>
		</div>

		<div>
			<form action="/team_dev_library_system/UserServlet" method="post">
				<button class="return_button">戻る</button>
				<input type="hidden" name="action" value="return_add">
			</form>
		</div>
	</div>

</body>

</html>