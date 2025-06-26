package la.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import la.bean.OverdueBean;



public class OverdueDAO {
    // URL、ユーザ名、パスワードの準備
    private String url = "jdbc:postgresql:team_dev_library_system";
    private String user = "student";
    private String pass = "himitu";

    public OverdueDAO() throws DAOException {
        try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
			
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの登録に失敗しました。");
        }
    }
    
    //延滞期間10日を過ぎた情報にreminderを付与するメソッド
  	public void setReminder () throws DAOException {
  		
  		String sql = "UPDATE lend SET first_reminder = 0 , second_reminder = 0"
  				+ " WHERE return_date IS NULL AND current_date - due_date > 9";
  		
  		try (// データベースへの接続
      			Connection con = DriverManager.getConnection(url, user, pass);
      			// PreparedStatementオブジェクトの取得
      			PreparedStatement st = con.prepareStatement(sql);) {
      		
      		// SQLの実行
  			st.executeUpdate();
  			
  		} catch (SQLException e) {
  			e.printStackTrace();
  			throw new DAOException("レコードの操作に失敗しました。");
  			
  		}	
  	}
  	
  	//延滞期間が10日以上のlistを取得するメソッド
    public List<OverdueBean> findOverdue10Days() throws DAOException{
    	
    	String sql = "SELECT l.lend_id , l.user_id , u.name , u.email , u.tel , s.book_id ,"
    			+ " s.title , l.due_date , l.first_reminder , l.second_reminder , l.memo"
    			+ " FROM lend l JOIN users u ON l.user_id = u.user_id"
    			+ " JOIN stock s ON l.book_id = s.book_id"
    			+ " WHERE l.return_date IS NULL AND current_date - l.due_date > 9"
    			+ " AND current_date - l.due_date < 30 ORDER BY l.lend_id";
    	
    	try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			
            try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
            	
            	if(!(rs.next())) {
            		//該当するデータが
            		//1件もないとき
            		return null;
            	}
            	
            } catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
            
            
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {

				// 結果の取得および表示
				List<OverdueBean> list = new ArrayList<OverdueBean>();

				
				//検索結果が存在するとき
				while (rs.next()) {
					//検索結果のデータを取得
					int lendId = rs.getInt("lend_id");
					int userId = rs.getInt("user_id");
					String name = rs.getString("name");
					String email = rs.getString("email");
					String tel = rs.getString("tel");
					int bookId = rs.getInt("book_id");
					String title = rs.getString("title");
					String dueDate = rs.getString("due_date");
					int firstReminder = rs.getInt("first_reminder");
					int secondReminder = rs.getInt("second_reminder");
					String memo = rs.getString("memo");
					
					//年月日表示用
					dueDate = makeDate(dueDate);

					OverdueBean bean = 
							new OverdueBean(lendId , userId , name , email , tel , bookId ,
									title , dueDate , firstReminder , secondReminder , memo);
					list.add(bean);
				}
				
				//検索結果をListとして返す
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
    
    //延滞期間が30日以上のlistを取得するメソッド
    public List<OverdueBean> findOverdue30Days() throws DAOException{
    	
    	String sql = "SELECT l.lend_id , l.user_id , u.name , u.email , u.tel , s.book_id ,"
    			+ " s.title , l.due_date , l.first_reminder , l.second_reminder , l.memo"
    			+ " FROM lend l JOIN users u ON l.user_id = u.user_id"
    			+ " JOIN stock s ON l.book_id = s.book_id"
    			+ " WHERE l.return_date IS NULL"
    			+ " AND current_date - l.due_date > 29 ORDER BY l.lend_id";
    	
    	try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			
            try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
            	
            	if(!(rs.next())) {
            		//該当するデータが
            		//1件もないとき
            		return null;
            	}
            	
            } catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
            
            
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {

				// 結果の取得および表示
				List<OverdueBean> list = new ArrayList<OverdueBean>();

				
				//検索結果が存在するとき
				while (rs.next()) {
					//検索結果のデータを取得
					int lendId = rs.getInt("lend_id");
					int userId = rs.getInt("user_id");
					String name = rs.getString("name");
					String email = rs.getString("email");
					String tel = rs.getString("tel");
					int bookId = rs.getInt("book_id");
					String title = rs.getString("title");
					String dueDate = rs.getString("due_date");
					int firstReminder = rs.getInt("first_reminder");
					int secondReminder = rs.getInt("second_reminder");
					String memo = rs.getString("memo");
					
					//年月日表示用
					dueDate = makeDate(dueDate);

					OverdueBean bean = 
							new OverdueBean(lendId , userId , name , email , tel , bookId ,
									title , dueDate , firstReminder , secondReminder , memo);
					list.add(bean);
				}
				
				//検索結果をListとして返す
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
    
    //延滞情報を更新するメソッド
	public void updateOverdue (int lendId , int firstReminder , int secondReminder , String memo) throws DAOException {
		
		String sql = "";
		// 在庫台帳の更新
		if(memo != null && memo.length() != 0) {
			
			sql = "UPDATE lend SET first_reminder = ? , second_reminder  = ? ,"
					+ " memo = ? WHERE lend_id = ?";
			
		}else {
			
			sql = "UPDATE lend SET first_reminder = ? , second_reminder = ? WHERE lend_id = ?";
		}
		
		try (// データベースへの接続
    			Connection con = DriverManager.getConnection(url, user, pass);
    			// PreparedStatementオブジェクトの取得
    			PreparedStatement st = con.prepareStatement(sql);) {
    		
			//パラメータ設定
			if(memo != null && memo.length() != 0) {
				
				st.setInt(1, firstReminder);
				st.setInt(2, secondReminder);
				st.setString(3, memo);
				st.setInt(4, lendId);
	    		
			}else {
				
				st.setInt(1, firstReminder);
				st.setInt(2, secondReminder);
				st.setInt(3, lendId);
			}
    		
    		
    		// SQLの実行
			st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
			
		}	
	}
	
	//年月日のフォーマット
		public String makeDate(String date) {
			
			String formatDate = date;
			
			formatDate = formatDate.replaceFirst("-", "年");
			formatDate = formatDate.replaceFirst("-", "月");
			
			if(date.contains("日")) {
				
				formatDate = formatDate.replace("日", "");
			}
			
			formatDate += "日";
			
			return formatDate;
		}
}