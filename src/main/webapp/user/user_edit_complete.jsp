<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="style.css" rel="stylesheet">
	<meta http-equiv="refresh" content="5;url=/team_dev_library_system/top/top.jsp">
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
		<div class="menu_name">会員情報変更</div>
	</div>
	
	<div class="user_container">
		<div>
			<p>会員情報を変更しました</p>
			<p>トップページへ遷移します</p>
		</div>
		
		<div>
			<ul>
				<li>会員ID：${user.userId}</li>
				<li>氏名：${user.userName}</li>
				<li>住所：${user.userAdress}</li>
				<li>電話番号：${user.userTel}</li>
				<li>E-Mail：${user.userEmail}</li>
				<li>生年月日：${user.userBirthday}</li>
				<li>入会年月日：${user.userAdmissionDate}</li>
			</ul>
		</div>
		
		<div class="transfar_link">				
			<form action="/team_dev_library_system/top/top.jsp" method="post">
 				<button>自動で遷移しない場合はこちらをクリック</button>
				<input type="hidden" name="action" value="">
			</form>
		</div>

	<jsp:include page="../top/footer.jsp" />

</body>

</html>