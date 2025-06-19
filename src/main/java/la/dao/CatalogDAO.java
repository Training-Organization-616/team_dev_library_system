package la.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import la.bean.AddResultBean;
import la.bean.SearchResultsBean;



public class CatalogDAO {
    // URL、ユーザ名、パスワードの準備
    private String url = "jdbc:postgresql:team_dev_library_system";
    private String user = "student";
    private String pass = "himitu";

    public CatalogDAO() throws DAOException {
        try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
			
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("JDBCドライバの登録に失敗しました。");
        }
    }
    
    //資料を在庫台帳に追加するメソッド
    public void addBookToStock(String isbn , String title , Date arrivalDate) throws DAOException{
    	//SQL文の作成
    	String sql = "";
    	
    	if(isbn != null && isbn.length() != 0) {
    		
    		//isbnが入力されている場合
    		sql = "INSERT INTO stock(isbn , title , arrival_date , stock) VALUES(? , ? , ? , 1)";
    		
    	}else {
    		//isbnが入力されていない場合
    		sql = "INSERT INTO stock(title , arrival_date , stock) VALUES(? , ? , 1)";
    	}
    	
    	try (// データベースへの接続
    			Connection con = DriverManager.getConnection(url, user, pass);
    			// PreparedStatementオブジェクトの取得
    			PreparedStatement st = con.prepareStatement(sql);) {
    		
    		if(isbn != null && isbn.length() != 0) {
        		
        		//isbnが入力されている場合
    			//パラメータ設定
        		st.setInt(1, Integer.parseInt(isbn));
        		st.setString(2, title);
        		st.setDate(3, arrivalDate);
        		
        	}else {
        		//isbnが入力されていない場合
        		//パラメータ設定
        		st.setString(1, title);
        		st.setDate(2, arrivalDate);
        	}
    		
    		// SQLの実行
			st.executeUpdate();
			return;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
			
		}
    }
    
    
    //在庫台帳から資料IDを取得するメソッド
	public List<Integer> getBookId(String isbn , String title) throws DAOException {

		//SQL文の作成
    	String sql = "";
    	
    	if(isbn != null && isbn.length() != 0) {
    		
    		//isbnが入力されている場合
    		sql = "SELECT book_id FROM stock WHERE isbn = ? AND title = ?";
    		
    	}else {
    		//isbnが入力されていない場合
    		sql = "SELECT book_id FROM stock WHERE title = ?";
    	}

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			
			if (isbn != null && isbn.length() != 0) {

				//isbnが入力されている場合
				//パラメータ設定
				st.setInt(1, Integer.parseInt(isbn));
				st.setString(2, title);

			} else {
				//isbnが入力されていない場合
				//パラメータ設定
				st.setString(1, title);
			}
			
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {

				// 結果の取得および表示
				List<Integer> list = new ArrayList<Integer>();

				while (rs.next()) {
					int bookId = rs.getInt("book_id");
					list.add(bookId);
				}
				// カテゴリ一覧をListとして返す
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
	
	//新規資料登録時、資料目録に資料が存在するかを確認するメソッド
    public boolean isFindBook(String title , int code , String author , String publicher ,
    		Date publicationDate) throws DAOException {
    	
    	//flagがtrueの時、資料目録に資料は既に存在している
    	boolean flag = false;
    	
        // SQL文の作成
        String sql = "SELECT * FROM catalog WHERE title = ? AND code = ?"
        		+ " AND author = ? AND publicher = ? AND publication_date = ?";
		
        try (// データベースへの接続
             Connection con = DriverManager.getConnection(url, user, pass);
			 // PreparedStatementオブジェクトの取得
			 PreparedStatement st = con.prepareStatement(sql);) {
        	
        	st.setString(1, title);
        	st.setInt(2, code);
        	st.setString(3, author);
        	st.setString(4, publicher);
        	st.setDate(5, publicationDate);
        	
        	try (// SQLの実行
					ResultSet rs = st.executeQuery();) {

        		// 結果の取得
    		    if (rs.next()) {
    		    	//レコードが存在するとき
    		    	flag = true;
    	    	}
    		    
    		    return flag;
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
        } catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
        } 
    }
    
  //新規資料登録時、資料目録に資料が存在した場合のstockAmountを設定するメソッド
    public void setStockAmount(String title , String stockAmount) throws DAOException{
    	
    	//資料目録の更新
		String sql = "Update catalog SET stock_amount = ? WHERE title = ?";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {

			//パラメータ設定
			st.setString(1, stockAmount);
			st.setString(2, title);

			// SQLの実行
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");

		}
	}
    
	//巻冊番号を得るためのメソッド
	public int getVolumuNumber(String author) throws DAOException {

		// SQL文の作成
		String sql = "SELECT COUNT(*) FROM catalog WHERE author = ?";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			
			//パラメータ設定
			st.setString(1, author);
			
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {

				rs.next();
				int volumeNumber = rs.getInt("count");
				
				return volumeNumber;
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}
	
	//資料を資料目録に追加するメソッド
    public void addBookToCatalog(String isbn , String title , int code , String author , String publicher ,
    		Date publicationDate , String stockAmount , int volumeNumber) throws DAOException{
    	
    	//SQL文の作成
    	String sql = "";
    	
    	if(isbn != null && isbn.length() != 0) {
    		
    		//isbnが入力されている場合
    		sql = "INSERT INTO catalog (isbn , title , code , author , publicher , publication_date ,"
        			+ " stock_amount , volume_number) VALUES(? , ? , ? , ? , ? , ? , ? , ?)";
    		
    	}else {
    		//isbnが入力されていない場合
    		sql = "INSERT INTO catalog (title , code , author , publicher , publication_date ,"
        			+ " stock_amount , volume_number) VALUES(? , ? , ? , ? , ? , ? , ?)";
    	}
    	
    	
    	try (// データベースへの接続
    			Connection con = DriverManager.getConnection(url, user, pass);
    			// PreparedStatementオブジェクトの取得
    			PreparedStatement st = con.prepareStatement(sql);) {
    		
    		if (isbn != null && isbn.length() != 0) {

				//isbnが入力されている場合
				//パラメータ設定
    			st.setInt(1, Integer.parseInt(isbn));
        		st.setString(2, title);
        		st.setInt(3, code);
        		st.setString(4, author);
        		st.setString(5, publicher);
        		st.setDate(6, publicationDate);
        		st.setString(7, stockAmount);
        		st.setInt(8, volumeNumber);

			} else {
				//isbnが入力されていない場合
				//パラメータ設定
				st.setString(1, title);
        		st.setInt(2, code);
        		st.setString(3, author);
        		st.setString(4, publicher);
        		st.setDate(5, publicationDate);
        		st.setString(6, stockAmount);
        		st.setInt(7, volumeNumber);
			}
    		
    		// SQLの実行
			st.executeUpdate();
			return;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
			
		}
    }
    
    //新規登録結果を返すメソッド
    public AddResultBean addResult(String isbn) throws DAOException{
    	
    	//SQL文の作成
    	String sql = "";
    	
    	if(isbn != null && isbn.length() != 0) {
    		
    		//isbnが入力されている場合
    		sql = "SELECT s.book_id , s.isbn , s.title , c.code , c.author , "
    				+ "c.publicher , c.publication_date , s.arrival_date "
    				+ "FROM stock s JOIN catalog c ON s.title = c.title "
    				+ "ORDER BY s.book_id DESC LIMIT 1";
    		
    	}else {
    		//isbnが入力されていない場合
    		sql = "SELECT s.book_id , s.title , c.code , c.author , "
    				+ "c.publicher , c.publication_date , s.arrival_date "
    				+ "FROM stock s JOIN catalog c ON s.title = c.title "
    				+ "ORDER BY s.book_id DESC LIMIT 1";
    	}
    	
    	try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);
    			ResultSet rs = st.executeQuery();) {
    		
    		//結果の取得
    		if(isbn != null && isbn.length() != 0) {
        		
        		//isbnが入力されている場合
        		rs.next();
        		int resultBookId = rs.getInt("book_id");
        		int resultIsbn = rs.getInt("isbn");
        		String resultTitle = rs.getString("title");
        		int resultCode = rs.getInt("code");
        		String resultAuthor = rs.getString("author");
        		String resultPublicher = rs.getString("publicher");
        		String resultPublicationDate = rs.getString("publication_date");
        		String resultArrivalDate = rs.getString("arrival_date");
        		
        		AddResultBean bean = new AddResultBean
        				(resultBookId , resultIsbn , resultTitle , resultCode , resultAuthor ,
        						resultPublicher , resultPublicationDate , resultArrivalDate);
        		
        		return bean;
        		
        	}else {
        		//isbnが入力されていない場合
        		rs.next();
        		int resultBookId = rs.getInt("book_id");
        		String resultTitle = rs.getString("title");
        		int resultCode = rs.getInt("code");
        		String resultAuthor = rs.getString("author");
        		String resultPublicher = rs.getString("publicher");
        		String resultPublicationDate = rs.getString("publication_date");
        		String resultArrivalDate = rs.getString("arrival_date");
        		
        		AddResultBean bean = new AddResultBean
        				(resultBookId , resultTitle , resultCode , resultAuthor ,
        						resultPublicher , resultPublicationDate , resultArrivalDate);
        		
        		return bean;
        		
        	}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
    }
    
    //資料の検索
    public List<SearchResultsBean> findBooks(String title , String code , String author , String publicher ,
    		String code2 , String authorHead , String volumeNumber) throws DAOException {
    	
    	// SQL文の作成
        String sql = "SELECT s.book_id , s.isbn , s.title , c.code , c.author , c.publicher , c.publication_date , s.arrival_date"
        		+ " FROM stock s join catalog c on s.title = c.title WHERE disposal_date IS NULL";
        
        //検索時、資料名が入力されているとき
        if(title != null && title.length() != 0) {
        	
        	sql += " AND s.title = ?";	
        }
        //検索時、分類コードが入力されているとき
        if(code != null && code.length() != 0) {
        	
        	sql += " AND c.code = ?";
        }
        //検索時、著者が入力されているとき
        if(author != null && author.length() != 0) {
        	
        	sql += " AND c.author = ?";
        }
        //検索時、出版社が入力されているとき
        if(publicher != null && publicher.length() != 0) {
        	
        	sql += " AND c.publicher = ?";
        	
        }
        //検索時、背ラベルが入力されているとき
        if(code2 != null && code2.length() != 0){
        	
        	sql += " AND c.code = ? AND c.author LIKE ? AND c.volume_number = ?";
        		
        }
        
        sql += " ORDER BY s.book_id";
        
		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {

			//プレースホルダに値を設定するための
        	//カウンタ変数
        	int i = 0;
        	
        	//検索時、資料名が入力されているとき
            if(title != null && title.length() != 0) {
            	
            	i++;
            	st.setString(i, title);
            }
            
            //検索時、分類コードが入力されているとき
            if(code != null && code.length() != 0) {
            	
            	i++;
            	st.setInt(i, Integer.parseInt(code));
            }
            
            //検索時、著者が入力されているとき
            if(author != null && author.length() != 0) {
            	
            	i++;
            	st.setString(i, author);
            }
            
            //検索時、出版社が入力されているとき
            if(publicher != null && publicher.length() != 0) {
            	
            	i++;
            	st.setString(i, publicher);
            }
            
            //検索時、背ラベルが入力されているとき
            if((code2 != null && code2.length() != 0)
            		&& (authorHead != null && authorHead.length() == 1)
            		&& (volumeNumber != null && volumeNumber.length() != 0)){
            	
            	i++;
            	st.setInt(i, Integer.parseInt(code2));
            	st.setString(i + 1, authorHead + "%");
            	st.setInt(i + 2, Integer.parseInt(volumeNumber));
            		
            }
            
            try (// SQLの実行
					ResultSet rs = st.executeQuery();) {
            	
            	if(!(rs.next())) {
            		
            		return null;
            	}
            	
            } catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
            
            
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {

				// 結果の取得および表示
				List<SearchResultsBean> list = new ArrayList<SearchResultsBean>();

				
				//検索結果が存在するとき
				while (rs.next()) {
					//検索結果のデータを取得
					int bookId = rs.getInt("book_id");
					int resultIsbn = rs.getInt("isbn");
					String resultTitle = rs.getString("title");
					int resultCode = rs.getInt("code");
					String resultAuthor = rs.getString("author");
					String resultPublicher = rs.getString("publicher");
					String resultPublicationDate = rs.getString("publication_date");
					String resultArrivalDate = rs.getString("arrival_date");
					
					if(resultTitle == null || resultTitle.length() == 0) {
						
						return null;
					}

					SearchResultsBean bean = 
							new SearchResultsBean(bookId , resultIsbn , resultTitle , resultCode , resultAuthor ,
									resultPublicher , resultPublicationDate , resultArrivalDate);
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
    
    
    //在庫台帳、資料目録を更新するメソッド
	public void updateCatalog (int bookId , String isbn , String title , String code ,String author ,
			String publicher , Date publicationDate , Date arrivalDate) throws DAOException {
		
		String sql = "";
		// 在庫台帳の更新
		if(isbn != null && isbn.length() != 0) {
			
			sql = "Update stock SET isbn = ? , title = ? , arrival_date = ? WHERE book_id = ?";
			
		}else {
			
			sql = "Update stock SET title = ? , arrival_date = ? WHERE book_id = ?";
		}
		
		try (// データベースへの接続
    			Connection con = DriverManager.getConnection(url, user, pass);
    			// PreparedStatementオブジェクトの取得
    			PreparedStatement st = con.prepareStatement(sql);) {
    		
			//パラメータ設定
			if(isbn != null && isbn.length() != 0) {
				
				st.setInt(1, Integer.parseInt(isbn));
	    		st.setString(2, title);
	    		st.setDate(3, arrivalDate);
	    		st.setInt(4, bookId);
	    		
			}else {
				
	    		st.setString(1, title);
	    		st.setDate(2, arrivalDate);
	    		st.setInt(3, bookId);
			}
    		
    		
    		// SQLの実行
			st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
			
		}
		
		// 資料目録の変更
		if(isbn != null && isbn.length() != 0) {
			
			sql = "Update catalog SET isbn = ? , title = ? , code = ? , author = ? , publicher = ? ,"
					+ " publication_date = ? WHERE stock_amount LIKE ?";
			
		}else {
			
			sql = "Update catalog SET title = ? , code = ? , author = ? , publicher = ? ,"
					+ " publication_date = ? WHERE stock_amount LIKE ?";
		}
	
		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
	
			//パラメータ設定
			if(isbn != null && isbn.length() != 0) {
				
				st.setInt(1, Integer.parseInt(isbn));
				st.setString(2, title);
				st.setInt(3, Integer.parseInt(code));
				st.setString(4, author);
				st.setString(5, publicher);
				st.setDate(6, publicationDate);
				st.setString(7, "%," + bookId + ",%");
	    		
			}else {
				
				st.setString(1, title);
				st.setInt(2, Integer.parseInt(code));
				st.setString(3, author);
				st.setString(4, publicher);
				st.setDate(5, publicationDate);
				st.setString(6, "%," + bookId + ",%");
			}
	
			// SQLの実行
			st.executeUpdate();
			return;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}
    
	//本を廃棄した時のメソッド
	public void deleteCatalog (int bookId , Date disposalDate , String memo) throws DAOException {
		
		String sql = "";
		
		if(memo != null && memo.length() != 0) {
			
			// 在庫台帳の更新
			sql = "Update stock SET disposal_date = ? , memo = ? , stock = 2 WHERE book_id = ?";
			
		}else {
			
			// 在庫台帳の更新
			sql = "Update stock SET disposal_date = ? , stock = 2 WHERE book_id = ?";
		}
		
		try (// データベースへの接続
    			Connection con = DriverManager.getConnection(url, user, pass);
    			// PreparedStatementオブジェクトの取得
    			PreparedStatement st = con.prepareStatement(sql);) {
    		
			if(memo != null && memo.length() != 0) {
				
				//パラメータ設定
	    		st.setDate(1, disposalDate);
	    		st.setString(2, memo);
	    		st.setInt(3, bookId);
				
			}else {
				
				//パラメータ設定
	    		st.setDate(1, disposalDate);
	    		st.setInt(2, bookId);
			}
    		
    		// SQLの実行
			st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
			
		}
		
		// catalogから現在のstock_amountを取得
		sql = "SELECT stock_amount FROM catalog WHERE stock_amount LIKE ?";
		
		//catalogのstock_amountを格納するメソッド
		String stockAmount = "";
	
		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatementオブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
	
			st.setString(1, "%," + bookId + ",%");
			
			try (// SQLの実行
					ResultSet rs = st.executeQuery();) {

				rs.next();
				
				stockAmount = rs.getString("stock_amount");
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました。");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
		
		String newStockAmount = makeStockAmount(bookId , stockAmount);
		
		//資料目録の更新
		sql = "UPDATE catalog SET stock_amount = ? WHERE stock_amount LIKE ?";
		
		try (// データベースへの接続
    			Connection con = DriverManager.getConnection(url, user, pass);
    			// PreparedStatementオブジェクトの取得
    			PreparedStatement st = con.prepareStatement(sql);) {
    		
    		//パラメータ設定
    		st.setString(1, newStockAmount);
    		st.setString(2, "%," + bookId + ",%");
    		
    		// SQLの実行
			st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
			
		}
	}
	
	
	//catalogのstock_amountから削除するためのメソッド
	public String makeStockAmount(int bookId , String oldStockAmount) {
		
		String newStockAmount = "";
		
		List<String> stockAmounts = Arrays.asList(oldStockAmount.split(","));
		
		for(int i = 1; i < stockAmounts.size(); i += 2) {
			
			if(bookId != Integer.parseInt(stockAmounts.get(i))) {
				
				newStockAmount += "," + stockAmounts.get(i) + ",";
			}
		}
		
		return newStockAmount;
	}
}