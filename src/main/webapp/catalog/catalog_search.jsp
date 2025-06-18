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
		<div class="menu_name">資料検索</div>
	</div>
	
	<div class="container">
		<div>
			<p>検索する資料の情報を入力してください</p>
		</div>
		
		<div class="message">
			${message}
		</div>
		
		<div class="input_table">
			<form action="/team_dev_library_system/CatalogServlet" method="post">
				<input type="hidden" name="action" value="search">
				<table>
					<tr>
				    	<th>資料名</th>
				    	<td>
				    		<input class="input_form" type="text" name="title" value="${title}">
				    	</td>
				    </tr>
					<tr>
				    	<th>分類コード</th>
				    	<td>
				    		<select name="code">
				    			<option value="" selected>選択</option>
				    			<option value="0">総記</option>
				    			<option value="1">哲学</option>
				    			<option value="2">歴史</option>
				    			<option value="3">社会科学</option>
				    			<option value="4">自然科学</option>
				    			<option value="5">技術</option>
				    			<option value="6">産業</option>
				    			<option value="7">芸術</option>
				    			<option value="8">言語</option>
				    			<option value="9">文学</option>
				    		</select>
				    	</td>
				    </tr>
				    <tr>
				    	<th>著者</th>
				    	<td>
				    		<input class="input_form" type="text" name="author" value="${author}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>出版社</th>
				    	<td>
				    		<input class="input_form" type="text" name="publicher" value="${publicher}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>背ラベル</th>
				    	<td>
				    		<select name="label_code">
				    			<option value="" selected>選択</option>
				    			<option value="0">総記</option>
				    			<option value="1">哲学</option>
				    			<option value="2">歴史</option>
				    			<option value="3">社会科学</option>
				    			<option value="4">自然科学</option>
				    			<option value="5">技術</option>
				    			<option value="6">産業</option>
				    			<option value="7">芸術</option>
				    			<option value="8">言語</option>
				    			<option value="9">文学</option>
				    		</select>
				    		<span> - </span>
				    		<input class="input_form label_form" type="text" name="label_author" value="${labelAuthor}">
				    		<span> - </span>
				    		<input class="input_form label_form" type="text" name="volume_number" value="${volumeNumber}">
				    	</td>
				    </tr>
				</table>				
				<button class="general_button search_button">検索</button>
			</form>			
		</div>
		
		<c:if test="${!empty books}">
			<div class="search_result_table">
			 	<table>
			 		<tr>
				    	<th>資料ID</th>
				    	<th>ISBN番号</th>
				    	<th>資料名</th>
				    	<th>著者</th>
				    	<th>出版社</th>
				    	<th>出版日</th>
				    	<th>変更</th>
				    	<th>削除</th>
				    </tr>
		
					<c:forEach items="${books}" var="book">
					    <tr>
					    	<td>${book.bookId}</td>
					    	<td>${book.isbn}</td>
					    	<td>${book.title}</td>
					    	<td>${book.author}</td>
					    	<td>${book.publicher}</td>
					    	<td>${book.publicationDate}</td>
					    	<td>
					    		<form action="/team_dev_library_system/CatalogServlet" method="post">
				    				<button class="edit_button">変更</button>
				    				<input type="hidden" name="action" value="edit_page">
				    				<input type="hidden" name="book_id" value="${book.bookId}">
			    				</form>
					    	</td>
					    	<td>
					    		<form action="/team_dev_library_system/CatalogServlet" method="post">
				    				<button class="edit_button">削除</button>
				    				<input type="hidden" name="action" value="delete_page">
				    				<input type="hidden" name="book_id" value="${book.bookId}">
			    				</form>
					    	</td>
					    </tr>
					</c:forEach>
			 	</table>
			</div>
		</c:if>
		
		<div>
			<form action="/team_dev_library_system/CatalogServlet" method="post">
 				<button class="return_button">戻る</button>
				<input type="hidden" name="action" value="return_add">
			</form>
		</div>
	

	<jsp:include page="../top/footer.jsp" />

</body>

</html>