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
		<div class="menu_name">新規会員登録</div>
	</div>
	
	<div class="container">
		<div>
			<p>登録する会員情報を入力してください</p>
		</div>
		
		<div class="message">
			${message}
		</div>
		
		<div class="input_table">
			<form action="/team_dev_library_system/UserServlet" method="post">
				<input type="hidden" name="action" value="add">
				<table>
					<tr>
				    	<th>氏名</th>
				    	<td>
				    		<input class="input_form" type="text" name="user_name" value="${user.userName}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>住所</th>
				    	<td>
				    		<input class="input_form" type="text" name="user_address" value="${user.address}">
				    	</td>
				    </tr>
				    			<tr>
				    	<th>電話番号</th>
				    	<td>
				    		<input class="input_form" type="text" name="user_tel" value="${user.tel}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>E-Mail</th>
				    	<td>
				    		<input class="input_form" type="email" name="user_email" value="${usre.email}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>生年月日</th>
				    	<td>
				    		<input class="input_form" type="date" name="user_birthday" value="${user.birthday}">
				    	</td>
				    </tr>
				</table>				
				<button class="general_button">新規登録</button>
			</form>
			
		</div>
		
		<div>
			<form action="/team_dev_library_system/UserServlet" method="post">
 				<button class="return_button">戻る</button>
				<input type="hidden" name="action" value="return_add">
			</form>
		</div>
	</div>
	

	<jsp:include page="../top/footer.jsp" />

</body>

</html>