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
		<div class="menu_name">お問い合わせ対応</div>
	</div>

	<div class="container">
		<div>
			<p>　　　お問い合わせ一覧</p>
		</div>
		<div class="message">　　　　${message}</div>
		
		<div class="search_result_table_contents">
			<c:if test = "${not empty inquiries }">
				<table class="overdue_table">
				<caption>お問い合わせ一覧</caption>
					<tr>
						<th>No</th>
						<th>受付日</th>
						<th>タイトル</th>
						<th>対応有無</th>
						<th>詳細</th>
					</tr>

					<c:forEach items="${inquiries}" var="inquiry" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td>${inquiry.receptionDate}</td>
							<td>${inquiry.contentsTitle}</td>
							<c:if test="${inquiry.handling==0}">
								<td>未対応</td>
							</c:if>
							<c:if test="${inquiry.handling==1}">
								<td>対応中</td>
							</c:if>
							<c:if test="${inquiry.handling==2}">
								<td>対応済み</td>
							</c:if>
							<td>
								<form action="/team_dev_library_system/InquiriesServlet"
									method="post">
									<button class="inquiries_edit_button">詳細</button>
									<input type="hidden" name="action" value="edit_page">
									<input type="hidden" name="inquiries_id" value="${inquiry.inquiriesId}">
									<input type="hidden" name="reception_date" value="${inquiry.receptionDate}">
									<input type="hidden" name="title" value="${inquiry.contentsTitle}">
									<input type="hidden" name="contents" value="${inquiry.contents}">
									<input type="hidden" name="handling" value="${inquiry.handling}">
									<input type="hidden" name="memo" value="${inquiry.memo}">
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</div>
			
		<div>	
			<form action="/team_dev_library_system/other/other_inquiries_add.jsp" method="post">
				<button class="general_button">登録</button>
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