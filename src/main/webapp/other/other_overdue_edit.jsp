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
		<div class="menu_name">延滞者対応編集</div>
	</div>

	<div class="container">
		<div>
			<p>　　　延滞者の対応状況を入力してください</p>
		</div>

		<div class="message">　　　　${message}</div>

		<div class="input_table">
			<form action="/team_dev_library_system/OverdueServlet" method="post">
				<input type="hidden" name="action" value="update">
				<table>
					<input type="hidden" name="lend_id" value="${overdue.lendId}">
					<tr>
						<th>会員ID</th>
						<td>
						${overdue.userId} 
						<input type="hidden" name="user_id" value="${overdue.userId}">
						</td>
					</tr>
					<tr>
						<th>氏名</th>
						<td>
						${overdue.name} 
						<input type="hidden" name="name" value="${overdue.name}">
						</td>
					</tr>
					<tr>
						<th>E-Mail</th>
						<td>
						${overdue.email}
						 <input type="hidden" name="email" value="${overdue.email}">
						</td>
					</tr>
					<tr>
						<th>資料ID</th>
						<td>
						${overdue.bookId} 
						<input type="hidden" name="book_id" value="${overdue.bookId}">
						</td>
					</tr>
					<tr>
						<th>返却期日</th>
						<td>${overdue.dueDate} 
						<input type="hidden" name="due_date"value="${overdue.dueDate}">
						</td>
					</tr>
					
					<tr>
						<c:if test="${not empty day}">
						<input type = "hidden" name = "second_reminder" value = "${overdue.secondReminder }">
						<th>一次対応</th>
						<td>
							<label>
								<input class="input_form" type="radio"name="first_reminder" value="0" <c:if test="${overdue.firstReminder==0}">checked</c:if>>
								未対応
							</label>
							<label>
								<input class="input_form" type="radio"name="first_reminder" value="1" <c:if test="${overdue.firstReminder==1}">checked</c:if>>
								対応中
							</label>
							<label>
								<input class="input_form" type="radio"name="first_reminder" value="2" <c:if test="${overdue.firstReminder==2}">checked</c:if>>
								対応済み
							</label>
						</td>
						</c:if>
						<c:if test="${not empty month}">
						<input type = "hidden" name = "first_reminder" value = "${overdue.firstReminder }">
						<input type="hidden" name="tel_reminder" >
						<th>二次対応</th>
						<td>
							<label>
								<input class="input_form" type="radio"name="second_reminder" value="0" <c:if test="${overdue.secondReminder==0}">checked</c:if>>
								未対応
							</label>
							<label>
								<input class="input_form" type="radio"name="second_reminder" value="1" <c:if test="${overdue.secondReminder==1}">checked</c:if>>
								対応中
							</label>
							<label>
								<input class="input_form" type="radio"name="second_reminder" value="2" <c:if test="${overdue.secondReminder==2}">checked</c:if>>
								対応済み
							</label>
						</td>
						</c:if>
					</tr>
					
					<tr>
						<th>備考</th>
						<td>
						<input class="input_form" type="text" size="70" name="memo" value="${overdue.memo}">
						</td>
					</tr>
				</table>
				<input type = "hidden" name = "tel" value = "${overdue.tel }">
				<input type = "hidden" name = "title" value = "${overdue.title }">
				<input type = "hidden" name = "due_date" value = "${overdue.dueDate}">
				<button class="general_button edit_button">編集</button>
			</form>

		</div>

		<div>
			<form action="/team_dev_library_system/OverdueServlet" method="post">
				<button class="return_button">戻る</button>
				<input type="hidden" name="action" value="return_add">
			</form>
		</div>
	</div>


</body>

</html>