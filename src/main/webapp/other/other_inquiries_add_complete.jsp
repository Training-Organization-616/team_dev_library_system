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
		<div class="menu_name">お問い合わせ登録完了</div>
	</div>

	<div class="container">
		<div>
			<p>　　　登録完了しました</p>
		</div>

		<div>
			<ul>
				<li>受付日：${inquiry.receptionDate}</li>
				<li>タイトル：${inquiry.contentsTitle}</li>
				<li>内容：${inquiry.contents}</li>
				<li>対応有無：
					<c:if test="${inquiry.handling==0}">
						未対応
					</c:if>
					<c:if test="${inquiry.handling==1}">
						対応中
					</c:if>
					<c:if test="${inquiry.handling==2}">
						対応済み
					</c:if>
				</li>
				<li>備考：${inquiry.memo}</li>
			</ul>
		</div>

</body>

</html>