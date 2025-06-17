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
			<p>貸出ID、会員ID、資料IDを入力してください</p>
		</div>
		
		<div class="message">
			${message}
		</div>
		
		<div class="input_table">
			<form action="/team_dev_library_system/UserServlet" method="post">
				<input type="hidden" name="action" value="add">
				<table>
					<tr>
				    	<th>貸出ID</th>
				    	<td>
				    		<input class="input_form" type="text" name="lend_id" value="${lendId}">
				    	</td>
				    </tr>
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
				<button class="general_button">返却</button>
			</form>
			
		</div>	
	</div>

	<jsp:include page="../top/footer.jsp" />

</body>

</html>