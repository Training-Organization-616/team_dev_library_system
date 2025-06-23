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
		<div class="menu_name">延滞者対応</div>
	</div>

	<div class="container">
		<div>
			<p>延滞者一覧</p>
		</div>

		<div class="message">${message}</div>


			<%//<c:if test="${!empty overdue10days}">%>
			<div class="search_result_table">
				<table>
				<caption>超過した日数　10日以上</caption>
					<tr>
						<th>会員ID</th>
						<th>氏名</th>
						<th>E-Mail</th>
						<th>資料ID</th>
						<th>返却期日</th>
						<th>一次対応</th>
						<th>編集</th>
					</tr>

					<c:forEach items="${overdue10days}" var="day">
						<tr>
							<td>${day.userId}</td>
							<td>${day.userName}</td>
							<td>${day.email}</td>
							<td>${day.bookId}</td>
							<td>${day.dueDate}</td>
							<c:if test="${day.firstReminder==0}">
							<td>未対応</td>
							</c:if>
							<c:if test="${day.firstReminder==1}">
							<td>対応中</td>
							</c:if>
							<c:if test="${day.firstReminder==2}">
							<td>対応済み</td>
							</c:if>
							<td>
								<form action="/team_dev_library_system/OverdueServlet"
									method="post">
									<button class="edit_button">編集</button>
									<input type="hidden" name="action" value="edit_page">
									<input type="hidden" name="user_id" value="${day.userId}">
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<% //</c:if>%>
			
			<%/*<c:if test="${empty overdue30days}">
			10日以上超過している人はいません<br><br>
			</c:if>*/%>
			
			<%// <c:if test="${!empty overdue10days}">%>
			<div class="search_result_table">
				<table>
				<caption>超過した日数　30日以上</caption>
					<tr>
						<th>会員ID</th>
						<th>氏名</th>
						<th>E-Mail</th>
						<th>資料ID</th>
						<th>返却期日</th>
						<th>二次対応</th>
						<th>編集</th>
					</tr>

					<c:forEach items="${overdue30days}" var="month">
						<tr>
							<td>${month.userId}</td>
							<td>${month.userName}</td>
							<td>${month.email}</td>
							<td>${month.bookId}</td>
							<td>${month.dueDate}</td>
							<c:if test="${month.secondReminder==0}">
							<td>未対応</td>
							</c:if>
							<c:if test="${month.secondReminder==1}">
							<td>対応中</td>
							</c:if>
							<c:if test="${month.secondReminder==2}">
							<td>対応済み</td>
							</c:if>
							<td>
								<form action="/team_dev_library_system/OverdueServlet"
									method="post">
									<button class="edit_button">編集</button>
									<input type="hidden" name="action" value="edit_page">
									<input type="hidden" name="user_id" value="${month.userId}">
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			
			<%/*></c:if>
			
			<c:if test="${empty overdue30days}">
			30日以上超過している人はいません<br><br>
			</c:if>*/%>
		

		<div>
			<form action="/team_dev_library_system/OtherServlet" method="post">
				<button class="return_button">戻る</button>
				<input type="hidden" name="action" value="return_add">
				
			</form>
		</div>


		<jsp:include page="../top/footer.jsp" />
</body>

</html>