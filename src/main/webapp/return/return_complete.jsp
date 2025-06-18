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
			<form action="return_top.jsp" method="post">
 				<button>続けて返却</button>
				<input type="hidden" name="action" value="">
			</form>
		</div>

	<jsp:include page="../top/footer.jsp" />

</body>

</html>