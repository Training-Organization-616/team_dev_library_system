package la.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import la.bean.UserBean;
import la.dao.DAOException;
import la.dao.UserDAO;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			UserDAO dao = new UserDAO();
			//入力日をDate型に
			Date today = new Date(System.currentTimeMillis());

			//actionのリクエストパラメータを取得
			String action = request.getParameter("action");
			if (action == null || action.length() == 0 || action.equals("top")) {
				//トップページへ
				gotoPage(request, response, "./top/top.jsp");
				return;
			} else if (action.equals("return_add")) {
				//新規入力画面から会員メニューに戻る
				request.setAttribute("action", "");//何かデータの受け渡し必要？
				gotoPage(request, response, "./user/user_top.jsp");
				return;
			} else if (action.equals("return_search")) {

				int userId = (int) request.getAttribute("user_id");
				List<UserBean> list = dao.findUserId(userId);
				request.setAttribute("user", list);
				gotoPage(request, response, "./user/user_search.jsp");
				return;
			} else if (action.equals("add")) {

				String userName = safeGetString(request.getAttribute("name"));
				String address = safeGetString(request.getAttribute("address"));
				int tel = safeGetInt(request.getAttribute("tel"));
				String email = safeGetString(request.getAttribute("email"));
				Date birthday = (Date) request.getAttribute("birthday");

				//バリデーション
				if (userName == null || userName.length() == 0 || address == null || address.length() == 0
						|| tel == -1 || email == null || email.length() == 0
						|| birthday == null) {
					request.setAttribute("message", "すべて必須項目です");
					gotoPage(request, response, "./user/user_add.jsp");
					return;
				}
				String message = "";
				if (userName.length() > 50) {
					message += "氏名は50文字以内で入力してください<br>";
				} else {
					request.setAttribute("userName", userName);
				}
				if (address.length() > 100) {
					message += "住所は100文字以内で入力してください<br>";
				} else {
					request.setAttribute("address", address);
				}
				if (tel == -2) {
					message += "電話番号は数字で入力してください<br>";
				} else {
					request.setAttribute("tel", tel);
				}
				if (email.length() > 100) {
					message += "メールアドレスは100文字以内で入力してください<br>";
				} else {
					request.setAttribute("email", email);
				}

				if (!message.isEmpty()) {
					request.setAttribute("message", message);
					gotoPage(request, response, "./user/user_add.jsp");
					return;
				}

				//メールチェック
				if (dao.isUserRegistered(email)) {
					request.setAttribute("message", "既に登録されています");
					gotoPage(request, response, "./user/user_add.jsp");
					return;
				} else {
					dao.addUser(userName, address, tel, email, birthday, today);//java.text.ParseException: Unparseable date: ""というエラー
					gotoPage(request, response, "./user/user_add_complete.jsp");
					return;
				}

			} else if (action.equals("search")) {
				//パラメータ取得
				Object userIdAttr = request.getAttribute("userId");
				int userId = (userIdAttr != null) ? (int) userIdAttr : 0;
				String userName = Optional.ofNullable((String) request.getAttribute("user_name")).orElse(null);
				String address = Optional.ofNullable((String) request.getAttribute("address")).orElse(null);
				Object telAttr = request.getAttribute("tel");

				int tel = safeGetInt(telAttr);
				String email = Optional.ofNullable((String) request.getAttribute("email")).orElse(null);
				Date birthday = Optional.ofNullable((Date) request.getAttribute("birthday")).orElse(null);

				List<UserBean> list = dao.findUser(userId, userName, address, tel, email, birthday);
				//該当判定
				if (list.isEmpty()) {
					request.setAttribute("message", "該当する会員情報が見つかりませんでした。");
				} else {
					request.setAttribute("users", list);
				}
				gotoPage(request, response, "./user/user_search.jsp");
			} else if (action.equals("editpage")) {
				//変更ボタンから変更ページに遷移
				int userId = (int) request.getAttribute("userId");
				List<UserBean> list = dao.findUserId(userId);
				request.setAttribute("user", list);
				request.setAttribute("userId", userId);
				gotoPage(request, response, "./user/user_edit.jsp");
			} else if (action.equals("update")) {
				//更新を実行し変更完了画面を表示
				int userId = safeGetInt(request.getAttribute("userId"));
				String userName = safeGetString(request.getAttribute("userName"));
				String address = safeGetString(request.getAttribute("address"));
				int tel = safeGetInt(request.getAttribute("tel"));
				String email = safeGetString(request.getAttribute("email"));
				Date birthday = (Date) request.getAttribute("birthday");
				dao.updateUser(userId, userName, address, tel, email, birthday, today);
				gotoPage(request, response, "./user/user_edit_complete.jsp");
			} else if (action.equals("deletepage")) {
				//削除確認画面
				request.setAttribute("userId", safeGetInt(request.getAttribute("userId")));
				request.setAttribute("userName", safeGetString(request.getAttribute("userName")));
				request.setAttribute("address", safeGetString(request.getAttribute("address")));
				request.setAttribute("tel", safeGetInt(request.getAttribute("tel")));
				request.setAttribute("email", safeGetString(request.getAttribute("email")));
				request.setAttribute("birthday", safeGetString(request.getAttribute("birthday")));
				request.setAttribute("admissionDate", safeGetString(request.getAttribute("admissionDate")));
				gotoPage(request, response, "./user/user_delete.jsp");
			} else if (action.equals("delete")) {
				//削除
				//退会

				int userId = safeGetInt(request.getAttribute("userId"));
				String userName = safeGetString(request.getAttribute("userName"));
				String address = safeGetString(request.getAttribute("address"));
				int tel = safeGetInt(request.getAttribute("tel"));
				String email = safeGetString(request.getAttribute("email"));
				Date birthday = (Date) request.getAttribute("birthday");
				if (dao.isUserLend(userId)) {
					request.setAttribute("message", "この会員は借りている本が存在するので削除することが出来ません");
					gotoPage(request, response, "./user/user_search.jsp");
				}
				dao.deleteUser(userId, today);
				gotoPage(request, response, "./user/user_delete_complete.jsp");
			}
		} catch (SQLException | ServletException | IOException | DAOException | ParseException e) {
			e.printStackTrace();
			request.setAttribute("message", "内部エラーが発生しました。");
			gotoPage(request, response, "./errInternal.jsp");
		}

	}

	private void gotoPage(HttpServletRequest request,
			HttpServletResponse response, String page) throws ServletException,
			IOException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	//数値系の入力がない場合-1を代入
	private int safeGetInt(Object value) {

		if (value == null) {
			return -1;
		}
		if (value instanceof Integer) {
			return (Integer) value;
		} else {
			return -2;
		}
	}

	//入力がない際に空文字を代入
	private String safeGetString(Object value) {
		if (value != null) {
			return value.toString();
		} else {
			return "";
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {//java.text.ParseException: Unparseable date: ""というエラー
		doGet(request, response);
	}

}
