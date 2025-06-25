package la.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import la.bean.UserBean;

public class UserDAO {
	private String url = "jdbc:postgresql:team_dev_library_system";
	private String user = "student";
	private String pass = "himitu";

	public UserDAO() throws DAOException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの登録に失敗しました。");
		}
	}

	//検索
	public List<UserBean> findUser(int userId, String userName, String address, String tel, String email, Date birthday)
			throws DAOException {

		String sql = "SELECT * FROM users WHERE cancel_date IS NULL";
		if (userId > 0) {
			sql = sql + " AND user_id = ?";
		}
		if (userName != "") {
			sql = sql + " AND name LIKE ?";
		}
		if (address != "") {
			sql = sql + " AND address LIKE ?";
		}
		if (tel != "") {
			sql = sql + " AND tel LIKE ?";
		}
		if (email != "") {
			sql = sql + " AND email LIKE ?";
		}
		if (birthday != null) {
			sql = sql + " AND birthday LIKE ?";
		}
		sql = sql + " ORDER BY user_id";

		try (Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql)) {
			int index = 1;
			if (userId > 0) {
				st.setInt(index++, userId);
			}
			if (userName != "") {
				st.setString(index++, "%" + userName + "%");
			}
			if (address != "") {
				st.setString(index++, "%" + address + "%");
			}
			if (tel != "") {
				st.setString(index++, "%" + tel + "%");
			}
			if (email != "") {
				st.setString(index++, "%" + email + "%");
			}
			if (birthday != null) {
				st.setDate(index++, birthday);
			}
			try (ResultSet rs = st.executeQuery()) {
				List<UserBean> list = new ArrayList<UserBean>();
				while (rs.next()) {
					int id = rs.getInt("user_id");
					String name = rs.getString("name");
					String userAddress = rs.getString("address");
					String userTel = rs.getString("tel");
					String userEmail = rs.getString("email");
					String userBirthday = rs.getString("birthday");

					//ユーザが借りている本のIDを取得
					int lendBooks = findLendBooks(id);

					//ユーザが予約している本のIDを取得
					String reservationBooks = findReservationBooks(id);

					UserBean bean = new UserBean(id, name, userAddress, userTel,
							userEmail, userBirthday, lendBooks, reservationBooks);
					list.add(bean);
				}
				return list.isEmpty() ? Collections.emptyList() : list;//ヒットしなければ空のlistを送信
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("データベース接続に失敗しました。");
		}
	}

	//ユーザーが借りている本のidを検索
	public int findLendBooks(int userId)
			throws DAOException {

		String sql = "SELECT book_id FROM lend WHERE user_id = ? AND return_date IS NULL";

		try (Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql)) {

			st.setInt(1, userId);

			try (ResultSet rs = st.executeQuery()) {

				int lendBooks = 0;

				while (rs.next()) {

					lendBooks++;
				}
				return lendBooks;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("データベース接続に失敗しました。");
		}
	}

	//ユーザーが借りている本のidを検索
	public String findReservationBooks(int userId) throws DAOException {

		String sql = "SELECT book_id FROM reservation"
				+ " WHERE user_id = ? AND NOT already_lent = 2";

		try (Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql)) {

			st.setInt(1, userId);

			try (ResultSet rs = st.executeQuery()) {

				String reservationBooks = "無";

				while (rs.next()) {

					reservationBooks = "有";
				}
				return reservationBooks;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("データベース接続に失敗しました。");
		}
	}

	//一件のデータを取得
	public UserBean findOneUser(int userId) throws DAOException {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		try (Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setInt(1, userId);
			try (ResultSet rs = st.executeQuery()) {

				if (rs.next()) {
					int id = rs.getInt("user_id");
					String name = rs.getString("name");
					String userAddress = rs.getString("address");
					String userTel = rs.getString("tel");
					String userEmail = rs.getString("email");
					String userBirthday = rs.getString("birthday");
					String userAdmissionDate = rs.getString("admission_date");
					String useUpdateDate = rs.getString("update_date");
					String userCancelDate = rs.getString("cancel_date");
					UserBean bean = new UserBean(id, name, userAddress, userTel, userEmail, userBirthday,
							userAdmissionDate, useUpdateDate, userCancelDate);
					return bean;
				}
				return null;

			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("データベース接続に失敗しました。");
		}

	}

	//ユーザーが登録されているかどうか判定
	public boolean isUserRegistered(String email) throws SQLException {
		String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
		try (Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			st.setString(1, email);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0; // 1以上なら登録済み
				}
			}
		}
		return false;
	}

	//他ユーザーが登録しているメールかどうか判定
	public boolean isOtherUserUseEmail(String email, int userId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM users WHERE email = ? AND user_id != ?";
		try (Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			st.setString(1, email);
			st.setInt(2, userId);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0; // 1以上なら登録済み
				}
			}
		}
		return false;
	}

	//登録
	public List<UserBean> addUser(String userName, String address, String tel, String email, Date birthday,
			Date admissionDate) throws DAOException, ParseException {
		String sql = "INSERT INTO users(name,address,tel,email,birthday,admission_date) VALUES(?,?,?,?,?,?)";

		try (Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, userName);
			st.setString(2, address);
			st.setString(3, tel);
			st.setString(4, email);
			st.setDate(5, birthday);
			st.setDate(6, admissionDate);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("データベース接続に失敗しました。");
		}

		sql = "SELECT * FROM users WHERE email = ?";
		try (Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, email);
			try (ResultSet rs = st.executeQuery()) {
				List<UserBean> list = new ArrayList<UserBean>();
				if (rs.next()) {
					int id = rs.getInt("user_id");
					String name = rs.getString("name");
					String userAddress = rs.getString("address");
					String userTel = rs.getString("tel");
					String userEmail = rs.getString("email");
					String userBirthday = rs.getString("birthday");
					String dateAdmission = rs.getString("admission_date");
					UserBean bean = new UserBean(id, name, userAddress, userTel, userEmail, userBirthday,
							dateAdmission);
					list.add(bean);

				}
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("データベース接続に失敗しました。");
		}

	}

	//情報更新
	public void updateUser(int userId, String userName, String address, String tel, String email,
			Date birthday, Date updateDate) throws DAOException {
		String sql = "UPDATE users SET name = ? , address = ?, tel = ? , email = ? , birthday = ? , update_date = ? WHERE user_id = ?";

		try (Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {

			st.setString(1, userName);
			st.setString(2, address);
			st.setString(3, tel);
			st.setString(4, email);
			st.setDate(5, birthday);
			st.setDate(6, updateDate);
			st.setInt(7, userId);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}

	//ユーザーが借りている本があるかどうか
	public boolean isUserLend(int userId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM lend WHERE user_id = ? AND return_date IS NULL";
		try (Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, userId);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0; // 1以上なら借りている本有り
				}
			}
		}
		return false;
	}

	public boolean isUserReservation(int userId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM reservation WHERE user_id = ? AND already_lent != 2";
		try (Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			st.setInt(1, userId);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0; // 1以上なら借りている本有り
				}
			}
		}
		return false;
	}

	//退会処理 削除ではない
	public void deleteUser(int userId, Date cancelDate) throws DAOException {
		String sql = "UPDATE users SET cancel_date = ? WHERE user_id = ?";
		try (Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {

			st.setDate(1, cancelDate);
			st.setInt(2, userId);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}

	}

}
