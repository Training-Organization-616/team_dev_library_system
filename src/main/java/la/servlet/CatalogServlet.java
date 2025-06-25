package la.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import la.bean.AddResultBean;
import la.bean.EditResultBean;
import la.bean.SearchResultsBean;
import la.bean.StockBean;
import la.dao.CatalogDAO;
import la.dao.DAOException;
import la.dao.LendDAO;


@WebServlet("/CatalogServlet")
public class CatalogServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8");
    	
        try {
        	//actionのリクエストパラメータを取得
            String action = request.getParameter("action");
            
            
            if(action.equals("top") || action == null) {
            	//actionが "top" or "なし" の時、top画面に遷移
                gotoPage(request, response, "/top/top.jsp");
                
                
            }else if(action.equals("return_add")){
            	//actionの値が「returnAdd」の場合
            	
            	//検索結果のsession開放
        		HttpSession session = request.getSession();
        		session.removeAttribute("books");
            	
            	//新規資料登録画面から、資料メニューに戻る
            	gotoPage(request , response , "/catalog/catalog_top.jsp");
            	
            }else if(action.equals("return_search")) {
            	//actionの値が「returnSearch」の場合
            	//資料変更画面、資料削除確認画面から
            	//資料検索結果画面に戻る
            	gotoPage(request , response , "/catalog/catalog_search.jsp");
            
            }else if(action.equals("add")) {
            	//actionの値が「add」の場合
            	//資料を追加する
            	
            	//パラメータ取得
            	String isbn = request.getParameter("isbn");
            	String title = request.getParameter("title");
            	String code = request.getParameter("code");
            	String author = request.getParameter("author");
            	String publicher = request.getParameter("publicher");
            	String strPublicationDate = request.getParameter("publication_date");
            	String strArrivalDate = request.getParameter("arrival_date");
            	
            	if((title != null && title.length() != 0)
            			&& (code != null && code.length() != 0)
            			&& (author != null && author.length() != 0)
            			&& (publicher != null && publicher.length() != 0)
            			&& (strPublicationDate != null && strPublicationDate.length() != 0)
            			&& (strArrivalDate != null && strArrivalDate.length() != 0)) {
            		
            		//資料を追加するときに
            		//必要事項が全て入力されているとき
            		
            		//isbnが入力されているとき
            		if(isbn != null && isbn.length() != 0) {
            			
            			//入力されたISBNが正しい値かを調べる
                		int flagIsbn = checkIsbn(isbn);
                		
                		if(flagIsbn == 2) {
                			//数字以外が入力された場合
                			request.setAttribute("title", title);
                			//request.setAttribute("code", code);
                			request.setAttribute("author", author);
                			request.setAttribute("publicher", publicher);
                			request.setAttribute("publicationDate", strPublicationDate);
                			request.setAttribute("arrivalDate", strArrivalDate);
                			request.setAttribute("message", "ISBN番号は数字で入力してください");
                    		gotoPage(request , response , "/catalog/catalog_add.jsp");
                    		return;
                    		
                		}else if(flagIsbn == 1) {
                			//桁数が多すぎる場合
                			request.setAttribute("title", title);
                			//request.setAttribute("code", code);
                			request.setAttribute("author", author);
                			request.setAttribute("publicher", publicher);
                			request.setAttribute("publicationDate", strPublicationDate);
                			request.setAttribute("arrivalDate", strArrivalDate);
                			request.setAttribute("message", "ISBN番号は13桁以内で入力してください");
                			return;
                		}
            		}
            		
            		//入力された資料名が正しいかを調べる
            		boolean flagTitle = isCheckTitle(title);
            		
            		if(flagTitle) {
            			//入力が正しくない場合
            			request.setAttribute("message", "資料名は50文字以内で入力してください");
            			
            			if(isbn != null && isbn.length() != 0) {
            				request.setAttribute("isbn", isbn);
            			}
            			//request.setAttribute("code", code);
            			request.setAttribute("author", author);
            			request.setAttribute("publicher", publicher);
            			request.setAttribute("publicationDate", strPublicationDate);
            			request.setAttribute("arrivalDate", strArrivalDate);
                		gotoPage(request , response , "/catalog/catalog_add.jsp");
                		return;
            		}
            		
            		//入力された著者が正しいかを調べる
            		boolean flagAuthor = isCheckAuthor(author);
            		
            		if(flagAuthor) {
            			//入力が正しくない場合
            			request.setAttribute("message", "著者は50文字以内で入力してください");
            			
            			if(isbn != null && isbn.length() != 0) {
            				request.setAttribute("isbn", isbn);
            			}
            			//request.setAttribute("code", code);
            			request.setAttribute("title", title);
            			request.setAttribute("publicher", publicher);
            			request.setAttribute("publicationDate", strPublicationDate);
            			request.setAttribute("arrivalDate", strArrivalDate);
                		gotoPage(request , response , "/catalog/catalog_add.jsp");
                		return;
            		}
            		
            		//入力された出版社が正しいかを調べる
            		boolean flagPublicher = isCheckPublicher(publicher);
            		
            		if(flagPublicher) {
            			//入力が正しくない場合
            			request.setAttribute("message", "出版社は100文字以内で入力してください");
            			
            			if(isbn != null && isbn.length() != 0) {
            				request.setAttribute("isbn", isbn);
            			}
            			//request.setAttribute("code", code);
            			request.setAttribute("title", title);
            			request.setAttribute("author", author);
            			request.setAttribute("publicationDate", strPublicationDate);
            			request.setAttribute("arrivalDate", strArrivalDate);
                		gotoPage(request , response , "/catalog/catalog_add.jsp");
                		return;
            		}
            		
            		//出版日、入荷日の型をstringからsql.dateに変換する
            		Date publicationDate = setDate(strPublicationDate);
            		Date arrivalDate = setDate(strArrivalDate);
            		
            		CatalogDAO dao = new CatalogDAO();
            		
            		//資料目録一意性制約確認
            		if(dao.isUniqueConstraint(title , author)) {
            			
            			//資料目録に資料があるかを確認する
                		if(!(dao.isFindBook(isbn , title, Integer.parseInt(code) ,
                				author, publicher, publicationDate))) {
                			
                			//資料がなかった場合
                			//入力内容が正しくないことを伝える
                			request.setAttribute("message", "正しく資料を入力してください");
                			
                		}
            		}
            		
            		//在庫台帳に資料を追加する
            		dao.addBookToStock(isbn , title , author , arrivalDate);
            		
            		//在庫台帳から今追加した資料の資料IDを取得する
            		List<Integer> idList = dao.getBookId(isbn , title);
            		
            		//資料目録に入れるstockAmount作成
            		String stockAmount = "";
            		for(int id : idList) {
            			
            			stockAmount += "," + id + ",";
            		}
            		
            		//資料目録に資料があるかを確認する
            		if(dao.isFindBook(isbn , title, Integer.parseInt(code) ,
            				author, publicher, publicationDate)) {
            			
            			//資料があった場合
            			//資料目録を更新する
            			dao.setStockAmount(title , stockAmount);
            			
            		}else {
            			//資料がなかった場合
            			
            			//追加する資料の著者の冊数を得る
            			int volumeNumber = dao.getVolumuNumber(author);
            			volumeNumber++;
            			
            			//資料目録に追加する
            			dao.addBookToCatalog(isbn , title , Integer.parseInt(code) , author ,
                				publicher , publicationDate ,  stockAmount , volumeNumber);
            		}
            		
            		AddResultBean bean = dao.addResult(isbn);
            		
            		request.setAttribute("book" , bean);
            		gotoPage(request , response , "/catalog/catalog_add_complete.jsp");
            		
            	}else {
            		//資料を追加するときに
            		//必要事項が入力されていないとき
            		request.setAttribute("message", "ISBN番号以外は入力必須です");
            		request.setAttribute("isbn", isbn);
            		request.setAttribute("title", title);
        			request.setAttribute("author", author);
        			request.setAttribute("publicationDate", strPublicationDate);
        			request.setAttribute("arrivalDate", strArrivalDate);
            		gotoPage(request , response , "/catalog/catalog_add.jsp");
            	}
            	
            }else if(action.equals("search")) {
            	//actionの値が「search」の場合
            	//資料を検索する
            	String title = request.getParameter("title");
            	String code = request.getParameter("code");
            	String author = request.getParameter("author");
            	String publicher = request.getParameter("publicher");
            	String code2 = request.getParameter("label_code");
            	String authorHead = request.getParameter("label_author");
            	String volumeNumber = request.getParameter("volume_number");
            	
            	//資料名が入力されているとき
            	if(title != null && title.length() != 0) {
            		
            		//入力された資料名が正しいかを調べる
            		boolean flagTitle = isCheckTitle(title);
            		
            		if(flagTitle) {
            			//入力が正しくない場合
            			request.setAttribute("message", "資料名は50文字以内で入力してください");
                		gotoPage(request , response , "/catalog/catalog_search.jsp");
                		return;
            		}
            		
            	}
            	
            	//著者が入力されているとき
            	if(author != null && author.length() != 0) {
            		
            		//入力された著者が正しいかを調べる
            		boolean flagAuthor = isCheckAuthor(author);
            		
            		if(flagAuthor) {
            			//入力が正しくない場合
            			request.setAttribute("message", "著者は50文字以内で入力してください");
                		gotoPage(request , response , "/catalog/catalog_search.jsp");
                		return;
            		}
            		
            	}
            	
            	//出版社が入力されているとき
            	if(publicher != null && publicher.length() != 0) {
            		
            		//入力された出版社が正しいかを調べる
            		boolean flagPublicher = isCheckPublicher(publicher);
            		
            		if(flagPublicher) {
            			//入力が正しくない場合
            			request.setAttribute("message", "出版社は100文字以内で入力してください");
                		gotoPage(request , response , "/catalog/catalog_search.jsp");
                		return;
            		}
            		
            	}
            	
            	//背ラベルが正しく入力されているかを調べる
            	//背ラベルの項目のうち、いずれかを入力したとき
            	if((code2 != null && code2.length() != 0)
            			|| (authorHead != null && authorHead.length() != 0)
            			|| (volumeNumber != null && code2.length() != 0)) {
            		
            		//背ラベルの項目を全て入力しているかを確認
            		if((code2 == null || code2.length() == 0)
                			|| (authorHead == null || authorHead.length() == 0)
                			|| (volumeNumber == null || code2.length() == 0)) {
            			
            			//入力が正しくない場合
            			request.setAttribute("message",
            					"背ラベルで検索する場合は、全て入力してください");
                		gotoPage(request , response , "/catalog/catalog_search.jsp");
                		return;
            		}
            		
            		//分類コードが入力されているとき
                	if(code2 != null && code2.length() != 0) {
                		
                		//かつ背ラベルが入力されているとき
                		if(code != null && code.length() != 0) {
                			
                			if(!(code.equals(code2))) {
                				//分類コードと背ラベルのコードが同じでない場合
                    			request.setAttribute("message",
                    					"「分類コード」と「背ラベルの分類コード」には同じ内容を入力してください");
                        		gotoPage(request , response , "/catalog/catalog_search.jsp");
                        		return;
                        		
                			}
                		}
                	}
                	
                	//入力された背ラベルの２番目が正しいかを調べる
            		boolean flagAuthorHead = isCheckAuthorHead(authorHead);
            		
            		if(flagAuthorHead) {
            			//入力が正しくない場合
            			request.setAttribute("message",
            					"背ラベルは「分類コード - 著者の頭文字(１文字) - 著者の巻冊番号」"
            					+ "で入力してください");
                		gotoPage(request , response , "/catalog/catalog_search.jsp");
                		return;
            		}
            		
            		//入力された背ラベルの３番目が正しいかを調べる
            		boolean flagVolumeNumber = isCheckVolumeNumber(volumeNumber);
            		
            		if(flagVolumeNumber) {
            			//入力が正しくない場合
            			request.setAttribute("message",
            					"背ラベルは「分類コード - 著者の頭文字(１文字) - 著者の巻冊番号」"
            					+ "で入力してください");
                		gotoPage(request , response , "/catalog/catalog_search.jsp");
                		return;
            		}
            	}
            	
            	//検索する
            	CatalogDAO dao = new CatalogDAO();
            	
            	List<SearchResultsBean> list = new ArrayList<SearchResultsBean>();
            	
            	list = dao.findBooks(title , code , author ,
            			publicher , code2 , authorHead , volumeNumber);
            	
            	//検索内容を保持する
            	HttpSession session = request.getSession();
            	
            	session.setAttribute("title", title);
            	session.setAttribute("code", code);
            	session.setAttribute("author", author);
            	session.setAttribute("publicher", publicher);
            	session.setAttribute("labelCode", code2);
            	session.setAttribute("labelAuthor", authorHead);
            	session.setAttribute("volumeNumber", volumeNumber);
            	
            	if(list == null) {
            		//検索結果が存在しなかった場合
            		request.setAttribute("message", "検索結果がありません");
            		gotoPage(request , response , "/catalog/catalog_search.jsp");
            		
            	}else {
            		//検索結果が存在した場合
            		session.setAttribute("books", list);
            		gotoPage(request , response , "/catalog/catalog_search.jsp");
            	}
            	
        	}else if(action.equals("edit_page")) {
            	//actionの値が「editpage」の場合
            	//資料変更画面に遷移する
        		String bookId = request.getParameter("book_id");
        		String isbn = request.getParameter("isbn");
        		String title = request.getParameter("title");
        		String code = request.getParameter("code");
        		String author = request.getParameter("author");
        		String publicher = request.getParameter("publicher");
        		String publicationDate = request.getParameter("publication_date");
        		String arrivalDate = request.getParameter("arrival_date");
        		
        		//資料変更画面に渡す値をbeanに保存
        		SearchResultsBean bean = new SearchResultsBean
        				(Integer.parseInt(bookId) , Long.parseLong(isbn) , title,
        						Integer.parseInt(code) , author , publicher ,
        						publicationDate , arrivalDate);
        		
        		//リクエストスコープで送る
        		request.setAttribute("book", bean);
        		gotoPage(request , response , "/catalog/catalog_edit.jsp");
            	
            }else if(action.equals("update")) {
            	//actionの値が「update」の場合
            	//資料を更新する
            	String bookId = request.getParameter("book_id");
        		String isbn = request.getParameter("isbn");
        		String title = request.getParameter("title");
        		String code = request.getParameter("code");
        		String author = request.getParameter("author");
        		String publicher = request.getParameter("publicher");
        		String strPublicationDate = request.getParameter("publication_date");
        		String strArrivalDate = request.getParameter("arrival_date");
        		
        		if((title != null && title.length() != 0)
        				&&(code != null && code.length() != 0)
        				&&(author != null && author.length() != 0)
            			&& (publicher != null && publicher.length() != 0)
            			&& (strPublicationDate != null && strPublicationDate.length() != 0)
            			&& (strArrivalDate != null && strArrivalDate.length() != 0)) {
        			
        			//資料を変更するときに
            		//必要事項が全て入力されているとき
            		
            		//isbnが入力されているとき
            		if(isbn != null && isbn.length() != 0) {
            			
            			//入力されたISBNが正しい値かを調べる
                		int flagIsbn = checkIsbn(isbn);
                		
                		if(flagIsbn == 2) {
                			//数字以外が入力されたとき
                			request.setAttribute("message", "ISBN番号は数字で入力してください");
                			gotoPage(request , response , "/catalog/catalog_edit.jsp");
                    		return;
                    		
                		}else if(flagIsbn == 1) {
                			//桁数が多すぎるとき
                			request.setAttribute("message", "ISBN番号は13桁以内で入力してください");
                			return;
                		}
            		}
            		
            		//入力された資料名が正しいかを調べる
            		boolean flagTitle = isCheckTitle(title);
            		
            		if(flagTitle) {
            			//入力が正しくない場合
            			request.setAttribute("message", "資料名は50文字以内で入力してください");
                		gotoPage(request , response , "/catalog/catalog_edit.jsp");
                		return;
            		}
            		
            		//入力された著者が正しいかを調べる
            		boolean flagAuthor = isCheckAuthor(author);
            		
            		if(flagAuthor) {
            			//入力が正しくない場合
            			request.setAttribute("message", "著者は50文字以内で入力してください");
                		gotoPage(request , response , "/catalog/catalog_edit.jsp");
                		return;
            		}
            		
            		//入力された出版社が正しいかを調べる
            		boolean flagPublicher = isCheckPublicher(publicher);
            		
            		if(flagPublicher) {
            			//入力が正しくない場合
            			request.setAttribute("message", "出版社は100文字以内で入力してください");
                		gotoPage(request , response , "/catalog/catalog_edit.jsp");
                		return;
            		}
            		
            		//出版日、入荷日の型をstringからsql.dateに変換する
            		Date publicationDate = setDate(strPublicationDate);
            		Date arrivalDate = setDate(strArrivalDate);
            		
            		
            		CatalogDAO dao = new CatalogDAO();
            		
            		//同じ資料があるか資料目録を確認
            		String stockAmount = dao.getStockAmount(Integer.parseInt(bookId));
            		
            		//Stringのstock_amountからbook_idを抽出
            		List<Integer> stockAmounts = new ArrayList<Integer>();
            		stockAmounts = getStockAmount(stockAmount);
            		
            		//idごとに更新
            		for(int id : stockAmounts) {
            			
            			//在庫台帳、資料目録の更新
                		dao.updateCatalog(id , isbn , title ,
                				code , author , publicher , publicationDate , arrivalDate);
            		}
            		
            		//資料変更完了画面に渡す値をbeanに保存
            		SearchResultsBean bean = new SearchResultsBean
            				(Integer.parseInt(bookId) , Long.parseLong(isbn) , title,
            						Integer.parseInt(code) , author ,
            						publicher , strPublicationDate , strArrivalDate);
            		
            		//検索結果のsession開放
            		HttpSession session = request.getSession();
            		session.removeAttribute("books");
            		
            		request.setAttribute("book" , bean);
            		gotoPage(request , response , "/catalog/catalog_edit_complete.jsp");
            		
        		}else {
        			//資料を変更するときに
            		//必要事項が入力されていないとき
            		request.setAttribute("message", "ISBN番号以外は入力必須です");
            		gotoPage(request , response , "/catalog/catalog_edit.jsp");
        		}
            	
            	
            }else if(action.equals("delete_page")) {
            	//actionの値が「deletepage」の場合
            	//資料を削除画面に遷移する
        		String bookId = request.getParameter("book_id");
        		String isbn = request.getParameter("isbn");
        		String title = request.getParameter("title");
        		String code = request.getParameter("code");
        		String author = request.getParameter("author");
        		String publicher = request.getParameter("publicher");
        		String publicationDate = request.getParameter("publication_date");
        		String arrivalDate = request.getParameter("arrival_date");
        		
        		//資料変更画面に渡す値をbeanに保存
        		SearchResultsBean bean = new SearchResultsBean(Integer.parseInt(bookId) ,
        				Long.parseLong(isbn) , title , Integer.parseInt(code) ,
        				author , publicher , publicationDate , arrivalDate);
        		
        		//リクエストスコープで送る
        		request.setAttribute("book", bean);
        		gotoPage(request , response , "/catalog/catalog_delete.jsp");
            	
            }else if(action.equals("delete_confirm")) {
            	
            	//actionの値が「delete_confirm」の場合
            	//資料を削除確認画面に遷移する
        		String bookId = request.getParameter("book_id");
        		String isbn = request.getParameter("isbn");
        		String title = request.getParameter("title");
        		String code = request.getParameter("code");
        		String author = request.getParameter("author");
        		String publicher = request.getParameter("publicher");
        		String publicationDate = request.getParameter("publication_date");
        		String arrivalDate = request.getParameter("arrival_date");
        		String disposalDate = request.getParameter("disposal_date");
        		String memo = request.getParameter("memo");
        		
        		if(disposalDate == null || disposalDate.length() == 0) {
        			//廃棄年月日が入力されていない場合
        			//エラーメッセージ出力
        			
        			//資料変更画面に渡す値をbeanに保存
            		SearchResultsBean bean = new SearchResultsBean(Integer.parseInt(bookId) ,
            				Long.parseLong(isbn) , title , Integer.parseInt(code) ,
            				author , publicher , publicationDate , arrivalDate);
            		
            		//リクエストスコープで送る
            		request.setAttribute("book", bean);
        			request.setAttribute("message", "廃棄年月日を入力してください");
            		gotoPage(request , response , "/catalog/catalog_delete.jsp");
            		return;
        		}
        		
        		//在庫確認(貸出中かどうか)
        		LendDAO lendDao = new LendDAO();
        		
        		StockBean stockBean = lendDao.getStock(Integer.parseInt(bookId));
        		
        		int stock = stockBean.getStock();
        		
        		if(stock == 0) {
        			
        			request.setAttribute("message", "この資料は貸出中です");
        			gotoPage(request , response , "/catalog/catalog_delete_confirm.jsp");
        			return;
        		}
        		
        		// 資料の予約情報を確認
				int reservation = stockBean.getReservation();
				
				if(reservation == 1) {
        			
        			request.setAttribute("message", "この資料は予約されています");
        			gotoPage(request , response , "/catalog/catalog_delete_confirm.jsp");
        			return;
        		}
        		
        		//資料変更画面に渡す値をbeanに保存
        		SearchResultsBean bean = new SearchResultsBean(Integer.parseInt(bookId) ,
        				Long.parseLong(isbn) , title , Integer.parseInt(code) ,
        				author , publicher , publicationDate , arrivalDate);
        		
        		//リクエストスコープで送る
        		request.setAttribute("book", bean);
        		request.setAttribute("disposalDate", disposalDate);
        		request.setAttribute("memo", memo);
        		gotoPage(request , response , "/catalog/catalog_delete_confirm.jsp");
        		
            }else if(action.equals("delete")) {
            	//actionの値が「delete」の場合
            	//資料を削除する
            	String bookId = request.getParameter("book_id");
            	String isbn = request.getParameter("isbn");
        		String title = request.getParameter("title");
        		String code = request.getParameter("code");
        		String author = request.getParameter("author");
        		String publicher = request.getParameter("publicher");
        		String publicationDate = request.getParameter("publication_date");
        		String arrivalDate = request.getParameter("arrival_date");
        		String strDisposalDate = request.getParameter("disposal_date");
        		String memo = request.getParameter("memo");
        		
        		
        		//廃棄日の型をstringからsql.dateに変換する
        		Date disposalDate = setDate(strDisposalDate);
        		
        		//削除処理を行う
        		CatalogDAO dao = new CatalogDAO();
        		
        		dao.deleteCatalog(Integer.parseInt(bookId) , disposalDate , memo);
        		
        		//結果出力用
        		EditResultBean bean = new EditResultBean
        				(bookId , isbn , title , code , author , publicher , publicationDate ,
        						arrivalDate , strDisposalDate , memo);
        		
        		//検索結果のsession開放
        		HttpSession session = request.getSession();
        		session.removeAttribute("books");
        		
        		request.setAttribute("book", bean);
        		gotoPage(request , response , "/catalog/catalog_delete_complete.jsp");
            }
        } catch (DAOException e) {
        	
        	//DAOのデータベース処理が失敗（エラー）となった場合
            e.printStackTrace();
            request.setAttribute("message", "内部エラーが発生しました。");
            gotoPage(request, response, "/errInternal.jsp");
        }
    }

    //JSPのフォワードを何度も書かなくて良いようにメソッドにして使用する
    private void gotoPage(HttpServletRequest request,
            HttpServletResponse response, String page) throws ServletException,
            IOException {
    	
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }
    
    //有効なISBNが入力されたのかを確認するメソッド
    public int checkIsbn(String strIsbn) {
    	
    	int flag = 0;
    	
    	if(strIsbn.length() > 13) {
    		
    		flag = 1;
    		return flag;
    	}
    	
    	if(strIsbn.length() > 7) {
    		
    		String strIsbn1 = "";
        	String strIsbn2 = "";
        	
    		strIsbn1 = strIsbn.substring(0 , 7);
    		strIsbn2 = strIsbn.substring(7);
    		
    		try {
        		@SuppressWarnings("unused")
    			int isbn1 = Integer.parseInt(strIsbn1);
        		@SuppressWarnings("unused")
				int isbn2 = Integer.parseInt(strIsbn2);
        		
        	}catch(NumberFormatException e) {
        		
        		flag = 2;
        		return flag;
        		
        	}
    	}else {
    		
    		try {
        		@SuppressWarnings("unused")
    			int isbn = Integer.parseInt(strIsbn);
        		
        	}catch(NumberFormatException e) {
        		
        		flag = 2;
        		return flag;
        	}
    	}
    	
    	return flag;
    }
    
    //有効な資料名が入力されたのかを確認するメソッド
    public boolean isCheckTitle(String title) {
    	
    	boolean flag = false;
    	
    	if(title.length() > 50) {
    		
    		flag = true;
    	}
    	
    	return flag;
    }
    
    //有効な著者が入力されたのかを確認するメソッド
    public boolean isCheckAuthor(String author) {
    	
    	boolean flag = false;
    	
    	if(author.length() > 50) {
    		
    		flag = true;
    	}
    	
    	return flag;
    }
    
    //有効な出版社が入力されたのかを確認するメソッド
    public boolean isCheckPublicher(String publicher) {
    	
    	boolean flag = false;
    	
    	if(publicher.length() > 100) {
    		
    		flag = true;
    	}
    	
    	return flag;
    }
    
    //有効な背ラベルの２番目が入力されたのかを確認するメソッド
    public boolean isCheckAuthorHead(String authorHead) {
    	
    	boolean flag = false;
    	
    	if(authorHead.length() > 1) {
    		
    		flag = true;
    	}
    	
    	return flag;
    }
    
    //有効な背ラベルの３番目が入力されたのかを確認するメソッド
    public boolean isCheckVolumeNumber(String volumeNumber) {
    	
    	boolean flag = false;
    	
    	if(volumeNumber.length() > 1) {
    		
    		flag = true;
    	}
    	
    	return flag;
    }

    //Stringの日付をsql.dateに変換するメソッド
    public Date setDate(String strDate) {
    	
    	Date date = Date.valueOf(strDate);
    	
    	return date;
    }
    
    //catalogのstock_amountを分割するメソッド
  	public List<Integer> getStockAmount(String stockAmount) {
  		
  		//カンマでスプリット
  		List<String> splitStockAmounts = Arrays.asList(stockAmount.split(","));
  		
  		//id格納用リスト
  		List<Integer> stockAmounts = new ArrayList<Integer>();
  		
  		//数字のみを抽出
  		for(int i = 1; i < splitStockAmounts.size(); i += 2) {
  			
  			stockAmounts.add(Integer.parseInt(splitStockAmounts.get(i)));
  		}
  		
  		return stockAmounts;
  	}
    
    //HTTPリクエストのPOSTリクエストが送信された場合実行される
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}