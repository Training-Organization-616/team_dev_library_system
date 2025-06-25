<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="/team_dev_library_system/style.css" rel="stylesheet">
	<meta http-equiv="refresh" content="5;url=/team_dev_library_system/other/other_overdue_top.jsp">
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
		<div class="menu_name">延滞者編集完了</div>
	</div>

	<div class="container">
		<div>
			<p>　　　編集完了しました</p>
		</div>

		<div>
			<ul>
				<li>会員ID：${overdue.userId}</li>
				<li>氏名：${overdue.name}</li>
				<li>E-Mail：${overdue.email}</li>
				<li>資料ID：${overdue.bookId}</li>
				<li>返却期日：${overdue.dueDate}</li>
				<c:if test="${not empty day}">
					<li>一次対応:
						<c:if test="${overdue.firstReminder==0}">
							未対応
						</c:if>
						<c:if test="${overdue.firstReminder==1}">
							対応中
						</c:if>
						<c:if test="${overdue.firstReminder==2}">
							対応済み
						</c:if>
					</li>
				</c:if>
				<c:if test="${not empty month}">
					<li>二次対応:
						<c:if test="${overdue.secondReminder==0}">
							未対応
						</c:if>
						<c:if test="${overdue.secondReminder==1}">
							対応中
						</c:if>
						<c:if test="${overdue.secondReminder==2}">
							対応済み
						</c:if>
					</li>
				</c:if>
				<li>備考:${overdue.memo}</li>
			</ul>
		</div>
		<br>
		<div class="transfar_link_top">
			<form action="/team_dev_library_system/other/other_overdue_top.jsp" method="post">
				<button>自動で遷移しない場合は<br>こちらをクリック</button>
				<input type="hidden" name="action" value="">
			</form>
		</div>

</body>

</html>