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

	<div class="holder">
		<div class="menu">
			<jsp:include page="../top/menu.jsp" />
		</div>
	<div class="holder">
		<div class="menu_image">
			<img src="/team_dev_library_system/image/menu_icon.png">
			
		</div>
		<div class="menu_name">新規資料登録</div>
	</div>
	
	<div class="container">
		<div>
			<p>新規登録が完了しました</p>
		</div>
		
		<div>
			<ul>
				<li>資料ID：${book.bookId}</li>
				<li>ISBN番号：${book.isbn}</li>
				<li>資料名：${book.title}</li>
				<li>分類コード：${book.code }</li>
				<li>著者：${book.author}</li>
				<li>出版社：${book.publicher}</li>
				<li>出版日：${book.publicationDate}</li>
				<li>入荷年月日：${book.arrivalDate}</li>
				<%-- 入荷年月日はリストにできない想定（catalogBeanじゃないので単体でフォワードされる想定）で書いてます --%>
				<%-- 必要に応じて修正お願いします --%>
			</ul>
		</div>

	<jsp:include page="../top/footer.jsp" />

</body>

</html>