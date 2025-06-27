<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="/team_dev_library_system/style.css" rel="stylesheet">
	<meta http-equiv="refresh" content="5;url=/team_dev_library_system/catalog/catalog_search.jsp">
</head>

<body>

	<jsp:include page="../top/header.jsp" />

	<div class="menu">
		<jsp:include page="../top/menu.jsp" />
	</div>

	<div class="holder">
		<div class="menu_image">
			<img src="/team_dev_library_system/image/book_icon.png">
		</div>
		<div class="menu_name">資料編集</div>
	</div>

	<div class="container">
		<div>
			<p>　　　資料の情報を編集しました</p>
			<p>　　　検索画面へ遷移します</p>
		</div>

		<div>
			<ul>
				<li>資料ID：${book.bookId}</li>
				<li>ISBN番号：<c:if test = "${book.isbn != 0}">${book.isbn}</c:if></li>
				<li>資料名：${book.title}</li>
				<li>
					分類コード：
					<c:if test = "${book.code == 0 }">総記</c:if>
					<c:if test = "${book.code == 1 }">哲学</c:if>
					<c:if test = "${book.code == 2 }">歴史</c:if>
					<c:if test = "${book.code == 3 }">社会科学</c:if>
					<c:if test = "${book.code == 4 }">自然科学</c:if>
					<c:if test = "${book.code == 5 }">技術</c:if>
					<c:if test = "${book.code == 6 }">産業</c:if>
					<c:if test = "${book.code == 7 }">芸術</c:if>
					<c:if test = "${book.code == 8 }">言語</c:if>
					<c:if test = "${book.code == 9 }">文学</c:if>
				</li>
				<li>著者：${book.author}</li>
				<li>出版社：${book.publicher}</li>
				<li>出版日：${book.publicationDate}</li>
				<li>入荷年月日：${book.arrivalDate}</li>
			</ul>
		</div>
		<br>
		<div class="transfar_link_top">				
			<form action="/team_dev_library_system/catalog/catalog_search.jsp" method="post">
 				<button>自動で遷移しない場合はこちらをクリック</button>
				<input type="hidden" name="action" value="">
			</form>
		</div>

</body>

</html>