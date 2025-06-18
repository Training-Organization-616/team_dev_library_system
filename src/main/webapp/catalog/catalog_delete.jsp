<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
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
		
		<div>
			<p>削除する資料を確認し、項目を入力してください</p>
		</div>
		
		<div class="message">
			${message}
		</div>
		
		<div class="input_table">
			<form action="/team_dev_library_system/CatalogServlet" method="post">
				<input type="hidden" name="action" value="delete_confirm">
				<table>
					<tr>
				    	<th>資料ID</th>
				    	<td>
				    		${book.bookId}
				    	</td>
				    </tr>
					<tr>
				    	<th>ISBN番号</th>
				    	<td>
				    		${book.isbn}
				    	</td>
				    </tr>
				    <tr>
				    	<th>資料名</th>
				    	<td>
				    		${book.title}
				    	</td>
				    </tr>
				    <tr>
				    	<th>分類コード</th>
				    	<td>
			    			<c:choose>
			    				<c:when test="${book.code == 0}">総記</c:when>
			    				<c:when test="${book.code == 1}">哲学</c:when>
			    				<c:when test="${book.code == 2}">歴史</c:when>
			    				<c:when test="${book.code == 3}">社会科学</c:when>
			    				<c:when test="${book.code == 4}">自然科学</c:when>
			    				<c:when test="${book.code == 5}">技術</c:when>
			    				<c:when test="${book.code == 6}">産業</c:when>
			    				<c:when test="${book.code == 7}">芸術</c:when>
			    				<c:when test="${book.code == 8}">言語</c:when>
			    				<c:when test="${book.code == 9}">文学</c:when>
			    			</c:choose>
				    	</td>
				    </tr>
				    <tr>
				    	<th>著者</th>
				    	<td>
				    		${book.author}
				    	</td>
				    </tr>
				    <tr>
				    	<th>出版社</th>
				    	<td>
				    		${book.publicher}
				    	</td>
				    </tr>
				    <tr>
				    	<th>出版日</th>
				    	<td>
				    		${book.publicationDate}
				    	</td>
				    </tr>
				    <tr>
				    	<th>入荷年月日</th>
				    	<td>
				    		${arrivalDate}
				    		<%-- これも入荷年月日はリストにできない想定で書いています --%>
				    	</td>
				    </tr>
				    <tr>
				    	<th>廃棄年月日</th>
				    	<td>
				    		<input class="input_form" type="date" name="disposal_date" value="${disposalDate}">
				    		<%-- 廃棄年月日もリストにできない想定で書いています --%>
				    	</td>
				    </tr>
				    <tr>
				    	<th>備考</th>
				    	<td>
				    		<textarea>${memo}</textarea>
				    		<%-- 備考も同上です --%>
				    	</td>
				    </tr>
				</table>				
				<button class="general_button edit_button">削除</button>
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