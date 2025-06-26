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
			<img src="/team_dev_library_system/image/library_icon.png">
		</div>
		<div class="menu_name">新規予約</div>
	</div>
	
	<div class="container">
		<div>
			<p>　　　予約が完了しました</p>
		</div>
		
		<div>
			<ul>
				<li>予約ID：${reservationId}</li>
				<li>会員ID：${userId}</li>
				<li>資料ID：${bookId}</li>
				<li>資料名：${title}</li>
			</ul>
		</div>
		<br>
		<div class="message_num_lend">				
			<div class="message">${reservationAmount}</div>
			<div class="message_num">件目の予約です。</div>
		</div>
	</div>

</body>

</html>