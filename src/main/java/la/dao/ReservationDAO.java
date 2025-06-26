package la.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import la.bean.ReservationBean;
import la.bean.SearchReservationBean;

public class ReservationDAO {

	// URL、ユーザ名、パスワードの準備
	private String url = "jdbc:postgresql:team_dev_library_system";
	private String user = "student";
	private String pass = "himitu";

	public ReservationDAO() throws DAOException {
		// JDBCドライバの登録
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの登録に失敗しました。");
		}
	}

	public boolean reservationStatus(int id) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM reservation WHERE user_id = ? AND already_lent != 2";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, id);
			boolean checkUser = true;
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
				// 結果の取得および表示
				while (rs.next()) {
					checkUser = false;
				}
				// stockを返す
				return checkUser;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	public ReservationBean addReservation(int uId, int bId, int aLent, int reservationAmount) throws DAOException {
		// reservationテーブルに予約情報を登録
		// SQL文の作成
		String sql = "INSERT INTO reservation VALUES(nextval('reservation_reservation_id_seq'), ?, CURRENT_DATE, ?, ?)";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			// プレースホルダーの設定
			st.setInt(1, uId);
			st.setInt(2, bId);
			st.setInt(3, aLent);
			// SQLの実行
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}

		// stockテーブルの予約有無を1に更新、予約人数を＋1する
		// SQL文の作成
		sql = "UPDATE stock SET reservation = 1, reservation_amount = ? WHERE book_id = ?";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			// プレースホルダーの設定
			st.setInt(1, reservationAmount);
			st.setInt(2, bId);
			// SQLの実行
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}

		// lend_idを返す
		// SQL文の作成
		sql = "SELECT * FROM reservation ORDER BY reservation_id DESC LIMIT 1";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);
				// SQLの実行
				ResultSet rs = st.executeQuery();) {
			// 結果の取得および表示
			ReservationBean bean = new ReservationBean();
			while (rs.next()) {
				int lendId = rs.getInt("reservation_id");
				int userId = rs.getInt("user_id");
				String reservationDate = rs.getString("reservation_date");
				int bookId = rs.getInt("book_id");
				int alreadyLent = rs.getInt("already_lent");
				String memo = rs.getString("memo");
				bean = new ReservationBean(lendId, userId, reservationDate, bookId, alreadyLent, memo);
			}
			// 予約情報を返す
			return bean;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	public List<SearchReservationBean> searchReservation(String paramReservationId, String paramBookId,
			String paramTitle) throws DAOException {
		// 予約テーブルを検索
		// SQL文の作成
		String sql = "SELECT r.reservation_id, r.reservation_date,"
				+ " u.user_id, u.name, u.tel, u.email, s.book_id, s.title,"
				+ " r.already_lent, r.memo"
				+ " FROM reservation r JOIN users u ON r.user_id = u.user_id"
				+ " JOIN stock s ON r.book_id = s.book_id"
				+ " WHERE r.already_lent != 2";

		// 検索時、空でない項目を追加する
		if (paramReservationId != null && paramReservationId.length() != 0) {
			sql += " AND r.reservation_id = ?";
		}

		if (paramBookId != null && paramBookId.length() != 0) {
			sql += " AND r.book_id = ?";
		}

		if (paramTitle != null && paramTitle.length() != 0) {
			sql += " AND s.title LIKE ?";
		}

		sql += " ORDER BY r.reservation_id";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {

			// プレースホルダの順序を保持するカウンタ変数
			int count = 0;

			if (paramReservationId != null && paramReservationId.length() != 0) {
				count++;
				int vindReservationId = Integer.parseInt(paramReservationId);
				st.setInt(count, vindReservationId);
			}

			if (paramBookId != null && paramBookId.length() != 0) {
				count++;
				int vindBookId = Integer.parseInt(paramBookId);
				st.setInt(count, vindBookId);
			}

			if (paramTitle != null && paramTitle.length() != 0) {
				count++;
				st.setString(count, "%" + paramTitle + "%");
			}

			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
				// 検索結果がない場合
				if (!rs.next()) {
					return null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}

			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
				// 結果の取得および表示
				List<SearchReservationBean> list = new ArrayList<SearchReservationBean>();

				// 検索結果がある場合
				while (rs.next()) {
					int reservationId = rs.getInt("reservation_id");
					String reservationDate = rs.getString("reservation_date");
					int userId = rs.getInt("user_id");
					String name = rs.getString("name");
					String tel = rs.getString("tel");
					String email = rs.getString("email");
					int bookId = rs.getInt("book_id");
					String title = rs.getString("title");
					int alreadyLent = rs.getInt("already_lent");
					String memo = rs.getString("memo");
					SearchReservationBean bean = new SearchReservationBean(reservationId, reservationDate, userId,
							name, tel, email, bookId, title, alreadyLent, memo);
					list.add(bean);
				}
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	public boolean getAlreadyLentByBookId(int bookId) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM reservation WHERE book_id = ? AND already_lent != 2";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, bookId);
			boolean checkAlreadyLent = true;
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
				// 結果の取得および表示
				while (rs.next()) {
					int alreadyLent = rs.getInt("already_lent");
					if (alreadyLent == 1) {
						checkAlreadyLent = false;
						return checkAlreadyLent;
					}
				}
				// 予約がすべて「1: 資料確保」出なかった場合、trueを返す
				return checkAlreadyLent;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	public void updateReservation(int reservationId, String reservationDate, int userId,
			String name, String tel, String email, int bookId, String title, int alreadyLent, String memo)
			throws DAOException {
		// 予約テーブルの更新
		// SQL文の作成
		String sql = "UPDATE reservation SET already_lent = ?, memo = ? WHERE reservation_id = ?";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			// プレースホルダーの設定
			st.setInt(1, alreadyLent);
			st.setString(2, memo);
			st.setInt(3, reservationId);
			// SQLの実行
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}

	public void updateStock(int bookId, int reservation, int reservationAmount) throws DAOException {
		// SQL文の作成
		String sql = "UPDATE stock SET reservation = ?, reservation_amount = ? WHERE book_id = ?";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			// プレースホルダーの設定
			st.setInt(1, reservation);
			st.setInt(2, reservationAmount);
			st.setInt(3, bookId);
			// SQLの実行
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}

}