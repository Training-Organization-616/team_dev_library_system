<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	document.querySelector('.menu_image').addEventListener('click', function() {
		const menu = document.querySelector('.side-menu');
		menu.classList.toggle('open');
	});
</script>

<div class="menu_container">

	<p>メニュー</p>

	<div class="side-menu">

		<form action="/team_dev_library_system/TransitionServlet"
			method="post">
			<button class="menu_button">貸出管理</button>
			<input type="hidden" name="action" value="lend">
		</form>

		<form action="/team_dev_library_system/TransitionServlet"
			method="post">
			<button class="menu_button">資料管理</button>
			<input type="hidden" name="action" value="catalog">
		</form>

		<form action="/team_dev_library_system/TransitionServlet"
			method="post">
			<button class="menu_button">会員管理</button>
			<input type="hidden" name="action" value="user">
		</form>

		<form action="/team_dev_library_system/TransitionServlet"
			method="post">
			<button class="menu_button">その他</button>
			<input type="hidden" name="action" value="other">
		</form>
	</div>
</div>