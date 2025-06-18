<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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
			<img src="/team_dev_library_system/image/menu_icon.png">
			
		</div>
		<div class="menu_name">貸出</div>
	</div>
	
	<div class="container">
		<div>
			<p>会員ID、資料IDを入力してください</p>
		</div>
		
		<div class="message">
			${message}
		</div>
		
		<div class="input_table">
			<form action="/team_dev_library_system/LendServlet" method="post">
				<input type="hidden" name="action" value="lend">
				<table>
				    <tr>
				    	<th>会員ID</th>
				    	<td>
				    		<input class="input_form" type="text" name="user_id" value="${userId}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>資料ID</th>
				    	<td>
				    		<input class="input_form" type="text" name="book_id" value="${bookId}">
				    	</td>
				    </tr>
				</table>				
				<button class="general_button">貸出</button>
			</form>
			
		</div>	
	</div>

	<jsp:include page="../top/footer.jsp" />

</body>

</html>