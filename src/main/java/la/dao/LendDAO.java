package la.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import la.bean.LendBean;

public class LendDAO {

	// URL、ユーザ名、パスワードの準備
	private String url = "jdbc:postgresql:team_dev_library_system";
	private String user = "student";
	private String pass = "himitu";

	public LendDAO() throws DAOException {
		// JDBCドライバの登録
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの登録に失敗しました。");
		}
	}

	public boolean findUser(int id) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM users WHERE user_id = ?";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, id);
			boolean checkUser = false;
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
				// 結果の取得および表示
				while (rs.next()) {
					checkUser = true;
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

	public int getStock(int bookId) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM stock WHERE book_id = ?";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, bookId);
			int stock = -1;
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
				// 結果の取得および表示
				while (rs.next()) {
					stock = rs.getInt("stock");
				}
				// stockを返す
				return stock;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	public int lending(int id) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM lend WHERE user_id = ? AND return_date IS NULL";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, id);
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
				// 結果の取得および表示
				int count = 0;
				while (rs.next()) {
					count++;
				}
				return count;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	public int returnStatus(int id) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM lend WHERE user_id = ? AND return_date IS NULL AND CURRENT_DATE > due_date";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, id);
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
				// 結果の取得および表示
				int count = 0;
				while (rs.next()) {
					count++;
				}
				return count;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	public String getPublicationDate(int id) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM catalog WHERE stock_amount LIKE ?";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			st.setString(1, "%," + id + ",%");
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
				String publicationDate = null;
				// 結果の取得および表示
				while (rs.next()) {
					publicationDate = rs.getString("publication_date");
				}
				return publicationDate;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	public void lending(int userId, int bookId, LocalDate today, LocalDate dueDay) throws DAOException {
		// lendテーブルに貸出情報を登録
		// SQL文の作成
		String sql = "INSERT INTO lend VALUES(nextval('lend_lend_id_seq'), ?, ?, ?, ?)";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			// プレースホルダーの設定
			st.setInt(1, userId);
			st.setInt(2, bookId);
			st.setDate(3, Date.valueOf(today));
			st.setDate(4, Date.valueOf(dueDay));
			// SQLの実行
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}

		// stockテーブルの在庫を0に更新
		// SQL文の作成
		sql = "UPDATE stock SET stock = 0 WHERE book_id = ?";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			// プレースホルダーの設定
			st.setInt(1, bookId);
			// SQLの実行
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}

	}

	public LendBean getLending() throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM lend ORDER BY lend_id DESC LIMIT 1";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);
				// SQLの実行
				ResultSet rs = st.executeQuery();) {
			// 結果の取得および表示
			LendBean bean = new LendBean();
			while (rs.next()) {
				int lendId = rs.getInt("lend_id");
				int userId = rs.getInt("user_id");
				int bookId = rs.getInt("book_id");
				String lendDate = rs.getString("lend_date");
				String dueDate = rs.getString("due_date");
				String returnDate = rs.getString("return_date");
				String memo = rs.getString("memo");
				bean = new LendBean(lendId, userId, bookId, lendDate, dueDate, returnDate, memo);
			}
			// カテゴリ一覧をListとして返す
			return bean;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	public String getTitle(int bookId) throws DAOException {
		// SQL文の作成
		String sql = "SELECT * FROM stock WHERE book_id = ?";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, bookId);
			String title = null;
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
				// 結果の取得および表示
				while (rs.next()) {
					title = rs.getString("title");
				}
				// stockを返す
				return title;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

}
