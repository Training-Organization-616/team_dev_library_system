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

	<jsp:include page="header.jsp" />

	<div class="top">
		<div class="top_container">
			<form action="/team_dev_library_system/lend/lend_top.jsp"method="post">
				<button class="top_button">貸出管理</button>
			</form>

			<form action="/team_dev_library_system/catalog/catalog_top.jsp"method="post">
				<button class="top_button">資料管理</button>
			</form>

			<form action="/team_dev_library_system/user/user_top.jsp"method="post">
				<button class="top_button">会員管理</button>
			</form>

			<form action="/team_dev_library_system/other/other_top.jsp"method="post">
				<button class="top_button">その他</button>
			</form>
		</div>
	</div>

	<jsp:include page="footer.jsp" />

</body>

</html>