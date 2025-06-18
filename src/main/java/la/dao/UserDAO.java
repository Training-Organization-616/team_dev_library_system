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
	public List<UserBean> findUser(int userId, String userName, String address, int tel, String email, Date birthday)
			throws DAOException {

		String sql = "SELECT * FROM users WHERE cancel_date IS NULL";
		if (userId > 0) {
			sql = sql + " AND user_id = ?";
		}
		if (userName != null) {
			sql = sql + " AND name = ?";
		}
		if (address != null) {
			sql = sql + " AND address = ?";
		}
		if (tel > 0) {
			sql = sql + " AND tel = ?";
		}
		if (email != null) {
			sql = sql + " AND email = ?";
		}
		if (birthday != null) {
			sql = sql + " AND birthday = ?";
		}
		System.out.println(sql);

		try (Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql)) {
			int index = 1;
			if (userId > 0) {
				st.setInt(index++, userId);
			}
			if (userName != null) {
				st.setString(index++, userName);
			}
			if (address != null) {
				st.setString(index++, address);
			}
			if (tel > 0) {
				st.setInt(index++, tel);
			}
			if (email != null) {
				st.setString(index++, email);
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
					int userTel = rs.getInt("tel");
					String userEmail = rs.getString("email");
					String userBirthday = rs.getString("birthday");
					UserBean bean = new UserBean(id, name, userAddress, userTel, userEmail, userBirthday);
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

	//一件のデータを取得
	public List<UserBean> findUserId(int userId) throws DAOException {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		try (Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setInt(1, userId);
			try (ResultSet rs = st.executeQuery()) {
				List<UserBean> list = new ArrayList<UserBean>();
				while (rs.next()) {
					int id = rs.getInt("user_id");
					String name = rs.getString("name");
					String userAddress = rs.getString("address");
					int userTel = rs.getInt("tel");
					String userEmail = rs.getString("email");
					String userBirthday = rs.getString("birthday");
					UserBean bean = new UserBean(id, name, userAddress, userTel, userEmail, userBirthday);
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

	//登録
	public void addUser(String userName, String address, int tel, String email, Date birthday,
			Date admissionDate) throws DAOException, ParseException {
		String sql = "INSERT INTO users(name,address,tel,email,birthday,admission_date) VALUES(?,?,?,?,?,?)";

		try (Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, userName);
			st.setString(2, address);
			st.setInt(3, tel);
			st.setString(4, email);
			st.setDate(5, birthday);
			st.setDate(6, admissionDate);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("データベース接続に失敗しました。");
		}
	}

	//情報更新
	public void updateUser(int userId, String userName, String address, int tel, String email,
			Date birthday, Date updateDate) throws DAOException {
		String sql = "UPDATE users SET name = ? , address = ?, tel = ? , email = ? , birthday = ? , update_date = ? WHERE user_id = ?";

		try (Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {

			st.setString(1, userName);
			st.setString(2, address);
			st.setInt(3, tel);
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
		String sql = "SELECT COUNT(*) FROM lend WHERE user_id = ?";
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
