<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
		<div class="menu_name">会員検索</div>
	</div>

	<div class="container">
		<div>
			<p>検索する会員情報を入力してください</p>
		</div>

		<div class="message">${message}</div>

		<div class="input_table">
			<form action="/team_dev_library_system/UserServlet" method="post">
				<input type="hidden" name="action" value="search">
				<table>
					<tr>
						<th>会員ID</th>
						<td><input class="input_form" type="text" size="80" name="user_id"
							value="${userId}"></td>
					</tr>
					<tr>
						<th>氏名</th>
						<td><input class="input_form" type="text" size="80" name="user_name"
							value="${userName}"></td>
					</tr>
					<tr>
						<th>住所</th>
						<td><input class="input_form" type="text" size="80" name="user_address"
							value="${address}"></td>
					</tr>
					<tr>
						<th>電話番号</th>
						<td><input class="input_form" type="text" size="80" name="user_tel"
							value="${tel}"></td>
					</tr>
					<tr>
						<th>E-Mail</th>
						<td><input class="input_form" type="text" size="80" name="user_email"
							value="${email}"></td>
					</tr>
					<tr>
						<th>生年月日</th>
						<td><input class="input_form" type="date" 
							name="user_birthday" value="${birthday}"></td>
					</tr>
				</table>
				<button class="general_button_search_button">検索</button>
			</form>

		</div>


		<br>
		<br>
		<c:if test="${!empty users}">
			<div class="search_result_table_container">
				<table class="search_result_table">
					<tr>
						<th>会員ID</th>
						<th>氏名</th>
						<th>E-Mail</th>
						<th>変更</th>
						<th>削除</th>
					</tr>

					<c:forEach items="${users}" var="user">
						<tr>
							<td>${user.userId}</td>
							<td>${user.userName}</td>
							<td>${user.email}</td>
							<td>
								<form action="/team_dev_library_system/UserServlet"
									method="post">
									<button class="table_edit_button">変更</button>
									<input type="hidden" name="action" value="edit_page"> <input
										type="hidden" name="user_id" value="${user.userId}">
								</form>
							</td>
							<td>
								<form action="/team_dev_library_system/UserServlet"
									method="post">
									<button class="table_edit_button">削除</button>
									<input type="hidden" name="action" value="delete_page">
									<input type="hidden" name="user_id" value="${user.userId}">
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>

		<div>
			<form action="/team_dev_library_system/UserServlet" method="post">
				<button class="search_return_button">戻る</button>
				<input type="hidden" name="action" value="return_add">
			</form>
		</div>

</body>

</html>