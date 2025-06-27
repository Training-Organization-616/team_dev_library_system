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
		<div class="menu_name">資料検索</div>
	</div>
	
	<div class="container">
		<div>
			<p>　　　検索する資料の情報を入力してください</p>
		</div>
		<div class="message">　　　　${message}</div>
		<div class="input_table">
			<form action="/team_dev_library_system/CatalogServlet" method="post">
				<input type="hidden" name="action" value="search">
				<table class="input_search_table_catalog">
					<tr>
				    	<th>資料ID</th>
				    	<td>
				    		<input class="input_form" type="text" size="40" name="book_id" value="${bookId}">
				    	</td>
				    </tr>
					<tr>
				    	<th>資料名</th>
				    	<td>
				    		<input class="input_form" type="text" size="40" name="title" value="${title}">
				    	</td>
				    </tr>
					<tr>
				    	<th>分類コード</th>
				    	<td>
				    		<select name="code">
				    			<option value="">選択</option>
				    			<option value="0"<c:if test="${code==0}">selected</c:if>>総記</option>
				    			<option value="1"<c:if test="${code==1}">selected</c:if>>哲学</option>
				    			<option value="2"<c:if test="${code==2}">selected</c:if>>歴史</option>
				    			<option value="3"<c:if test="${code==3}">selected</c:if>>社会科学</option>
				    			<option value="4"<c:if test="${code==4}">selected</c:if>>自然科学</option>
				    			<option value="5"<c:if test="${code==5}">selected</c:if>>技術</option>
				    			<option value="6"<c:if test="${code==6}">selected</c:if>>産業</option>
				    			<option value="7"<c:if test="${code==7}">selected</c:if>>芸術</option>
				    			<option value="8"<c:if test="${code==8}">selected</c:if>>言語</option>
				    			<option value="9"<c:if test="${code==9}">selected</c:if>>文学</option>
				    		</select>
				    	</td>
				    </tr>
				    <tr>
				    	<th>著者</th>
				    	<td>
				    		<input class="input_form" type="text" size="40" name="author" value="${author}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>出版社</th>
				    	<td>
				    		<input class="input_form" type="text" size="40" name="publicher" value="${publicher}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>背ラベル</th>
				    	<td>
				    		<select name="label_code">
				    			<option value="">選択</option>
				    			<option value="0"<c:if test="${labelCode==0}">selected</c:if>>総記</option>
				    			<option value="1"<c:if test="${labelCode==1}">selected</c:if>>哲学</option>
				    			<option value="2"<c:if test="${labelCode==2}">selected</c:if>>歴史</option>
				    			<option value="3"<c:if test="${labelCode==3}">selected</c:if>>社会科学</option>
				    			<option value="4"<c:if test="${labelCode==4}">selected</c:if>>自然科学</option>
				    			<option value="5"<c:if test="${labelCode==5}">selected</c:if>>技術</option>
				    			<option value="6"<c:if test="${labelCode==6}">selected</c:if>>産業</option>
				    			<option value="7"<c:if test="${labelCode==7}">selected</c:if>>芸術</option>
				    			<option value="8"<c:if test="${labelCode==8}">selected</c:if>>言語</option>
				    			<option value="9"<c:if test="${labelCode==9}">selected</c:if>>文学</option>
				    		</select>
				    		<span> - </span>
				    		<input class="input_form label_form" type="text" size="5" name="label_author" value="${labelAuthor}">
				    		<span> - </span>
				    		<input class="input_form label_form" type="text" size="5" name="volume_number" value="${volumeNumber}">
				    	</td>
				    </tr>
				    <tr>
				    	<td colspan="2"><button class="general_button_search_button">検索</button></td>
				    </tr>
				</table>
			</form>			
		</div>
		<br>
		<br>
		<c:if test="${!empty books}">
			<div class="search_result_table_container">
			 	<table class="search_result_table_catalog">
			 		<tr>
				    	<th>資料ID</th>
				    	<th>ISBN番号</th>
				    	<th>資料名</th>
				    	<th>著者</th>
				    	<th>出版社</th>
				    	<th>出版日</th>
				    	<th>編集</th>
				    	<th>削除</th>
				    </tr>
					<c:forEach items="${books}" var="book">
					    <tr>
					    	<td>${book.bookId}</td>
					    	<td>
					    		<c:if test = "${book.isbn != 0}">${book.isbn }</c:if>
					    	</td>
					    	<td>${book.title}</td>
					    	<td>${book.author}</td>
					    	<td>${book.publicher}</td>
					    	<td>${book.publicationDate}</td>
					    	<td>
					    		<form action="/team_dev_library_system/CatalogServlet" method="post">
				    				<button class="table_edit_button">編集</button>
				    				<input type="hidden" name="action" value="edit_page">
				    				<input type="hidden" name="book_id" value="${book.bookId}">
				    				<input type="hidden" name="isbn" value="${book.isbn}">
				    				<input type="hidden" name="title" value="${book.title}">
				    				<input type="hidden" name="code" value="${book.code}">
				    				<input type="hidden" name="author" value="${book.author}">
				    				<input type="hidden" name="publicher" value="${book.publicher}">
				    				<input type="hidden" name="publication_date" value="${book.publicationDate}">
				    				<input type="hidden" name="arrival_date" value="${book.arrivalDate}">
			    				</form>
					    	</td>
					    	<td>
					    		<form action="/team_dev_library_system/CatalogServlet" method="post">
				    				<button class="table_edit_button">削除</button>
				    				<input type="hidden" name="action" value="delete_page">
				    				<input type="hidden" name="book_id" value="${book.bookId}">
				    				<input type="hidden" name="isbn" value="${book.isbn}">
				    				<input type="hidden" name="title" value="${book.title}">
				    				<input type="hidden" name="code" value="${book.code}">
				    				<input type="hidden" name="author" value="${book.author}">
				    				<input type="hidden" name="publicher" value="${book.publicher}">
				    				<input type="hidden" name="publication_date" value="${book.publicationDate}">
				    				<input type="hidden" name="arrival_date" value="${book.arrivalDate}">
			    				</form>
					    	</td>
					    </tr>
					</c:forEach>
			 	</table>
			</div>
		</c:if>
		<br>
		<div class="return_button_contents">
			<form action="/team_dev_library_system/CatalogServlet" method="post">
 				<button class="search_return_button">戻る</button>
				<input type="hidden" name="action" value="return_add">
			</form>
		</div>
</body>

</html>