<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="../style.css" rel="stylesheet">
</head>

<body>

	<jsp:include page="../top/header.jsp" />

	<div class="menu_container">
		<div class="menu">
			<jsp:include page="../top/menu.jsp" />
			<%-- メニューはいったんぜんぶ表示してます --%>
		</div>
		<div class="menu_image">
			<img src="../image/画像名">
			<%-- imageフォルダに画像を入れて画像の名前を画像名に入れてください --%>
			<%-- 画像じゃない場合はリンク？ボタン？への変更をお願いします --%>
		</div>
		<div class="menu_name">資料削除</div>
	</div>
	
	<div class="container">
		<div class="message">
			以下の資料の情報を削除しますか？
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
				<li>入荷年月日：${arrivalDate}</li>
				<li>廃棄年月日：${disposalDate}</li>
				<li>備考：${memo}</li>
			</ul>
		</div>
		
		<div>
			<form action="/team_dev_library_system/CatalogServlet" method="post">
				<input type="hidden" name="action" value="delete">				
				<button class="general_button edit_button">削除</button>
			</form>	
			<form action="/team_dev_library_system/CatalogServlet" method="post">
 				<button class="return_button">戻る</button>
				<input type="hidden" name="action" value="return_search">
			</form>
		</div>

	<jsp:include page="../top/footer.jsp" />

</body>

</html>