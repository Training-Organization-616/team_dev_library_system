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
		<div class="menu_name">予約検索</div>
	</div>
	
	<div class="container">
		<div>
			<p>予約ID、資料ID、資料名を入力してください</p>
		</div>
		
		<div class="message">
			${message}
		</div>
		
		<div class="input_table">
			<form action="/team_dev_library_system/ReservationServlet" method="post">
				<input type="hidden" name="action" value="search">
				<table>
					<tr>
				    	<th>予約ID</th>
				    	<td>
				    		<input class="input_form" type="text" name="reservation_id" value="${reservationId}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>資料ID</th>
				    	<td>
				    		<input class="input_form" type="text" name="book_id" value="${bookId}">
				    	</td>
				    </tr>
				    <tr>
				    	<th>資料名</th>
				    	<td>
				    		<input class="input_form" type="text" name="title" value="${title}">
				    	</td>
				    </tr>
				</table>				
				<button class="general_button">検索</button>
			</form>
		</div>	
	</div>

	<c:if test="${!empty reservations}">
		<div class="search_result_table">
			 <table>
				<tr>
					<th>予約ID</th>
					<th>予約年月日</th>
				    <th>会員ID</th>
				    <th>氏名</th>
				    <th>電話番号</th>
				    <th>E-mail</th>
				    <th>資料ID</th>
				    <th>資料名</th>
				    <th>貸出有無</th>
				    <th>編集</th>
				</tr>
		
				<c:forEach items="${reservations}" var="reserve">
					<tr>
					    <td>${reserve.reservationId}</td>
					    <td>${reserve.reservationDate}</td>
					    <td>${reserve.userId}</td>
					    <td>${reserve.name}</td>
					    <td>${reserve.tel}</td>
					    <td>${reserve.email}</td>
					    <td>${reserve.bookId}</td>
					    <td>${reserve.title}</td>
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
						</td>
					    <td>
					    	<form action="/team_dev_library_system/ReservationServlet" method="post">
				    			<button class="edit_button">編集</button>
				    			<input type="hidden" name="action" value="edit_page">
				    			<input type="hidden" name="reservation_id" value="${reserve.reservationId}">
				    			<input type="hidden" name="reservation_date" value="${reserve.reservationDate}">
				    			<input type="hidden" name="userId" value="${reserve.userId}">
				    			<input type="hidden" name="name" value="${reserve.name}">
				    			<input type="hidden" name="tel" value="${reserve.tel}">
				    			<input type="hidden" name="email" value="${reserve.email}">
				    			<input type="hidden" name="book_id" value="${reserve.bookId}">
				    			<input type="hidden" name="title" value="${reserve.title}">
				    			<input type="hidden" name="already_lent" value="${reserve.alreadyLent}">
				    			<input type="hidden" name="memo" value="${reserve.memo}">
			    			</form>
					   	</td>
				    </tr>
				</c:forEach>
			</table>
		 </div>
	</c:if>

	<div>
		<form action="/team_dev_library_system/ReservationServlet" method="post">
			<button class="return_button">戻る</button>
			<input type="hidden" name="action" value="return_add">
		</form>
	</div>

</body>

</html>