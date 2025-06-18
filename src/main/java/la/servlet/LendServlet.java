package la.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import la.bean.LendBean;
import la.dao.DAOException;
import la.dao.LendDAO;

@WebServlet("/LendServlet")
public class LendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			request.setCharacterEncoding("UTF-8");

			// actionパラメータの取得
			String action = request.getParameter("action");

			// actionパラメータが空またはtopの場合はtop画面に遷移
			if (action == null || action.length() == 0 || action.equals("top")) {
				gotoPage(request, response, "../top/top.jsp");
				return;
			}

			// actionがlendのときは貸出処理を行ない、完了画面に遷移
			else if (action.equals("lend")) {

				// パラメータを取得
				String paramUserId = request.getParameter("user_id");
				String paramBookId = request.getParameter("book_id");

				// バリデーションチェック①
				if (paramUserId == null || paramUserId.length() == 0 || paramBookId == null
						|| paramBookId.length() == 0) {
					request.setAttribute("message", "すべて必須項目です");
					request.setAttribute("userId", paramUserId);
					request.setAttribute("bookId", paramBookId);
					gotoPage(request, response, "/lend/lend_top.jsp");
					return;
				}

				// バリデーションチェック②
				Pattern pattern = Pattern.compile("[0-9]+");
				Matcher userIdMatcher = pattern.matcher(paramUserId);
				if (userIdMatcher.matches() == false) {
					request.setAttribute("message", "数字で入力してください");
					request.setAttribute("userId", paramUserId);
					request.setAttribute("bookId", paramBookId);
					gotoPage(request, response, "/lend/lend_top.jsp");
					return;
				}
				Matcher bookIdMatcher = pattern.matcher(paramBookId);
				if (bookIdMatcher.matches() == false) {
					request.setAttribute("message", "数字で入力してください");
					request.setAttribute("userId", paramUserId);
					request.setAttribute("bookId", paramBookId);
					gotoPage(request, response, "/lend/lend_top.jsp");
					return;
				}

				// バリデーションチェックが問題なければ、パラメータをInt型に変更
				int userId = Integer.parseInt(paramUserId);
				int bookId = Integer.parseInt(paramBookId);

				// 会員が存在するか確認
				LendDAO dao = new LendDAO();
				boolean checkUser = dao.findUser(userId);

				if (!checkUser) {
					request.setAttribute("message", "この会員IDは登録されていません");
					request.setAttribute("userId", paramUserId);
					request.setAttribute("bookId", paramBookId);
					gotoPage(request, response, "/lend/lend_top.jsp");
					return;
				}

				// 資料の状況を確認（貸出中、廃棄中のものは貸出不可）
				int stock = dao.getStock(bookId);

				if (stock == 0) {
					request.setAttribute("message", "この資料は貸出中です");
					request.setAttribute("userId", paramUserId);
					request.setAttribute("bookId", paramBookId);
					gotoPage(request, response, "/lend/lend_top.jsp");
					return;
				} else if (stock == 2) {
					request.setAttribute("message", "この資料は廃棄処理済です");
					request.setAttribute("userId", paramUserId);
					request.setAttribute("bookId", paramBookId);
					gotoPage(request, response, "/lend/lend_top.jsp");
					return;
				} else if (stock != 1) {
					request.setAttribute("message", "この資料IDは登録されていません");
					request.setAttribute("userId", paramUserId);
					request.setAttribute("bookId", paramBookId);
					gotoPage(request, response, "/lend/lend_top.jsp");
					return;
				}

				// 会員の貸出状況を確認（5冊貸出中の場合は貸出不可）
				int lending = dao.lending(userId);

				if (lending >= 5) {
					request.setAttribute("message", "貸出上限数に達しています");
					request.setAttribute("userId", paramUserId);
					request.setAttribute("bookId", paramBookId);
					gotoPage(request, response, "/lend/lend_top.jsp");
					return;
				}

				// 会員の返却状況を確認（返却期限が過ぎている貸出がある場合は貸出不可）
				int passDueDateBook = dao.returnStatus(userId);

				if (passDueDateBook >= 1) {
					String message = "貸出期限を超過した貸出が" + passDueDateBook + "件あります";
					request.setAttribute("message", message);
					request.setAttribute("userId", paramUserId);
					request.setAttribute("bookId", paramBookId);
					gotoPage(request, response, "/lend/lend_top.jsp");
					return;
				}

				// ここから貸出処理

				// 今日の日付を取得
				LocalDate today = LocalDate.now();

				// 返却日を計算する
				// 出版日を取得し、LocalDate型に変換
				String strPublicationDate = dao.getPublicationDate(bookId);

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate publicationDate = LocalDate.parse(strPublicationDate, formatter);

				// 出版日の3か月後を取得する
				// 貸出日（today）が出版3か月以内であれば10日後、3か月以降であれば15日後を貸出期限とする
				LocalDate threeMonthLaterPublicationDate = publicationDate.plusMonths(3);

				int due = 0;
				if (today.isBefore(threeMonthLaterPublicationDate)) {
					due = 10;
				} else {
					due = 15;
				}

				LocalDate dueDay = today.plusDays(due);

				// 貸出情報を登録
				dao.lending(userId, bookId, today, dueDay);

				// 登録した貸出情報を取得
				LendBean bean = dao.getLending();

				int lendId = bean.getLendId();

				String dueDate = bean.getDueDate();
				dueDate = dueDate.replaceFirst("-", "年");
				dueDate = dueDate.replaceFirst("-", "月");
				dueDate = dueDate + "日";

				// 資料名を取得
				String title = dao.getTitle(bookId);

				request.setAttribute("lendId", lendId);
				request.setAttribute("userId", userId);
				request.setAttribute("bookId", bookId);
				request.setAttribute("title", title);
				request.setAttribute("dueDate", dueDate);

				// 完了画面にフォワード
				gotoPage(request, response, "/lend/lend_complete.jsp");
				return;

			}

		} catch (DAOException e) {
			e.printStackTrace();
			request.setAttribute("message", "内部エラーが発生しました。");
			gotoPage(request, response, "/errInternal.jsp");
			return;
		}

	}

	private void gotoPage(HttpServletRequest request, HttpServletResponse response, String page)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
