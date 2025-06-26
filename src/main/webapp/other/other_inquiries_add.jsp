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
			<img src="/team_dev_library_system/image/other_icon.png">
		</div>
		<div class="menu_name">お問い合わせ登録</div>
	</div>

	<div class="container">
		<div>
			<p>　　　必要事項を入力してください</p>
		</div>

		<div class="message">${message}</div>

		<div class="input_table">
			<form action="/team_dev_library_system/InquiriesServlet" method="post">
				<input type="hidden" name="action" value="add">
				<table>
					<tr>
						<th>受付日</th>
						<td><input class="input_form" type="date" name="reception_date"
							value="${receptionDate}"></td>
					</tr>
					<tr>
						<th>タイトル</th>
						<td><input class="input_form" type="text" size="40" name="title"
							value="${title}"></td>
					</tr>
					<tr>
						<th>内容</th>
						<td><textarea name = "contents">${contents}</textarea></td>
					</tr>
					<tr>
						<th>対応有無</th>
						<td><label>
								<input class="input_form" type="radio"name="handling" value="0" checked>
								未対応
							</label>
							<label>
								<input class="input_form" type="radio"name="handling" value="1" >
								対応中
							</label>
							<label>
								<input class="input_form" type="radio"name="handling" value="2" >
								対応済み
							</label>
						</td>
					</tr>
					<tr>
						<th>備考</th>
						<td><textarea name = "memo">${memo}</textarea></td>
					</tr>
				</table>
				<br>
				<button class="general_button_add">登録</button>
			</form>
		</div>

		<div>
			<form action="/team_dev_library_system/InquiriesServlet" method="post">
				<button class="return_button">戻る</button>
				<input type="hidden" name="action" value="return_add">
			</form>
		</div>
	</div>

</body>

</html>