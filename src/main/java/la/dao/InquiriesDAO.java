package la.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import la.bean.InquiriesBean;



public class InquiriesDAO {
    // URL、ユーザ名、パスワードの準備
    private String url = "jdbc:postgresql:team_dev_library_system";
    private String user = "student";
    private String pass = "himitu";

    public InquiriesDAO() throws DAOException {
        try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
			
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの登録に失敗しました。");
        }
    }
    
    //問合せを表示するためにlistを取得するメソッド
    public List<InquiriesBean> listInquiries() throws DAOException{
    	
    	String sql = "SELECT * FROM inquiries WHERE NOT handling = 2";
    	
    	try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			
            try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
            	
            	if(!(rs.next())) {
            		//未解決の問合せが
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
				List<InquiriesBean> list = new ArrayList<InquiriesBean>();

				
				//検索結果が存在するとき
				while (rs.next()) {
					//検索結果のデータを取得
					int inquiriesId = rs.getInt("inquiries_id");
					String receptionDate = rs.getString("reception_date");
					String title = rs.getString("contents_title");
					String contents = rs.getString("contents");
					int handling = rs.getInt("handling");
					String memo = rs.getString("memo");
					
					//年月日表示用
					receptionDate = makeDate(receptionDate);

					InquiriesBean bean = 
							new InquiriesBean(inquiriesId , receptionDate , title ,
									contents , handling , memo);
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
    
    //問合せを新規登録するメソッド
    public void addInquiries(Date receptionDate , String title ,
    		String contents , int handling , String memo) throws DAOException{
    	
    	//SQL文の作成
    	String sql = "";
    	
    	if(memo != null && memo.length() != 0) {
    		
    		//memoが入力されている場合
    		sql = "INSERT INTO inquiries(reception_date , contents_title , contents , handling , memo)"
    				+ " VALUES(? , ? , ? , ? , ?)";
    		
    	}else {
    		//memoが入力されていない場合
    		sql = "INSERT INTO inquiries(reception_date , contents_title , contents , handling)"
    				+ " VALUES(? , ? , ? , ?)";
    	}
    	
    	try (// データベースへの接続
    			Connection con = DriverManager.getConnection(url, user, pass);
    			// PreparedStatementオブジェクトの取得
    			PreparedStatement st = con.prepareStatement(sql);) {
    		
    		if(memo != null && memo.length() != 0) {
        		
        		//memoが入力されている場合
    			//パラメータ設定
        		st.setDate(1, receptionDate);
        		st.setString(2, title);
        		st.setString(3, contents);
        		st.setInt(4, handling);
        		st.setString(5, memo);
        		
        	}else {
        		//memoが入力されている場合
    			//パラメータ設定
        		st.setDate(1, receptionDate);
        		st.setString(2, title);
        		st.setString(3, contents);
        		st.setInt(4, handling);
        	}
    		
    		// SQLの実行
			st.executeUpdate();
			return;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
			
		}
    }
    
    //問合せを更新するメソッド
	public void updateInquiries (int inquiriesId , int handling , String memo) throws DAOException {
		
		String sql = "";
		// 在庫台帳の更新
		if(memo != null && memo.length() != 0) {
			
			sql = "UPDATE inquiries SET handling = ? , memo = ? WHERE inquiries_id = ?";
			
		}else {
			
			sql = "UPDATE inquiries SET handling = ? WHERE inquiries_id = ?";
		}
		
		try (// データベースへの接続
    			Connection con = DriverManager.getConnection(url, user, pass);
    			// PreparedStatementオブジェクトの取得
    			PreparedStatement st = con.prepareStatement(sql);) {
    		
			//パラメータ設定
			if(memo != null && memo.length() != 0) {
				
				st.setInt(1, handling);
				st.setString(2, memo);
				st.setInt(3, inquiriesId);
	    		
			}else {
				
				st.setInt(1, handling);
				st.setInt(2, inquiriesId);
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