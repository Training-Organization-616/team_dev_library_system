<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="/team_dev_library_system/style.css" rel="stylesheet">
	<meta http-equiv="refresh"content="5;url=/team_dev_library_system/catalog/catalog_search.jsp">
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
		<div class="menu_name">資料削除</div>
	</div>

	<div class="container">
		<div class="message" >　　　削除完了しました</div>
		<div>
			<p>　　　検索画面へ遷移します</p>
		</div>

		<div>
			<ul>
				<li>資料ID：${book.bookId}</li>
				<li>ISBN番号：${book.isbn}</li>
				<li>資料名：${book.title}</li>
				<li>分類コード：${book.code}</li>
				<li>著者：${book.author}</li>
				<li>出版社：${book.publicher}</li>
				<li>出版日：${book.publicationDate}</li>
				<li>入荷年月日：${book.arrivalDate}</li>
				<li>廃棄年月日：${book.disposalDate}</li>
				<li>備考：${memo}</li>
			</ul>
		</div>
		<br>
		<div class="transfar_link">
			<form action="/team_dev_library_system/catalog/catalog_search.jsp" method="post">
				<button>自動で遷移しない場合はこちらをクリック</button>
				<input type="hidden" name="action" value="">
			</form>
		</div>
</body>

</html>