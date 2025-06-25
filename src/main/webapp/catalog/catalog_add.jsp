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
		<div class="menu_name">新規資料登録</div>
	</div>

	<div class="container">
		<div>
			<p>　　　登録する資料の情報を入力してください</p>
		</div>
		<div class="message">　　　　${message}</div>
		<br>
		<div class="input_table">
			<form action="/team_dev_library_system/CatalogServlet" method="post">
				<input type="hidden" name="action" value="add">
				<table>
					<tr>
						<th>ISBN番号</th>
						<td><input class="input_form" type="text" size="40" name="isbn"
							value="${isbn}"></td>
					</tr>
					<tr>
						<th>資料名</th>
						<td><input class="input_form" type="text" size="40" name="title"
							value="${title}"></td>
					</tr>
					<tr>
						<th>分類コード</th>
						<td>
							<select name="code">
								<option value="" selected>選択</option>
								<option value="0">総記</option>
								<option value="1">哲学</option>
								<option value="2">歴史</option>
								<option value="3">社会科学</option>
								<option value="4">自然科学</option>
								<option value="5">技術</option>
								<option value="6">産業</option>
								<option value="7">芸術</option>
								<option value="8">言語</option>
								<option value="9">文学</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>著者</th>
						<td><input class="input_form" type="text" size="40" name="author" value="${author}"></td>
					</tr>
					<tr>
						<th>出版社</th>
						<td><input class="input_form" type="text" size="40" name="publicher" value="${publicher}"></td>
					</tr>
					<tr>
						<th>出版日</th>
						<td><input class="input_form" type="date" name="publication_date" value="${publicationDate}"></td>
					</tr>
					<tr>
						<th>入荷年月日</th>
						<td><input class="input_form" type="date" name="arrival_date" value="${arrivalDate}"></td>
					</tr>
				</table>
				<button class="general_button">新規登録</button>
			</form>
		</div>

		<div>
			<form action="/team_dev_library_system/CatalogServlet" method="post">
				<button class="return_button">戻る</button>
				<input type="hidden" name="action" value="return_add">
			</form>
		</div>
	</div>

</body>

</html>