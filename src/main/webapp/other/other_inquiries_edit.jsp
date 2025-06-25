<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<div class="menu_name">お問い合わせ編集</div>
	</div>

	<div class="container">
		<div>
			<p>　　　編集事項を入力してください</p>
		</div>

		<div class="message">${message}</div>

		<div class="input_table">
			<form action="/team_dev_library_system/InquiriesServlet" method="post">
				<input type="hidden" name="action" value="update">
				<table>
					<input type="hidden" name="inquiries_id" value="${inquiry.inquiriesId}">
					<tr>
						<th>受付日</th>
						<td>
							<span>&nbsp</span>
							${inquiry.receptionDate} 
							<input type="hidden" name="reception_date" value="${inquiry.receptionDate}">
						</td>
					</tr>
					<tr>
						<th>タイトル</th>
						<td>
							<span>&nbsp</span>
							${inquiry.contentsTitle} 
							<input type="hidden" name="title" value="${inquiry.contentsTitle}">
						</td>
					</tr>
					<tr>
						<th>内容</th>
						<td>
							<span>&nbsp</span>
							${inquiry.contents} 
							<input type="hidden" name="contents" value="${inquiry.contents}">
						</td>
					</tr>
					<tr>
						<th>対応有無</th>
						<td>
							<label>
								<input class="input_form" type="radio"name="handling" value="0" ${inquiry.handling == 0 ? 'checked = "checked"' : ''}>
								未対応
							</label>
							<label>
								<input class="input_form" type="radio"name="handling" value="1" ${inquiry.handling == 1 ? 'checked = "checked"' : ''}>
								対応中
							</label>
							<label>
								<input class="input_form" type="radio"name="handling" value="2" ${inquiry.handling == 2 ? 'checked = "checked"' : ''}>
								対応済み
							</label>
						</td>
					</tr>
					<tr>
						<th>備考</th>
						<td>
							<textarea name="memo" >${inquiry.memo}</textarea>
						</td>
					</tr>
				</table>
				<br>
				<button class="general_button edit_button">編集</button>
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