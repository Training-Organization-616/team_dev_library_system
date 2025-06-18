package la.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		try {
			UserDAO dao = new UserDAO();
			//入力日をDate型に
			Date today = new Date(System.currentTimeMillis());
			System.out.println(today);

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
				UserBean list = dao.findOneUser(userId);
				request.setAttribute("user", list);
				gotoPage(request, response, "./user/user_search.jsp");
				return;
			} else if (action.equals("add")) {

				String userName = safeGetString(request.getParameter("user_name"));
				String address = safeGetString(request.getParameter("user_address"));
				String tel = safeGetString(request.getParameter("user_tel"));
				String email = safeGetString(request.getParameter("user_email"));
				String birthdayStr = request.getParameter("user_birthday");
				Date birthday = null;
				if (!birthdayStr.equals("")) {
					birthday = setDate(birthdayStr);
				}

				//バリデーション

				String message = "";
				if (userName == null || userName.length() == 0 || address == null || address.length() == 0
						|| tel == null || tel.length() == 0 || email == null || email.length() == 0
						|| birthday == null) {
					request.setAttribute("message", "すべて必須項目です<br>");

				}

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
				if (tel.length() > 20) {
					message += "電話番号は20桁以内で入力してください<br>";
				} else if (1 == 1) {

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
					List<UserBean> list = dao.addUser(userName, address, tel, email, birthday, today);
					request.setAttribute("user", list);
					gotoPage(request, response, "./user/user_add_complete.jsp");
					return;
				}

			} else if (action.equals("search")) {
				//パラメータ取得
				String userIdAttr = request.getParameter("user_id");
				String userStock = userIdAttr;
				int userId = safeGetInt(userIdAttr);
				String userName = safeGetString(request.getParameter("user_name"));
				String address = safeGetString(request.getParameter("user_address"));
				String tel = safeGetString(request.getParameter("user_tel"));

				String email = safeGetString(request.getParameter("user_email"));
				String birthdayStr = request.getParameter("user_birthday");
				Date birthday = null;
				if (!birthdayStr.equals("")) {
					birthday = setDate(birthdayStr);
				}
				System.out.println(birthday);
				//バリデーション

				String message = "";

				if (userId == -2) {
					message += "IDは数字で入力してください<br>";
				} else if (userId != -1) {
					//userId = Integer.parseInt(userStock);
					request.setAttribute("userId", userId);
				}

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
				if (tel.length() > 20) {
					message += "電話番号は20桁以内で入力してください<br>";
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
					gotoPage(request, response, "./user/user_search.jsp");
					return;
				}

				List<UserBean> list = dao.findUser(userId, userName, address, tel, email, birthday);
				//該当判定
				if (list.isEmpty()) {
					request.setAttribute("message", "該当する会員情報が見つかりませんでした。");
				} else {
					request.setAttribute("users", list);
				}
				gotoPage(request, response, "./user/user_search.jsp");
			} else if (action.equals("edit_page")) {
				//変更ボタンから変更ページに遷移
				String userIdAttr = request.getParameter("user_id");
				int userId = safeGetInt(userIdAttr);

				UserBean list = dao.findOneUser(userId);

				request.setAttribute("user", list);
				request.setAttribute("userId", userId);

				gotoPage(request, response, "./user/user_edit.jsp");
			} else if (action.equals("update")) {
				//更新を実行し変更完了画面を表示
				int userId = safeGetInt(request.getParameter("user_id"));
				String userName = safeGetString(request.getParameter("user_name"));
				String address = safeGetString(request.getParameter("user_address"));
				String tel = safeGetString(request.getParameter("user_tel"));
				String email = safeGetString(request.getParameter("user_email"));
				String birthdayStr = request.getParameter("user_birthday");
				Date birthday = null;
				if (!birthdayStr.equals("")) {
					birthday = setDate(birthdayStr);
				}

				dao.updateUser(userId, userName, address, tel, email, birthday, today);
				UserBean list = dao.findOneUser(userId);
				request.setAttribute("user", list);
				gotoPage(request, response, "./user/user_edit_complete.jsp");
			} else if (action.equals("delete_page")) {
				//削除確認画面
				String userIdAttr = request.getParameter("user_id");
				int userId = safeGetInt(userIdAttr);
				UserBean list = dao.findOneUser(userId);
				request.setAttribute("user", list);
				gotoPage(request, response, "./user/user_delete.jsp");

			} else if (action.equals("delete")) {
				//削除
				//退会

				String userIdAttr = request.getParameter("user_id");
				int userId = safeGetInt(userIdAttr);

				if (dao.isUserLend(userId)) {
					request.setAttribute("message", "この会員は借りている本が存在するので削除することが出来ません");
					gotoPage(request, response, "./user/user_search.jsp");
					return;
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
			return -1; // 未入力扱い
		}
		try {
			String str = value.toString().trim();
			if (str.isEmpty()) {
				return -1; // 未入力扱い
			}
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return -2; // 数字以外が入力された場合
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

	//日付をdate型に
	public Date setDate(String strDate) {
		if (!strDate.equals(null)) {
			Date date = Date.valueOf(strDate);
			return date;
		} else {
			return null;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {//java.text.ParseException: Unparseable date: ""というエラー
		doGet(request, response);
	}

}
