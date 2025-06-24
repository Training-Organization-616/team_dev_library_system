<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="/team_dev_library_system/style.css" rel="stylesheet">
</head>

<body>

	<jsp:include page="../top/header.jsp" />

	<div class="menu">
		<jsp:include page="../top/menu.jsp" />

	</div>
	<div class="holder">
		<div class="menu_image">
			<img src="/team_dev_library_system/image/menu_icon.png">
		</div>
		<div class="menu_name">予約編集</div>
	</div>

	<div class="container">
		<div>
			<p>　　　編集する項目を入力してください</p>
		</div>

		<div class="message">　　　　${message}</div>

		<div class="input_table">
			<form action="/team_dev_library_system/ReservationServlet" method="post">
				<input type="hidden" name="action" value="update">
				<table>
					<tr>
						<th>予約ID</th>
						<td>
							${reservation.reservationId} 
							<input type="hidden" name="reservation_id" value="${reservation.reservationId}">
						</td>
					</tr>
					<tr>
						<th>予約年月日</th>
						<td>
							${reservation.reservationDate} 
							<input type="hidden" name="reservation_date"value="${reservation.reservationDate}">
						</td>
					</tr>
					<tr>
						<th>会員ID</th>
						<td>
							${reservation.userId}
							<input type="hidden" name="user_id" value="${reservation.userId}">
						</td>
					</tr>
					<tr>
						<th>氏名</th>
						<td>
							${reservation.name} 
							<input type="hidden" name="name" value="${reservation.name}">
						</td>
					</tr>
					<tr>
						<th>電話番号</th>
						<td>
							${reservation.tel} 
							<input type="hidden" name="tel" value="${reservation.tel}">
						</td>
					</tr>
					<tr>
						<th>E-Mail</th>
						<td>
							${reservation.email}
						 	<input type="hidden" name="email" value="${reservation.email}">
						</td>
					</tr>
					<tr>
						<th>資料ID</th>
						<td>
							${reservation.bookId} 
							<input type="hidden" name="book_id"value="${reservation.bookId}">
						</td>
					</tr>
					<tr>
						<th>資料名</th>
						<td>
							${reservation.title}
						 	<input type="hidden" name="title" value="${reservation.title}">
						</td>
					</tr>
					<tr>
						<th>貸出有無</th>
						<td>
							<c:choose>
			    				<c:when test="${reservation.alreadyLent == 0}">
			    					<input type="radio" name="already_lent" value="0" checked>返却待ち
			    					<input type="radio" name="already_lent" value="1">資料確保
				    				<input type="radio" name="already_lent" value="2">対応終了
			    				</c:when>
			    				<c:when test="${reservation.alreadyLent == 1}">
			    					<input type="radio" name="already_lent" value="0">返却待ち
			    					<input type="radio" name="already_lent" value="1" checked>資料確保
			    					<input type="radio" name="already_lent" value="2">対応終了
			    				</c:when>
			    				<c:when test="${reservation.alreadyLent == 2}">
			    					<input type="radio" name="already_lent" value="0">返却待ち
				    				<input type="radio" name="already_lent" value="1">資料確保
			    					<input type="radio" name="already_lent" value="2" checked>対応終了
			    				</c:when>
			    			</c:choose>
						</td>
					</tr>
					<tr>
						<th>備考</th>
						<td>
							<textarea name="memo">${reservation.memo}</textarea>
						</td>
					</tr>
				</table>
				<button class="general_button edit_button">変更</button>
			</form>
		</div>

		<div>
			<form action="/team_dev_library_system/ReservationServlet" method="post">
				<button class="return_button">戻る</button>
				<input type="hidden" name="action" value="return_search">
			</form>
		</div>
	</div>

</body>

</html>