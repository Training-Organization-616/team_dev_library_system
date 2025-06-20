<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="menu_container">

	<p>メニュー</p>

	<form action="/team_dev_library_system/TransitionServlet" method="post">
		<button class="menu_button">貸出</button>
		<input type="hidden" name="action" value="lend">
	</form>
	
	<form action="/team_dev_library_system/TransitionServlet" method="post">
		<button class="menu_button">返却</button>
		<input type="hidden" name="action" value="return">
	</form>
	
	<form action="/team_dev_library_system/TransitionServlet" method="post">
		<button class="menu_button">資料管理</button>
		<input type="hidden" name="action" value="catalog">
	</form>
	
	<form action="/team_dev_library_system/TransitionServlet" method="post">
		<button class="menu_button">会員管理</button>
		<input type="hidden" name="action" value="user">
	</form>
</div>