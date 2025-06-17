<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="style.css" rel="stylesheet">
</head>

<body>

	<jsp:include page="header.jsp" />

	<div class="container">
		<form action="../lend/lend_top.jsp" method="post">
 			<button class="top_button">貸出</button>
		</form>
		
		<form action="../return/return_top.jsp" method="post">
 			<button class="top_button">返却</button>
		</form>
		
		<form action="../catalog/catalog_top.jsp" method="post">
 			<button class="top_button">資料管理</button>
		</form>
		
		<form action="../user/user_top.jsp" method="post">
 			<button class="top_button">会員管理</button>
		</form>
	</div>

	<jsp:include page="footer.jsp" />

</body>

</html>