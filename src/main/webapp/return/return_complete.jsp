<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="../style.css" rel="stylesheet">
	<meta http-equiv="refresh" content="5;url=/team_dev_library_system/top/top.jsp">
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
		<div class="menu_name">返却</div>
	</div>
	
	<div class="container">
		<div>
			<p>返却が完了しました</p>
			<p>トップページへ遷移します</p>
		</div>
		
		<div>
			<ul>
				<li>貸出ID：${lendId}</li>
				<li>会員ID：${userId}</li>
				<li>資料ID：${bookId}</li>
				<li>資料名：${title}</li>
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