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
		<div class="menu_name">会員情報削除</div>
	</div>
	
	<div class="container">
		<div class="delete_message">
			<p>以下の会員情報を削除しますか？</p>
		</div>
		
		<div>
			<ul>
				<li>会員ID：${user.userId}</li>
				<li>氏名：${user.userName}</li>
				<li>住所：${user.address}</li>
				<li>電話番号：${user.tel}</li>
				<li>E-Mail：${user.email}</li>
				<li>生年月日：${user.birthday}</li>
				<li>入会年月日：${user.admissionDate}</li>
			</ul>
		</div>	

		<div>
			<form action="/team_dev_library_system/UserServlet" method="post">
				<input type="hidden" name="action" value="delete">				
				<button class="general_button edit_button">削除</button>
			</form>	
			<form action="/team_dev_library_system/UserServlet" method="post">
 				<button class="return_button">戻る</button>
				<input type="hidden" name="action" value="return_search">
			</form>
		</div>
	</div>
	

	<jsp:include page="../top/footer.jsp" />

</body>

</html>