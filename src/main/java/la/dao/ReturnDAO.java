package la.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import la.bean.LendBean;
import la.bean.StockBean;

public class ReturnDAO {

	// URL、ユーザ名、パスワードの準備
	private String url = "jdbc:postgresql:team_dev_library_system";
	private String user = "student";
	private String pass = "himitu";

	public ReturnDAO() throws DAOException {
		try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの登録に失敗しました。");
		}
	}

	public LendBean findByUserIdAndBookId(int id) throws DAOException {

		String sql = "select * from lend where lend_id = ?;";

		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery()) {
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
				return bean;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}
	
	//有効な貸出IDか
	public boolean isLendId(int id) throws DAOException {

		String sql = "select * from lend where lend_id = ?;";

		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			
			st.setInt(1, id);
			
			try (ResultSet rs = st.executeQuery()) {
				
				if(rs.next()) {
					
					return false;
					
				}else {
					
					return true;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

		//返却されていない貸出IDか
		public boolean isAlreadyReturn(int id) throws DAOException {

			String sql = "select * from lend where lend_id = ? AND return_date IS NULL";

			try (
					Connection con = DriverManager.getConnection(url, user, pass);
					// PreparedStatementオブジェクトの取得
					PreparedStatement st = con.prepareStatement(sql);) {
				
				st.setInt(1, id);
				
				try (ResultSet rs = st.executeQuery()) {
					
					if(rs.next()) {
						
						return false;
						
					}else {
						
						return true;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DAOException("レコードの取得に失敗しました。");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		}
	
	public void returnCatalog(int lendId, int userId, int bookId) throws DAOException, SQLException {

		// SQL文の作成

		String sqlUpdate = "UPDATE lend SET return_date = ? WHERE lend_id = ?;";

		String sqlStock = "UPDATE stock SET stock = ? WHERE book_id = ?;";

		try (//貸出台帳の返却年月日をUPDATEする

				Connection conUpdate = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement stUpdate = conUpdate.prepareStatement(sqlUpdate);) {

			//DATE型の変換と今日の日付
			Date today = new Date(System.currentTimeMillis());
			stUpdate.setDate(1, today);
			stUpdate.setInt(2, lendId);
			stUpdate.executeUpdate();

			try (//在庫台帳の在庫(stock)の有無を１(在庫有り)にする
					Connection conStock = DriverManager.getConnection(url, user, pass);
					// PreparedStatementオブジェクトの取得
					PreparedStatement stStock = conStock.prepareStatement(sqlStock);) {
				int stock = 1;
				stStock.setInt(1, stock);
				stStock.setInt(2, bookId);
				stStock.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	//在庫台帳　StockBeanから資料名を探す
	public StockBean findByBookId(int id) throws DAOException {

		String sql = "SELECT * FROM stock WHERE book_id = ?;";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			// 資料IDの設定
			st.setInt(1, id);

			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
				// 結果の取得および表示
				StockBean bean = new StockBean();
				while (rs.next()) {
					int bookId = rs.getInt("book_id");
					long isbn = rs.getLong("isbn");
					String title = rs.getString("title");
					String author = rs.getString("author");
					String arrivalDate = rs.getString("arrival_date");
					String disposalDate = rs.getString("disposal_date");
					String memo = rs.getString("memo");
					int stock = rs.getInt("stock");
					int reservation = rs.getInt("reservation");
					int reservationAmount = rs.getInt("reservation_amount");
					bean = new StockBean(bookId, isbn, title, author, arrivalDate, disposalDate, memo, stock,
							reservation, reservationAmount);
				}
				return bean;
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
