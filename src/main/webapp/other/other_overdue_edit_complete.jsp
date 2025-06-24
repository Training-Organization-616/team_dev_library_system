<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>図書管理システム</title>
	<link href="/team_dev_library_system/style.css" rel="stylesheet">
	<meta http-equiv="refresh" content="5;url=/team_dev_library_system/other/other_overdue_top.jsp">
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
		<div class="menu_name">延滞者編集完了</div>
	</div>

	<div class="container">
		<div>
			<p>編集完了しました</p>
		</div>

		<div>
			<ul>
				<li>会員ID：${overdue.userId}</li>
				<li>氏名：${overdue.name}</li>
				<li>E-Mail：${overdue.email}</li>
				<li>資料ID：${overdue.birthday}</li>
				<li>返却期日：${overdue.admissionDate}</li>
				<c:if test="${empty overdue.demandLetter}">
				<li>一次対応:
					<c:if test="${overdue.telReminder==0}">
						未対応
					</c:if>
					<c:if test="${overdue.telReminder==1}">
						対応中
					</c:if>
					<c:if test="${overdue.telReminder==2}">
						対応済み
					</c:if>
				</li>
				</c:if>
				<c:if test="${!empty overdue.demandLetter}">
				<li>二次対応:
					<c:if test="${overdue.demandLetter==0}">
						未対応
					</c:if>
					<c:if test="${overdue.demandLetter==1}">
						対応中
					</c:if>
					<c:if test="${overdue.demandLetter==2}">
						対応済み
					</c:if>
				</li>
				</c:if>
				<li>備考:${overdue.memo}</li>
			</ul>
		</div>

		<div class="transfar_link">
			<form action="/team_dev_library_system/other/other_overdue_top.jsp" method="post">
				<button>自動で遷移しない場合はこちらをクリック</button>
				<input type="hidden" name="action" value="">
			</form>
		</div>

		<jsp:include page="../top/footer.jsp" />
</body>

</html>