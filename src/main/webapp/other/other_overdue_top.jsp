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


			
			<div class="search_result_table">
				<c:if test = "${not empty overdue10days }">
					<table>
						<caption>超過した日数　10日以上</caption>
						<tr>
							<th>No</th>
							<th>会員ID</th>
							<th>氏名</th>
							<th>E-Mail</th>
							<th>資料ID</th>
							<th>返却期日</th>
							<th>一次対応</th>
							<th>編集</th>
						</tr>
	
						<c:forEach items="${overdue10days}" var="day" varStatus="status">
							<tr>
								<td>${status.count }</td>
								<td>${day.userId}</td>
								<td>${day.name}</td>
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
										<input type="hidden" name="lend_id" value="${day.lendId}">
										<input type="hidden" name="user_id" value="${day.userId}">
										<input type="hidden" name="name" value="${day.name}">
										<input type="hidden" name="email" value="${day.email}">
										<input type="hidden" name="tel" value="${day.tel}">
										<input type="hidden" name="book_id" value="${day.bookId}">
										<input type="hidden" name="title" value="${day.title}">
										<input type="hidden" name="due_date" value="${day.dueDate}">
										<input type="hidden" name="first_reminder" value="${day.firstReminder}">
										<input type="hidden" name="second_reminder" value="${day.secondReminder}"
										<input type="hidden" name="memo" value="${day.memo}">
									</form>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
			
			<div class="search_result_table">
				<c:if test = "${not empty overdue30days }">
					<table>
						<caption>超過した日数　30日以上</caption>
						<tr>
							<th>No</th>
							<th>会員ID</th>
							<th>氏名</th>
							<th>E-Mail</th>
							<th>資料ID</th>
							<th>返却期日</th>
							<th>二次対応</th>
							<th>編集</th>
						</tr>
	
						<c:forEach items="${overdue30days}" var="month" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${month.userId}</td>
								<td>${month.name}</td>
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
										<input type="hidden" name="lend_id" value="${month.lendId}">
										<input type="hidden" name="user_id" value="${month.userId}">
										<input type="hidden" name="name" value="${month.name}">
										<input type="hidden" name="email" value="${month.email}">
										<input type="hidden" name="tel" value="${month.tel}">
										<input type="hidden" name="book_id" value="${month.bookId}">
										<input type="hidden" name="title" value="${month.title}">
										<input type="hidden" name="due_date" value="${month.dueDate}">
										<input type="hidden" name="first_reminder" value="${month.firstReminder}">
										<input type="hidden" name="second_reminder" value="${month.secondReminder}"
										<input type="hidden" name="memo" value="${month.memo}">
									</form>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
			
		

		<div>
			<form action="/team_dev_library_system/OverdueServlet" method="post">
				<button class="return_button">戻る</button>
				<input type="hidden" name="action" value="top">
				
			</form>
		</div>
	</div>
	
		<jsp:include page="../top/footer.jsp" />
</body>

</html>