package la.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import la.bean.ReservationBean;
import la.bean.SearchReservationBean;
import la.bean.StockBean;
import la.dao.DAOException;
import la.dao.LendDAO;
import la.dao.ReservationDAO;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
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

			// actionがreturn_addの場合、貸出予約管理画面に戻る
			else if (action.equals("return_add")) {
				// 検索のsessionを破棄
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.invalidate();
				}

				// 貸出予約画面・貸出予約検索画面から、貸出メニューに戻る
				gotoPage(request, response, "/lend/lend_top.jsp");
			}

			// actionがreturn_searchの場合、貸出予約検索画面に戻る
			else if (action.equals("return_search")) {
				// 貸出予約編集画面から、貸出予約検索画面に戻る
				gotoPage(request, response, "/lend/reservation_search.jsp");
			}

			// actionがaddの場合、新規予約登録処理を行ない、完了画面に遷移
			else if (action.equals("add")) {

				// パラメータを取得
				String paramUserId = request.getParameter("user_id");
				String paramBookId = request.getParameter("book_id");

				// バリデーションチェック①
				if (paramUserId == null || paramUserId.length() == 0 || paramBookId == null
						|| paramBookId.length() == 0) {
					request.setAttribute("message", "すべて必須項目です");
					request.setAttribute("userId", paramUserId);
					request.setAttribute("bookId", paramBookId);
					gotoPage(request, response, "/lend/reservation_add.jsp");
					return;
				}

				// バリデーションチェック②
				Pattern pattern = Pattern.compile("[0-9]+");
				Matcher userIdMatcher = pattern.matcher(paramUserId);
				if (userIdMatcher.matches() == false) {
					request.setAttribute("message", "数字で入力してください");
					request.setAttribute("bookId", paramBookId);
					gotoPage(request, response, "/lend/reservation_add.jsp");
					return;
				}
				Matcher bookIdMatcher = pattern.matcher(paramBookId);
				if (bookIdMatcher.matches() == false) {
					request.setAttribute("message", "数字で入力してください");
					request.setAttribute("userId", paramUserId);
					gotoPage(request, response, "/lend/reservation_add.jsp");
					return;
				}

				// バリデーションチェックが問題なければ、パラメータをint型に変更
				int userId = Integer.parseInt(paramUserId);
				int bookId = Integer.parseInt(paramBookId);

				// 会員が存在するか確認
				LendDAO lendDao = new LendDAO();
				boolean checkUser = lendDao.checkUser(userId);

				if (!checkUser) {
					request.setAttribute("message", "この会員IDは登録されていません");
					request.setAttribute("bookId", paramBookId);
					gotoPage(request, response, "/lend/reservation_add.jsp");
					return;
				}

				// 会員の返却状況を確認（返却期限が過ぎている貸出がある場合は貸出不可）
				int passDueDateBook = lendDao.returnStatus(userId);

				if (passDueDateBook >= 1) {
					String message = "貸出期限を超過した貸出が" + passDueDateBook + "件あるため、予約できません";
					request.setAttribute("message", message);
					gotoPage(request, response, "/lend/reservation_add.jsp");
					return;
				}

				// 会員の予約状況を確認（既に予約している会員は追加予約不可）
				ReservationDAO dao = new ReservationDAO();
				boolean checkUserReservation = dao.reservationStatus(userId);

				if (checkUserReservation != true) {
					String message = "この会員は既に貸出予約しています";
					request.setAttribute("message", message);
					gotoPage(request, response, "/lend/reservation_add.jsp");
					return;
				}

				// 在庫テーブルから資料の情報を取得
				StockBean stockBean = lendDao.getStock(bookId);

				// 資料が存在するか確認
				if (stockBean == null) {
					request.setAttribute("message", "この資料IDは登録されていません");
					request.setAttribute("userId", paramUserId);
					gotoPage(request, response, "/lend/reservation_add.jsp");
					return;
				}

				// 資料の状況を確認（廃棄中のものは予約不可）
				int stock = stockBean.getStock(); // 0なら貸出中、1なら在庫有り、2なら廃棄済

				if (stock == 2) {
					request.setAttribute("message", "この資料は廃棄処理済です");
					request.setAttribute("userId", paramUserId);
					gotoPage(request, response, "/lend/reservation_add.jsp");
					return;
				}

				// ここから予約処理

				// 予約テーブルに登録する貸出状況を設定
				int alreadyLent = 0; // 0なら返却待ち、1なら資料確保、2なら対応終了

				// 資料が在庫有りで、予約もない場合、（資料確保）に変更。
				// 在庫無し（貸出中）の場合や、在庫はあるが既に予約もある場合は、在庫0（返却待ち）のまま

				int reservation = stockBean.getReservation();

				if (stock == 1 && reservation == 0) {
					alreadyLent = 1;
				}

				// 在庫テーブルに登録する予約人数を更新
				int reservationAmount = stockBean.getReservationAmount() + 1;

				// 予約処理
				ReservationBean bean = dao.addReservation(userId, bookId, alreadyLent, reservationAmount);

				// 完了画面に表示するデータの取得と設定
				int reservationId = bean.getReservationId();
				int reservationUserId = bean.getUserId();
				int reservationBookId = bean.getBookId();
				String title = stockBean.getTitle();

				request.setAttribute("reservationId", reservationId);
				request.setAttribute("userId", reservationUserId);
				request.setAttribute("bookId", reservationBookId);
				request.setAttribute("title", title);
				request.setAttribute("reservationAmount", reservationAmount);

				// 検索のsessionがある場合、破棄して完了画面に遷移
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.invalidate();
				}

				gotoPage(request, response, "/lend/reservation_add_complete.jsp");
				return;

			}

			// actionがsearchの場合、貸出予約検索画面を表示
			else if (action.equals("search")) {

				// セッションの取得
				HttpSession session = request.getSession(false);

				// パラメータの取得
				String paramReservationId = request.getParameter("reservation_id");
				String paramBookId = request.getParameter("book_id");
				String paramTitle = request.getParameter("title");

				// バリデーションチェック
				Pattern pattern = Pattern.compile("[0-9]+");
				if (paramReservationId != null && paramReservationId.length() != 0) {
					Matcher reservationIdMatcher = pattern.matcher(paramReservationId);
					if (reservationIdMatcher.matches() == false) {
						request.setAttribute("message", "数字で入力してください");
						request.setAttribute("bookId", paramBookId);
						request.setAttribute("title", paramTitle);
						gotoPage(request, response, "/lend/reservation_search.jsp");
						return;
					}
				}

				if (paramBookId != null && paramBookId.length() != 0) {
					Matcher bookIdMatcher = pattern.matcher(paramBookId);
					if (bookIdMatcher.matches() == false) {
						request.setAttribute("message", "数字で入力してください");
						request.setAttribute("reservationId", paramReservationId);
						request.setAttribute("title", paramTitle);
						gotoPage(request, response, "/lend/reservation_search.jsp");
						return;
					}
				}

				if (paramTitle.length() > 50) {
					request.setAttribute("message", "資料名は50文字以内で入力してください");
					request.setAttribute("reservationId", paramReservationId);
					request.setAttribute("bookId", paramBookId);
					gotoPage(request, response, "/lend/reservation_search.jsp");
					return;
				}

				// ここから検索処理
				ReservationDAO dao = new ReservationDAO();
				List<SearchReservationBean> list = new ArrayList<SearchReservationBean>();

				list = dao.searchReservation(paramReservationId, paramBookId, paramTitle);

				// セッションスコープにパラメータと検索値、検索結果を入れて、検索画面を表示
				if (list == null) {
					request.setAttribute("message", "検索結果がありません");
					session.setAttribute("reservationId", paramReservationId);
					session.setAttribute("bookId", paramBookId);
					session.setAttribute("title", paramTitle);
					session.setAttribute("reservations", null);
				} else {
					session.setAttribute("reservationId", paramReservationId);
					session.setAttribute("bookId", paramBookId);
					session.setAttribute("title", paramTitle);
					session.setAttribute("reservations", list);
				}

				gotoPage(request, response, "/lend/reservation_search.jsp");

			}

			// actionがedit_pageの場合、貸出予約編集画面に遷移
			else if (action.equals("edit_page")) {

				// パラメータの取得
				int reservationId = Integer.parseInt(request.getParameter("reservation_id"));

				String reservationDate = request.getParameter("reservation_date");
				reservationDate = reservationDate.replaceFirst("-", "年");
				reservationDate = reservationDate.replaceFirst("-", "月");
				reservationDate = reservationDate + "日";

				int userId = Integer.parseInt(request.getParameter("userId"));
				String name = request.getParameter("name");
				String tel = request.getParameter("tel");
				String email = request.getParameter("email");
				int bookId = Integer.parseInt(request.getParameter("book_id"));
				String title = request.getParameter("title");
				int alreadyLent = Integer.parseInt(request.getParameter("already_lent"));
				String memo = request.getParameter("memo");

				SearchReservationBean bean = new SearchReservationBean(reservationId, reservationDate, userId,
						name, tel, email, bookId, title, alreadyLent, memo);

				// セッションの取得
				HttpSession session = request.getSession(false);

				// セッション変数に予約情報を格納し、予約編集画面に遷移
				session.setAttribute("reservation", bean);
				gotoPage(request, response, "/lend/reservation_edit.jsp");

			}

			// actionがeditの場合、貸出予約編集処理を行ない、完了画面を表示後、貸出予約検索画面へ遷移
			else if (action.equals("update")) {

				// パラメータの取得
				int reservationId = Integer.parseInt(request.getParameter("reservation_id"));
				String reservationDate = request.getParameter("reservation_date");
				int userId = Integer.parseInt(request.getParameter("user_id"));
				String name = request.getParameter("name");
				String tel = request.getParameter("tel");
				String email = request.getParameter("email");
				int bookId = Integer.parseInt(request.getParameter("book_id"));
				String title = request.getParameter("title");
				int alreadyLent = Integer.parseInt(request.getParameter("already_lent"));
				String memo = request.getParameter("memo");

				// 貸出有無（already_lent）が「0: 返却待ち」から「1: 資料確保」に変更される場合
				// すでにその資料が「1: 資料確保」されていたら編集不可
				if (alreadyLent == 1) {
					ReservationDAO dao = new ReservationDAO();
					boolean checkAlreadyLent = dao.getAlreadyLentByBookId(bookId);
					if (checkAlreadyLent == false) {
						request.setAttribute("message", "この資料は他の予約で資料確保されています");
						gotoPage(request, response, "/lend/reservation_edit.jsp");
					}
				}

				// その資料が返却されていなかったら資料確保不可
				if (alreadyLent == 1) {
					LendDAO lendDao = new LendDAO();
					StockBean stockBean = lendDao.getStock(bookId);
					int stock = stockBean.getStock();
					if (stock == 0) {
						request.setAttribute("message", "この資料は貸出中です");
						gotoPage(request, response, "/lend/reservation_edit.jsp");
					}
				}

				// ここから編集処理
				ReservationDAO dao = new ReservationDAO();
				dao.updateReservation(reservationId, reservationDate, userId,
						name, tel, email, bookId, title, alreadyLent, memo);

				// 貸出有無（already_lent）が「2: 対応終了」に変更された場合
				// 在庫テーブルの予約人数、予約有無も更新する
				if (alreadyLent == 2) {
					LendDAO lendDao = new LendDAO();
					StockBean stockBean = lendDao.getStock(bookId);

					// 予約人数の取得、1人減らす
					int reservationAmount = stockBean.getReservationAmount();
					reservationAmount = reservationAmount - 1;

					// 予約有無の取得、もし予約人数が0なら「0: 予約なし」にする
					int reservation = stockBean.getReservation();
					if (reservationAmount == 0) {
						reservation = 0;
					}

					dao.updateStock(bookId, reservation, reservationAmount);
				}

				// 編集完了画面に遷移
				SearchReservationBean bean = new SearchReservationBean(reservationId, reservationDate, userId,
						name, tel, email, bookId, title, alreadyLent, memo);

				// セッションの破棄
				HttpSession session = request.getSession(false);
				session.invalidate();

				// リクエスト変数に予約情報を格納し、予約編集画面に遷移
				request.setAttribute("reservation", bean);
				gotoPage(request, response, "/lend/reservation_edit_complete.jsp");

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
