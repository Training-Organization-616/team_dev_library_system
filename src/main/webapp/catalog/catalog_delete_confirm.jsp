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
			<img src="/team_dev_library_system/image/book_icon.png">
		</div>
		<div class="menu_name">資料削除</div>
	</div>
	<br>
	<br>
	<div class="container">
		<div class="message">　　　以下の資料の情報を削除しますか？</div>

		<div>
			<ul>
				<li>資料ID：${book.bookId}</li>
				<li>ISBN番号：<c:if test = "${book.isbn != 0}">${book.isbn}</c:if></li>
				<li>資料名：${book.title}</li>
				<li>分類コード：
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
				<li>廃棄年月日：${disposalDate}</li>
				<li>備考：${memo}</li>
			</ul>
		</div>
		<div>
			<form action="/team_dev_library_system/CatalogServlet" method="post">
				<input type="hidden" name="action" value="delete"> <input
					type="hidden" name="book_id" value="${book.bookId}"> <input
					type="hidden" name="isbn" value="${book.isbn}"> <input
					type="hidden" name="title" value="${book.title}"> <input
					type="hidden" name="code" value="${book.code}"> <input
					type="hidden" name="author" value="${book.author}"> <input
					type="hidden" name="publicher" value="${book.publicher}"> <input
					type="hidden" name="publication_date"
					value="${book.publicationDate}"> <input type="hidden"
					name="arrival_date" value="${book.arrivalDate}"> <input
					type="hidden" name="disposal_date" value="${disposalDate}">
				<input type="hidden" name="memo" value="${memo}">
				<button class="general_button edit_button">削除</button>
			</form>
			<form action="/team_dev_library_system/CatalogServlet" method="post">
				<button class="return_button">戻る</button>
				<input type="hidden" name="action" value="return_search">
			</form>
		</div>

</body>

</html>