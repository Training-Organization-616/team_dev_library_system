<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
			<p>編集事項に入力してください</p>
		</div>

		<div class="message">${message}</div>

		<div class="input_table">
			<form action="/team_dev_library_system/ReservationServlet" method="post">
				<input type="hidden" name="action" value="update">
				<table>
					<tr>
						<th>予約ID</th>
						<td>${reservationId} 
						<input type="hidden" name="reservation_id" value="${reservationId}">
						</td>
					</tr>
					<tr>
						<th>予約年月日</th>
						<td>${reservation.reservationDate} 
						<input type="hidden" name="reservation_date"value="${reservation.reservationDate}">
						</td>
					</tr>
					<tr>
						<th>会員ID</th>
						<td>${reservation.userId} <input type="hidden" name="user_id"
							value="${reservation.userId}">
						</td>
					</tr>
					<tr>
						<th>氏名</th>
						<td>${reservation.userName} 
						<input type="hidden" name="user_name" value="${reservation.userName}">
						</td>
					</tr>
					<tr>
						<th>電話番号</th>
						<td>${reservation.userTel} 
						<input type="hidden" name="user_tel" value="${reservation.userTel}">
						</td>
					</tr>
					<tr>
						<th>E-Mail</th>
						<td>${reservation.userEmail}
						 <input type="hidden" name="user_email" value="${reservation.userEmail}">
						</td>
					</tr>
					<tr>
						<th>資料ID</th>
						<td>${reservation.bookId} 
						<input type="hidden" name="book_id"value="${reservation.bookId}">
						</td>
					</tr>
					<tr>
						<th>資料名</th>
						<td>${reservation.bookTitle}
						 	<input type="hidden" name="book_title" value="${reservation.bookTitle}">
						</td>
					</tr>
					<tr>
						<th>貸出有無</th>
						<td>
						<c:if test="${reserve.alreadyLent==0}">
							貸出中
						</c:if>
						<c:if test="${reserve.alreadyLent==1}">
							資料確保
						</c:if>
						<c:if test="${reserve.alreadyLent==2}">
							対応終了
						</c:if>
						<input type="hidden" name="already_lent" value="${reservation.alreadyLent}">
						</td>>
					</tr>
					<tr>
						<th>備考</th>
						<td>
							<input class="input_form" type="text" name="memo" value="${reservation.memo}">
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


	<jsp:include page="../top/footer.jsp" />

</body>

</html>