package la.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/TransitionServlet")
public class TransitionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// actionパラメータの取得
		String action = request.getParameter("action");

		// session情報を破棄
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// actionパラメータが空またはtopの場合はtop画面に遷移
		if (action == null || action.length() == 0 || action.equals("top")) {
			gotoPage(request, response, "/top/top.jsp");
			return;
		}

		// actionがlendのときは貸出管理画面に遷移
		else if (action.equals("lend")) {
			gotoPage(request, response, "/team_dev_library_system/lend/lend_top.jsp");
			return;
		}

		// actionがcatalogのときは資料管理画面に遷移
		else if (action.equals("catalog")) {
			gotoPage(request, response, "/team_dev_library_system/catalog/catalog_top.jsp");
			return;
		}

		// actionがuserのときは会員管理画面に遷移
		else if (action.equals("user")) {
			gotoPage(request, response, "/team_dev_library_system/user/user_top.jsp");
			return;
		}

		// actionがotherのときはその他画面に遷移
		else if (action.equals("other")) {
			gotoPage(request, response, "/team_dev_library_system/other/other_top.jsp");
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
