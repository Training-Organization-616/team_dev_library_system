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
		<div class="menu_name">資料情報変更</div>
	</div>
	
	<div class="container">
		<div>
			<p>変更項目を入力してください</p>
		</div>
		
		<div class="message">
			${message}
		</div>
		
		<div class="input_table">
			<form action="/team_dev_library_system/CatalogServlet" method="post">
				<input type="hidden" name="action" value="update">
				<table>
					<tr>
				    	<th>資料ID</th>
				    	<td>
				    		${book.bookId}
				    		<input type="hidden" name="book_id" value="${book.bookId}">
				    	</td>
				    </tr>
					<tr>
				    	<th>ISBN番号</th>
				    	<td>
				    		<input class="input_form" type="text" name="isbn" value="${book.isbn}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>資料名</th>
				    	<td>
				    		<input class="input_form" type="text" name="title" value="${book.title}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>分類コード</th>
				    	<td>
				    		<select name="code">
				    			<c:choose>
				    				<c:when test="${book.code == 0}"><option value="0" selected>総記</option></c:when>
				    				<c:when test="${book.code == 1}"><option value="1" selected>哲学</option></c:when>
				    				<c:when test="${book.code == 2}"><option value="2" selected>歴史</option></c:when>
				    				<c:when test="${book.code == 3}"><option value="3" selected>社会科学</option></c:when>
				    				<c:when test="${book.code == 4}"><option value="4" selected>自然科学</option></c:when>
				    				<c:when test="${book.code == 5}"><option value="5" selected>技術</option></c:when>
				    				<c:when test="${book.code == 6}"><option value="6" selected>産業</option></c:when>
				    				<c:when test="${book.code == 7}"><option value="7" selected>芸術</option></c:when>
				    				<c:when test="${book.code == 8}"><option value="8" selected>言語</option></c:when>
				    				<c:when test="${book.code == 9}"><option value="9" selected>文学</option></c:when>
			    				</c:choose>
<!--				    			<option value="0">総記</option>-->
<!--				    			<option value="1">哲学</option>-->
<!--				    			<option value="2">歴史</option>-->
<!--				    			<option value="3">社会科学</option>-->
<!--				    			<option value="4">自然科学</option>-->
<!--				    			<option value="5">技術</option>-->
<!--				    			<option value="6">産業</option>-->
<!--				    			<option value="7">芸術</option>-->
<!--				    			<option value="8">言語</option>-->
<!--				    			<option value="9">文学</option>-->
				    		</select>
				    	</td>
				    </tr>
				    <tr>
				    	<th>著者</th>
				    	<td>
				    		<input class="input_form" type="text" name="author" value="${book.author}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>出版社</th>
				    	<td>
				    		<input class="input_form" type="text" name="publicher" value="${book.publicher}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>出版日</th>
				    	<td>
				    		<input class="input_form" type="date" name="publication_date" value="${book.publicationDate}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>入荷年月日</th>
				    	<td>
				    		<input class="input_form" type="date" name="arrival_date" value="${book.arrivalDate}">
				    		<%-- これも入荷年月日はリストにできない想定で書いています --%>
				    	</td>
				    </tr>
				</table>				
				<button class="general_button edit_button">変更</button>
			</form>
			
		</div>
		
		<div>
			<form action="/team_dev_library_system/CatalogServlet" method="post">
				<input type="hidden" name="action" value="return_search">
 				<button class="return_button">戻る</button>
			</form>
		</div>
	</div>
	

	<jsp:include page="../top/footer.jsp" />

</body>

</html>