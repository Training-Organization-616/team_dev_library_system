package la.servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import la.bean.LendBean;
import la.bean.StockBean;
import la.dao.DAOException;
import la.dao.ReturnDAO;

@WebServlet("/ReturnServlet")
public class ReturnServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			request.setCharacterEncoding("UTF-8");
			//文字化け防止
			String action = request.getParameter("action");
			ReturnDAO dao = new ReturnDAO();

			if (action == null || action.length() == 0 || action.equals("top")) {
				// topまたはパラメータなしの場合 
				gotoPage(request, response, "/top/top.jsp");

			} else if (action.equals("return")) {
				//action＝returnのとき
				//返却作業を行い、返却完了画面を表示する

				try {

					// パラメータを取得
					String paramLendId = request.getParameter("lend_id");
					String paramUserId = request.getParameter("user_id");
					String paramBookId = request.getParameter("book_id");

					// バリデーションチェック①
					if (paramLendId == null || paramLendId.length() == 0 || paramUserId == null
							|| paramUserId.length() == 0 || paramBookId == null || paramBookId.length() == 0) {
						request.setAttribute("message", "すべて必須項目です");
						request.setAttribute("lendId", paramLendId);
						request.setAttribute("userId", paramUserId);
						request.setAttribute("bookId", paramBookId);
						gotoPage(request, response, "/lend/return_add.jsp");
						return;
					}

					// バリデーションチェック②
					Pattern pattern = Pattern.compile("[0-9]+");
					Matcher lendIdMatcher = pattern.matcher(paramLendId);
					if (lendIdMatcher.matches() == false) {
						request.setAttribute("message", "数字で入力してください");
						request.setAttribute("lendId", paramLendId);
						request.setAttribute("userId", paramUserId);
						request.setAttribute("bookId", paramBookId);
						gotoPage(request, response, "/lend/return_add.jsp");
						return;
					}

					Matcher userIdMatcher = pattern.matcher(paramUserId);
					if (userIdMatcher.matches() == false) {
						request.setAttribute("message", "数字で入力してください");
						request.setAttribute("lendId", paramLendId);
						request.setAttribute("userId", paramUserId);
						request.setAttribute("bookId", paramBookId);
						gotoPage(request, response, "/lend/return_add.jsp");
						return;
					}
					Matcher bookIdMatcher = pattern.matcher(paramBookId);
					if (bookIdMatcher.matches() == false) {
						request.setAttribute("message", "数字で入力してください");
						request.setAttribute("lendId", paramLendId);
						request.setAttribute("userId", paramUserId);
						request.setAttribute("bookId", paramBookId);
						gotoPage(request, response, "/lend/return_add.jsp");
						return;
					}

					// バリデーションチェックが問題なければ、パラメータをint型に変更
					int lendId = Integer.parseInt(paramLendId);
					int userId = Integer.parseInt(paramUserId);
					int bookId = Integer.parseInt(paramBookId);

					// 入力された貸出情報が登録されているか確認
					try {

						LendBean bean = dao.findByUserIdAndBookId(lendId);
						int checkUserId = bean.getUserId();
						int checkBookId = bean.getBookId();
						if (userId != checkUserId || bookId != checkBookId) {
							request.setAttribute("message", "貸出情報が一致しません");
							request.setAttribute("lendId", lendId);
							request.setAttribute("userId", userId);
							request.setAttribute("bookId", bookId);
							gotoPage(request, response, "/lend/return_add.jsp");
							return;
						}

					} catch (DAOException e) {
						//DAOのDB処理が失敗(エラー)した場合

						e.printStackTrace();
						request.setAttribute("message", "内部エラーが発生しました。");
						gotoPage(request, response, "/errInternal.jsp");
					}

					// 返却処理後、完了画面へ遷移
					dao.returnCatalog(lendId, userId, bookId);

					request.setAttribute("lendId", lendId);
					request.setAttribute("userId", userId);
					request.setAttribute("bookId", bookId);

					try {

						// 資料情報を取得
						StockBean bean = dao.findByBookId(bookId);

						// 資料名を取得
						String title = bean.getTitle();
						request.setAttribute("title", title);

						// 予約件数を取得
						int reservationAmount = bean.getReservationAmount();
						request.setAttribute("reservationAmount", reservationAmount);

						gotoPage(request, response, "/lend/return_complete.jsp");

					} catch (Exception e) {
						//DB処理が失敗(エラー)した場合

						e.printStackTrace();
						request.setAttribute("message", "内部エラーが発生しました");
						gotoPage(request, response, "/errInternal.jsp");
					}

				} catch (Exception e) {
					//DB処理が失敗(エラー)した場合

					e.printStackTrace();
					request.setAttribute("message", "内部エラーが発生しました");
					gotoPage(request, response, "/errInternal.jsp");
				}

			}

		} catch (DAOException e) {
			//DAOのDB処理が失敗(エラー)した場合

			e.printStackTrace();
			request.setAttribute("message", "内部エラーが発生しました。");
			gotoPage(request, response, "/errInternal.jsp");
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
