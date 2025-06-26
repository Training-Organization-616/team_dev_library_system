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
	
	<div class="container">
		
		<div>
			<p>　　　削除する資料を確認し、項目を入力してください</p>
		</div>
		
		<div class="message">　　　　${message}</div>
		
		<div class="input_table">
			<form action="/team_dev_library_system/CatalogServlet" method="post">
				<input type="hidden" name="action" value="delete_confirm">
				<table>
					<tr>
				    	<th>資料ID</th>
				    	<td>
				    		<span>&nbsp</span>
				    		${book.bookId}
				    	</td>
				    </tr>
					<tr>
				    	<th>ISBN番号</th>
				    	<td>
				    		<span>&nbsp</span>
				    		<c:if test = "${book.isbn != 0}">${book.isbn}</c:if>
				    	</td>
				    </tr>
				    <tr>
				    	<th>資料名</th>
				    	<td>
				    		<span>&nbsp</span>
				    		${book.title}
				    	</td>
				    </tr>
				    <tr>
				    	<th>分類コード</th>
				    	<td>
				    		<span>&nbsp</span>
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
				    		<span>&nbsp</span>
				    		${book.author}
				    	</td>
				    </tr>
				    <tr>
				    	<th>出版社</th>
				    	<td>
				    		<span>&nbsp</span>
				    		${book.publicher}
				    	</td>
				    </tr>
				    <tr>
				    	<th>出版日</th>
				    	<td>
				    		<span>&nbsp</span>
				    		${book.publicationDate}
				    	</td>
				    </tr>
				    <tr>
				    	<th>入荷年月日</th>
				    	<td>
				    		<span>&nbsp</span>
				    		${book.arrivalDate}
				    	</td>
				    </tr>
				    <tr>
				    	<th>廃棄年月日</th>
				    	<td>
				    		<input class="input_form" type="date" name="disposal_date" value="${disposalDate}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>備考</th>
				    	<td>
				    		<textarea name = "memo">${memo}</textarea>
				    	</td>
				    </tr>
				</table>		
				<input type="hidden" name="book_id" value="${book.bookId}">
   				<input type="hidden" name="isbn" value="${book.isbn}">
   				<input type="hidden" name="title" value="${book.title}">
   				<input type="hidden" name="code" value="${book.code}">
   				<input type="hidden" name="author" value="${book.author}">
   				<input type="hidden" name="publicher" value="${book.publicher}">
   				<input type="hidden" name="publication_date" value="${book.publicationDate}">
   				<input type="hidden" name="arrival_date" value="${book.arrivalDate}">	
   				<br>	
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

</body>

</html>