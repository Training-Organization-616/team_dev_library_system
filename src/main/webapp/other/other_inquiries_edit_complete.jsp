<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="/team_dev_library_system/style.css" rel="stylesheet">
	<meta http-equiv="refresh" content="5;url=/team_dev_library_system/other/other_inquiries_top.jsp">
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
		<div class="menu_name">お問い合わせ編集完了</div>
	</div>

	<div class="container">
		<div>
			<p>　　　編集完了しました</p>
			<p>　　　お問い合わせ一覧画面へ遷移します</p>
		</div>

		<div>
			<ul>
				<li>受付日：${inquiry.receptionDate}</li>
				<li>タイトル：${inquiry.contentsTitle}</li>
				<li>内容：${inquiry.contents}</li>
				<li>対応有無：
					<c:if test = "${inquiry.handling eq 0}" >
						未対応
					</c:if>
					<c:if test="${inquiry.handling eq 1}">
						対応中
					</c:if>
					<c:if test="${inquiry.handling eq 2}">
						対応済み
					</c:if>
				</li>
				<li>備考：${inquiry.memo}</li>
			</ul>
		</div>
		<br>
		<div class="transfar_link_top">				
			<form action="/team_dev_library_system/other/other_inquiries_top.jsp" method="post">
				<button>自動で遷移しない場合はこちらをクリック</button>
				<input type="hidden" name="action" value="">
			</form>
		</div>

</body>

</html>