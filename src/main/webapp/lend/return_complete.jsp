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
		<div class="menu_name">返却</div>
	</div>
	
	<div class="container">
		<div>
			<p>　　　返却が完了しました</p>
		</div>
		
		<div>
			<ul>
				<li>貸出ID：${lendId}</li>
				<li>会員ID：${userId}</li>
				<li>資料ID：${bookId}</li>
				<li>資料名：${title}</li>
			</ul>
		</div>
		
		<div class="message_lend">${message}</div>

		<div class="transfar_link">
			<form action="/team_dev_library_system/lend/return_add.jsp" method="post">
 				<button>続けて返却</button>
			</form>
		</div>

</body>

</html>