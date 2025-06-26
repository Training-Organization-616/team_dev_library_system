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
		<div class="menu_name">資料情報編集</div>
	</div>
	
	<div class="container">
		<div>
			<p>　　　編集する項目を入力してください</p>
		</div>
		<div class="message">　　　　${message}</div>
		<div class="input_table">
			<form action="/team_dev_library_system/CatalogServlet" method="post">
				<input type="hidden" name="action" value="update">
				<table>
					<tr>
				    	<th>資料ID</th>
				    	<td>
				    		<span>&nbsp</span>
				    		${book.bookId}
				    		<input type="hidden" name="book_id" value="${book.bookId}">
				    	</td>
				    </tr>
					<tr>
				    	<th>ISBN番号</th>
				    	<td>
				    		<input class="input_form" type="text" size="40" name="isbn" value="${book.isbn}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>資料名</th>
				    	<td>
				    		<input class="input_form" type="text" size="40" name="title" value="${book.title}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>分類コード</th>
				    	<td>
				    		<select name="code">
				    			<option value="">選択</option>
				    			<option value="0"<c:if test="${book.code==0}">selected</c:if>>総記</option>
				    			<option value="1"<c:if test="${book.code==1}">selected</c:if>>哲学</option>
				    			<option value="2"<c:if test="${book.code==2}">selected</c:if>>歴史</option>
				    			<option value="3"<c:if test="${book.code==3}">selected</c:if>>社会科学</option>
				    			<option value="4"<c:if test="${book.code==4}">selected</c:if>>自然科学</option>
				    			<option value="5"<c:if test="${book.code==5}">selected</c:if>>技術</option>
				    			<option value="6"<c:if test="${book.code==6}">selected</c:if>>産業</option>
				    			<option value="7"<c:if test="${book.code==7}">selected</c:if>>芸術</option>
				    			<option value="8"<c:if test="${book.code==8}">selected</c:if>>言語</option>
				    			<option value="9"<c:if test="${book.code==9}">selected</c:if>>文学</option>
				    		</select>
				    	</td>
				    </tr>
				    <tr>
				    	<th>著者</th>
				    	<td>
				    		<input class="input_form" type="text" size="40" name="author" value="${book.author}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>出版社</th>
				    	<td>
				    		<input class="input_form" type="text" size="40" name="publicher" value="${book.publicher}">
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
				    	</td>
				    </tr>
				</table>
				<br>				
				<button class="general_button edit_button">編集</button>
			</form>
		</div>
		
		<div>
			<form action="/team_dev_library_system/CatalogServlet" method="post">
				<input type="hidden" name="action" value="return_search">
 				<button class="return_button">戻る</button>
			</form>
		</div>
	</div>

</body>

</html>