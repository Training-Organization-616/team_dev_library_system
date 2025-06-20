<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="/team_dev_library_system/style.css" rel="stylesheet">
	<meta http-equiv="refresh" content="5;url=/team_dev_library_system/top/top.jsp">
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
		<div class="menu_name">資料情報変更</div>
	</div>
	
	<div class="container">
		<div>
			<p>資料の情報を変更しました</p>
			<p>トップページへ遷移します</p>
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
				<%-- これも入荷年月日はリストにできない想定で書いています --%>
			</ul>
		</div>
		
		<div class="transfar_link">				
			<form action="/team_dev_library_system/top/top.jsp" method="post">
 				<button>自動で遷移しない場合はこちらをクリック</button>
				<input type="hidden" name="action" value="">
			</form>
		</div>

	<jsp:include page="../top/footer.jsp" />

</body>

</html>