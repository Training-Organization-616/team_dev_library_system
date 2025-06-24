<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="/team_dev_library_system/style.css" rel="stylesheet">
	<meta http-equiv="refresh" content="5;url=/team_dev_library_system/lend/reservation_search.jsp">
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
		<div class="menu_name">予約編集完了</div>
	</div>

	<div class="container">
		<div>
			<p>　　　編集完了しました</p>
			<p>　　　検索ページへ遷移します</p>
		</div>

		<div>
			<ul>
				<li>予約ID：${reservation.reservationId}</li>
				<li>予約年月日：${reservation.reservationDate}</li>
				<li>会員ID：${reservation.userId}</li>
				<li>氏名：${reservation.name}</li>
				<li>電話番号：${reservation.tel}</li>
				<li>E-mail：${reservation.email}</li>
				<li>資料ID：${reservation.bookId}</li>
				<li>資料名：${reservation.title}</li>
				<li>
					貸出有無：
					<c:if test="${reservation.alreadyLent == 0}">
						返却待ち
					</c:if>
					<c:if test="${reservation.alreadyLent == 1}">
						資料確保
					</c:if>
					<c:if test="${reservation.alreadyLent == 2}">
						対応終了
					</c:if>
				</li>
				<li>備考：${reservation.memo}</li>
			</ul>
		</div>

		<div class="transfar_link">
			<form action="/team_dev_library_system/lend/reservation_search.jsp" method="post">
				<button>自動で遷移しない場合はこちらをクリック</button>
				<input type="hidden" name="action" value="">
			</form>
		</div>

</body>

</html>