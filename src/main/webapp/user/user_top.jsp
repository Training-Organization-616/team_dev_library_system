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

	<jsp:include page="../top/header.jsp" />

	<div class="holder">
		<div class="menu">
			<jsp:include page="../top/menu.jsp" />
			<%-- メニューはいったんぜんぶ表示してます --%>
		</div>
		<div class="menu_image">
			<img src="../image/画像名">
			<%-- imageフォルダに画像を入れて画像の名前を画像名に入れてください --%>
			<%-- 画像じゃない場合はリンク？ボタン？への変更をお願いします --%>
		</div>
		<div class="menu_name">会員管理</div>
	</div>
	
	<div class="container">
		<form action="user_add.jsp" method="post">
 			<button class="top_button">新規会員登録</button>
		</form>
		
		<form action="user_search.jsp" method="post">
 			<button class="top_button">会員検索</button>
		</form>
	</div>
	

	<jsp:include page="../top/footer.jsp" />

</body>

</html>