<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="menu_container">

	<p>メニュー</p>

	<form action="../lend/lend_top.jsp" method="post">
		<button class="menu_button">貸出</button>
	</form>
	
	<form action="../return/return_top.jsp" method="post">
		<button class="menu_button">返却</button>
	</form>
	
	<form action="../catalog/catalog_top.jsp" method="post">
		<button class="menu_button">資料管理</button>
	</form>
	
	<form action="../user/user_top.jsp" method="post">
		<button class="menu_button">会員管理</button>
	</form>
</div>