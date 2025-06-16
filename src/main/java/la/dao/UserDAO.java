package la.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public List<UserBean> findUser(int userId, String userName, String address, int tel, String email, String birthday)
			throws DAOException {
		String sql = "SELECT * FROM users WHERE 1=1";
		if (userId > 0) {
			sql += " AND user_id = ?";
		}
		if (userName != null) {
			sql += " AND name = ?";
		}
		if (address != null) {
			sql += " AND address = ?";
		}
		if (tel > 0) {
			sql += " AND tel = ?";
		}
		if (email != null) {
			sql += " AND email = ?";
		}
		if (birthday != null) {
			sql += " AND birthday = ?";
		}
		sql += " AND cancel_date IS NULL";//退会者は非表示
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
				st.setString(index++, birthday);
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

	//登録
	//既に登録されたアドレスの確認無し
	//別のメソッドを作成するか、引数などで調整する必要あり
	public void addUser(String userName, String address, int tel, String email, String birthday,
			String admissionDate) throws DAOException {
		String sql = "INSERT INTO users(name,address,tel,email,birthday,admission_date) VALUES(?,?,?,?,?,?)";
		try (Connection con = DriverManager.getConnection(url, user, pass);
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, userName);
			st.setString(2, address);
			st.setInt(3, tel);
			st.setString(4, email);
			st.setString(5, birthday);
			st.setString(6, admissionDate);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("データベース接続に失敗しました。");
		}
	}

	//情報更新
	public void updateUser(int userId, String userName, String address, int tel, String email,
			String birthday, String addmissionDate, String updateDate, String cancelDate) throws DAOException {
		String sql = "UPDATE users SET name = ? , address = ?, tel = ? , email = ? , birthday = ? WHERE user_id = ?";
		try (Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {

			st.setString(1, userName);
			st.setString(2, address);
			st.setInt(3, tel);
			st.setString(4, email);
			st.setString(5, birthday);
			st.setInt(6, userId);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}

	//退会処理　削除ではない
	public void deleteUser(int userId, String cancelDate) throws DAOException {
		String sql = "UPDATE users SET cancel_date = ? WHERE user_id = ?";
		try (Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {

			st.setString(1, cancelDate);
			st.setInt(2, userId);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}

	}

}
